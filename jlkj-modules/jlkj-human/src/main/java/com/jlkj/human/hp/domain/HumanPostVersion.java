package com.jlkj.human.hp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 岗位信息数据维护对象 sys_post
 *
 * @author 266861
 * @date 2023-03-15
 */
@Data
public class HumanPostVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 主键 */
    private Long postId;

    /** 岗位编码 */
    private String postCode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 所属公司/机构 */
    private Long deptId;

    /** 专长类别id */
    private String specialityTypeId;

    /** 专长id */
    private String specialityId;

    /** 岗位序列id */
    private String postSequenceId;

    /** 岗位类别id */
    private String postTypeId;

    /** 职位类别 */
    private String deptType;

    /** 职位名称id */
    private String jobTitleId;

    /** 流水号 */
    private String serialNumber;

    /** 倒班方式 */
    private String shiftWorkId;

    /** 用工性质 */
    private String employmentNatureId;

    /** 工作地点 */
    private String workAddressId;

    /** 公司（具体工作地点） */
    private String workAddress;

    /** 驻外地点（具体工作地点） */
    private String overseasLocations;

    /** 是否配置专人 */
    private String ifSpeciallyAssignedPerson;

    /** 定员 */
    @Excel(name = "定员")
    private Long planCapacity;

    /** 现员 */
    @Excel(name = "现员")
    private Long nowCapacity;

    /** 显示顺序 */
    private Long postSort;

    /** 定员依据 */
    private String capacityBasis;

    /** 成本中心 */
    private String costCenterId;

    /** 状态 */
    private String status;

    /** 上级岗位ID */
    private Long parentPostId;

    /** 该岗位上级岗位名称 */
    @Excel(name = "该岗位上级岗位名称")
    private String parentPostName;

    /** 职级ID */
    private String positionsClassId;

    /** 所属公司/机构id */
    private Long orgId;

    /** 所属公司/机构名称 */
    private String orgName;

    /** 所属公司/机构全称 */
    private String orgFullName;

    /** 职位费用类别 */
    private String positionExpenseCategory;

    /** 成本中心名称 */
    private String costCenterName;

    /** 变更原因 */
    private String changeReason;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDate;

    /** 版本 */
    private String versionNo;

    /** 是否新增 */
    private String ifNew;

    /** 公司别 */
    private String compId;

    /** 岗位工作概述 */
    private String workDesc;

    /** 岗位职责内容 */
    private String workCon;

    /** 考核指标 */
    private String apprKpi;

    /** 检讨指标 */
    private String reviewKpi;

    /** 工作标准 */
    private String workSta;

    /** 工作权限 */
    private String workAut;

    /** 工作环境 */
    private String workEnv;

    /** 使用工具 */
    private String opeEquHp;

    /** 使用工具 */
    private String toolUsed;

    /** 办公用品 */
    private String officeEqu;

    /** 劳保标准 */
    private String labourSta;

    /** 最小年龄 */
    private Long minAge;

    /** 最大年龄 */
    private Long maxAge;

    /** 性别 */
    private String gender;

    /** 最低学历 */
    private String recordId;

    /** 专业要求 */
    private String speClaim;

    /** 职业资格 */
    private String proQua;

    /** 工作经验 */
    private String workExp;

    /** 其他要求 */
    private String otherClaim;

    /** 知识与技能 */
    private String knowTec;

    /** 职业化行为 */
    private String proAct;

}
