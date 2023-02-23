package com.jlkj.common.dto.dto.qualityprovider;

import lombok.Data;

/**
 * @author 智能研发室 黄兵
 * @date 2022/6/8 - 14:28
 */
@Data
public class TsExperimentRecordCokeDTO {
    /**
     * 查询日期开始值
     */
    private String startDate;

    /**
     * 查询日期结束值
     */
    private String endDate;

    /**
     * 班别
     */
    private String className;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
