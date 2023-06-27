package com.jlkj.human.hd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 排班明细对象 human_hd_arrange_class
 *
 * @author 266861
 * @date 2023-04-10
 */
@Data
public class ArrangeClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 排班主档ID */
    @Excel(name = "排班主档ID")
    private String arrangeClassMasterId;

    /** 轮班方式ID */
    @Excel(name = "轮班方式ID")
    private String shiftmodeId;

    /** 班别ID */
    @Excel(name = "班别ID")
    private String classId;

    /** 班次ID */
    @Excel(name = "班次ID")
    private String shiftId;

    /** 班次编码 */
    @Excel(name = "班次编码")
    private String shiftCode;

    /** 排班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrShiDate;

    /** 排班说明 */
    @Excel(name = "排班说明")
    private String shiftDesc;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 开始日期 */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
