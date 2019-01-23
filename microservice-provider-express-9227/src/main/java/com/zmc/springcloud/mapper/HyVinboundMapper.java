package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyVinbound;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xyy on 2018/12/7.
 *
 * @author xyy
 */
@Mapper
public interface HyVinboundMapper {

    /**
     * 通过规格id获取虚拟库存
     */
    @Select("SELECT * FROM hy_vinbound WHERE specialty_specification_id = #{specificationId}")
    HyVinbound findBySpcification(Long specificationId);

    @Select("SELECT * FROM hy_vinbound WHERE specialty_specification_id = #{id}")
    List<HyVinbound> findListBySpecificationId(Long id);

    /**
     * 增加HyVinbound数据  注意sale_number和vupdate_time的初始化
     */
    @Insert("INSERT INTO hy_vinbound(specialty_specification_id, vinbound_number, sale_number, vupdate_time) VALUES(#{specialtySpecificationId}, #{vinboundNumber},0,NOW())")
    void insert(HyVinbound hyVinbound);

    /**
     * 更新HyVinbound 注意id和规格id应该不能修改
     */
    @Update("UPDATE hy_vinbound SET vinbound_number = #{vinboundNumber}, sale_number = #{saleNumber}, vupdate_time = NOW() WHERE id = #{id}")
    void update(HyVinbound hyVinbound);
}
