package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liukuiyan
 * @TableName v_energy_board_view1
 */
@TableName(value ="energy_ee_board_view1")
@Data
public class EnergyBoardView1 implements Serializable {
    /**
     *
     */
    private String energyCode;

    /**
     *
     */
    private String energyCodeName;

    /**
     *
     */
    private String energyType;

    /**
     *
     */
    private String energyTypeName;

    /**
     * 成本中心代号
     */
    private String costCenter;

    /**
     * 能源代码
     */
    private String engyId;

    /**
     * 耗用产出类别代号
     */
    private String ioTypeId;

    /**
     * 能源类别
     */
    private String engyType;

    /**
     * 卖方成本中心
     */
    private String chargeCostCenter;

    /**
     *
     */
    private BigDecimal qty;

    /**
     * 能源计量日期
     */
    private String eCountDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
