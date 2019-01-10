package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.service.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class PointRecordControllerApi {
    @Autowired
    private PointRecordService pointRecordService;

    @RequestMapping("/pointrecord/modify")
    public void changeUserPoint(Long orderWechatId, Integer changevalue, String reason) throws Exception{
        pointRecordService.changeUserPoint(orderWechatId, changevalue, reason);
    }

}
