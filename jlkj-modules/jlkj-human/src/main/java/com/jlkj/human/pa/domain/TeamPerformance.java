package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团队绩效主档
 * @author HuangBing
 * @TableName human_pa_team_performance
 */
@TableName(value ="human_pa_team_performance")
@Data
public class TeamPerformance implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

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
     * 状态（1-新增；2-确认）
     */
    private String meritStatus;

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