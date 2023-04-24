package com.jlkj.human.hp.dto;

import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 *
 * @author 266861
 * @date 2023-03-08
 */

@Data
public class FirstDeptDTO extends BaseEntity {

    /** 一级部门名称 */
    private String firstDeptName;

    /** 一级部门ID */
    private String firstDeptId;

    /** 公司别 */
    private String compId;

    /** 部门ID */
    private String childDeptId;

    /** 员工ID */
    private String childPersonId;
}
