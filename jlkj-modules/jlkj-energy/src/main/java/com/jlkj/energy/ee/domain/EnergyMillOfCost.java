package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 产线代码对应成本中心资料
 * @TableName energy_ee_mill_of_cost
 * @author 265675
 */
@TableName(value ="energy_ee_mill_of_cost")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EnergyMillOfCost extends BaseEntity {
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
     * 工场产线代号
     */
    @NotBlank(message = "产线代号不能为空")
    private String millIdCode;

    /**
     * 工场产线中文名称
     */
    @NotBlank(message = "工场产线中文名称不能为空")
    private String millIdName;

    /**
     * 成本中心代号
     */
    @NotBlank(message = "成本中心代号不能为空")
    private String costCenter;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
