package org.lynxz.webhook.bean;

/**
 * Created by lynxz on 07/03/2017.
 */
public class AccessTokenBean {

    /**
     * errcode : 0
     * errmsg : ok
     * accessToken : fw8ef8we8f76e6f7s8df8s
     */

    private int errcode;
    private String errmsg;
    private String access_token;

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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
