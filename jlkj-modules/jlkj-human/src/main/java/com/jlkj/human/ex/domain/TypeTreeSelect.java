package com.jlkj.human.ex.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 * 
 * @author ruoyi
 */
public class TypeTreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TypeTreeSelect> children;

    public TypeTreeSelect()
    {

    }

    public TypeTreeSelect(ExamType examType)
    {
        this.id = examType.getTypeId();
        this.label = examType.getTypeName();
        
        this.children = examType.getChildren().stream().map(TypeTreeSelect::new).collect(Collectors.toList());
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

    public List<TypeTreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TypeTreeSelect> children)
    {
        this.children = children;
    }
}
