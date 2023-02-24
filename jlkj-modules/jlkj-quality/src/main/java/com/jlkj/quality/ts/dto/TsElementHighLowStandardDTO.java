package com.jlkj.quality.ts.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.jlkj.quality.ts.domain.ElementHighLowStandard;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/16 - 16:11
 */
@Data
public class TsElementHighLowStandardDTO {
    /**
     * 唯一标识
     */
    @TableId
    private String id;

    /**
     * 样品类别
     */
    private String sampleClass;

    /**
     * 元素类别
     */
    private String elementType;

    /**
     * 元素代号
     */
    private String elementIndex;

    /**
     * 元素名称/试验项目
     */
    private String elementName;

    /**
     * 单位
     */
    private String elementUnit;

    /**
     * 最低值
     */
    private BigDecimal minValue;

    /**
     * 最高值
     */
    private BigDecimal maxValue;

    /**
     * 异动人员
     */
    private String modifyUserId;

    /**
     * 新增人员
     */
    private String createUser;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 异动日期
     */
    private String modifyTime;

    /**
     * 新增人职工编号
     */
    private String createUserId;

    /**
     * elementHighLowStandard数据
     */
    private List<ElementHighLowStandard> elementHighLowStandard;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
