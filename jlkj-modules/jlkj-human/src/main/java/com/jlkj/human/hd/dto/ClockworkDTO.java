package com.jlkj.human.hd.dto;

import lombok.Data;

/**
 * 卡钟查询
 * @author 266861
 * @date 2023-03-08
 */
@Data
public class ClockworkDTO {
    /**
     * 卡钟编号
     */
    private String code;

    /**
     * 卡钟名称
     */
    private String name;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 一级部门id
     */
    private String firstDeptId;

    /**
     * 一级部门名称
     */

    private String firstDeptName;

}
