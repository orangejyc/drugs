package org.ansoya.drugs.drugs;

import org.ansoya.drugs.drugs.configuration.WxPayConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({WxPayConfiguration.class})
public class DrugsApplication {
    private static final Logger log = LoggerFactory.getLogger(DrugsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DrugsApplication.class, args);
        /*
        String[] beanNames = ctx.getBeanDefinitionNames();
        for (String bn : beanNames) {
            System.out.println(bn);
        }*/
    }
}
