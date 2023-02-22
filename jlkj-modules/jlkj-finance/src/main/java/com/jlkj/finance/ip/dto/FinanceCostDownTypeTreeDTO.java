package com.jlkj.finance.ip.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


import java.util.List;

/**
 * @author wang'mai
 */
@Data
public class FinanceCostDownTypeTreeDTO {

    /**
     * 成本科目
     */
    private String costCode;
    /**
     * 户号类别
     */
    private String idTypeStus;

    /**
     * 参号类别
     */
    private String refTypeStus;
    /**
     * 会计科目编码
     */
    @TableId
    private String accountCode;
    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 上层代码
     */
    private String upperCode;

    /**
     * 阶层代码
     */
    private String levelCode;
    /**
     * 节点类别
     */
    private String nodeType;

    /**
     * 阶层名称
     */
    private String levelName;
    /**
     * 成本科目阶层编码
     */
    private String costCodeLevelCode;

    /**
     * 成本科目中文说明
     */
    private String costDesc;
    /**
     * 父键
     */
    private String parentId;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用来封装子节点数据
     */
    List<FinanceCostDownTypeTreeDTO> children ;

    public FinanceCostDownTypeTreeDTO() {
    }

    public FinanceCostDownTypeTreeDTO(String costCodeLevelCode,String parentId, String id, String upperCode, String levelCode,String levelName,String nodeType, List<FinanceCostDownTypeTreeDTO> children) {
        this.id = id;
        this.upperCode = upperCode;
        this.levelCode = levelCode;
        this.levelName = levelName;
        this.nodeType = nodeType;
        this.costCodeLevelCode =costCodeLevelCode;
        this.parentId = parentId;
        this.children = children;

    }

    public FinanceCostDownTypeTreeDTO(FinanceCostDownTypeTreeDTO financeCostDownTypeTreeDTO) {
        this.id = financeCostDownTypeTreeDTO.getId();
        this.upperCode = financeCostDownTypeTreeDTO.getUpperCode();
        this.levelCode = financeCostDownTypeTreeDTO.getLevelCode();
        this.levelName = financeCostDownTypeTreeDTO.getLevelName();
        this.nodeType = financeCostDownTypeTreeDTO.getNodeType();
        this.parentId = financeCostDownTypeTreeDTO.getParentId();
        this.costCodeLevelCode = financeCostDownTypeTreeDTO.getCostCodeLevelCode();
        this.children = financeCostDownTypeTreeDTO.getChildren();
    }

}
