package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 地区 hy_area
 *
 * Created by xyy on 2018/12/8.
 *
 * @author xyy
 */
@Data
public class HyArea {

    /** 树路径分隔符 */
    public static final String TREE_PATH_SEPARATOR = ",";
    private Long id;
    private Long pid;
    private String name;
    private String fullName;
    private String treePath;
    private Boolean status;
    private Long orders;
}
