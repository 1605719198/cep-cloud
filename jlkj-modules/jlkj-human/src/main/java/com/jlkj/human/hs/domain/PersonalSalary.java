package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 薪资核定对象 human_hs_salary
 *
 * @author 266861
 * @date 2023-06-01
 */
@Data
public class PersonalSalary extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 薪酬核定原因ID
     */
    private String payCheckReaId;

    /**
     * 薪酬核定原因
     */
    @Excel(name = "薪酬核定原因")
    private String payCheckRea;

    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /**
     * 主岗位名称
     */
    @Excel(name = "主岗位名称")
    private String mainPostName;

    /**
     * 职位等级
     */
    @Excel(name = "职位等级")
    private String postLevel;

    /**
     * 止薪日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "止薪日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endPayDate;

    /**
     * 定薪原因说明
     */
    private String payCheckDesc;

    /**
     * 工资支付银行
     */
    private String salBank;

    /**
     * 工资支付银行ID
     */
    private String salBankId;

    /**
     * 工资支付账号
     */
    private String salBankNo;

    /**
     * 奖金支付银行
     */
    private String bonBank;

    /**
     * 奖金支付银行ID
     */
    private String bonBankId;

    /**
     * 奖金支付账号
     */
    private String bonBankNo;

    /**
     * 公司ID
     */
    private String compId;

    /**
     * 公司名称
     */
    private String compName;

    /**
     * 岗位异动前
     */
    private String postId;

    /**
     * 岗位异动标识
     */
    private String postFlag;

    /**
     * 岗位异动生效日期
     */
    private Date postEffectDate;

    /**
     * 岗位异动工作通知
     */
    private String msgld;

    /**
     * 是否扣税
     */
    private String istax;

    /**
     * 员工ID
     */
    private String empId;

    /**
     * 员工工号
     */
    private String empNo;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 是否定薪确认
     */
    private String isCheck;

    /**
     * 绩效类别
     */
    private String achievementType;

    /**
     * 是否最新
     */
    private String isNew;

    /**
     * 输入人
     */
    @Excel(name = "输入人")
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入人工号
     */
    private String creatorNo;

    /**
     * 输入日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 薪资项目明细
     */
    private List<PersonalSalaryDetail> detailList = new ArrayList<PersonalSalaryDetail>();

}
