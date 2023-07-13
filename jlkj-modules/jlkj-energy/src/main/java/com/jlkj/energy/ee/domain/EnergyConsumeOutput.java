package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 能源项目基本资料
 * @TableName energy_ee_consume_output
 * @author 265675
 */
@TableName(value ="energy_ee_consume_output")
@Data
public class EnergyConsumeOutput extends BaseEntity {
    /**
     * 雪花主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公司别
     */
    @TableField(fill = FieldFill.INSERT)
    private String compId;

    /**
     * 成本中心代号
     */
    @NotBlank(message = "成本中心代号,不能为空")
    private String costCenter;

    /**
     * 能源计量日期
     */
    @NotBlank(message = "能源计量日期,不能为空")
    private String eCountDate;

    /**
     * 能源代码
     */
    @NotBlank(message = "能源代码,不能为空")
    private String engyId;
    /**
     * 能源名称
     */
    @NotBlank(message = "能源名称,不能为空")
    private String engyName;
    /**
     * 耗用产出类别代号
     */
    @NotBlank(message = "耗用产出类别代号,不能为空")
    private String ioTypeId;

    /**
     * 执行作业代码
     */
    private String execJobFunc;

    /**
     * 能源类别
     */
    @NotBlank(message = "能源类别,不能为空")
    private String engyType;

    /**
     * 工厂代号
     */
    @NotBlank(message = "工厂代号,不能为空")
    private String factoryId;

    /**
     * 产线别代号
     */
    @NotBlank(message = "产线别代号,不能为空")
    private String millId;

    /**
     * 抛帐对象
     */
    private String acctSys;

    /**
     * 卖方成本中心
     */
    @NotBlank(message = "卖方成本中心,不能为空")
    private String chargeCostCenter;

    /**
     * 卖方工厂代号
     */
    @NotBlank(message = "卖方工厂代号,不能为空")
    private String chargeFactoryId;

    /**
     * 能源产耗量
     */
    @Range(message = "能源产耗量,不能为空，且必须为数字")
    private BigDecimal proConQty;

    /**
     * 财务用能源计量单位
     */
    private String engyUnitF;

    /**
     * 能源部用能源计量单位
     */
    private String engyUnit;

    /**
     * 能源热值
     */
    private BigDecimal calValue;

    /**
     * 热值单位
     */
    private String calUnit;

    /**
     * 能量值
     */
    private BigDecimal engyValue;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 是否抛AM
     */
    private String throwAm;
    /** 报文消息内容 */
    @TableField(exist = false)
    private String messageInfo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
