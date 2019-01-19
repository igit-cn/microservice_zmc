package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@Mapper
public interface ShoppingCartMapper {
    /**
     * 根据条件筛选购物车列表
     */
    @SelectProvider(type = Provider.class, method = "getListShoppingCart")
    List<ShoppingCart> getListShoppingCart(Long wechatId, Long specialtyId, Long specialtySpecificationId);

    class Provider {
        public String getListShoppingCart(Long wechatId, Long specialtyId, Long specialtySpecificationId) {
            StringBuilder sql = new StringBuilder("SELECT * FROM shopping_cart WHERE 1=1");
            if (wechatId != null) {
                sql.append(" AND wechat_id = " + wechatId);
            }
            if (specialtyId != null) {
                sql.append(" AND specialty_id = " + specialtyId);
            }
            if (specialtySpecificationId != null) {
                sql.append(" AND specialty_specification_id = " + specialtySpecificationId);
            }
            return sql.toString();
        }
    }

    /**
     * 保存购物车
     */
    @Insert("INSERT INTO shopping_cart(wechat_id, specialty_id, specialty_specification_id, quantity, add_time, is_group_promotion) VALUES(#{wechatId}, #{specialtyId}, #{specialtySpecificationId}, #{quantity},NOW(), #{isGroupPromotion})")
    void save(ShoppingCart shoppingCart);

    /** 更新购物车中的数量*/
    @Update("UPDATE shopping_cart SET quantity = #{quantity} WHERE id = #{id}")
    void updateQuantity(ShoppingCart tmp);

    @Delete("DELETE FROM shopping_cart WHERE id = #{id}")
    void delete(Long id);
}
