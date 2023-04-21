package com.jlkj.finance.aa.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jlkj.common.core.utils.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author jlkj
 */
public class TreeSelectAcctCode implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 树显示标签 */
    private String label;

    /** 名称 */
    private String name;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectAcctCode> children;

    public TreeSelectAcctCode()
    {

    }

    public TreeSelectAcctCode(FinanceAcctcodeGroup dept)
    {
        this.id = dept.getGroupAcctCode();
        this.label = StringUtils.joinWith("_",dept.getGroupAcctCode(),dept.getGroupAcctName());
        this.name =  dept.getGroupAcctName();
        this.children = dept.getChildren().stream().map(TreeSelectAcctCode::new).collect(Collectors.toList());
    }

    public TreeSelectAcctCode(FinanceAaAcctcodeCorp dept)
    {
        this.id = dept.getAcctCode();
        this.label = StringUtils.joinWith("_",dept.getAcctCode(),dept.getAcctName());
        this.name =  dept.getAcctName();
        this.children = dept.getChildren().stream().map(TreeSelectAcctCode::new).collect(Collectors.toList());
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeSelectAcctCode> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelectAcctCode> children)
    {
        this.children = children;
    }
}
