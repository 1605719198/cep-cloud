package com.jlkj.finance.aa.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 现金流量代码对象 finance_aa_cashflow_code
 *
 * @author 265799
 * @date 2023-04-17
 */
@Data
public class FinanceAaCashflowCodeDTO {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 现金流量表代码 */
    @Excel(name = "现金流量表代码")
    private String cashFlowCode;

    /** 现金流量表代码名称 */
    @Excel(name = "现金流量表代码名称")
    private String cashFlowName;

    /** 上层ID */
    @Excel(name = "上层ID")
    private String parentId;

    /** 上层代码 */
    @Excel(name = "上层代码")
    private String parentCode;
    /** 上层代码 */
    @Excel(name = "上层名称")
    private String parentName;

    /** 是否可用 */
    @Excel(name = "是否可用")
    private String isunabl;

    /** 流入流出 */
    @Excel(name = "流入流出")
    private String inorout;

    /** 取值方式 */
    @Excel(name = "取值方式")
    private String quedataway;



    /** 新增人名称 */
    private String createName;

    /** 异动人名称 */
    private String updateName;
    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /** 新增人名称 */
    private String createBy;
    /** 修改人名称 */
    private String updateBy;

    /** 现金流量代码 */
    private List<FinanceAaCashflowCodeDTO> codeList;
    /**
     * 用来封装子节点数据
     */
    List<FinanceAaCashflowCodeDTO> children ;


    public FinanceAaCashflowCodeDTO() {
    }

    public FinanceAaCashflowCodeDTO(String id, String companyId, String parentId, String cashFlowCode, String cashFlowName, List<FinanceAaCashflowCodeDTO> children) {
        this.id = id;
        this.companyId = companyId;
        this.parentId = parentId;
        this.cashFlowCode = cashFlowCode;
        this.cashFlowName = cashFlowName;
        this.children = children;
    }

    public FinanceAaCashflowCodeDTO(FinanceAaCashflowCodeDTO financeAaCashflowCode) {
        this.id = financeAaCashflowCode.getId();
        this.companyId= financeAaCashflowCode.getCompanyId();
        this.parentId = financeAaCashflowCode.getParentId();
        this.cashFlowCode = financeAaCashflowCode.getCashFlowCode();
        this.cashFlowName = financeAaCashflowCode.getCashFlowName();
        this.children =financeAaCashflowCode.getChildren();
    }
}
