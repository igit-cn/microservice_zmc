package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@Mapper
public interface StoreMapper {
    @Select("SELECT * FROM hy_store WHERE id = #{id}")
    Store findStoreById(Long id);
}
