package com.jlkj.common.dto.dto.energyprovider;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author 智能研发室 黄兵
 * @date 2022/4/29 - 9:39
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergyCodeFoMaterialDTO {

    /**
     * ID
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 能源代码
     */
    private String engyId;

    /**
     * 能源名称
     */
    private String engyName;

    /**
     * 成本中心代号
     */
    private String costCenter;

    /**
     * 能源量类型
     */
    private String engyClass;

    /**
     * 来源系统
     */
    private String engySource;

    /**
     * 料号1
     */
    private String engyCmp1No;

    /**
     * 料号2
     */
    private String engyCmp2No;

    /**
     * 料号3
     */
    private String engyCmp3No;

    /**
     * 料号4
     */
    private String engyCmp4No;

    /**
     * 料号5
     */
    private String engyCmp5No;

    /**
     * 料号6
     */
    private String engyCmp6No;

    /**
     * 料号7
     */
    private String engyCmp7No;

    /**
     * 料号8
     */
    private String engyCmp8No;

    /**
     * 料号9
     */
    private String engyCmp9No;

    /**
     * 料号10
     */
    private String engyCmp10No;

    /**
     * 建立人员
     */
    private String createEmpNo;

    /**
     * 修改人员
     */
    private String updateEmpNo;

    /**
     * 能源类别
     */
    private String engyType;

    /**
     * 能源代码开始值
     */
    private String engyIdStart;

    /**
     * 能源代码结束值
     */
    private String engyIdEnd;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
