package com.zmc.springcloud.entity;

import lombok.Data;

/**
 * Created by xyy on 2019/1/16.
 *
 * @author xyy
 */
@Data
public class Receiver {
    private Long id;
    private Boolean isDefaultReceiverAddress;
    private String receiverAddress;
    private String receiverMobile;
    private String receiverName;
    private Long wechatId;
    private Boolean isVipAddress;
}
