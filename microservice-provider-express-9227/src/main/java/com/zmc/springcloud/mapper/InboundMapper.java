package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Inbound;
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
public interface InboundMapper {
    @Select("SELECT * FROM hy_inbound WHERE specialty_specification_id = #{specificationId} AND inbound_number >= #{quantity}")
    List<Inbound> findListInbound(@Param("specificationId") Long specificationId, @Param("quantity") Integer quantity);

    @Select("SELECT * FROM hy_inbound WHERE specialty_specification_id = #{specificationId} AND depot_code = #{depotCode}")
    List<Inbound> findListBySpecificationIdAndDepotCode(@Param("specificationId") Long specificationId, @Param("depotCode") String depotCode);
}
