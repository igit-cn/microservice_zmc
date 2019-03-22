package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BusinessStore {
  private Long id;
  private String storeName;
  private String address;
  /** 0无效,1有效*/
  private Long state;
  /** 负责人的WeBusiness的id*/
  private Long headWebusinessId;
  /** 负责人的HyAdmin*/
  private String head;
  /** 推荐人的WeBusiness的id*/
  private Long introducerWebusinessId;
  private Date createTime;
  private Date deadTime;
  /** 创建人的HyAdmin*/
  private String creator;
}
