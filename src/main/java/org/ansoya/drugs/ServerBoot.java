package org.ansoya.drugs;

import lombok.extern.slf4j.Slf4j;
import org.ansoya.drugs.dal.UserDao;
import org.ansoya.drugs.engine.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2016/11/6.
 */
@Component
@Slf4j
public class ServerBoot {

    @Value("${inner.listener.port}")
    private Integer innerListenerPort;
    @Autowired
    private UserDao userDao;

    @PostConstruct
    private void startListener() {
        try {
            Server server = new Server(userDao, innerListenerPort);
            server.start();
            log.info("start inner linstener successful...");
        } catch (Throwable throwable) {
            log.error("start inner linstener err...");
        }
    }
}
