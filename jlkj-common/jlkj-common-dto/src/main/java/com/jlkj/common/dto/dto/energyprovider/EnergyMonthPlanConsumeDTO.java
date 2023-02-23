package com.jlkj.common.dto.dto.energyprovider;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/5 - 11:14
 */
@Data
public class EnergyMonthPlanConsumeDTO {
    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 能源介质名称
     */
    private String energyName;

    /**
     * 能源介质代码
     */
    private String energyCode;

    /**
     * 类别
     */
    private String types;

    /**
     * 工序名称
     */
    private String factoryName;

    /**
     * 作业区
     */
    private String factoryArea;

    /**
     * 年月(YYYY-MM)
     */
    private String planyearMonth;

    /**
     * 月计划值
     */
    private BigDecimal planValue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 备注
     */
    private String memo;

    /**
     * 日计划公式
     */
    private String memoa;

    /**
     * 行业先进值
     */
    private BigDecimal advancedValue;

    /**
     * 内控指标值
     */
    private BigDecimal lawsValue;

    /**
     * 新增时间(YYYY-MM-DD HH:mm:ss)
     */
    private String createDate;
    /**
     * 新增人员(XXXXX NNNNN)
     */
    private String createEmpNo;
    /**
     * 修改时间(YYYY-MM-DD HH:mm:ss)
     */
    private String updateEmpNo;
    /**
     * 修改人员(XXXXX NNNNN)
     */
    private String updateDate;

}
