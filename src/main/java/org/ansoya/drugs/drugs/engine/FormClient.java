package org.ansoya.drugs.drugs.engine;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;


@Slf4j
public class FormClient {


    private String clientHost;
    private int clienrPort;
    private String uid;
    private Connector connector;


    public int getClienrPort() {
        return clienrPort;
    }

    public void setClienrPort(int clienrPort) {
        this.clienrPort = clienrPort;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public FormClient(String uid, Socket socket) {
        this.uid = uid;
        this.clientHost = socket.getInetAddress().toString().replace("/", "");
        this.clienrPort = socket.getPort();
        this.connector = new Connector(socket);
    }

    public FormClient(String uid, String clientHost, int clienrPort) {
        this.setUid(uid);
        this.setClientHost(clientHost);
        this.clienrPort = clienrPort;
        this.connector = new Connector(clientHost, clienrPort);
    }


    public void handle(String mesage) {
        try {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(mesage));
            connector.send(mesage.getBytes(Constans.CHARACTER_ENCODING));
            log.info("发送消息" + clientHost + clienrPort + mesage);
        } catch (Throwable e) {
            log.error(uid + clientHost + clienrPort + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }


}
