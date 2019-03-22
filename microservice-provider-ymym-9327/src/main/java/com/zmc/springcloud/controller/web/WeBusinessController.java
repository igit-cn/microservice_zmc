package com.zmc.springcloud.controller.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zmc.springcloud.entity.BusinessStore;
import com.zmc.springcloud.entity.WeBusiness;
import com.zmc.springcloud.entity.Store;
import com.zmc.springcloud.feignclient.wechataccount.BusinessStoreFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.StoreFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WeBusinessFeignClient;
import com.zmc.springcloud.utils.CommonAttributes;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@RestController
public class WeBusinessController {
    @Autowired
    private WeBusinessFeignClient weBusinessFeignClient;

    @Autowired
    private BusinessStoreFeignClient businessStoreFeignClient;

    @Autowired
    private StoreFeignClient StoreFeignClient;

    /** 微商信息*/
    @GetMapping("/webusiness/detail")
    public Object weBusinessDetail(Long id, String callback) {
        Map<String, Object> map = new HashMap<>();

        Json j = new Json();
        try {
            WeBusiness weBusiness = weBusinessFeignClient.getWeBusinessById(id);
            if (weBusiness == null) {
                j.setMsg("微商不存在");
                j.setSuccess(false);
                j.setObj(null);
            } else {
                Map<String, Object> weBusinessMap = new HashMap<>();
                weBusinessMap.put("id", weBusiness.getId());
                weBusinessMap.put("name", weBusiness.getName());
                weBusinessMap.put("typeId", weBusiness.getType());
                weBusinessMap.put("mobile", weBusiness.getMobile());
                weBusinessMap.put("address", weBusiness.getAddress());
                weBusinessMap.put("isActive", weBusiness.getIsActive());
                weBusinessMap.put("openid", weBusiness.getWechatOpenId());
                weBusinessMap.put("wechatAccount", weBusiness.getWechatAccount());
                weBusinessMap.put("isLineWechatBusiness", weBusiness.getIsLineWechatBusiness());
                weBusinessMap.put("logo", weBusiness.getLogo());
                weBusinessMap.put("shopName", weBusiness.getShopName());
                map.put("weBusiness", weBusinessMap);

                // get store info
                if (weBusiness.getStoreId() == null) {
                    map.put("store", null);
                } else if (weBusiness.getType() == CommonAttributes.WeBusinessType.Interior) {
                    Store inStore = StoreFeignClient.getStoreById(weBusiness.getStoreId());
                    Map<String, Object> store = new HashMap<>();
                    store.put("id", inStore.getId());
                    store.put("type", CommonAttributes.WeBusinessType.Interior);
                    store.put("name", inStore.getStoreName());
                    store.put("address", inStore.getAddress());
                    map.put("store", store);
                } else if (weBusiness.getType() == CommonAttributes.WeBusinessType.Exterior) {
                    BusinessStore exStore = businessStoreFeignClient.getBusinessStoreById(weBusiness.getStoreId());
                    Map<String, Object> store = new HashMap<String, Object>();
                    store.put("id", exStore.getId());
                    store.put("type", CommonAttributes.WeBusinessType.Exterior);
                    store.put("name", exStore.getStoreName());
                    store.put("address", exStore.getAddress());
                    map.put("store", store);
                }
                // get introducer info
                Long introducerId = weBusiness.getIntroducerId();
                WeBusiness introducer = weBusinessFeignClient.getWeBusinessById(introducerId);
                if (introducer == null) {
                    map.put("introducer", null);
                } else {
                    Map<String, Object> introducerMap = new HashMap<>();
                    introducerMap.put("id", introducer.getId());
                    introducerMap.put("name", introducer.getName());
                    introducerMap.put("mobile", introducer.getMobile());
                    introducerMap.put("wechatAccount", introducer.getWechatAccount());
                    map.put("introducer", introducerMap);
                }
                j.setSuccess(true);
                j.setMsg("操作成功");
                j.setObj(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("微商不存在");
        }
        if (callback != null) {
            return new JSONPObject(callback, j);
        }
        return j;
    }
}
