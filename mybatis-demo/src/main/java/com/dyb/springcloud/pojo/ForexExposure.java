package com.dyb.springcloud.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForexExposure implements Serializable {

    private static final long serialVersionUID = 985999611566331797L;


    /**
     * 外键
     */
    private Long headerId;

    /**
     * 序号
     */
    private Long rowIndex;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 核算科目
     */
    private String accountSubject;

    /**
     * 美元
     */
    private BigDecimal usd;

    /**
     * 英镑
     */
    private BigDecimal gbp;

    /**
     * 港币
     */
    private BigDecimal hkd;

    /**
     * 人民币
     */
    private String cny;

    /**
     * 合计（折美元）
     */
    private BigDecimal total;

    /**
     * 年末余额
     */
    private BigDecimal yearBalance;

    /**
     * 备注（针对外汇敞口端拟采取的措施）
     */
    private String comment;

    /**
     * 操作
     */
    private String operation;

    /**
     * 更新人姓名
     */
    private String updateByName;

    /**
     * 创建人姓名
     */
    private String createByName;


    private Long createBy;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;

    private Long updateBy;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateTime;

    private String delFlag;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAccountSubject() {
        return accountSubject;
    }

    public void setAccountSubject(String accountSubject) {
        this.accountSubject = accountSubject;
    }

    public BigDecimal getUsd() {
        return usd;
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    public BigDecimal getGbp() {
        return gbp;
    }

    public void setGbp(BigDecimal gbp) {
        this.gbp = gbp;
    }

    public BigDecimal getHkd() {
        return hkd;
    }

    public void setHkd(BigDecimal hkd) {
        this.hkd = hkd;
    }

    public String getCny() {
        return cny;
    }

    public void setCny(String cny) {
        this.cny = cny;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getYearBalance() {
        return yearBalance;
    }

    public void setYearBalance(BigDecimal yearBalance) {
        this.yearBalance = yearBalance;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
