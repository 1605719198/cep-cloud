package com.jlkj.flow.workflow.domain.vo;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程模型对象导出VO
 *
 * @author xin
 */
@Data
@NoArgsConstructor
public class WfModelExportVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 模型ID
     */
    @Excel(name = "模型ID")
    private String modelId;
    /**
     * 模型Key
     */
    @Excel(name = "模型Key")
    private String modelKey;
    /**
     * 模型名称
     */
    @Excel(name = "模型名称")
    private String modelName;
    /**
     * 分类编码
     */
    @Excel(name = "分类编码")
    private String category;
    /**
     * 流程分类
     */
    @Excel(name = "流程分类")
    private String categoryName;
    /**
     * 模型版本
     */
    @Excel(name = "模型版本")
    private Integer version;
    /**
     * 模型描述
     */
    @Excel(name = "模型描述")
    private String description;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createTime;
}
