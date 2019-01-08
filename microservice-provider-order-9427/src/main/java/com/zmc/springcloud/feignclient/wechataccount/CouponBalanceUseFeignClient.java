package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.CouponBalanceUse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface CouponBalanceUseFeignClient {
    @RequestMapping("/coupon/balanceuse/save")
    void save(CouponBalanceUse couponBalanceUse);
}
