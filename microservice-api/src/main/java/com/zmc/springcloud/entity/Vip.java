package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * hy_vip
 *
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Data
public class Vip {
    private Long id;
    private Long wechatAccount;
    private Long viplevelId;
    private Date birthday;
    private Date createTime;
}
