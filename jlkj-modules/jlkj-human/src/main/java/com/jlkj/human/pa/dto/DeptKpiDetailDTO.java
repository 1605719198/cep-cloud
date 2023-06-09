package com.jlkj.human.pa.dto;

import com.jlkj.human.pa.domain.DeptKpiDetail;
import lombok.Data;

import java.util.List;


/**
 * 公司薪酬项目设定表
 * @author HuangBing
 * @TableName human_pa_dept_kpi
 */
@Data
public class DeptKpiDetailDTO {

    /**
     * 薪酬项目List
     */
    private List<DeptKpiDetail> deptKpiDetailList;

    /**
     * 单位KPI指标主档ID
     */
    private String id;
}
