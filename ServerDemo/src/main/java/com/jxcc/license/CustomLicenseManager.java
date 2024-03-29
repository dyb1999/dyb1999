package com.jxcc.license;

import com.jxcc.util.ApplicationShutdownManager;
import com.jxcc.util.MD5Util;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseNotary;
import de.schlichtherle.license.LicenseParam;
import de.schlichtherle.license.NoLicenseInstalledException;
import de.schlichtherle.xml.GenericCertificate;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自定义LicenseManager，用于增加额外的服务器硬件信息校验
 *
 * @author dingyb
 * @date 2023/2/23
 * @since 1.0.0
 */
public class CustomLicenseManager extends LicenseManager implements ApplicationContextAware {
    private static Logger logger = LogManager.getLogger(CustomLicenseManager.class);

    //XML编码
    private static final String XML_CHARSET = "UTF-8";
    //默认BUFSIZE
    private static final int DEFAULT_BUFSIZE = 8 * 1024;

    //盐值
    private static final String salt = "jxcc-license";

    private static ApplicationContext ctx;
    

    public CustomLicenseManager() {

    }

    public CustomLicenseManager(LicenseParam param) {
        super(param);
    }

    /**
     * 复写create方法
     * @author dingyb
     * @date 2023/2/23 10:36
     * @since 1.0.0
     * @param
     * @return byte[]
     */
    @Override
    protected synchronized byte[] create(
            LicenseContent content,
            LicenseNotary notary)
            throws Exception {
        initialize(content);
        this.validateCreate(content);
        final GenericCertificate certificate = notary.sign(content);
        return getPrivacyGuard().cert2key(certificate);
    }

