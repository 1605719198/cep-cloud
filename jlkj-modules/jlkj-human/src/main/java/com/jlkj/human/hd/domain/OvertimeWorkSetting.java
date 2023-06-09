package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 加班补休参数设定对象 human_hd_overtimeworksetting
 *
 * @author 266861
 * @date 2023-03-29
 */
@Data
public class OvertimeWorkSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 存班小时是否累计到下一月度 */
    @Excel(name = "存班小时是否累计到下一月度")
    private String isTranMon;

    /** 存班小时是否累计到下一季度 */
    @Excel(name = "存班小时是否累计到下一季度")
    private String isTranQua;

    /** 存班小时是否累计到下一年度 */
    @Excel(name = "存班小时是否累计到下一年度")
    private String isTranYear;

    /** 补休最小单位（单位：小时） */
    private BigDecimal holUnit;

    /** 加班最小单位（单位：小时） */
    private BigDecimal oveUnit;

    /** 月最大加班小时数（单位：小时） */
    private BigDecimal monthOve;

    /** 月最大剩余存班小时数（单位：小时） */
    private BigDecimal resvAttr3;

    /** 月最大可送出异常次数 */
    private BigDecimal resvAttr1;

    /** 补休的最小班次数 */
    private BigDecimal resvAttr2;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
}
