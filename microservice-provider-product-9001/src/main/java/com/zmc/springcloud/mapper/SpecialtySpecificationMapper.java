package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.SpecialtySpecification;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtySpecificationMapper {
    @Select("SELECT * FROM hy_specialty_specification WHERE id = #{id}")
    SpecialtySpecification findById(Long id);

    @InsertProvider(type = Provider.class, method = "batchInsert")
    void batchInsert(List<SpecialtySpecification> list);

    @Update("UPDATE hy_specialty_specification SET base_inbound = #{baseInbound}, has_sold = #{hasSold}")
    void updateInboundAndHasSold(SpecialtySpecification specification);

    class Provider {
        /* 批量插入 */
        public String batchInsert(List<SpecialtySpecification> list) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO hy_specialty_specification(specialty_id,create_time,has_sold,is_active,specification,creator_name,pid,sale_number,base_inbound) VALUES ");
            MessageFormat mf = new MessageFormat("(#'{'list[{0}].specialtyId}, #'{'list[{0}].createTime}, #'{'list[{0}].hasSold}, #'{'list[{0}].isActive}, #'{'list[{0}].specification}, #'{'list[{0}].creatorName}, #'{'list[{0}].pid}, #'{'list[{0}].saleNumber}, #'{'list[{0}].baseInbound})");
            for (int i = 0; i < list.size(); i++) {
                sb.append(mf.format(new Object[]{i}));
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
    }

    @Select("SELECT * FROM hy_specialty_specification WHERE  specialty_id = #{specialtyid} AND pid = 0")
    List<SpecialtySpecification> getParentSpecificationListByspecialtyid(Long specialtyid);

    /**根据特产id获取该特产的所有规格*/
    @Select("SELECT * FROM hy_specialty_specification WHERE specialty_id = #{specialtyid}")
    List<SpecialtySpecification> findAllSpecificationBySpecialtyId(Long specialtyid);


    @Update("UPDATE hy_specialty_specification(cost_price, is_active, market_price, platform_price, modify_time,modifier_name, is_free_gift) VALUES(#{costPrice}, #{isActive}, #{marketPrice}, #{platformPrice}, #{modifyTime},#{modifierName}, #{isFreeGift})")
    void update(SpecialtySpecification specialtySpecification);
}
