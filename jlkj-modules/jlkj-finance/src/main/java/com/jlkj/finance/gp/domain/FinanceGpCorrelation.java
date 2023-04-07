package com.jlkj.finance.gp.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 是否关联方更新记录对象 finance_gp_correlation
 *
 * @author 265799
 * @date 2023-04-06
 */
public class FinanceGpCorrelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uuid;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 厂商编码 */
    @Excel(name = "厂商编码")
    private String manufacturerId;

    /** 当前状态 */
    @Excel(name = "当前状态")
    private String currentStatus;

    /** 前状态 */
    @Excel(name = "前状态")
    private String preStatus;

    /** 关联日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "关联日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date correlationDate;

    /** 建立人 */
    private String createUser;

    /** 建立日期 */
    private Date createDate;

    /** 最后修改人 */
    private String updateUser;

    /** 修改人姓名 */
    private String updateUserName;

    /** 创建人姓名 */
    private String createUserName;

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
    public void setCurrentStatus(String currentStatus)
    {
        this.currentStatus = currentStatus;
    }

    public String getCurrentStatus()
    {
        return currentStatus;
    }
    public void setPreStatus(String preStatus)
    {
        this.preStatus = preStatus;
    }

    public String getPreStatus()
    {
        return preStatus;
    }
    public void setCorrelationDate(Date correlationDate)
    {
        this.correlationDate = correlationDate;
    }

    public Date getCorrelationDate()
    {
        return correlationDate;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateUser()
    {
        return createUser;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
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
    public void setUpdateUserName(String updateUserName)
    {
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserName()
    {
        return updateUserName;
    }
    public void setCreateUserName(String createUserName)
    {
        this.createUserName = createUserName;
    }

    public String getCreateUserName()
    {
        return createUserName;
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
            .append("currentStatus", getCurrentStatus())
            .append("preStatus", getPreStatus())
            .append("correlationDate", getCorrelationDate())
            .append("createUser", getCreateUser())
            .append("createDate", getCreateDate())
            .append("updateUser", getUpdateUser())
            .append("updateUserName", getUpdateUserName())
            .append("createUserName", getCreateUserName())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
