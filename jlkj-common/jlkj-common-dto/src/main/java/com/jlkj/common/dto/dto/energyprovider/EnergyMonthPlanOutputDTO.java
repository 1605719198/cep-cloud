package com.jlkj.common.dto.dto.energyprovider;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/5 - 14:20
 */
@Data
public class EnergyMonthPlanOutputDTO {
    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 序号
     */
    private String serialNo;

    /**
     * 工序名称
     */
    private String factoryName;

    /**
     * 作业区
     */
    private String factoryArea;

    /**
     * 月计划产量
     */
    private BigDecimal planValue;

    /**
     * 年月(YYYY-MM)
     */
    private String planyearMonth;

    /**
     * 单位
     */
    private String unit;

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
    /**
     * 备注
     */
    private String memo;
}
