package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.entity.BusinessBanner;
import com.zmc.springcloud.feignclient.product.BusinessBannerFeignClient;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
@RestController
public class BannerController {
    @Autowired
    private BusinessBannerFeignClient businessBannerFeignClient;

    /**
     * 商城展示广告列表
     */
    @GetMapping("/banner/listad")
    public Json bannerList() {
        Json j = new Json();
        try {
            List<BusinessBanner> list = businessBannerFeignClient.getBusinessBannerList(true);
            j.setObj(list);
            j.setSuccess(true);
            j.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }
}
