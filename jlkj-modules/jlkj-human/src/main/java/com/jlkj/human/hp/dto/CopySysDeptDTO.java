package com.jlkj.human.hp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 复制部门资料维护对象 sys_dept
 *
 * @author jlkj
 * @date 2023-03-08
 */
public class CopySysDeptDTO extends BaseEntity {
    /** 来源公司别 */
    private String oldCompId;

    /** 目标公司别 */
    private String newCompId;

    /** 创建人 */
    private String createBy;

    /** 更新人 */
    private String updateBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 生效时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDate;

    public void setOldCompId(String oldCompId)
    {
        this.oldCompId = oldCompId;
    }
    public String getOldCompId()
    {
        return oldCompId;
    }

    public void setNewCompId(String newCompId)
    {
        this.newCompId = newCompId;
    }
    public String getNewCompId()
    {
        return newCompId;
    }

    @Override
    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }
    @Override
    public String getCreateBy()
    {
        return createBy;
    }

    @Override
    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }
    @Override
    public String getUpdateBy()
    {
        return updateBy;
    }

    @Override
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    @Override
    public Date getCreateTime()
    {
        return createTime;
    }

    @Override
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    @Override
    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }
    public Date getEffectDate()
    {
        return effectDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("oldCompId", getOldCompId())
                .append("newCompId", getNewCompId())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("createTime", getCreateTime())
                .append("updateTime",getUpdateTime())
                .append("effectDate",getEffectDate())
                .toString();
    }
}
