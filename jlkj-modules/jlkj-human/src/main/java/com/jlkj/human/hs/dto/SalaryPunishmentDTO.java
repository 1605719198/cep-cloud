package com.jlkj.human.hs.dto;

import com.jlkj.human.hs.domain.SalaryPunishment;
import lombok.Data;

import java.util.List;

/**
 * 公司薪酬项目设定表
 * @author HuangBing
 * @TableName human_hs_pay_formation
 */
@Data
public class SalaryPunishmentDTO {
    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;
    /**
     * 薪资类别ID
     */
    private String payType;

    /**
     * 公司ID
     */
    private String compId;

    /**
     * 公司ID
     */
    private List<String> ids;
    /**
     * 薪酬项目List
     */
    private List<SalaryPunishment> salaryPunishmentList;
}