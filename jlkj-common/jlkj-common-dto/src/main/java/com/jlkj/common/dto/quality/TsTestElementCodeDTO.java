package com.jlkj.common.dto.quality;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/11 - 11:18
 */
@Data
public class TsTestElementCodeDTO {
    /**
     * 唯一标识
     */
    @TableId
    private String id;

    /**
     * 元素类别
     */
    private String elementType;

    /**
     * 试验元素序号
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
     * 整数字数
     */
    private Integer integerNo;

    /**
     * 小数字数
     */
    private Integer pointerNo;

    /**
     * 建档人员
     */
    private String createEmployNo;

    /**
     * 建立人员
     */
    private String createUser;

    /**
     * 建档日期
     */
    private String createDate;

    /**
     * 建档日期
     */
    private String createTime;

    /**
     * 异动人员
     */
    private String modifyEmployNo;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 异动日期
     */
    private String modifyDate;

    /**
     * 异动日期
     */
    private String updateTime;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
