package org.ansoya.drugs.drugs.entity;

import java.io.Serializable;

public class RequestCmd implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6877742628031682129L;

    private String action;

    private String uname;

    private String pwd;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


}
