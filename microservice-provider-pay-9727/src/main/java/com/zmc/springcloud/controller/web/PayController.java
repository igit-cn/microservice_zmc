package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.util.WechatPayMainOffcialAccount;
import com.zmc.springcloud.wechatpay.bean.PayBean;
import com.zmc.springcloud.wechatpay.bean.ReqOfficialBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Controller
public class PayController {
    /** 统一下单*/
    @RequestMapping(value = "/pay/wechat/mp/{orderId}")
    public Map<String, Object> detailWithWap(@PathVariable("orderId") String orderId, @RequestParam Map<String, Object> params, @RequestBody Map<String, Object> models, HttpServletResponse servletResponse) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        PayBean payBean = new PayBean();
        payBean.setOrder(orderId);
        if (params.containsKey("total_fee")) {
            payBean.setAmount((String) params.get("total_fee"));
        }
        if (params.containsKey("body")) {
            payBean.setBody((String) params.get("body"));
        }
        if (params.containsKey("notify_url")) {
            payBean.setCallbackUrl((String) params.get("notify_url"));
        }
        if (params.containsKey("openid")) {
            payBean.setOpenId((String) params.get("openid"));
        }

        if (models.containsKey("total_fee")) {
            payBean.setAmount((String) models.get("total_fee"));
        }
        if (models.containsKey("body")) {
            payBean.setBody((String) models.get("body"));
        }
        if (models.containsKey("notify_url")) {
            payBean.setCallbackUrl((String) models.get("notify_url"));
        }
        if (models.containsKey("openid")) {
            payBean.setOpenId((String) models.get("openid"));
        }

        //回调地址，将来要修改
        //payBean.setCallbackUrl("http://www.tobyli16.com:8080/pay/wechat/notify/"+orderId);
        ReqOfficialBean reqBean = WechatPayMainOffcialAccount.getReqOfficial(payBean,1);

        System.out.println(payBean.getCallbackUrl());
        result.put("appId", reqBean.appId);
        result.put("timestamp", reqBean.timeStamp);
        result.put("nonceStr", reqBean.nonceStr);
        result.put("package", reqBean.packageValue);
        result.put("signType", "MD5");
        result.put("paySign", reqBean.paySign);
        response.put("code", "1");
        response.put("result", result);
        return response;
    }

}
