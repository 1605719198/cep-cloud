package com.jlkj.human.ex.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 试题对象 human_ex_questions
 *
 * @author 116519
 * @date 2023-07-04
 */
public class Questions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 题目 */
    @Excel(name = "题目")
    private String name;

    /** 题目图片 */
    @Excel(name = "题目图片")
    private String images;

    /** 题目类型 */
    @Excel(name = "题目类型")
    private String type;

    /** 问答题答案 */
    @Excel(name = "问答题答案")
    private String answer;

    /** 试题解析 */
    @Excel(name = "试题解析")
    private String analysis;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Integer isEnable;

    /** 部門 */
    @Excel(name = "部門")
    private Long deptid;

    /** 公司 */
    @Excel(name = "公司")
    private String compId;

    /** 标签D */
    @Excel(name = "标签D")
    private String labels;

    /** 题库ID */
    @Excel(name = "题库ID")
    private String banks;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setImages(String images)
    {
        this.images = images;
    }

    public String getImages()
    {
        return images;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public String getAnswer()
    {
        return answer;
    }
    public void setAnalysis(String analysis)
    {
        this.analysis = analysis;
    }

    public String getAnalysis()
    {
        return analysis;
    }
    public void setIsEnable(Integer isEnable)
    {
        this.isEnable = isEnable;
    }

    public Integer getIsEnable()
    {
        return isEnable;
    }
    public void setDeptid(Long deptid)
    {
        this.deptid = deptid;
    }

    public Long getDeptid()
    {
        return deptid;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setLabels(String labels)
    {
        this.labels = labels;
    }

    public String getLabels()
    {
        return labels;
    }
    public void setBanks(String banks)
    {
        this.banks = banks;
    }

    public String getBanks()
    {
        return banks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("images", getImages())
            .append("type", getType())
            .append("answer", getAnswer())
            .append("analysis", getAnalysis())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isEnable", getIsEnable())
            .append("deptid", getDeptid())
            .append("compId", getCompId())
            .append("labels", getLabels())
            .append("banks", getBanks())
            .toString();
    }
}
