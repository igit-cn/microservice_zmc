package com.zmc.springcloud.controller;

import com.zmc.springcloud.service.CommonSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@RestController
public class CommonSequenceController {
    @Autowired
    private CommonSequenceService commonSequenceService;

    @RequestMapping("/sequence/{type}")
    public Long findValueByType(Integer type) throws Exception {
        return commonSequenceService.getValue(type);
    }

    @RequestMapping("/sequence/update")
    public void updateValue(Integer type, Long newValue) throws Exception {
        commonSequenceService.updateValue(type, newValue);
    }
}
