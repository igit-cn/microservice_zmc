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
    private BusinessOrderFeignClient businessOrderFeignClient;

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
}
