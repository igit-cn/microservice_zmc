package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/** hy_provider*/
@Data
public class Provider {
  private Long id;
  private Integer accountType;
  private String address;
  private String bankAccount;
  private String bankCode;
  private String bankName;
  private String businessCertificate;
  private String businessIcence;
  private String contactorEmail;
  private String contactorMobile;
  private String contactorName;
  private String contactorPostcode;
  private String contactorQq;
  private String contactorWechat;
  private String contractNumber;
  private Date endTime;
  private String introduction;
  private Boolean isContracted;
  private String operator;
  private String organizationCodeCertificate;
  private String postcode;
  private String providerName;
  private Integer providerType;
  private String remark;
  private Date startTime;
  private Boolean state;
  private String taxRegistrationCertificate;
  private String businessLicence;
  private Date cancelTime;
  private Date createTime;
  private Date modifyTime;
  private String accountName;
  private String accountUser;
  /** 结算类型 0：单结  1：月结*/
  private Integer balanceType;
  /** 只在月结的时候起作用，单结时默认为0*/
  private Integer balanceDate;
}
