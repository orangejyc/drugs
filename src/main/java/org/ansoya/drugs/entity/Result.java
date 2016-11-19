package org.ansoya.drugs.entity;

import com.alibaba.fastjson.JSON;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Map;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = -3887725517645851694L;
    private T data;
    private String statusText;
    private Map<String, String> extData;
    private boolean sucess;

    Result() {
    }

    public T getData() {
        return this.data;
    }

    void setData(T data) {
        this.data = data;
    }

    void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public boolean isSuccess() {
        return sucess;
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public String getStatusText() {
        return this.statusText;
    }

    void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Map<String, String> getExtData() {
        return this.extData;
    }

    void setExtData(Map<String, String> extData) {
        this.extData = extData;
    }

    public String toString() {
        MoreObjects.ToStringHelper toString = MoreObjects.toStringHelper(this);

        return toString.add("data", this.data).add("statusText", this.statusText).add("extData", this.extData)
                .omitNullValues().toString();
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
