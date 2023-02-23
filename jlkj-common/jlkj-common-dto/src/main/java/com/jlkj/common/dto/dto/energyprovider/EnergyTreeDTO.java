package com.jlkj.common.dto.dto.energyprovider;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/6 - 10:33
 */
@Data
public class EnergyTreeDTO {
    /**
     * ID
     */
    @TableId
    private String itemId;

    /**
     * 主档id
     */
    private String pid;

    /**
     * 序号
     */
    private String serialNo;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 类别
     */
    private String types;

    /**
     * 状态
     */
    private String status;

    /**
     * 字段1的值
     */
    private String column1;

    /**
     * 字段2的值
     */
    private String column2;

    /**
     * 字段3的值
     */
    private String column3;

    /**
     * 字段4的值
     */
    private String column4;

    /**
     * 字段5的值
     */
    private String column5;

    /**
     * 能源代码(ERP)
     */
    private String engyId;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;
}
