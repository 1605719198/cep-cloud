package com.jlkj.finance.aa.dto;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 报表数据
 *
 * @author 114288
 * @date 2023-07-10
 */
@Data
public class FinanceAaReportBalanceDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 报表id */
    private String reportId;

    /** 报表代号 */
    private String reportNo;

    /** 印表项目 */
    @Excel(name = "印表项目")
    private String itemCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String itemName;

    /** 行次 */
    @Excel(name = "行次")
    private Long itemRow;

    /** 类别 */
    @Excel(name = "年初数")
    private BigDecimal yearBegAmt;

    /** 控制 */
    @Excel(name = "期末数")
    private BigDecimal endAmt;

    /** 印表项目 */
    @Excel(name = "印表项目")
    private String twoItemCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String twoItemName;

    /** 行次 */
    @Excel(name = "行次")
    private Long twoItemRow;

    /** 类别 */
    @Excel(name = "年初数")
    private BigDecimal twoYearBegAmt;

    /** 控制 */
    @Excel(name = "期末数")
    private BigDecimal twoEndAmt;

    @Override
    public String toString() {
        return "FinanceAaReportBalanceDTO{" +
                "id='" + id + '\'' +
                ", companyId='" + companyId + '\'' +
                ", reportId='" + reportId + '\'' +
                ", reportNo='" + reportNo + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemRow=" + itemRow +
                ", yearBegAmt='" + yearBegAmt + '\'' +
                ", endAmt='" + endAmt + '\'' +
                ", twoItemCode='" + twoItemCode + '\'' +
                ", twoItemName='" + twoItemName + '\'' +
                ", twoItemRow=" + twoItemRow +
                ", twoYearBegAmt='" + twoYearBegAmt + '\'' +
                ", twoEndAmt='" + twoEndAmt + '\'' +
                '}';
    }
}
