package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class VipControllerApi {
    @Autowired
    private VipService vipService;

    @RequestMapping("/vip/318")
    public void setVip318(WechatAccount wechatAccount, BigDecimal money)throws Exception{
       vipService.setVip318(wechatAccount, money);
    }

}
