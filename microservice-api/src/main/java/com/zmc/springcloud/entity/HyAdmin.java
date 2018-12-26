package com.zmc.springcloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xyy
 * */

@Data
public class HyAdmin {
  private String username;
  private String password;
  private Long department;
  private Long role;
  private String qq;
  private String name;
  private String mobile;
  private String address;
  private String position;
  private String wechat;
  private String wechatUrl;
  private Date loginDate;
  private String loginIp;
  private Long loginFailureCount;
  private Boolean isLocked;
  private Date lockedDate;
  private Boolean isEnabled;
  private Boolean isOnjob;
  private Boolean isManager;
  private Date createDate;
  private Date modifyDate;
  private String pid;
  private Long contract;
  private String creator;
}
