package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.CouponGift;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface CouponGiftMapper {
    @Select("SELECT * FROM hy_coupon_gift WHERE id = #{id}")
    CouponGift find(long id);

    @Update("UPDATE hy_coupon_gift SET state = #{state}, use_time = #{useTime} WHERE id = #{id}")
    void update(CouponGift coupon);
}
