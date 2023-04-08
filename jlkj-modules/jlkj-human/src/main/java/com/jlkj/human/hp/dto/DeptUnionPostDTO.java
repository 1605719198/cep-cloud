package com.jlkj.human.hp.dto;

import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 266861
 * @date 2023-03-08
 */

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

    /** 子部门 */
    private List<DeptUnionPostDTO> children ;

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }
    public Long getDeptId()
    {
        return deptId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }
    public Long getParentId()
    {
        return parentId;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public void setCompId(String compId)
    {
        this.compId = compId;
    }
    public String getCompId()
    {
        return compId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }
    public Long getPostId()
    {
        return postId;
    }

    public List<DeptUnionPostDTO> getChildren()
    {
        return children;
    }
    public void setChildren(List<DeptUnionPostDTO> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("deptId", getDeptId())
                .append("parentId", getParentId())
                .append("name", getName())
                .append("compId", getCompId())
                .append("postId", getPostId())
                .toString();
    }




}
