package com.jlkj.finance.ft.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 选单内容设定对象 finance_ft_dict_data
 *
 * @author SunXuTong
 * @date 2023-06-15
 */
public class FinanceFtDictData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 选单 */
    private String dictId;

    /** 公司别 */
    private String companyId;

    /** 选单编码 */
    private String dictType;

    /** 选单名称 */
    private String dictName;

    /** 选项编码 */
    @Excel(name = "选项编码")
    private String dictCode;

    /** 选项名称 */
    @Excel(name = "选项名称")
    private String dictLabel;

    /** 栏位1 */
    @Excel(name = "栏位1")
    private String bakOne;

    /** 栏位2 */
    @Excel(name = "栏位2")
    private String bakTwo;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 排序 */
    @Excel(name = "排序")
    private Long seq;

    /** 备注 */
    private String dictRemark;

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
    public void setDictId(String dictId)
    {
        this.dictId = dictId;
    }

    public String getDictId()
    {
        return dictId;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getDictType()
    {
        return dictType;
    }
    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    public String getDictName()
    {
        return dictName;
    }
    public void setDictCode(String dictCode)
    {
        this.dictCode = dictCode;
    }

    public String getDictCode()
    {
        return dictCode;
    }
    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    public String getDictLabel()
    {
        return dictLabel;
    }
    public void setBakOne(String bakOne)
    {
        this.bakOne = bakOne;
    }

    public String getBakOne()
    {
        return bakOne;
    }
    public void setBakTwo(String bakTwo)
    {
        this.bakTwo = bakTwo;
    }

    public String getBakTwo()
    {
        return bakTwo;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setSeq(Long seq)
    {
        this.seq = seq;
    }

    public Long getSeq()
    {
        return seq;
    }
    public void setDictRemark(String dictRemark)
    {
        this.dictRemark = dictRemark;
    }

    public String getDictRemark()
    {
        return dictRemark;
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
            .append("dictId", getDictId())
            .append("companyId", getCompanyId())
            .append("dictType", getDictType())
            .append("dictName", getDictName())
            .append("dictCode", getDictCode())
            .append("dictLabel", getDictLabel())
            .append("bakOne", getBakOne())
            .append("bakTwo", getBakTwo())
            .append("status", getStatus())
            .append("seq", getSeq())
            .append("dictRemark", getDictRemark())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
