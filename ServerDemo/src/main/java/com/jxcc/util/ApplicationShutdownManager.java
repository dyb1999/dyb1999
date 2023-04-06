package com.jxcc.util;

/**
 * @Author dingyb
 * @Date 2023/4/3
 * @Description
 */
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationShutdownManager {
    @Autowired
    private ApplicationContext appContext;

    /**
     * Invoke with `0` to indicate no error or different code to indicate
     * abnormal exit. es: shutdownManager.initiateShutdown(0);
     **/
    public void initiateShutdown(int returnCode){
        SpringApplication.exit(appContext, () -> returnCode);
    }
//        implements ApplicationContextAware {

//    private ConfigurableApplicationContext context;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        context = (ConfigurableApplicationContext) applicationContext;
//    }
//
//    public void exit() {
//        context.close();
//    }

}
