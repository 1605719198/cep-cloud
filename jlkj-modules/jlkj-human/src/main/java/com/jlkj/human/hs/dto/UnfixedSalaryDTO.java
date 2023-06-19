package com.jlkj.human.hs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.human.hs.domain.UnfixedSalary;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 未定薪人员挑选作业结果DTO
 *
 * @author 266861
 * @date 2023-06-15
 */
@Data
public class UnfixedSalaryDTO {

    private static final long serialVersionUID = 1L;

    /** 参数编码 */
    private String groupType;

    /** 参数名称 */
    private String typeId;

    /** 公司别 */
    private String compId;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDate;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 人员编码 */
    private String empNo;

    /** 人员姓名 */
    private String empName;

    /** 是否定薪 */
    private String isCheck;

    /** 输入人 */
    private String creator;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    /** 未定薪人员列表 */
    private List<UnfixedSalary> list ;
}
