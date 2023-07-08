package com.jlkj.finance.ft.dto;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import com.jlkj.finance.ft.domain.FinanceFtStatusDetail;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 资产使用状态对象 finance_ft_status
 *
 * @author SunXuTong
 * @date 2023-07-03
 */
@Data
public class FinanceFtStatusDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String companyId;

    /** 使用状态代码 */
    @Excel(name = "使用状态代码")
    @NotBlank(message = "使用状态代码")
    private String statusCode;

    /** 使用状态名称 */
    @Excel(name = "使用状态名称")
    private String statusName;

    /** 备注 */
    @Excel(name = "备注")
    private String statusRemark;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    /** 账务资料表单数据 */
    private List<FinanceFtStatusDetail> statusListA;
}
