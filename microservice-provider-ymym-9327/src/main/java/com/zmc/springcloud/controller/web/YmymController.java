package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.entity.ShoppingCart;
import com.zmc.springcloud.feignclient.order.BusinessOrderFeignClient;
import com.zmc.springcloud.service.ShoppingCartService;
import com.zmc.springcloud.utils.Json;
import com.zmc.springcloud.util.WechatPayMainOffcialAccount;
import com.zmc.springcloud.wechatpay.bean.PayBean;
import com.zmc.springcloud.wechatpay.bean.ReqOfficialBean;
import com.zmc.springcloud.wechatpay.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@Controller
public class YmymController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private BusinessOrderFeignClient businessOrderFeignClient;

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

    /** 订单支付成功回调*/
    @RequestMapping(value = "/order/pay/wechat/notify/{orderId}")
    public void notify(@PathVariable("orderId") String orderId, @RequestParam Map<String, Object> params,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();

        // 解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());

		/*支付成功，处理回调,修改订单状态*/
        businessOrderFeignClient.updateOrderAfterPay(orderId);
        String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        return;
    }

    /** 添加到购物车  实体中是wechatId而不是wechat_id 增加一个参数接收*/
    @RequestMapping(value = "/shopping_cart/add_items")
    public Json add(ShoppingCart shoppingCart, Long wechat_id){
        Json j = new Json();
        try{
            shoppingCartService.addShoppingCart(shoppingCart, wechat_id);
            j.setMsg("操作成功");
            j.setSuccess(true);
        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /** 创建订单*/
    @RequestMapping(value = "/order/create")
    public Json createOrder(@RequestParam(required = false) HashMap<String, Object> params,
                            @RequestBody HashMap<String, Object> bodys){
        Json j = new Json();
        try{
            // 将两个Map放到一个Map中 避免FeignClient调用时 Query map can only be present once
            // 这样不可避免产生耦合 因为FeignClient中 必须要知道map中的key分别是 "params"和"bodys"
            HashMap<String, Object> map = new HashMap<>();
            map.put("params", params);
            map.put("bodys", bodys);
            Map<String, Object> obj = businessOrderFeignClient.createOrder(map);
            j.setMsg("操作成功");
            j.setSuccess(true);
            j.setObj(obj);
        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }
}
