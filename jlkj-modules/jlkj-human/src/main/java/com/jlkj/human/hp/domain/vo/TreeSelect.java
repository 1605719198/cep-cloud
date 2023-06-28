package com.jlkj.human.hp.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jlkj.human.hd.domain.Attendencebasis;
import com.jlkj.human.hp.domain.HumanDept;
import com.jlkj.human.hp.dto.DeptUnionPostDTO;
import com.jlkj.human.hs.domain.SalaryProjectBasis;
import com.jlkj.human.pa.domain.PerformanceBasis;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author 266861
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String label;

    private String label2;

    private String label3;

    private String label4;

    private String label5;

    private Long label6;

    private String label7;


    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(HumanDept dept)
    {
        // 节点ID
        this.id = dept.getDeptId();
        // 部门名称
        this.label = dept.getDeptName();
        // 公司编码
        this.label2 = dept.getCompId();
        // 是否是公司
        this.label3 = dept.getIfCompany();
        //状态正常/作废
        this.label4 = dept.getStatus();
        //祖级列表
        this.label5 = dept.getAncestors();
        //公司简称
        this.label7 = dept.getCompanyName();
        //子列表
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(DeptUnionPostDTO deptunionpost)
    {
        // 部门ID
        this.id = deptunionpost.getDeptId();
        // 公司编码
        this.label2 = deptunionpost.getCompId();
        // 名称
        this.label3 = deptunionpost.getName();
        // 岗位id
        this.label6 = deptunionpost.getPostId();
        this.children = deptunionpost.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(Attendencebasis attendencebasis)
    {
        this.id = attendencebasis.getId();
        this.label = attendencebasis.getCode();
        this.label2 = attendencebasis.getName();
        this.label3 = attendencebasis.getStatus();
        this.label4 = attendencebasis.getCompId();
        this.children = attendencebasis.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SalaryProjectBasis salaryProjectBasis)
    {
        this.id = salaryProjectBasis.getId();
        this.label = salaryProjectBasis.getPayProCode();
        this.label2 = salaryProjectBasis.getPayProName();
        this.label3 = salaryProjectBasis.getStatus();
        this.label4 = salaryProjectBasis.getPayType();
        this.children = salaryProjectBasis.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    public TreeSelect(PerformanceBasis performanceBasis)
    {
        this.id = performanceBasis.getId();
        this.label = performanceBasis.getCode();
        this.label2 = performanceBasis.getName();
        this.label3 = performanceBasis.getStatus();
        this.label4 = performanceBasis.getCompId();
        this.children = performanceBasis.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
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

    public String getLabel7()
    {
        return label7;
    }

    public void setLabel7(String label7)
    {
        this.label7 = label7;
    }

    public Long getLabel6()
    {
        return label6;
    }

    public void setLabel5(Long label6)
    {
        this.label6 = label6;
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
