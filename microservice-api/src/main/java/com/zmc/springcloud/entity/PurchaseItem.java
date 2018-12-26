package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hy_purchase_item 采购单条目
 * Created by xyy on 2018/12/5.
 * @author xyy
 */
@Data
public class PurchaseItem {
    private Long id;
    /**微商个人提成比例*/
    private BigDecimal businessPersonDivide;
    /**成本价*/
    private BigDecimal costPrice;
    /**创建时间*/
    private Date createTime;
    /**非虹宇门店提成比例*/
    private BigDecimal exterStoreDivide;
    /**市场价*/
    private BigDecimal marketPrice;
    /**采购数量*/
    private Integer quantity;
    /**平台销售价*/
    private BigDecimal salePrice;
    /**运费*/
    private BigDecimal deliverPrice;
    /**状态，0：未入库，1：已入库*/
    private Boolean state;
    /**虹宇门店提成比例*/
    private BigDecimal storeDivide;
    /**采购单*/
    private Long purchaseId;
    /**采购清单对应的产品规格*/
    private Long specificationId;
    /**是否有效*/
    private Boolean isValid;
    /**设置时间*/
    private Date setProportionTime;
    /**设置人*/
    private String setProportionOperator;
    /**是否设置提成比例*/
    private Boolean setState;

    /**已入库数量，不保存到数据库的*/
    private Integer inboundedQuantity;
    /**损失产品数量,不保存到数据库的*/
    private Integer lostQuantity;
    /**特产名称，不保存到数据库的*/
    private String specialtyName;
    /**不保存到数据库的*/
    private String purchaseCode;
    /**不保存到数据库的*/
    private Integer auditedLostQuantity;
}
