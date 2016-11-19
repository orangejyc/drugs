package org.ansoya.drugs.entity;

/**
 * 扫描消息
 *
 * @author ansoya
 */
public class ScanMessage {
    private String clientHost;
    private String uid;
    private String message;

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
