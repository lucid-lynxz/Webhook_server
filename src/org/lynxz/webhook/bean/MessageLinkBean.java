package org.lynxz.webhook.bean;

/**
 * Created by lynxz on 21/03/2017.
 * 钉钉发送link类型消息实体
 */
public class MessageLinkBean {

    /**
     * touser : manager8104
     * otherUser : manager8104,022420076726300948
     * agentid : 80289011
     * msgtype : link
     * link : {"messageUrl":"http://s.dingtalk.com/market/dingtalk/error_code.php","picUrl":"http://pic.qiniu.anfensi.com/2016-08-23/57bba51b49715.jpg","title":"测1试","text":"测试-我是服务端发过来的link消息22"}
     */
    private String touser;
    private String otherUser;
    private String agentid;
    private String msgtype;
    private LinkBean link;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public LinkBean getLink() {
        return link;
    }

    public void setLink(LinkBean link) {
        this.link = link;
    }

    public static class LinkBean {
        /**
         * messageUrl : http://s.dingtalk.com/market/dingtalk/error_code.php
         * picUrl : http://pic.qiniu.anfensi.com/2016-08-23/57bba51b49715.jpg
         * title : 测1试
         * text : 测试-我是服务端发过来的link消息22
         */

        private String messageUrl;
        private String picUrl;
        private String title;
        private String text;

        public String getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(String messageUrl) {
            this.messageUrl = messageUrl;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
