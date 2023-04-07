package com.jlkj.finance.aa.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 系统管理-字典类型对象 finance_aa_dict_type
 *
 * @author 265799
 * @date 2023-04-03
 */
public class FinanceAaDictType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    private Long dictId;

    /** 字典名称 */
    @Excel(name = "字典名称")
    private String dictName;

    /** 字典类型 */
    @Excel(name = "字典类型")
    private String dictType;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 字符型字段 */
    @Excel(name = "字符型字段")
    private String field1;

    /** 字符型字段 */
    @Excel(name = "字符型字段")
    private String field2;

    /** 字符型字段 */
    @Excel(name = "字符型字段")
    private String field3;

    /** 数值型字段 */
    @Excel(name = "数值型字段")
    private BigDecimal field4;

    /** 数值型字段 */
    @Excel(name = "数值型字段")
    private BigDecimal field5;

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    public Long getDictId()
    {
        return dictId;
    }
    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    public String getDictName()
    {
        return dictName;
    }
    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getDictType()
    {
        return dictType;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setField1(String field1)
    {
        this.field1 = field1;
    }

    public String getField1()
    {
        return field1;
    }
    public void setField2(String field2)
    {
        this.field2 = field2;
    }

    public String getField2()
    {
        return field2;
    }
    public void setField3(String field3)
    {
        this.field3 = field3;
    }

    public String getField3()
    {
        return field3;
    }
    public void setField4(BigDecimal field4)
    {
        this.field4 = field4;
    }

    public BigDecimal getField4()
    {
        return field4;
    }
    public void setField5(BigDecimal field5)
    {
        this.field5 = field5;
    }

    public BigDecimal getField5()
    {
        return field5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("field1", getField1())
            .append("field2", getField2())
            .append("field3", getField3())
            .append("field4", getField4())
            .append("field5", getField5())
            .toString();
    }
}
