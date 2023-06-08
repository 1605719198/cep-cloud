package com.jlkj.human.hs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 集团级薪资项目输入维护对象 human_hs_salary_project_basis
 *
 * @author jiangbingchen
 * @date 2023-05-08
 */
@TableName(value ="human_hs_salary_project_basis")
@Data
public class SalaryProjectBasis implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 薪酬项目编码 */
    @Excel(name = "薪酬项目编码")
    private String payProCode;

    /** 薪酬项目名称 */
    @Excel(name = "薪酬项目名称")
    private String payProName;

    /** 是否需设定标准 */
    private String isSta;

    /** 标准的内容 */
    private String staCon;

    /** 是否岗位定薪项目 */
    private Long isPostPro;

    /** 是否员工定薪项目 */
    private String isEmpPro;

    /** 是否下拉选单 */
    private String isLov;

    /** 下拉选单的内容ID */
    private String lovConId;

    /** 说明 */
    @Excel(name = "说明")
    private String salaryDescribe;

    /** 父结点ID */
    private Long parentid;

    /** 祖籍关系 */
    private String parents;

    /** 薪酬项目类别 */
    private String payType;

    /** 是否显示编码 */
    @Excel(name = "是否显示编码")
    private String isShowno;

    /** 排序 */
    @Excel(name = "排序")
    private String num;

    /** 默认值 */
    private String defaultValue;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 该代码是否在使用 */
    private String ifUsed;

    /** 该节点是否最最终节点 */
    private String ifEnd;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 子部门 */
    @TableField(exist = false)
    private List<SalaryProjectBasis> children = new ArrayList<>();

}
