package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.service.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public void changeUserPoint(@RequestParam Long orderWechatId, @RequestParam Integer changevalue, @RequestParam String reason) throws Exception{
        pointRecordService.changeUserPoint(orderWechatId, changevalue, reason);
    }

}
