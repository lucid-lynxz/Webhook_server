package org.lynxz.webhook.utils;

import com.google.gson.Gson;
import okhttp3.*;
import org.apache.http.util.TextUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.lynxz.webhook.bean.*;
import org.lynxz.webhook.constants.MessageType;
import org.lynxz.webhook.constants.Params;

import java.io.IOException;
import java.util.List;

/**
 * @author created by zxz
 * @date 2016-07-25
 * <a href="https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7386797.0.0.MzKmQ9&treeId=172&articleId=104979&docType=1">钉钉文档</a>
 * POST请求请在HTTP Header中设置 Content-Type:application/json，否则接口调用失败
 */
public class HttpUtil {
    private Logger mLogger = LogManager.getLogger(HttpUtil.class);
    //    private static OkHttpClient httpClient = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public Gson gson = new Gson();

    private Interceptor sInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .addHeader("contentType ", "application/json")
                    .addHeader("Content-Type ", "application/json")
                    .build();
            return chain.proceed(request);
        }
    };

    public OkHttpClient getOkHttp() {
        return new OkHttpClient.Builder()
                .addInterceptor(sInterceptor)
                // .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public String get(String actionName, String... contents) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(getFullUrl(actionName, contents)).build();
        Response response;
        String result = "";
        try {
            response = getOkHttp().newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            mLogger.error("get Exception action: " + actionName + " , detail: " + e.getMessage());
        }
        return result;
    }


    /**
     * @param actionName 访问网址
     * @param tClass     返回值实体类
     * @param contents   查询参数列表, 按顺序:key1,value1,key2,value2...
     */
    public <T> T get(String actionName, Class<T> tClass, String... contents) {
        T ret = null;
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(getFullUrl(actionName, contents)).build();
        Response response;
        try {
            response = getOkHttp().newCall(request).execute();
            String result = response.body().string();
            ret = gson.fromJson(result, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            mLogger.error("get Exception action: " + actionName + " , detail: " + e.getMessage());
        }
        //        mLogger.info("get ........ " + result);
        return ret;
    }

    private <T> T postNormal(String actionName, Class<T> tClass, Object linkBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(linkBean));
        Request request = new Request.Builder().url(getFullUrl(actionName)).post(body).build();
        T ret = null;
        String result = null;
        try {
            Response response = getOkHttp().newCall(request).execute();
            result = response.body().string();
            if (!TextUtils.isEmpty(result)) {
                //                if (result.contains("error_code")) {
                //                    mLogger.error("postForm result error ,actionName = " + actionName + " ,detail : " + result);
                //                } else {
                ret = gson.fromJson(result, tClass);
                //                }
            }
            System.out.println("post成功 result = " + result);
        } catch (Exception e) {
            mLogger.error("postForm IOException1 action: " + actionName + " ," + tClass.getName() + " ,detail: " + e.getMessage() + ",resultStr = " + result);
            e.printStackTrace();
        }
        return ret;
    }
