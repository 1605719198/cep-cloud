package com.jlkj.finance.ao.domain;

import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 个人信息设置对象 finance_ao_card
 *
 * @author SunXuTong
 * @date 2023-05-08
 */
public class FinanceAoCard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String companyId;

    /** 工号 */
    private String userNo;

    /** 部门 */
    private String deptId;

    /** 银行卡号 */
    private String cardNo;

    /** 开户银行 */
    private String janBudget;

    /** 联行号 */
    private String bankNo;

    /** 开户地区 */
    private String area;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 联系电话 */
    private String phoneNo;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setUserNo(String userNo)
    {
        this.userNo = userNo;
    }

    public String getUserNo()
    {
        return userNo;
    }
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptId()
    {
        return deptId;
    }
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    public String getCardNo()
    {
        return cardNo;
    }
    public void setJanBudget(String janBudget)
    {
        this.janBudget = janBudget;
    }

    public String getJanBudget()
    {
        return janBudget;
    }
    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }

    public String getBankNo()
    {
        return bankNo;
    }
    public void setArea(String area)
    {
        this.area = area;
    }

    public String getArea()
    {
        return area;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getProvince()
    {
        return province;
    }
    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCity()
    {
        return city;
    }
    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo()
    {
        return phoneNo;
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
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uuid", getUuid())
            .append("companyId", getCompanyId())
            .append("userNo", getUserNo())
            .append("deptId", getDeptId())
            .append("cardNo", getCardNo())
            .append("janBudget", getJanBudget())
            .append("bankNo", getBankNo())
            .append("area", getArea())
            .append("province", getProvince())
            .append("city", getCity())
            .append("phoneNo", getPhoneNo())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
