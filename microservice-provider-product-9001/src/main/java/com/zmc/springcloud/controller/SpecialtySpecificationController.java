package com.zmc.springcloud.controller;

import com.zmc.springcloud.feignclient.express.SpecialtySpecificationFeignClient;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
/**
 * SpecialtySpecification的库存属性重于产品属性
 * SpecialtySpecification的Service Mapper和数据库表均已划分到 microservice-provider-express模块
 * 但考虑前台的URL以及Zuul转发的限制 这里只能仍然保留一个SpecialtySpecificationController
 * */
@RestController
public class SpecialtySpecificationController {
    @Autowired
    private SpecialtySpecificationFeignClient specialtySpecificationFeignClient;

    /** 渠道销售-产品管理-查看产品-详情-获取父规格*/
    @RequestMapping(value = "/admin/business/product/getparentspecificationlist/view")
    public Json getParentSpecificationList(Long specialtyid){
        Json j = new Json();
        try{
            List<Map<String, Object>> res = specialtySpecificationFeignClient.getParentSpecificationList(specialtyid);
            j.setMsg("操作成功");
            j.setSuccess(true);
            j.setObj(res);
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }
}
