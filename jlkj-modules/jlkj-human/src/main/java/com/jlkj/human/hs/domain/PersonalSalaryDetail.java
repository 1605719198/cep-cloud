package com.jlkj.human.hs.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 个人薪资核定明细对象 human_hs_salary_detail
 *
 * @author 266861
 * @date 2023-06-01
 */
@Data
public class PersonalSalaryDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 标识哪个薪资标准核定主项 */
    @Excel(name = "标识哪个薪资标准核定主项")
    private String salaryId;

    /** 该公司薪资项目ID */
    @Excel(name = "该公司薪资项目ID")
    private String payCheckRea;

    /** 该公司薪资项目名称 */
    @Excel(name = "该公司薪资项目名称")
    private String payProName;

    /** 第二列标题 */
    @Excel(name = "第二列标题")
    private String twoTitle;

    /** 第三列是否下拉选单 */
    @Excel(name = "第三列是否下拉选单")
    private String thrIsLov;

    /** 第三列值 */
    @Excel(name = "第三列值")
    private String thrSta;

    /** 第三列ID值 */
    @Excel(name = "第三列ID值")
    private String thrIdSta;

    /** 第四列标题 */
    @Excel(name = "第四列标题")
    private String fourTitle;

    /** 第五列是否下拉选单 */
    @Excel(name = "第五列是否下拉选单")
    private String fivIsLov;

    /** 第五列值 */
    @Excel(name = "第五列值")
    private String fivSta;

    /** 第五列ID值 */
    @Excel(name = "第五列ID值")
    private String fivIdSta;

    /** 第六列标题 */
    @Excel(name = "第六列标题")
    private String sixTitle;

    /** 第七列是否下拉选单 */
    @Excel(name = "第七列是否下拉选单")
    private String sevIsLov;

    /** 第七列值 */
    @Excel(name = "第七列值")
    private String sevSta;

    /** 第七列ID值 */
    @Excel(name = "第七列ID值")
    private String sevIdSta;

    /** 顺序号 */
    @Excel(name = "顺序号")
    private String serialNo;

    /** 岗位异动标识 */
    @Excel(name = "岗位异动标识")
    private String postFlag;

    /** 岗位异动生效日期 */
    @Excel(name = "岗位异动生效日期")
    private String postEffectDate;

    /** 岗位异动工作通知 */
    @Excel(name = "岗位异动工作通知")
    private String msgld;

    /** 是否扣税 */
    @Excel(name = "是否扣税")
    private String istax;

    /** 是否定薪项目 */
    @Excel(name = "是否定薪项目")
    private String isEmpPro;

    /** 下拉选单ID */
    @Excel(name = "下拉选单ID")
    private String lovConId;

    /** 是否基薪 */
    @Excel(name = "是否基薪")
    private String isSalaryBasis;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 员工工号 */
    @Excel(name = "员工工号")
    private String empNo;

    /** 版本号 */
    @Excel(name = "版本号")
    private String versionNo;

    /** 是否定薪确认 */
    @Excel(name = "是否定薪确认")
    private String isCheck;

    /** 祖级列表 */
    private String parents;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入人工号 */
    @Excel(name = "输入人工号")
    private String creatorNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createDate;

}
