package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.CouponGift;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface CouponGiftFeignClient {
    @RequestMapping("/coupon/gift/{id}")
    CouponGift getCouponGiftById(@PathVariable Long id);

    /** 将商城赠送电子券设置为使用状态*/
    @RequestMapping("/coupon/gift/update")
    void updateUseState(CouponGift coupon);
}
