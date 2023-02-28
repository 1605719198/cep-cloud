package com.jlkj.common.dto.energy.ee;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/5 - 8:56
 */
@Data
public class EnergyConsumeOutputDTO {
    /**
     * 时间序号
     */
    @TableId
    private String timeSeqNo;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 成本中心代号
     */
    private String costCenter;

    /**
     * 能源计量日期
     */
    private String eCountDate;

    /**
     * 能源代码
     */
    private String engyId;

    /**
     * 耗用产出类别代号
     */
    private String ioTypeId;

    /**
     * 执行作业代码
     */
    private String execJobFunc;

    /**
     * 能源类别
     */
    private String engyType;

    /**
     * 工厂代号
     */
    private String factoryId;

    /**
     * 产线别代号
     */
    private String millId;

    /**
     * 抛帐对象
     */
    private String acctSys;

    /**
     * 卖方成本中心
     */
    private String chargeCostCenter;

    /**
     * 卖方工厂代号
     */
    private String chargeFactoryId;

    /**
     * 能源产耗量
     */
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
     * 是否抛AM
     */
    private String throwAm;

    /**
     * 能源计量日期开始
     */
    private String eCountDateStart;

    /**
     * 能源计量日期结束
     */
    private String eCountDateEnd;

    /**
     * 成本中心1
     */
    private String costCenter1;

    /**
     * 成本中心2
     */
    private String costCenter2;

    /**
     * 能源代码开始
     */
    private String engyIdStart;

    /**
     * 能源代码结束
     */
    private String engyIdEnd;

    /**
     * 报文信息
     */
    private String MessageInfo;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
