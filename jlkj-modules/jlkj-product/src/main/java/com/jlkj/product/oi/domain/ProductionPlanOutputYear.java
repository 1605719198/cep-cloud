package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author  generator
 * 计划管理--年产量计划
 * @TableName product_oi_plan_output_year
 */
@TableName(value ="product_oi_plan_output_year")
@Data
public class ProductionPlanOutputYear implements Serializable {
    /**
     * 计划编号;
     */
    @TableId
    private String id;

    /**
     * 计划年度;
     */
    private Integer planYear;

    /**
     * 指标项编号;
     */
    private String targetItemId;

    /**
     * 指标项产量;
     */
    private BigDecimal targetItemOutput;

    /**
     * 创建人编号;
     */
    private String createUserId;

    /**
     * 创建人姓名;
     */
    private String createUserName;

    /**
     * 创建时间;
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        ProductionPlanOutputYear other = (ProductionPlanOutputYear) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlanYear() == null ? other.getPlanYear() == null : this.getPlanYear().equals(other.getPlanYear()))
            && (this.getTargetItemId() == null ? other.getTargetItemId() == null : this.getTargetItemId().equals(other.getTargetItemId()))
            && (this.getTargetItemOutput() == null ? other.getTargetItemOutput() == null : this.getTargetItemOutput().equals(other.getTargetItemOutput()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCreateUserName() == null ? other.getCreateUserName() == null : this.getCreateUserName().equals(other.getCreateUserName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlanYear() == null) ? 0 : getPlanYear().hashCode());
        result = prime * result + ((getTargetItemId() == null) ? 0 : getTargetItemId().hashCode());
        result = prime * result + ((getTargetItemOutput() == null) ? 0 : getTargetItemOutput().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateUserName() == null) ? 0 : getCreateUserName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", planYear=").append(planYear);
        sb.append(", targetItemId=").append(targetItemId);
        sb.append(", targetItemOutput=").append(targetItemOutput);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createUserName=").append(createUserName);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
