package com.zmc.springcloud.entity;

import lombok.Data;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Data
public class SpecialtyAppraiseImage {
    private Long id;
    private String largePath;
    private String mediumPath;
    private Integer orders;
    private String sourcePath;
    private String thumbnailPath;
    private Long appraiseId;
}
