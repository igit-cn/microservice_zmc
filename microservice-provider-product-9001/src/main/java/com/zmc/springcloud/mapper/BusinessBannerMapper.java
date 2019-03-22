package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessBanner;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/12/12.
 *
 * @author xyy
 */
@Mapper
public interface BusinessBannerMapper {
    @Results(@Result(property = "type", column = "type", typeHandler = EnumOrdinalTypeHandler.class))
    @Select("SELECT * FROM hy_business_banner WHERE target_id = #{targetId} AND type = #{type} ")
    BusinessBanner findBusinessBanner(@Param("targetId") Long targetId, @Param("type") int type);


    @UpdateProvider(type = Provider.class, method = "updateBannerState")
    void updateBannerState(Long targetId, Integer type, boolean state, Date startTime, Date endTime);

    class Provider{
        public String updateBannerState(Long targetId, Integer type, boolean state, Date startTime, Date endTime){
            StringBuilder sql = new StringBuilder("UPDATE hy_business_banner SET state = ");
            sql.append(type);
            if(startTime != null){
                sql.append(", start_time = ");
                sql.append(startTime);
            }
            if(endTime != null){
                sql.append(", end_time = ");
                sql.append(endTime);
            }
            sql.append(" WHERE target_id = ");
            sql.append(targetId);
            sql.append(" AND type = ");
            sql.append(type);
            return sql.toString();
        }
    }

    @Insert("INSERT INTO hy_business_banner(title, img, type, target_id, state, start_time, end_time, pv_price, uv_price, create_date, modify_date, creator) VALUES(#{title},#{img},#{type, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler},#{targetId},#{state},#{startTime},#{endTime},0,0,NOW(),NOW(),#{creator})")
    void insert(BusinessBanner banner);

    /** 根据BusinessBanner的状态获取列表*/
    @Select("SELECT * FROM hy_business_banner WHERE state = #{state}")
    List<BusinessBanner> findBusinessBannerListByState(Boolean state);
}
