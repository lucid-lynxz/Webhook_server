package org.lynxz.webhook.constants;

/**
 * Created by lynxz on 21/03/2017.
 * merge等hook通知的action字段值
 */
public interface Actions {
    String OPEN = "open";//提交合并请求
    String MERGE = "merge";//审核人同意合并
    String CLOSE = "close";// 合并申请被关闭
    String REOPEN = "reopen";//重开已关闭的merge request
}
