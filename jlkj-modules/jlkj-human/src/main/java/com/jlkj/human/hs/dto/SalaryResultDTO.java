package com.jlkj.human.hs.dto;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * 薪资计算结果对象DTO
 *
 * @author 266861
 * @date 2023-06-20
 */
@Data
public class SalaryResultDTO {

    /** 主键 */
    private String id;

    /** 公司ID */
    private String compId;

    /** 员工工号 */
    @Excel(name = "员工ID")
    private String empId;

    /** 员工工号 */
    @Excel(name = "员工工号")
    private String empNo;

    /** 员工工号 */
    @Excel(name = "员工姓名")
    private String empName;

    /** 所在组织机构 */
    @Excel(name = "所在组织机构")
    private String orgName;

    /** 年 */
    @Excel(name = "年")
    private Long year;

    /** 月 */
    @Excel(name = "月")
    private Long month;

    /** 应发金额 */
    private String result01;

    /** 应扣金额 */
    private String result02;

    /** 实发金额 */
    private String result;

    /** 应发明细列表 */
    private List<SalaryResultDetailDTO> list01;

    /** 应扣明细列表 */
    private List<SalaryResultDetailDTO> list02;

    /** 企业支付明细列表 */
    private List<SalaryResultDetailDTO> list03;

    /** 计算参数明细列表 */
    private List<SalaryResultDetailDTO> list04;

    /** 追补扣列表 */
    private List<SalaryResultDetailDTO> list00;


}
