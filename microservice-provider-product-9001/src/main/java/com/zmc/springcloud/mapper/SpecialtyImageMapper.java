package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.SpecialtyImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtyImageMapper {

    @Insert("INSERT INTO hy_specialty_image(specialty_id,large_path,medium_path,source_path,thumbnail_path,is_logo) VALUES(#{specialtyId},#{largePath},#{mediumPath},#{sourcePath},#{thumbnailPath}, #{isLogo})")
    void insert(SpecialtyImage specialtyImage);

    @InsertProvider(type = Provider.class, method = "batchInsert")
    void batchInsert(List<SpecialtyImage> list);

    class Provider{
        /* 批量插入 */
        public String batchInsert(List<SpecialtyImage> list) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO hy_specialty_image(specialty_id,large_path,medium_path,source_path,thumbnail_path,is_logo) VALUES ");
            MessageFormat mf = new MessageFormat("(#'{'list[{0}].specialtyId}, #'{'list[{0}].largePath}, #'{'list[{0}].mediumPath}, #'{'list[{0}].sourcePath}, #'{'list[{0}].thumbnailPath}, #'{'list[{0}].isLogo})");
            for (int i = 0; i < list.size(); i++) {
                sb.append(mf.format(new Object[]{i}));
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
    }

    @Select("SELECT * FROM hy_specialty_image WHERE specialty_id = #{id}")
    List<SpecialtyImage> getListSpecailtyBySpecialtyId(Long id);
}
