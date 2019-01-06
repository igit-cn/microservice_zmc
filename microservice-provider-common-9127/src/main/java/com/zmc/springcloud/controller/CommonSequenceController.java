package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.CommonSequence;
import com.zmc.springcloud.service.CommonSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/sequence/code")
    public String getCode(CommonSequence.SequenceTypeEnum type, Long param){
        return commonSequenceService.getCode(type, param);
    }
}
