package com.jxcc.config;

import com.jxcc.license.LicenseCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author dingyb
 * @Date 2023/3/30
 * @Description
 */
@Configuration
public class LicenseInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LicenseCheckInterceptor getLicenseCheckInterceptor() {
        return new LicenseCheckInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getLicenseCheckInterceptor()).addPathPatterns("/**");
    }
}
