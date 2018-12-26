package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * hy_vinbound 虚拟库存
 *
 * Created by xyy on 2018/12/7.
 *
 * @author xyy
 */
@Data
public class HyVinbound {
    private Long id;
    private Long specialtySpecificationId;
    private Integer vinboundNumber;
    private Integer saleNumber;
    private Date vupdateTime;
}
