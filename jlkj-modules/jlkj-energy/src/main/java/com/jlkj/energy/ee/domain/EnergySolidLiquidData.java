package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 固体液体能源量资料
 * @TableName energy_ee_solid_liquid_data
 * @author 265675
 */
@TableName(value ="energy_ee_solid_liquid_data")
@Data
public class EnergySolidLiquidData extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 公司别
     */
    @TableField(fill = FieldFill.INSERT)
    private String compId;

    /**
     * 能源代码
     */
    @NotBlank(message = "能源代码不能为空")
    private String engyId;

    /**
     * 成本中心代号
     */
    @NotBlank(message = "能源代码不能为空")
    private String costCenter;

    /**
     * 能源量类型
     */
    @NotBlank(message = "能源代码不能为空")
    private String engyClass;

    /**
     * 来源系统
     */
    @NotBlank(message = "能源代码不能为空")
    private String engySource;

    /**
     * 计量单位
     */
    @NotBlank(message = "能源代码不能为空")
    private String engyUnit;

    /**
     * 能源类别
     */
    @NotBlank(message = "能源代码不能为空")
    private String engyType;

    /**
     * 能源计量日期
     */
    private String engyDate;

    /**
     * 能源量
     */
    private BigDecimal engyValue;

    /**
     * 料号1
     */
    private String engyCmp1No;

    /**
     * 料号名1
     */
    private String engyCmp1Name;

    /**
     * 料号量1
     */
    private BigDecimal engyCmp1Value;

    /**
     * 料号2
     */
    private String engyCmp2No;

    /**
     * 料号名2
     */
    private String engyCmp2Name;

    /**
     * 料号量2
     */
    private BigDecimal engyCmp2Value;

    /**
     * 料号3
     */
    private String engyCmp3No;

    /**
     * 料号名3
     */
    private String engyCmp3Name;

    /**
     * 料号量3
     */
    private BigDecimal engyCmp3Value;

    /**
     * 料号4
     */
    private String engyCmp4No;

    /**
     * 料号名4
     */
    private String engyCmp4Name;

    /**
     * 料号量4
     */
    private BigDecimal engyCmp4Value;

    /**
     * 料号5
     */
    private String engyCmp5No;

    /**
     * 料号名5
     */
    private String engyCmp5Name;

    /**
     * 料号量5
     */
    private BigDecimal engyCmp5Value;

    /**
     * 料号6
     */
    private String engyCmp6No;

    /**
     * 料号名6
     */
    private String engyCmp6Name;

    /**
     * 料号量6
     */
    private BigDecimal engyCmp6Value;

    /**
     * 料号7
     */
    private String engyCmp7No;

    /**
     * 料号名7
     */
    private String engyCmp7Name;

    /**
     * 料号量7
     */
    private BigDecimal engyCmp7Value;

    /**
     * 料号8
     */
    private String engyCmp8No;

    /**
     * 料号名8
     */
    private String engyCmp8Name;

    /**
     * 料号量8
     */
    private BigDecimal engyCmp8Value;

    /**
     * 料号9
     */
    private String engyCmp9No;

    /**
     * 料号名9
     */
    private String engyCmp9Name;

    /**
     * 料号量9
     */
    private BigDecimal engyCmp9Value;

    /**
     * 料号10
     */
    private String engyCmp10No;

    /**
     * 料号名10
     */
    private String engyCmp10Name;

    /**
     * 料号量10
     */
    private BigDecimal engyCmp10Value;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
