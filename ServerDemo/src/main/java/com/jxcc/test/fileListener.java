package com.jxcc.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.function.Predicate;

/**
 * @Author dingyb
 * @Date 2023/2/13
 * @Description
 */
@Component
@Slf4j
public class fileListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("=================this is a listener");

        }
    }
}
