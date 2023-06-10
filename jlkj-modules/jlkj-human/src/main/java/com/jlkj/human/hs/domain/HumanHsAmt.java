package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 薪资应付作业对象 human_hs_amt
 *
 * @author jlkj
 * @date 2023-05-25
 */
public class HumanHsAmt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司 */
    @Excel(name = "公司")
    private String compId;

    /** 年月 */
    @Excel(name = "年月")
    private String yyMonth;

    /** 作业时点 */
    @Excel(name = "作业时点")
    private String operTime;

    /** 申请人 */
    @Excel(name = "申请人")
    private String sendEmpNo;

    /** 申请日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendDate;

    /** 申请理由 */
    @Excel(name = "申请理由")
    private String sendReason;

    /** 附件地址 */
    @Excel(name = "附件地址")
    private String fileUrl;

    /** 申请单状态 */
    @Excel(name = "申请单状态")
    private String status;

    /** 传票号 */
    @Excel(name = "传票号")
    private String vchrno;

    /** 传票日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "传票日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date vchrDate;

    /** 输入人名称 */
    @Excel(name = "输入人名称")
    private String createName;

    /** 薪资应付明细信息 */
    private List<HumanHsAmtDetail> humanHsAmtDetailList;

    /**附件清单*/
    private List<Map<String,String>> fileList;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setYyMonth(String yyMonth)
    {
        this.yyMonth = yyMonth;
    }

    public String getYyMonth()
    {
        return yyMonth;
    }
    public void setOperTime(String operTime)
    {
        this.operTime = operTime;
    }

    public String getOperTime()
    {
        return operTime;
    }
    public void setSendEmpNo(String sendEmpNo)
    {
        this.sendEmpNo = sendEmpNo;
    }

    public String getSendEmpNo()
    {
        return sendEmpNo;
    }
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }

    public Date getSendDate()
    {
        return sendDate;
    }
    public void setSendReason(String sendReason)
    {
        this.sendReason = sendReason;
    }

    public String getSendReason()
    {
        return sendReason;
    }
    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl()
    {
        return fileUrl;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setVchrno(String vchrno)
    {
        this.vchrno = vchrno;
    }

    public String getVchrno()
    {
        return vchrno;
    }
    public void setVchrDate(Date vchrDate)
    {
        this.vchrDate = vchrDate;
    }

    public Date getVchrDate()
    {
        return vchrDate;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }

    public List<HumanHsAmtDetail> getHumanHsAmtDetailList()
    {
        return humanHsAmtDetailList;
    }

    public void setHumanHsAmtDetailList(List<HumanHsAmtDetail> humanHsAmtDetailList)
    {
        this.humanHsAmtDetailList = humanHsAmtDetailList;
    }
    public List<Map<String, String>> getFileList() { return fileList; }

    public void setFileList(List<Map<String, String>> fileList) { this.fileList = fileList; }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("compId", getCompId())
                .append("yyMonth", getYyMonth())
                .append("operTime", getOperTime())
                .append("sendEmpNo", getSendEmpNo())
                .append("sendDate", getSendDate())
                .append("sendReason", getSendReason())
                .append("fileUrl", getFileUrl())
                .append("status", getStatus())
                .append("vchrno", getVchrno())
                .append("vchrDate", getVchrDate())
                .append("createBy", getCreateBy())
                .append("createName", getCreateName())
                .append("createTime", getCreateTime())
                .append("humanHsAmtDetailList", getHumanHsAmtDetailList())
                .toString();
    }
}
