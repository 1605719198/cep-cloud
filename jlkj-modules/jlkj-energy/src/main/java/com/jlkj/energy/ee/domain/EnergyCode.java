package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 能源项目基本资料档
 *
 * @author 265675
 * @TableName energy_ee_code
 */
@Data
@TableName("energy_ee_code")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EnergyCode  extends BaseEntity {
    private static final long serialVersionUID = 1L;
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
     * 能源名称
     */
    @NotBlank(message = "能源名称不能为空")
    private String engyName;

    /**
     * 能源缩写
     */
    @NotBlank(message = "能源缩写不能为空")
    private String engyAc;

    /**
     * 计量单位
     */
    @NotBlank(message = "计量单位不能为空")
    private String engyUnit;

    /**
     * 热值系数
     */
    @Digits(integer = 10, fraction = 2, message = "热值系数格式错误")
    @DecimalMin(value = "0.00", message = "热值系数最小值不能低于0.00元")
    @DecimalMax(value = "1000000.00", message = "热值系数最大值不能高于1000000.00元")
    @NotNull(message = "热值系数不可为空")
    private BigDecimal calValue;

    /**
     * 热值单位
     */
    private String calUnit;

    /**
     * 能源类别
     */
    @NotBlank(message = "能源类别不能为空")
    private String engyType;

    /**
     * 来源方式
     */
    private String srcType;

    /**
     * 抛帐对象
     */
    @NotBlank(message = "抛帐对象不能为空")
    private String acctSys;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

}
