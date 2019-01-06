package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 特产规格 hy_specialty_specification
 *
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Data
public class SpecialtySpecification {
    private Long id;
    /** 所属产品*/
    private Long specialtyId;
    /** 成本价*/
    private BigDecimal costPrice;
    /** 创建时间*/
    private Date createTime;
    /** */
//  private String creator;
    /** */
//  private Date dead_time;
    /** */
    private Integer hasSold;
    /** */
    private String iconUrl;
    /** 有效状态，false：无效，true：有效*/
    private Boolean isActive;
    /** */
    private BigDecimal marketPrice;
    /** */
    private String packing;
    /** */
    private Long packingIndex;
    /** */
    private BigDecimal platformPrice;
    /** */
    private Long platformStock;
    /** */
    private Long salesVolume;
    /** 产品规格名称*/
    private String specification;
    /** */
    private Long totalStock;
    /** */
    private Long limitNumber;
    /** 修改时间*/
    private Date modifyTime;
    /** */
    private String creatorName;
    /** */
    private String modifier;
    /** 修改者姓名*/
    private String modifierName;
    /** 是否是赠品*/
    private Boolean isFreeGift;
    /** 父规格id*/
    private Long pid;
    /** 与父规格的倍数关系*/
    private Integer saleNumber;
    /** 基础库存*/
    private Integer baseInbound;

    // TODO 这些字段的数据来自于SpecialtyPrice,hy_specialty_specification中没有相应的列
    //门店提成比例
    private BigDecimal storeDivide;
    //非虹宇门店提成比例
    private BigDecimal exterStoreDivide;
    //个人提成比例
    private BigDecimal businessPersonDivide;
    //虚拟运费
    private BigDecimal deliverPrice;
    //虚拟库存数量
    private Integer vInboundNumber;
}
