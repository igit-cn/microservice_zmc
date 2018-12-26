package com.zmc.springcloud.entity;

import lombok.Data;

@Data
public class SpecialtyImage {
    private Long id;
    private String imageUrl;
    private Long specialtyId;
    private Integer orders;
    private String sourcePath;
    private String largePath;
    private String mediumPath;
    private String thumbnailPath;
    /**
     * 是否是标志图片
     */
    private Boolean isLogo;
}
