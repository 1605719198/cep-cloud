package com.jlkj.human.pa.dto;

import com.jlkj.human.pa.domain.DeptTypeDetail;
import lombok.Data;

import java.util.List;


/**
 * 人员类别考核项目设定明细档
 * @author HuangBing
 * @TableName human_pa_dept_detail_type
 */
@Data
public class DeptTypeDetailDTO {

    /**
     * 人员类别考核项目设定明细档List
     */
    private List<DeptTypeDetail> deptTypeDetailList;

}
