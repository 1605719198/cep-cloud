package com.jlkj.human.hp.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.domain.vo.SysMenu;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author jlkj
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 部门名称 */
    private String label;

    /** 公司编码 */
    private String label2;

    /** 是否是公司 */
    private String label3;

    /** 状态正常/作废 */
    private String label4;

    /** 祖级列表 */
    private String label5;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.label2 = dept.getCompId();
        this.label3 = dept.getIfCompany();
        this.label4 = dept.getStatus();
        this.label5 = dept.getAncestors();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getLabel2()
    {
        return label2;
    }

    public void setLabel2(String label2)
    {
        this.label2 = label2;
    }

    public String getLabel3()
    {
        return label3;
    }

    public void setLabel3(String label3)
    {
        this.label3 = label3;
    }

    public String getLabel4()
    {
        return label4;
    }

    public void setLabel4(String label4)
    {
        this.label4 = label4;
    }

    public String getLabel5()
    {
        return label5;
    }

    public void setLabel5(String label5)
    {
        this.label5 = label5;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
