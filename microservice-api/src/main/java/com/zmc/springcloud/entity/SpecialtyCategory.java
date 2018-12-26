package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 *  特产分区
 * */
@Data
public class SpecialtyCategory {
  private Long id;
  private Date createTime;
  private String iconUrl;
  private Boolean isActive;
  private Boolean ishow;
  private String name;
  private String operator;
  private Long orders;
  private Long pid;
  private Date deadTime;
  /** 上一级分区  不持久化到数据库表  本质上为pid指向的数据*/
  private SpecialtyCategory parent;
}
