package com.jlkj.human.pa.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jlkj.human.pa.domain.TeamPerformanceDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 个人绩效主档
 * @author HuangBing
 * @TableName human_pa_team_performance
 */
@TableName(value ="human_pa_team_performance")
@Data
public class TeamPerformanceDTO implements Serializable {
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
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    private Date createDate;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 重点工作项List
     */
    private List<TeamPerformanceDetail> keyWorkItemsList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}