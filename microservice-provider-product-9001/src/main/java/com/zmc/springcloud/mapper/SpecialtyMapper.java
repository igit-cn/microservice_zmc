package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Specialty;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xyy on 2018/12/3.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtyMapper {
    /**
     * 根据主键id获取Specialty
     * */
    @Select("SELECT * FROM hy_specialty WHERE id = #{id}")
    Specialty findById(Long id);

    /**
     * 根据筛选条件获取产品列表
     */
    @SelectProvider(type = SpecialtyDaoProvicer.class, method = "findListSpecialty")
    List<Specialty> findListSpecialty(Long providerId, String name, Long categoryId, String userNameInSession);

    class SpecialtyDaoProvicer {
        public String findListSpecialty(Long providerId, String name, Long categoryId, String userNameInSession) {
            String sql = "SELECT * FROM hy_specialty WHERE 1=1";
            if (null != providerId) {
                sql += " AND provider = " + providerId;
            }
            if (null != categoryId) {
                sql += " AND category_id = " + categoryId;
            }
            if (StringUtils.isNotEmpty(name)) {
                sql += " AND name LIKE '%" + name + "%'";
            }
            if (StringUtils.isNotEmpty(userNameInSession)) {
                sql += " AND creator = '" + userNameInSession + "'";
            }
            sql += " ORDER BY id DESC";
            return sql;
        }
    }

    /**
     * 新建产品 主键di自增
     */
    @Insert("INSERT INTO hy_specialty(brand, code, create_time, descriptions, is_active, number_of_free_gift, category_id, name, original_place, providerId, storage_method, sale_state, production_license_number, creator, creator_name, is_banner, base_sale_number)　VALUES(#{brand},#{code},#{createTime},#{descriptions},#{isActive},#{numberOfFreeGift},#{categoryId},#{name},#{originalPlace},#{providerId},#{storageMethod},#{saleState},#{productionLicenseNumber},#{creator},#{creatorName},#{isBanner},#{baseSaleNumber})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insertProduct(Specialty specialty);

    /**
     * 获取某种特产的相关推荐  使用hy_specialty和中间表hy_recommend_specialty进行关联查询
     * */
    @Select("SELECT * FROM hy_specialty WHERE id IN (SELECT recommend_specialty_id FROM hy_recommend_specialty WHERE specilty_id = #{id})")
    List<Specialty> getSpecialtiesForRecommendSpecialty(Long id);

    @Update("UPDATE hy_specialty SET coupon_available=#{couponAvailable}, deliver_type = #{deliverType}, is_active= #{isActive}, is_recommend= #{isRecommend}, is_returnable= #{isReturnable}, orders= #{orders},puton_time = #{putonTime},putoff_time = #{putoffTime}, ship_type= #{shipType}, area_id= #{areaId}, is_banner= #{isBanner},recommend_order = #{recommendOrder},base_sale_number = #{baseSaleNumber} WHERE id = #{id}")
    void update(Specialty specialty);

    // 特产-推荐分割线==================================================================================================================

    /**
     * 删除某个特产下的所有的推荐
     * */
    @Delete("DELETE FROM hy_recommend_specialty WHERE specilty_id = #{id} ")
    void clearSpecialtiesForRecommend(Long id);

    @Insert("INSERT INTO hy_recommend_specialty(specilty_id, recommend_specialty_id) VALUES(#{id}, #{recommendSpecialtyId})")
    void insertSpecialtiesForRecommend(@Param("id") Long id, @Param("recommendSpecialtyId") Long recommendSpecialtyId);
}
