package com.jlkj.human.hd.dto;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 假别参数对象
 *
 * @author 266861
 * @date 2023-03-21
 */
@Data
public class HolidaySettingDTO {

    /** 假别名称 */
    @Excel(name = "假别名称")
    private String holidayTypeCode;

    /** 公司别 */
    private String compId;

    /** 是否含假日 */
    @Excel(name = "是否含假日")
    private String isIncHol;

    /** 常白班请假最小单位（班次） */
    @Excel(name = "常白班请假最小单位", readConverterExp = "班=次")
    private BigDecimal isNeedCheck;

    /** 常白班请假最小单位（小时） */
    @Excel(name = "常白班请假最小单位", readConverterExp = "小=时")
    private BigDecimal minUnitDay;

    /** 倒班请假最小单位（班次） */
    @Excel(name = "倒班请假最小单位", readConverterExp = "班=次")
    private BigDecimal minUnitTurns;

    /** 说明 */
    @Excel(name = "说明")
    private String description;
}
