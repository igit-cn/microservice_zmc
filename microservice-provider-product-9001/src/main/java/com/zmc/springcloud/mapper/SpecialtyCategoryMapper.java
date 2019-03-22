package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.SpecialtyCategory;
import com.zmc.springcloud.utils.CommonAttributes;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xyy on 2018/11/23.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtyCategoryMapper {

    /**
     * 获取所有的的顶级产品分区(pid为NULL)
     */
    @Select("SELECT * FROM `hy_specialty_category` WHERE pid is NULL;")
    List<SpecialtyCategory> findListSpecialtyCategoryParent();

    /**
     * 根据id获取特产分区
     */
    @Select("SELECT * FROM hy_specialty_category WHERE id = #{id}")
    SpecialtyCategory findSpecialtyCategory(Long id);

    /**
     * 根据筛选条件获取特产分区列表
     */
    @SelectProvider(type = SpecialtyCategoryDaoProvicer.class, method = "findListSpecialtyCategory")
    List<SpecialtyCategory> findListSpecialtyCategory(Long id, Boolean isActive, String name, Long pid);

    class SpecialtyCategoryDaoProvicer {
        public String findListSpecialtyCategory(Long id, Boolean isActive, String name, Long pid) {
            String sql = "SELECT * FROM hy_specialty_category WHERE 1=1";
            if (null != id) {
                sql += " AND id = " + id;
            }
            if (null != isActive) {
                sql += " AND is_active = " + isActive;
            }
            if (StringUtils.isNotEmpty(name)) {
                sql += " AND name LIKE '%" + name + "%'";
            }
            if (null != pid) {
                sql += " AND pid = " + pid;
            }
            // 注意降序
            sql += " ORDER BY id DESC";
            return sql;
        }

        public String findSubListByCategoryIdAndSize(String categoryIdStr, Integer size){
            String sqlstr = CommonAttributes.SQL_MIN_PRICE_SPEC_PARAMS + CommonAttributes.SQL_MIN_PRICE_SPEC + " and s1.category_id in (" + categoryIdStr + ")";
            sqlstr += " group by p1.id";
            sqlstr += " order by hasSold desc";
            sqlstr += " limit 0," + size;
            return sqlstr;
        }
    }

    /**
     * 添加新的特产分区
     */
    @Insert("INSERT INTO hy_specialty_category(create_time, icon_url, is_active, name, operator, pid) VALUES(NOW(), #{iconUrl}, #{isActive}, #{name}, #{operator}, #{pid})")
    void insert(@Param(value = "name") String name, @Param(value = "isActive") Boolean isActive, @Param(value = "pid") Long pid, @Param(value = "iconUrl") String iconUrl, @Param(value = "operator") String operator);

    @SelectProvider(type = SpecialtyCategoryDaoProvicer.class, method = "findSubListByCategoryIdAndSize")
    List<Object[]> findSubListByCategoryIdAndSize(String categoryIdStr, Integer size);
}
