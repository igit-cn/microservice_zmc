package com.zmc.springcloud.entity;

import lombok.Data;

/**
 * Created by xyy on 2019/3/27.
 *
 * @author xyy
 */
@Data
public class ServicePromise {
    private Long id;
    /** 服务承诺*/
    private String servicePromise;
    /** 温馨提示*/
    private String prompt;
}
