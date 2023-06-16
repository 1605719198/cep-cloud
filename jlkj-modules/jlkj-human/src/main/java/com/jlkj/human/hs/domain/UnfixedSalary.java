package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 未定薪人员挑选作业对象 human_hs_unfixed_salary
 *
 * @author 266861
 * @date 2023-06-15
 */
@Data
public class UnfixedSalary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 参数编码 */
    @Excel(name = "参数编码")
    private String groupType;

    /** 参数名称 */
    private String typeId;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 人员编码 */
    @Excel(name = "人员编码")
    private String empNo;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;
}
