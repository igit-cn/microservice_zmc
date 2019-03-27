package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Data
public class HyLabel {
    private Long id;
    private String productName;
    private String operatorName;
    private Date createTime;
    private Boolean isActive;
    private String iconUrl;
}
