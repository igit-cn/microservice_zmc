package com.zmc.springcloud.entity;

import lombok.Data;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@Data
public class HyPromotionPic {
    private Long id;
    private Long promotionId;
    private String sourcePath;
    private String largePath;
    private String mediumPath;
    private String thumbnailPath;
    private Boolean isTag;
}
