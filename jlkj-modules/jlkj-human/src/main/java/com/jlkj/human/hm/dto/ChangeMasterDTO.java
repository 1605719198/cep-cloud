package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.human.hm.domain.ChangeDetail;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author huangbing
 */
@Data
public class ChangeMasterDTO {

    /**
     * 主键id
     */
    private String uuid;

    /**
     * 公司
     */
    private String compId;

    /**
     * 员工ID（人员基础信息表uuid）
     */
    private String empId;

    /**
     * 工号
     */
    private String empNo;

    /**
     * 姓名
     */
    private String empName;

    /**
     * 异动类别
     */
    private String changeTypeId;

    /**
     * 异动细分类
     */
    private String changeTypeItemId;

    /**
     * 生效日期
     */
    private Date effectDate;

    /**
     * 版本号
     */
    private Integer versionNo;

    /**
     * 职位等级
     */
    private String postLevel;

    /**
     * 更新人员
     */
    private String creator;

    /**
     * 更新人员ID
     */
    private String creatorId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 备用字段1
     */
    private String alternateField1;

    /**
     * 备用字段2
     */
    private String alternateField2;

    /**
     * 备用字段3
     */
    private String alternateField3;

    /**
     * 备用字段4
     */
    private String alternateField4;

    /**
     * 岗位信息明细List
     */
    private List<ChangeDetail> employeeInductionList;

    /**
     * 岗位资料List
     */
    private List<ChangeDetail> employeeTurnoverList;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 部门字段
     */
    private String departmentName;

    /**
     * 部门ID字段
     */
    private String departmentId;
}
