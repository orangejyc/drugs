package org.ansoya.drugs.entity;

import java.io.Serializable;

public class RequestCmdParameter implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7113009735622130864L;

    private String Key;
    private String Value;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

}
