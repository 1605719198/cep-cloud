package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 社保公积金标准核定对象 human_hs_social_security_basis
 *
 * @author 266861
 * @date 2023-05-30
 */
@Data
public class SocialSecurityBasis extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 社保核定原因
     */
    @Excel(name = "社保核定原因")
    private String insureRea;

    /**
     * 社保核定原因ID
     */
    private String insureReaId;

    /**
     * 保险编号
     */
    private String insureNo;

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
    private String posLevel;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    /**
     * 最高学历
     */
    private String lastExper;

    /**
     * 参加工作日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinWorkDate;

    /**
     * 民族
     */
    private String nation;

    /**
     * 户籍所在地
     */
    private String reg;

    /**
     * 户口性质
     */
    private String regFea;

    /**
     * 证件号码
     */
    private String cerNo;

    /**
     * 入企日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String entCorDate;

    /**
     * 现居住地
     */
    private String nowAddr;

    /**
     * 联系电话（手机）
     */
    private String mobPhone;

    /**
     * 基本工资
     */
    private BigDecimal basicPay;

    /**
     * 离职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveEffectDate;

    /**
     * 职工身份
     */
    private String empIde;

    /**
     * 长期工作地
     */
    private String workAddr;

    /**
     * 用工性质
     */
    private String empFea;

    /**
     * 社保缴费地类别
     */
    private String payAreaType;

    /**
     * 社保缴费地类别ID
     */
    private String payAreaTypeId;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 员工姓名
     */
    @Excel(name = "员工姓名")
    private String empName;

    /**
     * 员工工号
     */
    @Excel(name = "员工工号")
    private String empNo;

    /**
     * 员工ID
     */
    @Excel(name = "员工ID")
    private String empId;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 信息通知ID
     */
    private String msgid;

    /**
     * 医保编号
     */
    private String medicalInsuranceNo;

    /**
     * 公司保险手册编号
     */
    private String compInsureBookNo;

    /**
     * 住房公积金账号
     */
    private String houseAccount;

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
    @Excel(name = "输入人ID")
    private String creatorId;

    /**
     * 输入人工号
     */
    @Excel(name = "输入人工号")
    private String creatorNo;

    /**
     * 输入日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 社保公积金明细
     */
    private List<SocialSecurityBasisDetail> detailList = new ArrayList<SocialSecurityBasisDetail>();


}
