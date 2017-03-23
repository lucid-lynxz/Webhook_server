package org.lynxz.webhook.constants;

public interface KeyNames {
    String corpid = "corpid";
    String corpsecret = "corpsecret";

    String versionCode = "versionCode";
    String versionName = "versionName";
    String agentId = "agentId";
    String defaultAssignee = "defaultAssignee";

    String jiraUrl = "jira_borwse_url";
    // gitlab 的 hook 请求会带有本header信息
    String HEADER_GITLAB = "X-Gitlab-Event";
    String HEADER_GITLAB_MERGE_HOOK = "Merge Request Hook";
    // gira 的 hook请求的 user-agent head中带有 jira 字样
    String HEADER_GIRA = "JIRA";
    String HEADER_USER_AGENT = "user-agent";


}
