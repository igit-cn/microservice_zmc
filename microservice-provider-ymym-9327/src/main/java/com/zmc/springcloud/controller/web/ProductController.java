package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.entity.Specialty;
import com.zmc.springcloud.entity.SpecialtyCategory;
import com.zmc.springcloud.feignclient.product.SpecialtyAppraiseFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyCategoryFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtySpecificationFeignClient;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/3/13.
 *
 * @author xyy
 */
@RestController
public class ProductController {
    @Autowired
    private SpecialtyFeignClient specialtyFeignClient;

    @Autowired
    private SpecialtyCategoryFeignClient specialtyCategoryFeignClient;

    @Autowired
    private SpecialtySpecificationFeignClient specialtySpecificationFeignClient;

    @Autowired
    private SpecialtyAppraiseFeignClient specialtyAppraiseFeignClient;

    /**
     * 获取推荐商品列表
     */
    @GetMapping("/product/sub_list_for_recommend")
    public Json subListForRecommend(Integer size) {
        Json j = new Json();
        try {
            List<Map<String, Object>> maps = specialtyCategoryFeignClient.getSubListForRecommendBySize(size);
            j.setObj(maps);
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
     * 获取所有的顶级分区
     */
    @GetMapping("/product/category/super_categories")
    public Json superCategories() {
        Json j = new Json();
        try {
            List<SpecialtyCategory> list = specialtyCategoryFeignClient.getSpecialtyCategorySuperList();
            List<HashMap<String, Object>> obj = new ArrayList<>();
            for (SpecialtyCategory parent : list) {
                HashMap<String, Object> hm = new HashMap<>();
                hm.put("id", parent.getId());
                hm.put("name", parent.getName());
                hm.put("iconUrl", parent.getIconUrl());
                hm.put("orders", parent.getOrders());
                hm.put("isActive", parent.getIsActive());
                // 这里的商品分类都为顶级分区
                hm.put("pid", null);
                obj.add(hm);
            }
            j.setSuccess(true);
            j.setMsg("查询成功");
            j.setObj(obj);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("查询失败");
        }
        return j;
    }

    /**
     * 获取某个顶级分区下的商品
     */
    @GetMapping("/product/sub_list_by_category_id")
    public Json subListByCategoryId(@RequestParam("category_id") Long categoryId, Integer size) {
        Json j = new Json();
        try {
            List<Map<String, Object>> maps = specialtyCategoryFeignClient.getSubListByCategoryIdAndSize(categoryId, size);
            j.setObj(maps);
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
     * 客户端 通过商品id获取商品详情
     */
    @GetMapping("/product/specification_detail_by_specialty_id")
    public Json specificationDetailBySpecialtyId(Long id, HttpSession session) {
        Json j = new Json();
        try {
            Specialty specialty = specialtyFeignClient.getSpecialtyById(id);
            if (specialty == null) {
                j.setSuccess(false);
                j.setMsg("没有该产品");
                j.setObj(null);
                return j;
            }
            Long weChatId = (Long) session.getAttribute("wechat_id");
            List<Map<String, Object>> rows = specialtySpecificationFeignClient.getSpecificationDetailBySpecialtyIdAndWechatId(id, weChatId);
            j.setObj(rows);
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
     * 客户端 商品详情中的商品评价
     */
    @GetMapping("/product/appraisedetail")
    public Json appraisedetail(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Long id) {
        Json j = new Json();
        try {
            Specialty specialty = specialtyFeignClient.getSpecialtyById(id);
            if (specialty == null) {
                j.setSuccess(false);
                j.setMsg("产品不存在");
            } else {
                HashMap<String, Object> hashMap = specialtyAppraiseFeignClient.getSpecialtyAppraiseBySpecialtyId(page, rows, id);
                j.setObj(hashMap);
            }
            j.setSuccess(true);
            j.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 客户端 商品详情 通过规格id获取规格详情*/
    @GetMapping("/product/specification_detail_by_specification_id")
    public Json specificationDetailBySpecialtyId2(Long id, HttpSession session){
        Json j = new Json();
        try{
            Long weChatId = (Long) session.getAttribute("wechat_id");
            List<Map<String, Object>> rows = specialtySpecificationFeignClient.getSpecificationDetailBySpecialtyId(id, weChatId);
            j.setObj(rows);
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