//
//    /**
//     * 以表单的方式提交
//     */
//    public <T> T postForm(String actionName, Class<T> tClass, Map<String, String> map) {
//        FormBody.Builder builder = new FormBody.Builder();
//
//        if (map != null) {
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                builder.add(entry.getKey(), entry.getValue());
//            }
//        }
//        RequestBody body = builder.build();
//
//
//        Request request = new Request.Builder().url(getBaseUrl(actionName)).post(body).build();
//        T ret = null;
//        String result = null;
//        try {
//            Response response = getOkHttp().newCall(request).execute();
//            result = response.body().string();
//            if (!TextUtils.isEmpty(result)) {
//                //                if (result.contains("error_code")) {
//                //                    mLogger.error("postForm result error ,actionName = " + actionName + " ,detail : " + result);
//                //                } else {
//                ret = gson.fromJson(result, tClass);
//                //                }
//            }
//            System.out.println("post成功 result = " + result);
//        } catch (Exception e) {
//            mLogger.error("postForm IOException1 action: " + actionName + " ," + tClass.getName() + " ,detail: " + e.getMessage() + ",resultStr = " + result);
//            e.printStackTrace();
//        }
//        return ret;
//    }


    private String getBaseUrl(String actionName) {
        return Params.getDingTalkServerUrl() + actionName;
    }

    /**
     * 生成get方法完整url地址:  https://www.okcoin.com/api/v1/future_index.do?symbol=btc_usd
     *
     * @param actionName 请求动作名称:future_index.do
     * @param contents   查询参数:  symbol,btc_usd
     */
    private String getFullUrl(String actionName, String... contents) {
        if (TextUtils.isEmpty(Params.accessToken)) {
            System.out.println("access_token非法 " + Params.accessToken);
        }
        // 这个是钉钉要加的,都统一加上吧,暂时没发现问题,就不做特殊处理了
        String result = "?access_token=" + Params.accessToken + "&";
        if (contents != null && contents.length > 0 && contents.length % 2 == 0) {
            for (int i = 0; i < contents.length - 1; i += 2) {
                result += contents[i] + "=" + contents[i + 1] + "&";
            }
        }
        if (!TextUtils.isEmpty(result)) {
            result = result.substring(0, result.length() - 1);
        }
        return Params.getDingTalkServerUrl() + actionName + result;
    }

    /**
     * 获取access_token
     */
    public void refreshAccessToken() {
        AccessTokenBean token = get("gettoken", AccessTokenBean.class, "corpid", Params.dd_corp_id, "corpsecret", Params.dd_corp_secret);
        String accessToken = token.getAccess_token();
        if (TextUtils.isEmpty(accessToken)) {
            mLogger.error("获取access_token失败: " + token.getErrcode() + ":" + token.getErrmsg());
        } else {
            Params.accessToken = accessToken;
            System.out.println("获取token成功: " + Params.accessToken);
            getDepartmentList();
        }
    }

    /**
     * 获取部门列表信息
     */
    private void getDepartmentList() {
        DepartmentListBean list = get("department/list", DepartmentListBean.class);
        List<DepartmentListBean.DepartmentBean> department = list.getDepartment();
        if (department != null) {
            int size = department.size();
            System.out.println("getDepartmentList成功,size =  " + size);
            if (size > 0) {
                Params.departmentId = department.get(0).getId();
                System.out.println("getDepartmentList成功: " + Params.departmentId);
                getDepartmentMenberList();
            }
        }
    }

    /**
     * 获取指定部门id下的成员信息
     */
    public void getDepartmentMenberList() {
        DepartmentMemberListBean departSimpleList = get("user/simplelist", DepartmentMemberListBean.class, "department_id", Params.departmentId + "");
        if (departSimpleList != null) {
            Params.userlist = departSimpleList.getUserlist();
        }
    }

    /**
     * 发送消息给指定的人
     */
    public void sendLinkMsg(String toUserName, MessageLinkBean linkBean) {
        if (Params.userlist != null) {
            Params.userlist.stream()
                    .filter(bean -> bean.getName().equalsIgnoreCase(toUserName))
                    .forEach(bean -> {
                        String toUserId = bean.getUserid();
                        linkBean.setTouser(toUserId);
                        postNormal("message/send", MessageResponseBean.class, linkBean);
                    });
        } else {
            getDepartmentMenberList();
        }
    }

    /**
     * 发送简单的文本通知给部门所有人
     * 目前用于merge请求通过的时候通知所有人员的时候
     *
     * @param toSpecialUser 是否只是发给单个用户,若非empty,则发给指定用户,否则发给部门所有人
     * @param content       要发送的文本内容
     */
    public void sendTextMsg(@Nullable String toSpecialUser, String content) {
        if (Params.userlist != null) {
            Params.userlist.stream()
                    .filter(bean -> TextUtils.isEmpty(toSpecialUser) || bean.getName().equalsIgnoreCase(toSpecialUser))
                    .forEach(bean -> {
                        String userId = bean.getUserid();

                        MessageTextBean textBean = new MessageTextBean();
                        textBean.setTouser(userId);
                        textBean.setAgentid(Params.agentId);
                        textBean.setMsgtype(MessageType.TEXT);

                        MessageTextBean.TextBean subTextBean = new MessageTextBean.TextBean();
                        subTextBean.setContent(content);

                        textBean.setText(subTextBean);
                        postNormal("message/send", MessageResponseBean.class, textBean);
                    });
        } else {
            getDepartmentMenberList();
        }
    }
}