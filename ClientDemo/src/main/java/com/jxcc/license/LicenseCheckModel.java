package com.jxcc.license;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义需要校验的License参数
 *
 * @author dingyb
 * @date 2023/2/23
 * @since 1.0.0
 */
public class LicenseCheckModel implements Serializable{

    private static final long serialVersionUID = 8600137500316662317L;

    /**
     * 是否开启IP校验
     */
    private Boolean ipCheck;

    /**
     * 是否开启MAC地址校验
     */
    private Boolean macCheck;

    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

    public String getSaltVerify() {
        return saltVerify;
    }

    public void setSaltVerify(String saltVerify) {
        this.saltVerify = saltVerify;
    }

    /**
     * 综合校验
     */
    private String saltVerify;

    public Boolean getIpCheck() {
        return ipCheck;
    }

    public void setIpCheck(Boolean ipCheck) {
        this.ipCheck = ipCheck;
    }

    public Boolean getMacCheck() {
        return macCheck;
    }

    public void setMacCheck(Boolean macCheck) {
        this.macCheck = macCheck;
    }

    public List<String> getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(List<String> ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<String> getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(List<String> macAddress) {
        this.macAddress = macAddress;
    }

    public String getCpuSerial() {
        return cpuSerial;
    }

    public void setCpuSerial(String cpuSerial) {
        this.cpuSerial = cpuSerial;
    }

    public String getMainBoardSerial() {
        return mainBoardSerial;
    }

    public void setMainBoardSerial(String mainBoardSerial) {
        this.mainBoardSerial = mainBoardSerial;
    }

    @Override
    public String toString() {
        return "LicenseCheckModel{" +
                "ipAddress=" + ipAddress +
                ", macAddress=" + macAddress +
                ", cpuSerial='" + cpuSerial + '\'' +
                ", mainBoardSerial='" + mainBoardSerial + '\'' +
                ", saltVerify='" + saltVerify + '\'' +
                '}';
    }
}
