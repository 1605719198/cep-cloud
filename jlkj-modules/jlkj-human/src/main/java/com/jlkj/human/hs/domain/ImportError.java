package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 薪资导入错误信息对象 human_hs_salary_error
 *
 * @author 266861
 * @date 2023-06-05
 */
@Data
public class ImportError extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 薪资类别ID */
    @Excel(name = "薪资类别ID")
    private String payType;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 员工编码 */
    @Excel(name = "员工编码")
    private String empNo;


    /** 信息 */
    @Excel(name = "信息")
    private String mark;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    @Excel(name = "输入人工号")
    private String creatorNo;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;
}
