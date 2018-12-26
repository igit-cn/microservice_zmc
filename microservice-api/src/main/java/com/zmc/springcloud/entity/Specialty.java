package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/** hy_specialty*/
@Data
public class Specialty {
  private Long id;
  /** 品牌*/
  private String brand;
  /** 产品编号*/
  private String code;
  /** 是否可使用电子券*/
  private Boolean couponAvailable;
  /** 创建时间*/
  private Date createTime;
  /** 发货源类型，0：平台，1：供应商*/
  private Integer deliverType;
  /** 产品描述*/
  private String descriptions;
  /** 产品图标*/
  private String iconUrl;
  /** 是否有效*/
  private Boolean isActive;
  /** 该特产对应的可作为赠品的规格的数量*/
  private Integer numberOfFreeGift;
  /** 是否作为赠品赠送,别删，有用*/
  private Boolean isFreeGift;
  /** 是否推荐*/
  private Boolean isRecommend;
  /** 特产分区*/
  private Long categoryId;
  /** 是否可退*/
  private Boolean isReturnable;
  /** 商品名称*/
  private String name;
  /** 排序*/
  private Long orders;
  /** 原产地*/
  private String originalPlace;
  /** 供应商*/
  private Long providerId;
  /** 下架时间*/
  private Date putoffTime;
  /** 上架时间*/
  private Date putonTime;
  /** */
  private String recommend_products;
  /** 送货类型，0：免费送货到家，1：加钱送货到家*/
  private Integer shipType;
  /** 销售状态，0：未上架，1：销售中*/
  private Integer state;
  /** 储藏方法*/
  private String storageMethod;
  /** 修改时间*/
  private Date modifyTime;
  /** */
//  private String provider;
  /** 销售地区*/
  private Long areaId;
  /** */
  private Long audit_state;
  /** 销售状态，0：未上架，1：销售中*/
  private Integer saleState;
  /** 生产许可证*/
  private String productionLicenseNumber;
  /** 创建人账号*/
  private String creator;
  /** 创建人姓名*/
  private String creatorName;
  /** */
  private String modifier;
  /** 修改人姓名*/
  private String modifierName;
  /** */
//  private String production_standard;
  /** */
//  private String specification_type;
  /** */
//  private String durability_period;
  /** */
//  private String gross_weight;
  /** */
//  private String specialty_property;
  /** 是否在banner显示*/
  private Boolean isBanner;
  /** */
//  private Boolean isbanner;
  /** */
//  private String modify_name;
  /** */
//  private String property;
  /** */
//  private String sale_region;
  /** 推荐序号*/
  private Integer recommendOrder;
  /** 基础销量*/
  private Integer baseSaleNumber;

  /** 产品的查看或编辑权限(数据库中无字段)*/
  private String privilege;

  /** 产品评论数目*/
  private Long appraiseCount;
}
