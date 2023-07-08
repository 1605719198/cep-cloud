package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 团队绩效明细
 * @author HuangBing
 * @TableName human_pa_team_performance_detail
 */
@TableName(value ="human_pa_team_performance_detail")
@Data
public class TeamPerformanceDetail implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 团队绩效主档ID
     */
    private String teamPerformanceId;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 考评类别（1-月度；2-季度；3-年度）
     */
    private String meritType;

    /**
     * 考评年月
     */
    private String meritMonth;

    /**
     * 一级单位
     */
    private String dept;

    /**
     * 编码
     */
    private String num;

    /**
     * 指标项目
     */
    private String item;

    /**
     * 数据提供单位
     */
    private String dataDept;

    /**
     * 权重
     */
    private BigDecimal weight;

    /**
     * 目标值
     */
    private String goalValue;

    /**
     * 评价标准
     */
    private String standard;

    /**
     * 实际完成值
     */
    private String resultValue;

    /**
     * 得分
     */
    private BigDecimal score;

    /**
     * 输入人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String creator;

    /**
     * 输入人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
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