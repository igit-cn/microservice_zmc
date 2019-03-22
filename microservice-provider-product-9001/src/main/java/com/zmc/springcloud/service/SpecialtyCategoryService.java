package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.SpecialtyCategory;
import com.zmc.springcloud.utils.Json;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xyy on 2018/11/23.
 */
public interface SpecialtyCategoryService {
    /** 获取分区*/
    SpecialtyCategory getSpecialtyCategoryById(Long categoryId) throws Exception;

    /**
     * 系统配置-分区管理-列表
     */
    HashMap<String, Object> getSpecialtyCategoryList(Integer page, Integer rows, Boolean isActive, String name) throws Exception;

    /**
     * 系统配置-分区管理-新建-获取父分区
     */
    List<HashMap<String, Object>> getSpecialtyCategorySelectlist(Long id, Boolean isActive, String name) throws Exception;

    /**
     * 系统配置-分区管理-新建-保存
     */
    Json addSpecialtyCategory(String name, Boolean isActive, String parentName, Long pid, String iconUrl, String username) throws Exception;

    /**
     * 编辑部-产品管理-列表-特产种类下拉列表
     */
    List<HashMap<String, Object>> getSpecialtyCategoryTreeList() throws Exception;

    /** 客户端-获取某个顶级分类categoryId下的size个商品*/
    List<Object[]> getSubListByCategoryIdAndSize(Long categoryId, Integer size) throws Exception;

    /** 获取商品的所有顶级分区*/
    List<SpecialtyCategory> getSpecialtyCategorySuperList();
}
