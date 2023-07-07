package com.jlkj.finance.aa.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会计科目-公司级对象 finance_aa_acctcode
 *
 * @author 116514
 * @date 2023-04-19
 */
public class FinanceAaAcctcodeCorp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会计科目主键 */
    private String acctId;

    /** 会计公司id */
    private String companyId;

    /** 集团会计科目主键 */
    private String groupAcctId;

    /** 会计科目编号 */
    @Excel(name = "会计科目编号")
    private String acctCode;

    /** 会计科目中文名称 */
    @Excel(name = "会计科目中文名称")
    private String acctName;

    /** 上层会计科目ID */
    private String parentId;

    /** 上层会计科目编号 */
    private String parentAcctCode;

    /** 传票性科目 */
    @Excel(name = "传票性科目",readConverterExp = "Y=是,N=否")
    private String isVoucher;

    /** 借/贷方会计科目 */
    @Excel(name = "借贷方",readConverterExp = "D=借方,C=贷方")
    private String drOrCr;

    /** 现金科目 */
    @Excel(name = "现金科目",readConverterExp = "Y=是,N=否")
    private String isCash;

    /** 级别 */
    private int level;

    /** 祖级列表 */
    private String ancestors;

    /** 核算项目一id */
    private String calTypeIda;

    /** 核算项目一编号 */
    @Excel(name = "核算项目一编号")
    private String calTypeCodea;

    /** 核算项目二id */
    private String calTypeIdb;

    /** 核算项目二编号 */
    @Excel(name = "核算项目二编号")
    private String calTypeCodeb;

    /** 核算项目三id */
    private String calTypeIdc;

    /** 核算项目三编号 */
    @Excel(name = "核算项目三编号")
    private String calTypeCodec;

    /** 核算项目四id */
    private String calTypeIdd;

    /** 核算项目四编号 */
    @Excel(name = "核算项目四编号")
    private String calTypeCoded;

    /** 到期日 */
    @Excel(name = "到期日核算",readConverterExp = "Y=是,N=否")
    private String isDueDate;

    /** 数量金额辅助核算（单位） */
    @Excel(name = "数量金额辅助核算", readConverterExp = "Y=是,N=否")
    private String isUnit;

    /** 悬计帐 */
    private String isLqdt;

    /** 外币核算（币别） */
    @Excel(name = "外币核算", readConverterExp = "Y=是,N=否")
    private String isFrnCrcy;

    /** 机器专用 */
    @Excel(name = "机器专用", readConverterExp = "Y=是,N=否")
    private String isPrvlg;

    /** 禁用标识 */
    @Excel(name = "状态",readConverterExp = "Y=启用,N=禁用")
    private String disabledCode;

    /** 禁用日期 */
    private Date disabledDate;

    /** 新增人名称 */
    private String createName;

    /** 异动人名称 */
    @Excel(name = "修改人")
    private String updateName;


    /** 修改时间 */
    @Excel(name = "修改时间",dateFormat="yyyy-MM-dd")
    private Date updateTime;

    private String calTypeName1;
    private String calTypeName2;
    private String calTypeName3;
    private String calTypeName4;

    /** 子节点 */
    private List<FinanceAaAcctcodeCorp> children = new ArrayList<FinanceAaAcctcodeCorp>();

    public void setAcctId(String acctId)
    {
        this.acctId = acctId;
    }

    public String getAcctId()
    {
        return acctId;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setGroupAcctId(String groupAcctId)
    {
        this.groupAcctId = groupAcctId;
    }

    public String getGroupAcctId()
    {
        return groupAcctId;
    }
    public void setAcctCode(String acctCode)
    {
        this.acctCode = acctCode;
    }

    public String getAcctCode()
    {
        return acctCode;
    }
    public void setAcctName(String acctName)
    {
        this.acctName = acctName;
    }

    public String getAcctName()
    {
        return acctName;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setParentAcctCode(String parentAcctCode)
    {
        this.parentAcctCode = parentAcctCode;
    }

    public String getParentAcctCode()
    {
        return parentAcctCode;
    }
    public void setIsVoucher(String isVoucher)
    {
        this.isVoucher = isVoucher;
    }

    public String getIsVoucher()
    {
        return isVoucher;
    }
    public void setDrOrCr(String drOrCr)
    {
        this.drOrCr = drOrCr;
    }

    public String getDrOrCr()
    {
        return drOrCr;
    }
    public void setIsCash(String isCash)
    {
        this.isCash = isCash;
    }

    public String getIsCash()
    {
        return isCash;
    }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }
    public void setIsUnit(String isUnit)
    {
        this.isUnit = isUnit;
    }

    public String getIsUnit()
    {
        return isUnit;
    }
    public void setCalTypeIda(String calTypeIda)
    {
        this.calTypeIda = calTypeIda;
    }

    public String getCalTypeIda()
    {
        return calTypeIda;
    }
    public void setCalTypeCodea(String calTypeCodea)
    {
        this.calTypeCodea = calTypeCodea;
    }

    public String getCalTypeCodea()
    {
        return calTypeCodea;
    }
    public void setCalTypeIdb(String calTypeIdb)
    {
        this.calTypeIdb = calTypeIdb;
    }

    public String getCalTypeIdb()
    {
        return calTypeIdb;
    }
    public void setCalTypeCodeb(String calTypeCodeb)
    {
        this.calTypeCodeb = calTypeCodeb;
    }

    public String getCalTypeCodeb()
    {
        return calTypeCodeb;
    }
    public void setCalTypeIdc(String calTypeIdc)
    {
        this.calTypeIdc = calTypeIdc;
    }

    public String getCalTypeIdc()
    {
        return calTypeIdc;
    }
    public void setCalTypeCodec(String calTypeCodec)
    {
        this.calTypeCodec = calTypeCodec;
    }

    public String getCalTypeCodec()
    {
        return calTypeCodec;
    }
    public void setCalTypeIdd(String calTypeIdd)
    {
        this.calTypeIdd = calTypeIdd;
    }

    public String getCalTypeIdd()
    {
        return calTypeIdd;
    }
    public void setCalTypeCoded(String calTypeCoded)
    {
        this.calTypeCoded = calTypeCoded;
    }

    public String getCalTypeCoded()
    {
        return calTypeCoded;
    }
    public void setIsDueDate(String isDueDate)
    {
        this.isDueDate = isDueDate;
    }

    public String getIsDueDate()
    {
        return isDueDate;
    }
    public void setIsLqdt(String isLqdt)
    {
        this.isLqdt = isLqdt;
    }

    public String getIsLqdt()
    {
        return isLqdt;
    }
    public void setIsFrnCrcy(String isFrnCrcy)
    {
        this.isFrnCrcy = isFrnCrcy;
    }

    public String getIsFrnCrcy()
    {
        return isFrnCrcy;
    }
    public void setIsPrvlg(String isPrvlg)
    {
        this.isPrvlg = isPrvlg;
    }

    public String getIsPrvlg()
    {
        return isPrvlg;
    }
    public void setDisabledCode(String disabledCode)
    {
        this.disabledCode = disabledCode;
    }

    public String getDisabledCode()
    {
        return disabledCode;
    }
    public void setDisabledDate(Date disabledDate)
    {
        this.disabledDate = disabledDate;
    }

    public Date getDisabledDate()
    {
        return disabledDate;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    @Override
    public Date getUpdateTime() { return updateTime; }

    @Override
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public List<FinanceAaAcctcodeCorp> getChildren() { return children; }

    public void setChildren(List<FinanceAaAcctcodeCorp> children) { this.children = children; }

    public String getCalTypeName1() {
        return calTypeName1;
    }

    public void setCalTypeName1(String calTypeName1) {
        this.calTypeName1 = calTypeName1;
    }

    public String getCalTypeName2() {
        return calTypeName2;
    }

    public void setCalTypeName2(String calTypeName2) {
        this.calTypeName2 = calTypeName2;
    }

    public String getCalTypeName3() {
        return calTypeName3;
    }

    public void setCalTypeName3(String calTypeName3) {
        this.calTypeName3 = calTypeName3;
    }

    public String getCalTypeName4() {
        return calTypeName4;
    }

    public void setCalTypeName4(String calTypeName4) {
        this.calTypeName4 = calTypeName4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("acctId", getAcctId())
            .append("companyId", getCompanyId())
            .append("groupAcctId", getGroupAcctId())
            .append("acctCode", getAcctCode())
            .append("acctName", getAcctName())
            .append("parentId", getParentId())
            .append("parentAcctCode", getParentAcctCode())
            .append("isVoucher", getIsVoucher())
            .append("drOrCr", getDrOrCr())
            .append("isCash", getIsCash())
            .append("level", getLevel())
            .append("ancestors", getAncestors())
            .append("isUnit", getIsUnit())
            .append("calTypeIda", getCalTypeIda())
            .append("calTypeCodea", getCalTypeCodea())
            .append("calTypeIdb", getCalTypeIdb())
            .append("calTypeCodeb", getCalTypeCodeb())
            .append("calTypeIdc", getCalTypeIdc())
            .append("calTypeCodec", getCalTypeCodec())
            .append("calTypeIdd", getCalTypeIdd())
            .append("calTypeCoded", getCalTypeCoded())
            .append("isDueDate", getIsDueDate())
            .append("isLqdt", getIsLqdt())
            .append("isFrnCrcy", getIsFrnCrcy())
            .append("isPrvlg", getIsPrvlg())
            .append("disabledCode", getDisabledCode())
            .append("disabledDate", getDisabledDate())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
