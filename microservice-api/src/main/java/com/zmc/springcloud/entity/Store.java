package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Store {
  private Long id;
  private String storeName;
  /** 门店对应部门*/
  private Long departmentId;
  /** 所属部门id*/
  private Long suoshudepartmentId;
  private Long areaId;
  /** 0未审核，1未激活，2激活，3强制激活*/
  private Integer status;
  /** 0虹宇门店，1挂靠门店，2直营门店，3非虹宇门店*/
  private Integer type;
  private String address;
  /** 门店银行帐号信息*/
  private Long bankId;
  private BigDecimal pledge;
  /** 0待缴纳，1交纳中，2已缴纳,3退出中*/
  private Long pstatus;
  private java.sql.Date ppayday;
  private BigDecimal managementFee;
  private java.sql.Date mpayday;
  /** 0未填写,1填写中，2待交纳,3缴纳中, 4已缴纳, 5 已过期*/
  private Long mstatus;
  /** 负责人*/
  private Long adminId;
  private Date registerDate;
  private String contract;
  private Date validDate;
  /** HyAdmin*/
  private String storeAdder;
  /** 统一信用代码*/
  private String uniqueCode;
  /** 营业执照*/
  private String businessLicense;
  /** 经营许可证*/
  private String businessCertificate;
  private BigDecimal credits;
  private BigDecimal deposit;
  /** 存储强制激活附件地址*/
  private String special;
  private Integer waibuStoreType;
  /** 虹宇门店负责人微商*/
  private Long headWebusinessId;
  private Long account;
}
