package com.zmc.springcloud.controller.web;

import com.netflix.discovery.converters.Auto;
import com.zmc.springcloud.entity.ServicePromise;
import com.zmc.springcloud.entity.WeBusiness;
import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.feignclient.order.BusinessOrderFeignClient;
import com.zmc.springcloud.feignclient.product.ServicePromiseFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WeBusinessFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WechatAccountFeignClient;
import com.zmc.springcloud.utils.Json;
import com.zmc.springcloud.wechatpay.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import sun.rmi.server.WeakClassHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@RestController
public class YmymController {

    @Autowired
    private WechatAccountFeignClient wechatAccountFeignClient;

    @Autowired
    private WeBusinessFeignClient weBusinessFeignClient;

    @Autowired
    private ServicePromiseFeignClient servicePromiseFeignClient;

    /**
     * 登录到商城首页时的账户处理
     */
    @PostMapping("/api/createAccount")
    public Json createAccount(String wechatName, String openId) {
        Json j = new Json();
        try {
            List<WechatAccount> lists = wechatAccountFeignClient.getWechatAccountListByOpenId(openId);
            if (lists.size() > 0) {
                j.setSuccess(false);
                j.setMsg("账户已存在");
            } else {
                WechatAccount wechatAccount = new WechatAccount();
                wechatAccount.setIsActive(true);
                wechatAccount.setTotalbalance(BigDecimal.ZERO);
                wechatAccount.setWechatName(wechatName);
                wechatAccount.setWechatOpenid(openId);
                wechatAccount.setIsWeBusiness(false);
                wechatAccount.setIsVip(false);
                wechatAccount.setTotalpoint(0);
                wechatAccount.setPoint(0);
                wechatAccount.setIsNew(false);
                wechatAccountFeignClient.createWechatAccount(wechatAccount);
                j.setSuccess(true);
                j.setMsg("账户创建成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /**
     * 服务承诺详情
     */
    @GetMapping("/system_settings/service_promise/detail/view")
    public Json servicePromissDetail() {
        Json j = new Json();
        try {
            List<ServicePromise> promises = servicePromiseFeignClient.getServicePromise();
            if (promises.size() > 0) {
                j.setObj(promises.get(0));
            }
            j.setSuccess(true);
            j.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /**
     * 个人中心 信息页
     */
    @GetMapping("/usermanagement/info")
    public Json info(Long wechat_id) {
        Json j = new Json();
        try {
            WechatAccount wechatAccount = wechatAccountFeignClient.getWechatAccountById(wechat_id);
            if (wechatAccount == null) {
                j.setSuccess(false);
                j.setMsg("账户不存在");
                return j;
            } else {
                Map<String, Object> maps = new HashMap<>();
                maps.put("wechatAccount", wechatAccount);
                if (wechatAccount.getIsWeBusiness()) {
                    List<WeBusiness> weBusinesses = weBusinessFeignClient.getWeBusinessListByOpenId(wechatAccount.getWechatOpenid());
                    if (weBusinesses != null && !weBusinesses.isEmpty()) {
                        maps.put("weBusiness", weBusinesses.get(0));
                    } else {
                        maps.put("weBusiness", null);
                    }
                } else {
                    maps.put("weBusiness", null);
                }
            }
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
