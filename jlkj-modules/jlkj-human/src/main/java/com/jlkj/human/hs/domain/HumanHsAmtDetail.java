package com.jlkj.human.hs.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 薪资应付明细对象 human_hs_amt_detail
 *
 * @author jlkj
 * @date 2023-05-24
 */
public class HumanHsAmtDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 主档id */
    @Excel(name = "主档id")
    private String amtId;

    /** 公司id */
    @Excel(name = "公司id")
    private String compId;

    /** 应发金额 */
    @Excel(name = "应发金额")
    private BigDecimal amtPayable;

    /** 实发金额 */
    @Excel(name = "实发金额")
    private BigDecimal amtNet;

    /** 缓发金额 */
    @Excel(name = "缓发金额")
    private BigDecimal amtDeferred;

    /** 福利金额(餐补) */
    @Excel(name = "福利金额(餐补)")
    private BigDecimal amtBenefitMeal;

    /** 福利金额(住房) */
    @Excel(name = "福利金额(住房)")
    private BigDecimal amtBenefitHouse;

    /** 培训费 */
    @Excel(name = "培训费")
    private BigDecimal amtTraining;

    /** 预留 */
    @Excel(name = "预留")
    private BigDecimal amtA;

    /** 预留 */
    @Excel(name = "预留")
    private BigDecimal amtB;

    /** 预留 */
    @Excel(name = "预留")
    private BigDecimal amtC;

    /** 工资支付银行 */
    @Excel(name = "工资支付银行")
    private String salBank;

    /** 工资支付银行ID */
    @Excel(name = "工资支付银行ID")
    private String salBankId;

    /** 工资银行账号 */
    @Excel(name = "工资银行账号")
    private String salBankNo;

    /** 奖金支付银行 */
    @Excel(name = "奖金支付银行")
    private String bonBank;

    /** 奖金支付银行ID */
    @Excel(name = "奖金支付银行ID")
    private String bonBankId;

    /** 奖金银行账号 */
    @Excel(name = "奖金银行账号")
    private String bonBankNo;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    private String createName;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setAmtId(String amtId)
    {
        this.amtId = amtId;
    }

    public String getAmtId()
    {
        return amtId;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setAmtPayable(BigDecimal amtPayable)
    {
        this.amtPayable = amtPayable;
    }

    public BigDecimal getAmtPayable()
    {
        return amtPayable;
    }
    public void setAmtNet(BigDecimal amtNet)
    {
        this.amtNet = amtNet;
    }

    public BigDecimal getAmtNet()
    {
        return amtNet;
    }
    public void setAmtDeferred(BigDecimal amtDeferred)
    {
        this.amtDeferred = amtDeferred;
    }

    public BigDecimal getAmtDeferred()
    {
        return amtDeferred;
    }
    public void setAmtBenefitMeal(BigDecimal amtBenefitMeal)
    {
        this.amtBenefitMeal = amtBenefitMeal;
    }

    public BigDecimal getAmtBenefitMeal()
    {
        return amtBenefitMeal;
    }
    public void setAmtBenefitHouse(BigDecimal amtBenefitHouse)
    {
        this.amtBenefitHouse = amtBenefitHouse;
    }

    public BigDecimal getAmtBenefitHouse()
    {
        return amtBenefitHouse;
    }
    public void setAmtTraining(BigDecimal amtTraining)
    {
        this.amtTraining = amtTraining;
    }

    public BigDecimal getAmtTraining()
    {
        return amtTraining;
    }
    public void setAmtA(BigDecimal amtA)
    {
        this.amtA = amtA;
    }

    public BigDecimal getAmtA()
    {
        return amtA;
    }
    public void setAmtB(BigDecimal amtB)
    {
        this.amtB = amtB;
    }

    public BigDecimal getAmtB()
    {
        return amtB;
    }
    public void setAmtC(BigDecimal amtC)
    {
        this.amtC = amtC;
    }

    public BigDecimal getAmtC()
    {
        return amtC;
    }
    public void setSalBank(String salBank)
    {
        this.salBank = salBank;
    }

    public String getSalBank()
    {
        return salBank;
    }
    public void setSalBankId(String salBankId)
    {
        this.salBankId = salBankId;
    }

    public String getSalBankId()
    {
        return salBankId;
    }
    public void setSalBankNo(String salBankNo)
    {
        this.salBankNo = salBankNo;
    }

    public String getSalBankNo()
    {
        return salBankNo;
    }
    public void setBonBank(String bonBank)
    {
        this.bonBank = bonBank;
    }

    public String getBonBank()
    {
        return bonBank;
    }
    public void setBonBankId(String bonBankId)
    {
        this.bonBankId = bonBankId;
    }

    public String getBonBankId()
    {
        return bonBankId;
    }
    public void setBonBankNo(String bonBankNo)
    {
        this.bonBankNo = bonBankNo;
    }

    public String getBonBankNo()
    {
        return bonBankNo;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("amtId", getAmtId())
            .append("compId", getCompId())
            .append("amtPayable", getAmtPayable())
            .append("amtNet", getAmtNet())
            .append("amtDeferred", getAmtDeferred())
            .append("amtBenefitMeal", getAmtBenefitMeal())
            .append("amtBenefitHouse", getAmtBenefitHouse())
            .append("amtTraining", getAmtTraining())
            .append("amtA", getAmtA())
            .append("amtB", getAmtB())
            .append("amtC", getAmtC())
            .append("salBank", getSalBank())
            .append("salBankId", getSalBankId())
            .append("salBankNo", getSalBankNo())
            .append("bonBank", getBonBank())
            .append("bonBankId", getBonBankId())
            .append("bonBankNo", getBonBankNo())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
