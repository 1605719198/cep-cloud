package com.jlkj.finance.aa.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 系统管理-字典数据对象 finance_aa_dict_data
 *
 * @author 265799
 * @date 2023-04-03
 */
public class FinanceAaDictData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    private Long dictCode;

    /** 字典排序 */
    @Excel(name = "字典排序")
    private Long dictSort;

    /** 字典标签 */
    @Excel(name = "字典标签")
    private String dictLabel;

    /** 字典键值 */
    @Excel(name = "字典键值")
    private String dictValue;

    /** 字典类型 */
    @Excel(name = "字典类型")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @Excel(name = "样式属性", readConverterExp = "其=他样式扩展")
    private String cssClass;

    /** 表格回显样式 */
    @Excel(name = "表格回显样式")
    private String listClass;

    /** 是否默认（Y是 N否） */
    @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
    private String isDefault;

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

    public void setDictCode(Long dictCode)
    {
        this.dictCode = dictCode;
    }

    public Long getDictCode()
    {
        return dictCode;
    }
    public void setDictSort(Long dictSort)
    {
        this.dictSort = dictSort;
    }

    public Long getDictSort()
    {
        return dictSort;
    }
    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    public String getDictLabel()
    {
        return dictLabel;
    }
    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }

    public String getDictValue()
    {
        return dictValue;
    }
    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getDictType()
    {
        return dictType;
    }
    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    public String getCssClass()
    {
        return cssClass;
    }
    public void setListClass(String listClass)
    {
        this.listClass = listClass;
    }

    public String getListClass()
    {
        return listClass;
    }
    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getIsDefault()
    {
        return isDefault;
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
            .append("dictCode", getDictCode())
            .append("dictSort", getDictSort())
            .append("dictLabel", getDictLabel())
            .append("dictValue", getDictValue())
            .append("dictType", getDictType())
            .append("cssClass", getCssClass())
            .append("listClass", getListClass())
            .append("isDefault", getIsDefault())
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
