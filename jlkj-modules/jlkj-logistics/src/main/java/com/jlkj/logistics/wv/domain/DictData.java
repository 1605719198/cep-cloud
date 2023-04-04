package com.jlkj.logistics.wv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统管理-字典数据表
 * @author Xin
 * @TableName logistics_wv_dict_data
 */
@TableName(value ="logistics_wv_dict_data")
public class DictData implements Serializable {
    /**
     * 字典编码
     */
    @TableId(type = IdType.AUTO)
    private Long dictCode;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字符型字段
     */
    private String field1;

    /**
     * 字符型字段
     */
    private String field2;

    /**
     * 字符型字段
     */
    private String field3;

    /**
     * 数值型字段
     */
    private BigDecimal field4;

    /**
     * 数值型字段
     */
    private BigDecimal field5;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    public Long getDictCode() {
        return dictCode;
    }

    /**
     * 字典编码
     */
    public void setDictCode(Long dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 字典排序
     */
    public Integer getDictSort() {
        return dictSort;
    }

    /**
     * 字典排序
     */
    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    /**
     * 字典标签
     */
    public String getDictLabel() {
        return dictLabel;
    }

    /**
     * 字典标签
     */
    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }

    /**
     * 字典键值
     */
    public String getDictValue() {
        return dictValue;
    }

    /**
     * 字典键值
     */
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    /**
     * 字典类型
     */
    public String getDictType() {
        return dictType;
    }

    /**
     * 字典类型
     */
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    /**
     * 样式属性（其他样式扩展）
     */
    public String getCssClass() {
        return cssClass;
    }

    /**
     * 样式属性（其他样式扩展）
     */
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    /**
     * 表格回显样式
     */
    public String getListClass() {
        return listClass;
    }

    /**
     * 表格回显样式
     */
    public void setListClass(String listClass) {
        this.listClass = listClass;
    }

    /**
     * 是否默认（Y是 N否）
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * 是否默认（Y是 N否）
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 状态（0正常 1停用）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态（0正常 1停用）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 字符型字段
     */
    public String getField1() {
        return field1;
    }

    /**
     * 字符型字段
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }

    /**
     * 字符型字段
     */
    public String getField2() {
        return field2;
    }

    /**
     * 字符型字段
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }

    /**
     * 字符型字段
     */
    public String getField3() {
        return field3;
    }

    /**
     * 字符型字段
     */
    public void setField3(String field3) {
        this.field3 = field3;
    }

    /**
     * 数值型字段
     */
    public BigDecimal getField4() {
        return field4;
    }

    /**
     * 数值型字段
     */
    public void setField4(BigDecimal field4) {
        this.field4 = field4;
    }

    /**
     * 数值型字段
     */
    public BigDecimal getField5() {
        return field5;
    }

    /**
     * 数值型字段
     */
    public void setField5(BigDecimal field5) {
        this.field5 = field5;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DictData other = (DictData) that;
        return (this.getDictCode() == null ? other.getDictCode() == null : this.getDictCode().equals(other.getDictCode()))
            && (this.getDictSort() == null ? other.getDictSort() == null : this.getDictSort().equals(other.getDictSort()))
            && (this.getDictLabel() == null ? other.getDictLabel() == null : this.getDictLabel().equals(other.getDictLabel()))
            && (this.getDictValue() == null ? other.getDictValue() == null : this.getDictValue().equals(other.getDictValue()))
            && (this.getDictType() == null ? other.getDictType() == null : this.getDictType().equals(other.getDictType()))
            && (this.getCssClass() == null ? other.getCssClass() == null : this.getCssClass().equals(other.getCssClass()))
            && (this.getListClass() == null ? other.getListClass() == null : this.getListClass().equals(other.getListClass()))
            && (this.getIsDefault() == null ? other.getIsDefault() == null : this.getIsDefault().equals(other.getIsDefault()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getField1() == null ? other.getField1() == null : this.getField1().equals(other.getField1()))
            && (this.getField2() == null ? other.getField2() == null : this.getField2().equals(other.getField2()))
            && (this.getField3() == null ? other.getField3() == null : this.getField3().equals(other.getField3()))
            && (this.getField4() == null ? other.getField4() == null : this.getField4().equals(other.getField4()))
            && (this.getField5() == null ? other.getField5() == null : this.getField5().equals(other.getField5()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDictCode() == null) ? 0 : getDictCode().hashCode());
        result = prime * result + ((getDictSort() == null) ? 0 : getDictSort().hashCode());
        result = prime * result + ((getDictLabel() == null) ? 0 : getDictLabel().hashCode());
        result = prime * result + ((getDictValue() == null) ? 0 : getDictValue().hashCode());
        result = prime * result + ((getDictType() == null) ? 0 : getDictType().hashCode());
        result = prime * result + ((getCssClass() == null) ? 0 : getCssClass().hashCode());
        result = prime * result + ((getListClass() == null) ? 0 : getListClass().hashCode());
        result = prime * result + ((getIsDefault() == null) ? 0 : getIsDefault().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getField1() == null) ? 0 : getField1().hashCode());
        result = prime * result + ((getField2() == null) ? 0 : getField2().hashCode());
        result = prime * result + ((getField3() == null) ? 0 : getField3().hashCode());
        result = prime * result + ((getField4() == null) ? 0 : getField4().hashCode());
        result = prime * result + ((getField5() == null) ? 0 : getField5().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dictCode=").append(dictCode);
        sb.append(", dictSort=").append(dictSort);
        sb.append(", dictLabel=").append(dictLabel);
        sb.append(", dictName=").append(dictName);
        sb.append(", dictValue=").append(dictValue);
        sb.append(", dictType=").append(dictType);
        sb.append(", cssClass=").append(cssClass);
        sb.append(", listClass=").append(listClass);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", status=").append(status);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", field1=").append(field1);
        sb.append(", field2=").append(field2);
        sb.append(", field3=").append(field3);
        sb.append(", field4=").append(field4);
        sb.append(", field5=").append(field5);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
