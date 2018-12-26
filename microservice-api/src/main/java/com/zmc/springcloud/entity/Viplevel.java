package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * hy_viplevel
 *
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Data
public class Viplevel {
    private Long id;
    private String levelname;
    private Integer startvalue; //开始储蓄值
    private Integer endvalue;  //结束储蓄值
    private BigDecimal discount;	//会员折扣
}
