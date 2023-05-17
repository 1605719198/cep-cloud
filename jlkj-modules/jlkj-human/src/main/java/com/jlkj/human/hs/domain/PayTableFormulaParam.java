package com.jlkj.human.hs.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 公司薪资计算参数对象 human_hs_pay_table_formula_param
 *
 * @author 266861
 * @date 2023-05-15
 */
public class PayTableFormulaParam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String compId;

    /** 是否选择 */
    private String isCheck;

    /** 参数代码 */
    @Excel(name = "参数代码")
    private String paramCode;

    /** 参数名称 */
    @Excel(name = "参数名称")
    private String paramName;

    /** 参数类别 */
    @Excel(name = "参数类别")
    private String paramTypeId;

    /** 是否系统自动生成 */
    private String isAuto;

    /** 参数来源 */
    private String paramSrcId;

    /** 状态 */
    private String status;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

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
    public void setIsCheck(String isCheck)
    {
        this.isCheck = isCheck;
    }

    public String getIsCheck()
    {
        return isCheck;
    }
    public void setParamCode(String paramCode)
    {
        this.paramCode = paramCode;
    }

    public String getParamCode()
    {
        return paramCode;
    }
    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }

    public String getParamName()
    {
        return paramName;
    }
    public void setParamTypeId(String paramTypeId)
    {
        this.paramTypeId = paramTypeId;
    }

    public String getParamTypeId()
    {
        return paramTypeId;
    }
    public void setIsAuto(String isAuto)
    {
        this.isAuto = isAuto;
    }

    public String getIsAuto()
    {
        return isAuto;
    }
    public void setParamSrcId(String paramSrcId)
    {
        this.paramSrcId = paramSrcId;
    }

    public String getParamSrcId()
    {
        return paramSrcId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId()
    {
        return creatorId;
    }
    public void setCreatorNo(String creatorNo)
    {
        this.creatorNo = creatorNo;
    }

    public String getCreatorNo()
    {
        return creatorNo;
    }
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uuid", getUuid())
            .append("compId", getCompId())
            .append("paramCode", getParamCode())
            .append("paramName", getParamName())
            .append("paramTypeId", getParamTypeId())
            .append("isAuto", getIsAuto())
            .append("paramSrcId", getParamSrcId())
            .append("status", getStatus())
            .append("creatorId", getCreatorId())
            .append("creatorNo", getCreatorNo())
            .append("creator", getCreator())
            .append("createDate", getCreateDate())
            .toString();
    }
}
