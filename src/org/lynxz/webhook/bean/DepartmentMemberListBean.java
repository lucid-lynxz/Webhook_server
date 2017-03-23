package org.lynxz.webhook.bean;

import java.util.List;

/**
 * Created by lynxz on 21/03/2017.
 * 钉钉指定部门内的用户列表,包含姓名和userId
 */
public class DepartmentMemberListBean {

    /**
     * errcode : 0
     * errmsg : ok
     * hasMore : false //在分页查询时返回，代表是否还有下一页更多数据
     * userlist : [{"userid":"zhangsan","name":"张三"}]
     */

    private int errcode;
    private String errmsg;
    private boolean hasMore;
    private List<UserlistBean> userlist;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<UserlistBean> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<UserlistBean> userlist) {
        this.userlist = userlist;
    }

    public static class UserlistBean {
        /**
         * userid : zhangsan
         * name : 张三
         */

        private String userid;
        private String name;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
