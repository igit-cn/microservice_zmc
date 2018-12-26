package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.PurchaseItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Mapper
public interface PurchaseItemMapper {
    @Select("SELECT * FROM hy_purchase_item WHERE id = #{id}")
    PurchaseItem findById(Long id);
}
