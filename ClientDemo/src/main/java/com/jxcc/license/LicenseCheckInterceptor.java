package com.jxcc.license;

import com.alibaba.fastjson.JSON;
import com.jxcc.annotation.VLicense;
import com.jxcc.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * LicenseCheckInterceptor
 *
 * @author dingyb
 * @date 2023/2/25
 * @since 1.0.0
 */
@Slf4j
public class LicenseCheckInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            VLicense annotation = method.getAnnotation(VLicense.class);
            if (CommonUtils.isNotEmpty(annotation)) {
                LicenseVerify licenseVerify = new LicenseVerify();

                //校验证书是否有效
                boolean verifyResult = licenseVerify.verify();

                if(verifyResult){
                    return true;
                }else{
                    response.setCharacterEncoding("utf-8");
                    Map<String,String> result = new HashMap<>(1);
                    result.put("result","您的证书无效，请核查服务器是否取得授权或重新申请证书！");

                    response.getWriter().write(JSON.toJSONString(result));

                    return false;
                }
            }
        }
        return true;
    }

}
