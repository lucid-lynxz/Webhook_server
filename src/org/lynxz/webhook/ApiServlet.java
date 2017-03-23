package org.lynxz.webhook;

import com.google.gson.Gson;
import org.apache.http.util.TextUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lynxz.webhook.bean.GbMergeRequestEventBean;
import org.lynxz.webhook.bean.JiraBugEventBean;
import org.lynxz.webhook.constants.Actions;
import org.lynxz.webhook.constants.KeyNames;
import org.lynxz.webhook.constants.Params;
import org.lynxz.webhook.utils.HttpUtil;
import org.lynxz.webhook.utils.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by lynxz on 07/03/2017.
 * Gitlab的hook请求会带有header X-Gitlab-Event:Merge Request Hook
 * Jira的hook请求会带有header  user-agent=Atlassian HttpClient0.17.3 / JIRA-6.3.15 (6346) / Default
 * <p>
 * 注意: 钉钉规定:因发送消息过于频繁或超量而被流控过滤后实际未发送的userid。未被限流的接收者仍会被成功发送。限流规则包括：1、给同一用户发相同内容消息一天仅允许一次；2、如果是ISV接入方式，给同一用户发消息一天不得超过100次；如果是企业接入方式，此上限为500。
 * 因此,每个通知中都添加了当前时间
 */
