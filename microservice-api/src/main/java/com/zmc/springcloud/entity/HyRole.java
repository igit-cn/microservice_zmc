package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HyRole {
  private Long id;
  private String name;
  private String description;
  private Date createDate;
  private Date modifyDate;
  private Boolean status;

}
