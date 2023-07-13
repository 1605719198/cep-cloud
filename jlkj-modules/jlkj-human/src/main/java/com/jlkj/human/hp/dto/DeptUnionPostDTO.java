package com.jlkj.human.hp.dto;

import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 岗位部门DTO
 * @author 266861
 * @date 2023-03-08
 */
@Data
public class DeptUnionPostDTO extends BaseEntity {

    /** 部门id */
    private Long deptId;

    /** 父节点id */
    private Long parentId;

    /** 资料名称 */
    private String name;

    /** 公司别id */
    private String compId;

    /** 岗位id*/
    private Long postId;

    /** 岗位编码 */
    private String postCode;

    /** 子部门 */
    private List<DeptUnionPostDTO> children ;


}
