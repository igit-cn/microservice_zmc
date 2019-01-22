package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.service.BusinessOrderService;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 商贸手机端-订单相关
 *
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@RestController
public class OrderController {
    @Autowired
    private BusinessOrderService businessOrderService;

    /** 订单列表*/
    @RequestMapping(value = "/order/list_by_account")
    public Json listByAccount(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Long wechatId, Integer status){
        Json j = new Json();
        try{
            HashMap<String, Object> obj = businessOrderService.getOrderListByAccount(page, rows, wechatId, status, true, false);
            j.setObj(obj);
            j.setSuccess(true);
            j.setMsg("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 订单详情*/
    @RequestMapping(value = "/order/detail")
    public Json detail(Long orderId){
        Json j = new Json();
        try{
            HashMap<String, Object> map = businessOrderService.getOrderDetailById(orderId);
            j.setObj(map);
            j.setSuccess(true);
            j.setMsg("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 退款订单详情*/
    @RequestMapping(value = "/order/refund_detail")
    public Json refundDetail(Long orderId){
        Json j = new Json();
        try{
            j = businessOrderService.getRefundOrderDetail(orderId);
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 订单申请退款*/
    @RequestMapping(value = "/order/apply_refund")
    public Json applyRefund(@RequestParam(required = false) HashMap<String, Object> params,
                            @RequestBody HashMap<String, Object> bodys){
        Json j = new Json();
        try{
            j = businessOrderService.applyRefund(params, bodys);
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 确认收货*/
    @RequestMapping(value = "/order/confirm_receive")
    public Json confirmReceive(@RequestParam("order_id") Long id){
        Json j = new Json();
        try{
            j = businessOrderService.confirmReceive(id);
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 取消订单*/
    @RequestMapping(value = "/order/cancel")
    public Json cancel(@RequestParam("order_id") Long id){
        Json j = new Json();
        try{
            j = businessOrderService.cancelOrder(id);
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 删除订单 将订单状态置为无效*/
    @RequestMapping(value = "/delete")
    public Json delete(Long id){
        Json j = new Json();
        try{
            businessOrderService.deleteBusinessOrder(id);
            j.setSuccess(true);
            j.setMsg("操作成功");
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }


}
