package com.jlkj.common.dto.energy.ee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 智能研发室 黄兵
 * @date 2022/4/29 - 8:40
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergyCodeDTO {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
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
     * 能源缩写
     */
    private String engyAc;

    /**
     * 计量单位
     */
    private String engyUnit;

    /**
     * 热值
     */
    private BigDecimal calValue;

    /**
     * 热值单位
     */
    private String calUnit;

    /**
     * 能源类别
     */
    private String engyType;

    /**
     * 来源方式
     */
    private String srcType;

    /**
     * 抛帐对象
     */
    private String acctSys;

    /**
     * 建立人员
     */
    private String createEmpNo;

    /**
     * 修改人员
     */
    private String updateEmpNo;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

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
