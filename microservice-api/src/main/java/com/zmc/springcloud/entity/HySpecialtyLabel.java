package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Data
public class HySpecialtyLabel {
    private Long id;
    private Long labelId;
    private Long specialtyId;
    private String operator;
    private Date createTime;
    private Boolean isMarked;
}
