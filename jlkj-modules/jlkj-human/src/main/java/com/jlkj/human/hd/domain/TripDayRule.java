package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出差日标准维护对象 human_hd_trip_day_rule
 *
 * @author 266861
 * @date 2023-04-22
 */
@Data
public class TripDayRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 人员类别 */
    @Excel(name = "人员类别")
    private String titleKind;

    /** 国别 */
    @Excel(name = "国别")
    private String nation;

    /** 城市 */
    @Excel(name = "城市")
    private String area;

    /** 住宿费 */
    @Excel(name = "住宿费")
    private BigDecimal lodgAllow;

    /** 交通费 */
    @Excel(name = "交通费")
    private BigDecimal trafficAllow;

    /** 伙食费 */
    @Excel(name = "伙食费")
    private BigDecimal foodAllow;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 是否国内 */
    @Excel(name = "是否国内")
    private String isInternal;

    /** 操作人 */
    @Excel(name = "操作人")
    private String creator;

    /** 操作人ID */
    private String creatorId;

    /** 操作日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

}
