package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.entity.Receiver;
import com.zmc.springcloud.service.ReceiverService;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/16.
 *
 * @author xyy
 */
@RestController
public class ReceiverController {
    @Autowired
    private ReceiverService receiverService;

    /** 新增收货地址*/
    @RequestMapping("/receiver/add")
    public Json addReceiver(Receiver receiver, Long wechat_id){
        Json j = new Json();
        try{
            if(wechat_id == null){
                j.setSuccess(false);
                j.setMsg("请输入wechat_id");
            }else{
                if(receiver.getIsDefaultReceiverAddress() != null && receiver.getIsDefaultReceiverAddress() == true){
                    updateIsDefaultReceiverAddress(wechat_id);
                }
                receiver.setWechatId(wechat_id);
                receiverService.save(receiver);
                j.setSuccess(true);
                j.setMsg("操作成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("添加失败");
        }
        return j;
    }

    /** 收货地址列表*/
    @RequestMapping("/receiver/list")
    public Json receiverList(Long wechat_id){
        Json j = new Json();
        try{
            List<Receiver> receivers = receiverService.getReceriverListByWechatId(wechat_id);
            j.setObj(receivers);
            j.setSuccess(true);
            j.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 编辑收货地址*/
    @RequestMapping("/receiver/edit")
    public Json editReceiver(Receiver receiver, Long wechat_id){
        Json j = new Json();
        try{
            if(receiver.getIsDefaultReceiverAddress() != null && receiver.getIsDefaultReceiverAddress()){
                updateIsDefaultReceiverAddress(wechat_id);
            }
            receiverService.updateExceptWechatId(receiver);
            j.setSuccess(true);
            j.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    @RequestMapping("/receiver/delete")
    public Json deleteReceiver(Long id){
        Json j = new Json();
        try{
            receiverService.delete(id);
            j.setSuccess(true);
            j.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 将之前是默认的收货地址改为非默认*/
    private void updateIsDefaultReceiverAddress(Long wechat_id) throws Exception{
        List<Receiver> receivers = receiverService.getReceriverListByWechatId(wechat_id);
        for (Receiver r : receivers) {
            if(r.getIsDefaultReceiverAddress() != null && r.getIsDefaultReceiverAddress() == true){
                r.setIsDefaultReceiverAddress(false);
                receiverService.updateIsDefaultRecieverAddress(r);
            }
        }
    }
}
