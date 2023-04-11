package com.jlkj.finance.gp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 厂商增户证照资料对象 finance_gp_addfile
 *
 * @author 265799
 * @date 2023-04-10
 */
public class FinanceGpAddfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 厂商名称 */
    private String manufacturerName;
    /** 状态 */
    @Excel(name = "状态")
    private String status;
    /** 主键 */
    private String uuid;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 申请单号 */
    @Excel(name = "申请单号")
    private String applyId;

    /** 申请单类型 */
    @Excel(name = "申请单类型")
    private String applyType;

    /** 文件类型 */
    private String filetype;

    /** 文件 */
    @Excel(name = "文件")
    private String filebase64;

    /** 文件路径 */
    private String fileUrl;

    /** 文件名 */
    private String fileName;

    /** 上传资料类型 */
    @Excel(name = "上传资料类型")
    private String dataType;

    /** 修改日期 */
    private String updateDate;

    /** 修改人 */
    private String updateUser;

    /** 创建人 */
    private String createUser;

    /** 修改人姓名 */
    private String updateUserName;

    /** 创建人姓名 */
    private String createUserName;
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setManufacturerName(String manufacturerName)
    {
        this.manufacturerName = manufacturerName;
    }
    public String getManufacturerName()
    {
        return manufacturerName;
    }
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
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
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }

    public String getApplyId()
    {
        return applyId;
    }
    public void setApplyType(String applyType)
    {
        this.applyType = applyType;
    }

    public String getApplyType()
    {
        return applyType;
    }
    public void setFiletype(String filetype)
    {
        this.filetype = filetype;
    }

    public String getFiletype()
    {
        return filetype;
    }
    public void setFilebase64(String filebase64)
    {
        this.filebase64 = filebase64;
    }

    public String getFilebase64()
    {
        return filebase64;
    }
    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl()
    {
        return fileUrl;
    }
    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getDataType()
    {
        return dataType;
    }
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getUpdateDate()
    {
        return updateDate;
    }
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateUser()
    {
        return createUser;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uuid", getUuid())
            .append("compId", getCompId())
            .append("fileName", getFileName())
            .append("applyId", getApplyId())
            .append("applyType", getApplyType())
            .append("status", getStatus())
            .append("manufacturerName", getManufacturerName())
            .append("filetype", getFiletype())
            .append("filebase64", getFilebase64())
            .append("fileUrl", getFileUrl())
            .append("dataType", getDataType())
            .append("updateDate", getUpdateDate())
            .append("updateUser", getUpdateUser())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("updateUserName", getUpdateUserName())
            .append("createUserName", getCreateUserName())
            .toString();
    }
}
