package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author auto
 * 能源项目基本资料档
 * @TableName t_energy_code
 */
@TableName(value ="energy_ee_code")
@Data
public class EnergyCode implements Serializable {
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
     * 建立时间
     */
    private Date createTime;

    /**
     * 修改人员
     */
    private String updateEmpNo;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