    /**
     * 复写install方法，其中validate方法调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     * @author dingyb
     * @date 2023/2/23 10:40
     * @since 1.0.0
     * @param
     * @return de.schlichtherle.license.LicenseContent
     */
    @Override
    protected synchronized LicenseContent install(
            final byte[] key,
            final LicenseNotary notary)
            throws Exception {
        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);

        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);

        return content;
    }

    /**
     * 复写verify方法，调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     * @author dingyb
     * @date 2023/2/23 10:40
     * @since 1.0.0
     * @param
     * @return de.schlichtherle.license.LicenseContent
     */
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary)
            throws Exception {
        GenericCertificate certificate = getCertificate();

        // Load license key from preferences,
        final byte[] key = getLicenseKey();
        if (null == key){
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }

        certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        this.validate(content);
        setCertificate(certificate);

        return content;
    }

    /**
     * 校验生成证书的参数信息
     * @author dingyb
     * @date 2023/5/2 15:43
     * @since 1.0.0
     * @param content 证书正文
     */
    protected synchronized void validateCreate(final LicenseContent content)
            throws LicenseContentException {
        final LicenseParam param = getLicenseParam();

        final Date now = new Date();
        final Date notBefore = content.getNotBefore();
        final Date notAfter = content.getNotAfter();
        if (null != notAfter && now.after(notAfter)){
            throw new LicenseContentException("证书失效时间不能早于当前时间");
        }
        if (null != notBefore && null != notAfter && notAfter.before(notBefore)){
            throw new LicenseContentException("证书生效时间不能晚于证书失效时间");
        }
        final String consumerType = content.getConsumerType();
        if (null == consumerType){
            throw new LicenseContentException("用户类型不能为空");
        }
    }


    /**
     * 复写validate方法，增加IP地址、Mac地址等其他信息校验
     * @author dingyb
     * @date 2023/2/23 10:40
     * @since 1.0.0
     * @param content LicenseContent
     */
    @SneakyThrows
    @Override
    protected synchronized void validate(final LicenseContent content)
            throws LicenseContentException {
        try {
            //1. 首先调用父类的validate方法
            super.validate(content);

            //2. 然后校验自定义的License参数
            //License中可被允许的参数信息
            LicenseCheckModel expectedCheckModel = (LicenseCheckModel) content.getExtra();
            //当前服务器真实的参数信息
            LicenseCheckModel serverCheckModel = getServerInfos();

            if (expectedCheckModel != null && serverCheckModel != null) {
                //校验盐值
                if (!checkSalt(content)) {
                    throw new LicenseContentException("当前证书内容不合法,请确认证书合法性");
                }

                //校验IP地址
                if (expectedCheckModel.getIpCheck() && !checkIpAddress(expectedCheckModel.getIpAddress(), serverCheckModel.getIpAddress())) {
                    throw new LicenseContentException("当前服务器的IP没在授权范围内");
                }

                //校验Mac地址
                if (expectedCheckModel.getMacCheck() && !checkIpAddress(expectedCheckModel.getMacAddress(), serverCheckModel.getMacAddress())) {
                    throw new LicenseContentException("当前服务器的Mac地址没在授权范围内");
                }

                //校验主板序列号
                if (!checkSerial(expectedCheckModel.getMainBoardSerial(), serverCheckModel.getMainBoardSerial())) {
                    throw new LicenseContentException("当前服务器的主板序列号没在授权范围内");
                }

                //校验CPU序列号
                if (!checkSerial(expectedCheckModel.getCpuSerial(), serverCheckModel.getCpuSerial())) {
                    throw new LicenseContentException("当前服务器的CPU序列号没在授权范围内");
                }
            } else {
                throw new LicenseContentException("不能获取服务器硬件信息");
            }
        } catch (LicenseContentException e) {
            // TODO: 2023/4/5
            logger.warn("license校验失败: {}",e.getMessage());
            Thread shutdownThread = new Thread(this::shutdownApp);
            shutdownThread.setContextClassLoader(this.getClass().getClassLoader());
            shutdownThread.start();
            throw new LicenseContentException(e.getLocalizedMessage());
        }
    }

    /**
     * 关闭应用
     * @author dingyb
     * @date 2023/2/25 14:19
     * @since 1.0.0
     * @return java.lang.Object
     */
    private void shutdownApp() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}

        SpringApplication.exit(ctx, () -> 0);
    }


    /**
     * 重写XMLDecoder解析XML
     * @author dingyb
     * @date 2023/2/25 14:02
     * @since 1.0.0
     * @param encoded XML类型字符串
     * @return java.lang.Object
     */
    private Object load(String encoded){
        BufferedInputStream inputStream = null;
        XMLDecoder decoder = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XML_CHARSET)));

            decoder = new XMLDecoder(new BufferedInputStream(inputStream, DEFAULT_BUFSIZE),null,null);

            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if(decoder != null){
                    decoder.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.error("XMLDecoder解析XML失败",e);
            }
        }

        return null;
    }

    /**
     * 获取当前服务器需要额外校验的License参数
     * @author dingyb
     * @date 2023/2/23 14:33
     * @since 1.0.0
     * @return demo.LicenseCheckModel
     */
    private LicenseCheckModel getServerInfos(){
        //操作系统类型
        String osName = System.getProperty("os.name").toLowerCase();
        AbstractServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        }else{//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }

    /**
     * 校验当前服务器的IP/Mac地址是否在可被允许的IP范围内<br/>
     * 如果存在IP在可被允许的IP/Mac地址范围内，则返回true
     * @author dingyb
     * @date 2023/2/24 11:44
     * @since 1.0.0
     * @return boolean
     */
    private boolean checkIpAddress(List<String> expectedList,List<String> serverList){
        if(expectedList != null && expectedList.size() > 0){
            if(serverList != null && serverList.size() > 0){
                for(String expected : expectedList){
                    if(serverList.contains(expected.trim())){
                        return true;
                    }
                }
            }

            return false;
        }else {
            return true;
        }
    }

    /**
     * 校验当前服务器硬件（主板、CPU等）序列号是否在可允许范围内
     * @author dingyb
     * @date 2023/2/24 14:38
     * @since 1.0.0
     * @return boolean
     */
    private boolean checkSerial(String expectedSerial,String serverSerial){
        if(StringUtils.isNotBlank(expectedSerial)){
            if(StringUtils.isNotBlank(serverSerial)){
                if(expectedSerial.equals(serverSerial)){
                    return true;
                }
            }

            return false;
        }else{
            return true;
        }
    }

    /**
     * 校验盐值
     * @author dingyb
     * @date 2023/2/24 14:38
     * @since 1.0.1
     * @return boolean
     */
    private boolean checkSalt(LicenseContent content) throws NoSuchAlgorithmException {
        //获取当前服务器信息的综合盐值
        ArrayList<String> list = new ArrayList<>();
        if (null != content.getNotBefore()) {
            list.add(String.valueOf(content.getNotBefore()));
        }
        if (null != content.getNotAfter()) {
            list.add(String.valueOf(content.getNotAfter()));
        }
        LicenseCheckModel checkModel = (LicenseCheckModel) content.getExtra();
        if (null != checkModel.getCpuSerial()) {
            list.add(checkModel.getCpuSerial());
        }
        if (null != checkModel.getMainBoardSerial()) {
            list.add(checkModel.getMainBoardSerial());
        }
        list.add(salt);

        String[] saltArray = list.toArray(new String[list.size()]);
        String res = MD5Util.getMD5(saltArray);
        String saltVerify = checkModel.getSaltVerify();
        if (StringUtils.isNotBlank(saltVerify) && res.equals(saltVerify)) {
            return true;
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
