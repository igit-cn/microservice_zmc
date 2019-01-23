package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.HyVinbound;
import com.zmc.springcloud.service.HyVinboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@RestController
public class HyVinboundControllerApi {
    @Autowired
    private HyVinboundService hyVinboundService;

    @RequestMapping("/vinbound/{id}")
    public HyVinbound getHyVinboundBySpecificationId(@PathVariable("id") Long id) throws Exception{
        return hyVinboundService.findBySpecification(id);
    }

    @RequestMapping("/vinbound/add")
    public void addHyVinbound(@RequestBody HyVinbound hyVinbound) throws Exception{
        hyVinboundService.saveHyVinbound(hyVinbound);
    }

    @RequestMapping("/vinbound/update")
    public void updateHyVinbound(@RequestBody HyVinbound hyVinbound) throws Exception{
        hyVinboundService.updateHyVinbound(hyVinbound);
    }

    @RequestMapping("/vinbound/list/{id}")
    public List<HyVinbound> getHyVinboundListBySpecificationId(@PathVariable("id") Long id) throws Exception{
        return hyVinboundService.getHyVinboundListBySpecificationId(id);
    }
}
