package org.ansoya.drugs.engine;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Worker extends Thread {

    public Worker(byte[] message, Connector connector) {
        this.message = message;
        this.connector = connector;
    }

    private byte[] message;

    private Connector connector;

    @Override
    public void run() {
        try {
            Preconditions.checkArgument(null != message && null != connector);
            if (!connector.isConnected()) {
                connector.init();
                connector.send(message);
            }
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }

}
