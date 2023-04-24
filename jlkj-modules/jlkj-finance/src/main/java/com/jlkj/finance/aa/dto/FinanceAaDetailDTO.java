package com.jlkj.finance.aa.dto;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 系统选单-明细设定对象 finance_aa_detail
 *
 * @author 265799
 * @date 2023-04-19
 */
@Data
public class FinanceAaDetailDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 节点指示码 */
    @Excel(name = "节点指示码")
    private String leafId;

    /** 项次一名称 */
    @Excel(name = "项次一名称")
    private String itema;

    /** 项次二名称 */
    @Excel(name = "项次二名称")
    private String itemb;

    /** 项次三名称 */
    @Excel(name = "项次三名称")
    private String itemc;

    /** 项次四名称 */
    @Excel(name = "项次四名称")
    private String itemd;

    /** 项次五名称 */
    @Excel(name = "项次五名称")
    private String iteme;

    /** 资料一名称 */
    @Excel(name = "资料一名称")
    private String dataa;

    /** 资料二名称 */
    @Excel(name = "资料二名称")
    private String datab;

    /** 资料三名称 */
    @Excel(name = "资料三名称")
    private String datac;

    /** 资料四名称 */
    @Excel(name = "资料四名称")
    private String datad;

    /** 资料五名称 */
    @Excel(name = "资料五名称")
    private String datae;

    /** 资料六名称 */
    @Excel(name = "资料六名称")
    private String dataf;

    /** 资料七名称 */
    @Excel(name = "资料七名称")
    private String datag;

    /** 排列指示 */
    private Long dataSeq;

    /** 是否启用 */
    private String status;

    /** 新增日期 */
    private String createDate;

    /** 异动日期 */
    private String updateDate;

    /** 新增人姓名 */
    private String createName;

    /** 异动人姓名 */
    private String updateName;
    /** 现金流量代码 */
    private List<FinanceAaDetailDTO> financeDetailList;

}
