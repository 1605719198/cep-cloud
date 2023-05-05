package com.jlkj.finance.aa.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * @author wang'mai
 */
@TableName(value ="finance_aa_index")
@Data
public class FinanceAaIndexDTO {
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 上层节点编码 */
    private String parentNo;

    /** 上层节点ID */
    private String parentId;

    /** 节点序号 */
    @Excel(name = "节点序号")
    private String nodeNo;

    /** 节点类别 */
    @Excel(name = "节点类别")
    private String nodeType;

    /** 节点名称 */
    @Excel(name = "节点名称")
    private String nodeName;

    /** 用途说明 */
    @Excel(name = "用途说明")
    private String useDesc;

    /** 排列指示 */
    @Excel(name = "排列指示")
    private Long dataSeq;

    /** 节点资料操作授权群组 */
    @Excel(name = "节点资料操作授权群组")
    private String nodeAuthgrp;

    /** 明细资料操作授权群组 */
    @Excel(name = "明细资料操作授权群组")
    private String dataAuthgrp;

    /** 新增日期 */
    private String createDate;

    /** 异动日期 */
    private String updateDate;

    /** 新增人姓名 */
    private String createName;

    /** 异动人姓名 */
    private String updateName;
    /**
     * 用来封装子节点数据
     */
    List<FinanceAaIndexDTO> children ;
    public FinanceAaIndexDTO() {
    }

    public FinanceAaIndexDTO(String id,String companyId, String parentId, String nodeNo, String nodeName, List<FinanceAaIndexDTO> children) {
        this.id = id;
        this.companyId = companyId;
        this.parentId = parentId;
        this.nodeNo = nodeNo;
        this.nodeName = nodeName;
        this.children = children;
    }

    public FinanceAaIndexDTO(FinanceAaIndexDTO financeAaIndexDTO) {
        this.id = financeAaIndexDTO.getId();
        this.companyId=financeAaIndexDTO.getCompanyId();
        this.parentId = financeAaIndexDTO.getParentId();
        this.nodeName = financeAaIndexDTO.getNodeName();
        this.nodeNo = financeAaIndexDTO.getNodeNo();
        this.children =financeAaIndexDTO.getChildren();
    }
}