public class ApiServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger();
    private HttpUtil httpUtil = new HttpUtil();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initPara();
        initRefreshTokenTimer();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        getHeadersInfo(req);
        routeByHeader(req);
        String retJson = "hello i'm running... server time: " + TimeUtil.msec2date(System.currentTimeMillis()) + " ,version " + Params.versionName;
        PrintWriter writer = resp.getWriter();
        writer.write(retJson);
        writer.flush();
        writer.close();
    }

    /**
     * 处理gitlab的merge请求通知
     */
    private boolean processMergeRequest(HttpServletRequest req) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        GbMergeRequestEventBean bean = convertBody(inputStream, GbMergeRequestEventBean.class);
        if (bean != null) {
            GbMergeRequestEventBean.ObjectAttributesBean objectAttributes = bean.getObject_attributes();
            String action = objectAttributes.getAction();
            System.out.println("merge " + action + " , url = " + objectAttributes.getUrl());
            GbMergeRequestEventBean.UserBean user = bean.getUser();
            GbMergeRequestEventBean.AssigneeBean assignee = bean.getAssignee();
            StringBuilder sb = new StringBuilder();

            switch (action) {
                case Actions.OPEN:
                case Actions.REOPEN:
                    // 有新的merge请求时,通知审核人员审核
//                    titleText = user.getName() + "[ " + user.getUsername() + " ]请求合并代码到 " + objectAttributes.getTarget_branch() + " 分支, 说明: " + objectAttributes.getTitle();
//                    MessageLinkBean.LinkBean link = new MessageLinkBean.LinkBean();
//                    link.setMessageUrl(objectAttributes.getUrl());
//                    link.setPicUrl(user.getAvatar_url());
//                    link.setTitle("merge代码请求: " + action);
//                    link.setText(titleText + "\n时间: " + TimeUtil.msec2date(System.currentTimeMillis()));
//
//                    MessageLinkBean msg = new MessageLinkBean();
//                    msg.setAgentid(Params.agentId);
//                    msg.setMsgtype(MessageType.LINK);
//                    msg.setLink(link);
//                    httpUtil.sendLinkMsg(assignee.getName(), msg);

                    sb.append("Gitlab: 有新merge请求: ").append(action).append("\n")
                            .append("项目:\t").append(user.getUsername()).append("\n")
                            .append("用户:\t").append(objectAttributes.getTarget_branch()).append("\n")
                            .append("目标分支:\t").append(objectAttributes.getTarget_branch()).append("\n")
                            .append("请求概要:\t").append(objectAttributes.getTitle()).append("\n")
                            .append("服务器时间:\t").append(TimeUtil.msec2date(System.currentTimeMillis())).append("\n")
                            .append("审核地址:\t").append(objectAttributes.getUrl());
                    httpUtil.sendTextMsg(assignee.getName(), sb.toString());
                    break;

                case Actions.CLOSE:
                    // request请求被关闭的时候,通知提交请求的人
                    sb.append("Gitlab: 您的merge请求被关闭").append("\n")
                            .append("项目: ").append(bean.getProject().getName()).append("\n")
                            .append("目标分支: ").append(objectAttributes.getTarget_branch()).append("\n")
                            .append("概要: ").append(objectAttributes.getTitle()).append("\n")
                            .append("服务器时间: ").append(TimeUtil.msec2date(System.currentTimeMillis()));
                    httpUtil.sendTextMsg(user.getName(), sb.toString());
                    break;

                case Actions.MERGE:
                    // 当merge请求被通过的时候,通知相关所有人更新代码
                    sb.append("Gitlab: 有merge请求被通过,请更新代码").append("\n")
                            .append("项目: ").append(bean.getProject().getName()).append("\n")
                            .append("分支: ").append(objectAttributes.getTarget_branch()).append("\n")
                            .append("概要: ").append(objectAttributes.getTitle()).append("\n")
                            .append("服务器时间: ").append(TimeUtil.msec2date(System.currentTimeMillis()));
                    httpUtil.sendTextMsg(null, sb.toString());
                    break;
            }
        }
        return false;
    }

    /**
     * 根据请求的header信息来区分不同的请求
     */
    private void routeByHeader(HttpServletRequest request) throws IOException {
        String userAgent = request.getHeader(KeyNames.HEADER_USER_AGENT);
        System.out.println("userAgent == " + userAgent);
        if (userAgent != null && userAgent.contains(KeyNames.HEADER_GIRA)) {
            processGiraHook(request);
        } else {
            String gitlabHeader = request.getHeader(KeyNames.HEADER_GITLAB);
            System.out.println("gitlabHeader == " + gitlabHeader);
            if (!TextUtils.isEmpty(gitlabHeader) && KeyNames.HEADER_GITLAB_MERGE_HOOK.equalsIgnoreCase(gitlabHeader)) {
                processMergeRequest(request);
            }
        }
    }

    /**
     * 处理gira 的 hook 请求
     */
    private void processGiraHook(HttpServletRequest req) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        JiraBugEventBean bean = convertBody(inputStream, JiraBugEventBean.class);
        if (bean != null) {
            String event = bean.getWebhookEvent();
            JiraBugEventBean.IssueBean issue = bean.getIssue();
            JiraBugEventBean.IssueBean.FieldsBean fields = issue.getFields();

            List<String> affectLabels = fields.getLabels();//测试版本号
            String type = fields.getIssuetype().getName();//issue类型,如 Bug
            String projectName = fields.getProject().getKey();

            String creatorName = fields.getCreator().getDisplayName();
            String summary = fields.getSummary();// bug标题
            String keyId = issue.getKey();// bug编号,如 UPLUSGO-1241
            String url = Params.jiraBrowseUrl + keyId;//issue详情访问网址
            String assigneeName = fields.getAssignee().getDisplayName();//bug归属人

            StringBuilder sb = new StringBuilder();
            sb.append(event).append("\n")
                    .append("类型: ").append(type).append("\n")
                    .append("版本: ").append(affectLabels).append("\n")
                    .append("项目: ").append(projectName).append("\n")
                    .append("创建: ").append(creatorName).append("\n")
                    .append("概要: ").append(summary).append("\n")
                    .append("查看: ").append(url).append("\n")
                    .append("服务器时间: ").append(TimeUtil.msec2date(System.currentTimeMillis()));

            httpUtil.sendTextMsg(assigneeName, sb.toString());
        }
    }

    /**
     * 获取header信息
     */
    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        System.out.println(map);
        return map;
    }

    /**
     * 获取客户端get/post方法上传来的参数信息,并转换为
     * <code>map<String,String></code>形式
     */
    private HashMap<String, String> getReqParaMap(ServletInputStream is) throws IOException {
        byte[] buf = new byte[1024];
        int len;
        StringBuffer sb = new StringBuffer();
        while ((len = is.read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }

        String paraStr = sb.toString();
        HashMap<String, String> paraMap = new HashMap<>();
        if (paraStr.length() > 0) {
            String[] split = paraStr.split("&");
            for (String aSplit : split) {
                String[] entry = aSplit.split("=");
                paraMap.put(entry[0], entry[1]);
            }
        }
        return paraMap;
    }

    /**
     * 将请求的body部分转换成指定的实体类
     */
    <T> T convertBody(ServletInputStream is, Class<T> clz) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            String bodyStr = sb.toString();
            System.out.println("request body 是:  " + bodyStr);
            return new Gson().fromJson(bodyStr, clz);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private void initPara() {
        Properties configProp = loadConfig("WEB-INF" + File.separator + "config.properties");
        if (configProp == null) {
            return;
        }

        Params.dd_corp_id = configProp.getProperty(KeyNames.corpid);
        Params.dd_corp_secret = configProp.getProperty(KeyNames.corpsecret);

        Params.versionCode = Integer.parseInt(configProp.getProperty(KeyNames.versionCode));
        Params.versionName = configProp.getProperty(KeyNames.versionName);
        Params.agentId = configProp.getProperty(KeyNames.agentId);
        Params.defaultAssignee = configProp.getProperty(KeyNames.defaultAssignee);
        Params.jiraBrowseUrl = configProp.getProperty(KeyNames.jiraUrl);
    }

    /**
     * 加载指定根目录下指定路径的属性文件
     *
     * @param configPath 文件路径,如 "WEB-INF/custom.properties"
     * @return 属性对象 Properties
     */
    private Properties loadConfig(String configPath) {
        Properties prop = null;
        // 获取servlet上下文的绝对路径，如：C:Program FilesApacheTomcat 6.0webappsfee
        String path = getServletContext().getRealPath("") + File.separator + configPath;

        logger.info("loadConfig path = " + path);
        // 把文件读入文件输入流，存入内存中
        FileInputStream fis = null;
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("FileNotFoundException " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //加载文件流的属性
        return prop;
    }

    /**
     * 每个小时去刷新一次token
     */
    private void initRefreshTokenTimer() {
        Timer refreshTimer = new Timer();
        refreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                httpUtil.refreshAccessToken();
            }
        }, 0, 60 * 60 * 1000);
    }
}