package com.jlkj.quality.ts.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.jlkj.quality.ts.domain.ElementTableData;
import lombok.Data;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/17 - 16:20
 */
@Data
public class TsElementTableDataDTO {
    /**
     * 元素编号
     */
    @TableId
    private String id;

    /**
     * 表格代号
     */
    private String formatId;

    /**
     * 表格说明
     */
    private String formatMemo;

    /**
     * 元素类别
     */
    private String elementType;

    /**
     * 试验元素代号
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
     * 元素显示顺序
     */
    private Integer elementSeq;

    /**
     * 表格元素数量
     */
    private Integer elementCount;

    /**
     * 建档人员
     */
    private String createUserId;

    /**
     * 异动人员
     */
    private String modifyUserId;

    /**
     * 异动日期
     */
    private String modifyTime;

    /**
     * elementTable数据
     */
    private List<ElementTableData> elementTable;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 新增人员
     */
    private String createUser;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
