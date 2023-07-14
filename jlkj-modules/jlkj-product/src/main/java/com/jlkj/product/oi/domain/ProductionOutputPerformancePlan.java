package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 实体类-生产管理-炼焦实绩-推焦计划-数据同步
 *
 * @author sudeyou
 * @since 2023-04-10 10:11:01
 */
@TableName(value ="t_production_output_performance_plan")
@Data
public class ProductionOutputPerformancePlan implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 炉区（对应推焦计划表0、推焦计划表1）
     */
    private Integer furnaceArea;

    /**
     * 出炉序号
     */
    private Integer releasedNum;

    /**
     * 班别(对应班组字段：甲班、乙班...)
     */
    private String className;

    /**
     * 炉号
     */
    private Integer furnaceNum;

    /**
     * 上次装煤时间
     */
    private Date lastCoalLoadTime;

    /**
     * 计划结焦时间
     */
    private String planCokingTime;

    /**
     * 计划推焦时间
     */
    private Date planFocusTime;

    /**
     * 推焦计划表id
     */
    private Integer syncId;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 出炉实绩id
     */
    private String pushId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
