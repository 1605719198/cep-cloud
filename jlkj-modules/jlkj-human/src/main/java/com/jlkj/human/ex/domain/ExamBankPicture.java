package com.jlkj.human.ex.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 题库图片对象 exam_bank_picture
 * 
 * @author qnsdt
 * @date 2022-12-22
 */
@Data
public class ExamBankPicture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图片序号 */
    private String photoId;

    /** 图片代码 */
    @Excel(name = "图片代码")
    private String photoCode;

    /** 文档代码 */
    @Excel(name = "文档代码")
    private String photoPath;

    /** 图片链接 */
    @Excel(name = "图片链接")
    private String photoUrl;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 原始名称 */
    @Excel(name = "原始名称")
    private String originalName;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setPhotoId(String photoId)
    {
        this.photoId = photoId;
    }

    public String getPhotoId()
    {
        return photoId;
    }
    public void setPhotoCode(String photoCode)
    {
        this.photoCode = photoCode;
    }

    public String getPhotoCode()
    {
        return photoCode;
    }
    public void setPhotoPath(String photoPath)
    {
        this.photoPath = photoPath;
    }

    public String getPhotoPath()
    {
        return photoPath;
    }
    public void setPhotoUrl(String photoUrl)
    {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setOriginalName(String originalName)
    {
        this.originalName = originalName;
    }

    public String getOriginalName()
    {
        return originalName;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("photoId", getPhotoId())
                .append("photoCode", getPhotoCode())
                .append("photoPath", getPhotoPath())
                .append("photoUrl", getPhotoUrl())
                .append("fileName", getFileName())
                .append("originalName", getOriginalName())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("remark", getRemark())
                .toString();
    }
}
