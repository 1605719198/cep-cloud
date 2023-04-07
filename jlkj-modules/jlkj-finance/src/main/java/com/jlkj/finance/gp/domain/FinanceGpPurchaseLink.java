package com.jlkj.finance.gp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 厂商采购关系联系人对象 finance_gp_purchase_link
 *
 * @author 265799
 * @date 2023-04-07
 */
public class FinanceGpPurchaseLink extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uuid;

    /** 公司 */
    @Excel(name = "公司")
    private String compId;

    /** 厂商编码 */
    @Excel(name = "厂商编码")
    private String manufacturerId;

    /** 联络人 */
    @Excel(name = "联络人")
    private String contact;

    /** 部门 */
    @Excel(name = "部门")
    private String dept;

    /** 职称 */
    @Excel(name = "职称")
    private String title;

    /** 电话 */
    @Excel(name = "电话")
    private String tel;

    /** 行动电话 */
    @Excel(name = "行动电话")
    private String moblie;

    /** 邮件 */
    @Excel(name = "邮件")
    private String email;

    /** 建立人 */
    private String createUser;

    /** 建立日期 */
    private String createDate;

    /** 最后异动人 */
    private String updateUser;

    /** 最后异动日期 */
    private String updateDate;

    /** 创建人姓名 */
    private String createUserName;

    /** 修改人姓名 */
    private String updateUserName;

    /** 逻辑删除标识 */
    private Long delFlag;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getContact()
    {
        return contact;
    }
    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public String getDept()
    {
        return dept;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getTel()
    {
        return tel;
    }
    public void setMoblie(String moblie)
    {
        this.moblie = moblie;
    }

    public String getMoblie()
    {
        return moblie;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateUser()
    {
        return createUser;
    }
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getCreateDate()
    {
        return createDate;
    }
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getUpdateDate()
    {
        return updateDate;
    }
    public void setCreateUserName(String createUserName)
    {
        this.createUserName = createUserName;
    }

    public String getCreateUserName()
    {
        return createUserName;
    }
    public void setUpdateUserName(String updateUserName)
    {
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserName()
    {
        return updateUserName;
    }
    public void setDelFlag(Long delFlag)
    {
        this.delFlag = delFlag;
    }

    public Long getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uuid", getUuid())
            .append("compId", getCompId())
            .append("manufacturerId", getManufacturerId())
            .append("contact", getContact())
            .append("dept", getDept())
            .append("title", getTitle())
            .append("tel", getTel())
            .append("moblie", getMoblie())
            .append("email", getEmail())
            .append("createUser", getCreateUser())
            .append("createDate", getCreateDate())
            .append("updateUser", getUpdateUser())
            .append("updateDate", getUpdateDate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createUserName", getCreateUserName())
            .append("updateUserName", getUpdateUserName())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
