package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.feignclient.order.BusinessOrderFeignClient;
import com.zmc.springcloud.service.BusinessOrderService;
import com.zmc.springcloud.utils.Json;
import com.zmc.springcloud.wechatpay.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 商贸手机端-订单相关
 * <p>
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@RestController
public class OrderController {
    @Autowired
    private BusinessOrderService businessOrderService;

    @Autowired
    private BusinessOrderFeignClient businessOrderFeignClient;

    /**
     * 创建订单
     */
    @RequestMapping(value = "/order/create")
    public Json createOrder(@RequestParam(required = false) HashMap<String, Object> params,
                            @RequestBody HashMap<String, Object> bodys) {
        Json j = new Json();
        try {
            // 将两个Map放到一个Map中 避免FeignClient调用时 Query map can only be present once
            // 这样不可避免产生耦合 因为FeignClient中 必须要知道map中的key分别是 "params"和"bodys"
            HashMap<String, Object> map = new HashMap<>();
            map.put("params", params);
            map.put("bodys", bodys);
            Map<String, Object> obj = businessOrderFeignClient.createOrder(map);
            j.setMsg("操作成功");
            j.setSuccess(true);
            j.setObj(obj);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 订单支付成功回调
     */
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

    /**
     * 订单列表
     */
    @RequestMapping(value = "/order/list_by_account")
    public Json listByAccount(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Long wechatId, Integer status) {
        Json j = new Json();
        try {
            HashMap<String, Object> obj = businessOrderService.getOrderListByAccount(page, rows, wechatId, status, true, false);
            j.setObj(obj);
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
     * 订单详情
     */
    @RequestMapping(value = "/order/detail")
    public Json detail(Long orderId) {
        Json j = new Json();
        try {
            HashMap<String, Object> map = businessOrderService.getOrderDetailById(orderId);
            j.setObj(map);
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
     * 退款订单详情
     */
    @RequestMapping(value = "/order/refund_detail")
    public Json refundDetail(Long orderId) {
        Json j = new Json();
        try {
            j = businessOrderService.getRefundOrderDetail(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /**
     * 订单申请退款
     */
    @RequestMapping(value = "/order/apply_refund")
    public Json applyRefund(@RequestParam(required = false) HashMap<String, Object> params,
                            @RequestBody HashMap<String, Object> bodys) {
        Json j = new Json();
        try {
            j = businessOrderService.applyRefund(params, bodys);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /**
     * 确认收货
     */
    @RequestMapping(value = "/order/confirm_receive")
    public Json confirmReceive(@RequestParam("order_id") Long id) {
        Json j = new Json();
        try {
            j = businessOrderService.confirmReceive(id);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /**
     * 取消订单
     */
    @RequestMapping(value = "/order/cancel")
    public Json cancel(@RequestParam("order_id") Long id) {
        Json j = new Json();
        try {
            j = businessOrderService.cancelOrder(id);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /**
     * 删除订单 将订单状态置为无效
     */
    @RequestMapping(value = "/delete")
    public Json delete(Long id) {
        Json j = new Json();
        try {
            businessOrderService.deleteBusinessOrder(id);
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
