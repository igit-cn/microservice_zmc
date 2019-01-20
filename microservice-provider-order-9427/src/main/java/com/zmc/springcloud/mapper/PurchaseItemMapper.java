package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.PurchaseItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Mapper
public interface PurchaseItemMapper {
    @Select("SELECT * FROM hy_purchase_item WHERE id = #{id}")
    PurchaseItem findById(Long id);

    /** 根据 pecificationId,  isValid,  state 获取PurchaseItem的List*/
    @Select("SELECT * FROM hy_purchase_item WHERE specification_id = #{specificationId} AND is_valid = #{isValid} AND state = #{state} ORDER BY id")
    List<PurchaseItem> getPurchaseItemList(@Param("specificationId") Long specificationId, @Param("isValid") Boolean isValid, @Param("state") Boolean state);
}
