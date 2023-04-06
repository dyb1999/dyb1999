//package com.jxcc.config;
//
//import com.jxcc.license.*;
//import com.jxcc.util.MD5Util;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author dingyb
// * @Date 2023/2/13
// * @Description 证书生成:脚本+获取服务器信息+生成证书文件
// */
//@Configuration
//@Slf4j
//@Order(1)
//public class LicenseConfigListener implements CommandLineRunner {
//    /**
//     * 证书生成路径
//     */
//    @Value("${license.licensePath}")
//    private String licensePath;
//
//    /**
//     * 证书内容盐值
//     **/
//    @Value("${license.saltVerify}")
//    private String saltVerify;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        String osName = null;
//        //操作系统类型
//        if (StringUtils.isBlank(osName)) {
//            osName = System.getProperty("os.name");
//        }
//        osName = osName.toLowerCase();
//        System.out.println("==============获得的osName操作系统为：" + osName);
//
//        AbstractServerInfos abstractServerInfos = null;
//
//        //根据不同操作系统类型选择不同的数据获取方法
//        if (osName.startsWith("windows")) {
//            abstractServerInfos = new WindowsServerInfos();
//        } else if (osName.startsWith("linux")) {
//            abstractServerInfos = new LinuxServerInfos();
//        } else {//其他服务器类型
//            abstractServerInfos = new LinuxServerInfos();
//        }
//        System.out.println("======================获取服务器信息如下：");
//        System.out.println(abstractServerInfos.getServerInfos());
//
//        //执行keytool脚本
//        //生成证书
//        LicenseCreatorParam param = new LicenseCreatorParam();
//        param.setSubject("license_demo");
//        param.setPrivateAlias("privateKey");
//        param.setStorePass("public_password1234");
//        param.setKeyPass("private_password1234");
//        param.setLicensePath(licensePath);
//        param.setPrivateKeysStorePath("C:\\Users\\dingyb\\Desktop\\license_demo\\privateKeys.keystore");
////        param.setPrivateKeysStorePath(File.separator + "license"+ File.separator + "privateKeys.keystore");
//        param.setIssuedTime(new Date("2023/02/10 01:30:01"));
//        param.setExpiryTime(new Date("2023/04/28 17:00:01"));
//        param.setConsumerType("User");
//        param.setConsumerAmount(1);
//        param.setDescription("这是证书描述信息");
//
//        LicenseCheckModel checkModel = abstractServerInfos.getServerInfos();
//        //获取当前服务器信息的综合盐值
//        String[] saltArray = new String[]{((AbstractServerInfos) abstractServerInfos).getServerInfos().getCpuSerial(), ((AbstractServerInfos) abstractServerInfos).getServerInfos().getMainBoardSerial(), "jxcc-license"};
//        String res = MD5Util.getMD5(saltArray);
//        checkModel.setSaltVerify(res);
//        param.setLicenseCheckModel(checkModel);
//        System.out.println("============证书描述信息-----");
//        log.info(param.toString());
//
//        Map<String, Object> resultMap = new HashMap<>(2);
//
//
//        LicenseCreator licenseCreator = new LicenseCreator(param);
//        boolean result = licenseCreator.generateLicense();
//
//        if (result) {
//            resultMap.put("result", "ok");
//            resultMap.put("msg", param);
//        } else {
//            resultMap.put("result", "error");
//            resultMap.put("msg", "证书文件生成失败！");
//        }
//        System.out.println("-----------------------------");
//        System.out.println(result);
//        System.out.println(resultMap);
//    }
//}
