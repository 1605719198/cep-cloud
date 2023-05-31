package com.jlkj.human.hs.dto;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 各公司专业奖罚明细项目维护DTO
 *
 * @author 266861
 * @date 2023-05-26
 */
@Data
public class CompPunishmentDTO {

    /**
     * 主键
     */
    private Long punishmentId;

    /**
     * 薪酬项目编码
     */
    @Excel(name = "薪酬项目编码")
    private String payProCode;

    /**
     * 薪酬项目名称
     */
    @Excel(name = "薪酬项目名称")
    private String payProName;

    /**
     * 是否需设定标准
     */
    @Excel(name = "是否需设定标准")
    private String isSta;

    /**
     * 标准的内容
     */
    @Excel(name = "标准的内容")
    private String staCon;

    /**
     * 是否岗位定薪项目
     */
    @Excel(name = "是否岗位定薪项目")
    private String isPostPro;

    /**
     * 是否员工定薪项目
     */
    @Excel(name = "是否员工定薪项目")
    private String isEmpPro;

    /**
     * 是否下拉选单
     */
    @Excel(name = "是否下拉选单")
    private String isLov;

    /**
     * 下拉选单的内容ID
     */
    @Excel(name = "下拉选单的内容ID")
    private String lovConId;

    /**
     * 说明
     */
    @Excel(name = "说明")
    private String salaryDescribe;

    /**
     * 父结点ID
     */
    private Long parentid;

    /**
     * 祖籍关系
     */
    private String parents;

    /**
     * 薪酬项目类别
     */
    private String payType;

    /**
     * 是否显示编码
     */
    private String isShowno;

    /**
     * 排序
     */
    private String num;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 状态
     */
    private String status;

    /**
     * 该代码是否在使用
     */
    private String ifUsed;

    /**
     * 顺序号
     */
    private String srino;

    /**
     * 该代码某公司是否勾选
     */
    private String ifChecked;

    /** 公司别*/
    private String compId;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入日期 */
    private Date createDate;



}
