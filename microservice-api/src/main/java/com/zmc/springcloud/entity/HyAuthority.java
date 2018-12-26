package com.zmc.springcloud.entity;

import lombok.Data;

@Data
public class HyAuthority {
    enum EditRange {
        /**
         * 无
         */
        none,

        /**
         * 查看
         */
        one,

        /**
         * 查看，编辑
         */
        two,

        /**
         * 查看，编辑，编辑个人
         */
        three,

        /**
         * 编辑
         */
        edit,

        /**
         * 编辑个人
         */
        editSelf,

        /**
         * 编辑，编辑个人
         */
        editAll
    }

    enum OpRange {

        /**
         * 无
         */
        none,

        /**
         * 公司
         */
        one,

        /**
         * 公司，分公司
         */
        two,

        /**
         * 公司，分公司 ，部门
         */
        three,

        /**
         * 公司，分公司，部门，个人
         */
        all
    }

    private Long id;
    private Long pid;
    private String name;
    private OpRange range;
    private EditRange operation;
    private Boolean isDisplay;
    private String icon;
    private String requestUrl;
    private String url;
    private String fullUrl;
}
