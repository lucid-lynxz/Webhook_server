package org.lynxz.webhook.constants;

import org.lynxz.webhook.bean.DepartmentMemberListBean;

import java.util.List;

/**
 * Created by Lynxz on 2016/7/28 .
 * 程序所用参数,可从文件配置
 */
public class Params {
    public static String versionName = "V0";//版本名称
    public static int versionCode = -1;//版本号

    private static String dingTalkServerUrl = "https://oapi.dingtalk.com/";
    public static String jiraBrowseUrl = "http://www.jianshu.com/u/302253a7ed00";//jira查看issue详情网址前缀
    public static String dd_corp_id = "";
    public static String dd_corp_secret = "";

    public static String accessToken = "";
    public static int departmentId = 1; // 部门id,1表示根部门
    public static String agentId = ""; // 应用id
    public static String defaultAssignee = "lynxz";//默认通知人

    public static List<DepartmentMemberListBean.UserlistBean> userlist;//指定部门内的用户,由于自建公司部门,因此为部门1的成员信息

    public static String getDingTalkServerUrl() {
        return dingTalkServerUrl;
    }

}
