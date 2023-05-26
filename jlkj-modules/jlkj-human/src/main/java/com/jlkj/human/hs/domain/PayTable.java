package com.jlkj.human.hs.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司级薪资表项目维护
 * @TableName human_hs_pay_table
 */
@TableName(value ="human_hs_pay_table")
@Data
public class PayTable implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String uuid;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 薪酬表项目ID（human_hs_salary_project_basis表id）
     */
    private String payTabCodeId;

    /**
     * 薪酬表项目编码
     */
    private String payTabCode;

    /**
     * 薪酬表项目名称
     */
    private String payTabName;

    /**
     * 薪资类别
     */
    private String payTypeId;

    /**
     * 数据范围
     */
    private String dataAreaId;

    /**
     * 计算顺序
     */
    private String computeNo;

    /**
     * 是否抛送财务
     */
    private String isThrowFinance;

    /**
     * 是否统计项目
     */
    private String isReport;

    /**
     * 报表中显示顺序
     */
    private String reportLocation;

    /**
     * 是否工资条项目
     */
    private String isSheetPro;

    /**
     * 工资条显示位置
     */
    private String sheetLocationId;

    /**
     * 工资条中显示位置
     */
    private String serialNo;

    /**
     * 是否二级团队绩效
     */
    private String isTeamPerformance;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}