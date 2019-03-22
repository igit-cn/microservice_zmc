package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@Mapper
public interface BusinessStoreMapper {
    /** 根据id获取BusinessStore*/
    @Select("SELECT * FROM hy_business_store WHERE id = #{id}")
    BusinessStore findBusinessStoreById(Long id);
}
