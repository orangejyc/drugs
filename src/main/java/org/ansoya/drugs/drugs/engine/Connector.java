package org.ansoya.drugs.drugs.engine;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
public class Connector {
    private String clientHost;
    private int clienrPort;
    private Socket socket;

    private boolean needInit;

    public Connector(Socket socket) {
        this.clientHost = socket.getInetAddress().toString().replace("/", "");
        this.clienrPort = socket.getPort();
        this.socket = socket;
    }

    public Connector(String clientHost, int clienrPort) {
        this.clientHost = clientHost;
        this.clienrPort = clienrPort;
    }

    public synchronized boolean isConnected() {
        return socket != null && (socket.isConnected() && !socket.isClosed());
    }

    public synchronized void init() {
        try {
            socket = new Socket(clientHost, clienrPort);
        } catch (UnknownHostException e) {
            log.error(clientHost + clienrPort + e.getMessage(), e);
        } catch (IOException e) {
            log.error(clientHost + clienrPort + e.getMessage(), e);
        }
    }

    public synchronized void send(byte[] bytes) {
        try {
            Preconditions.checkArgument(null != bytes);
            if (!isConnected() && needInit) {
                init();
            }
            socket.getOutputStream().write(bytes);
        } catch (Throwable t) {
            log.error(clientHost + clienrPort + t.getMessage(), t);
            throw new RuntimeException(t);
        }
    }

}
