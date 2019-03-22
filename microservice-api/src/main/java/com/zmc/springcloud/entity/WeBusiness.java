package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
@Data
public class WeBusiness {
    private Long id;
    private Integer accountCategory;
    private String accountName;
    private String address;
    private String alipayAccount;
    private String area;
    private String bankCode;
    private String bankName;
    private String contactor;
    private Integer credibilityPropertyId;
    private Date deadTime;
    private Integer divideTypeId;
    private String introducer;
    private Boolean isActive;
    private String mobile;
    private String name;
    private String operator;
    private String phone;
    private String postCode;
    private String qrcodeUrl;
    /** 注册时间在数据库中用varchar保存？*/
    private String registerTime;
    private Integer status;
    private Long storeId;
    private Integer typeId;
    private String url;
    private String wechatAccount;
    private String wechatOpenId;
    private Long introducerId;
    private Integer auditStatus;
    private Boolean isLineWechatBusiness;
    private Float lineDivideProportion;
    private Integer type;
    private String nameOfStore;
    private String account;
    private String logo;
    private String shopName;
    private String originUrl;
}