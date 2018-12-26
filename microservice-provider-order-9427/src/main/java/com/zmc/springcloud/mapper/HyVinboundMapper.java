package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyVinbound;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by xyy on 2018/12/7.
 *
 * @author xyy
 */
@Mapper
public interface HyVinboundMapper {
    @Select("SELECT * FROM hy_vinbound WHERE specialty_specification_id = #{id}")
    List<HyVinbound> findListBySpecificationId(Long id);

    /** 注意时间更新*/
    @Update("UPDATE hy_vinbound SET vinbound_number = #{vinboundNumber}, sale_number = #{saleNumber}, vupdate_time = NOW() WHERE id = #{id}")
    void updateHyVinboundNum(@Param("id") Long id, @Param("saleNumber") Integer saleNumber, @Param("vinboundNumber") Integer vinboundNumber);
}
