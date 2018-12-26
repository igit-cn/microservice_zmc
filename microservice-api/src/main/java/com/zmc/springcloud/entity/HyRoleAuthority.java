package com.zmc.springcloud.entity;

import lombok.Data;

/**
 * hy_role_authority
 * */
@Data
public class HyRoleAuthority {
  public enum CheckedRange {

    /** 公司 **/
    company,

    /** 分公司 **/
    subcompany,

    /** 部门 **/
    department,

    /** 个人 **/
    individual,
  }

  public enum CheckedOperation {

    /** 查看 **/
    view,

    /** 编辑**/
    edit,

    /** 编辑个人 **/
    editIndividual,
  }

  private Long id;
  private Long roles;
  private Long authoritys;
  private CheckedRange rangeCheckedNumber;
  private CheckedOperation operationCheckedNumber;

}
