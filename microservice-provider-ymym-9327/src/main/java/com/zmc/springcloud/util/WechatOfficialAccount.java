package com.zmc.springcloud.util;

import lombok.Data;

/**
 * hy_wechat_official_account
 *
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Data
public class WechatOfficialAccount {
    private Long id;
    private String appId;
    private String mchId;
    private String apiKey;
    private String xcxAppId;
    private Boolean isValid;
}
