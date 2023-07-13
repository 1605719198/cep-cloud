package com.jlkj.human.pa.dto;

import com.jlkj.human.pa.domain.YearPerformance;
import lombok.Data;

import java.util.List;


/**
 * 年度绩效主档
 * @author HuangBing
 * @TableName human_pa_year_performance
 */
@Data
public class YearPerformanceDTO {

    /**
     * 年度绩效主档List
     */
    private List<YearPerformance> yearPerformanceList;

}
