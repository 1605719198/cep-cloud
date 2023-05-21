package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 刷卡记录对象 human_hd_card_record
 *
 * @author jiangbingchen
 * @date 2023-04-03
 */
@Data
public class CardRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 公司名 */
    private String companyName;

    /**员工姓名 */
    private String empName;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 刷卡时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "刷卡时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cardTime;

    /** 卡钟编号 */
    @Excel(name = "卡钟编号")
    private String macCode;

    /** 卡钟名称 */
    private String macName;

    /** 卡钟数据上传时间 */
    private Date createDate;

    /** 审卡时间 */
    private Date checkTime;
    /** 查询范围 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;

    public void setDate1(Date date1)
    {
        this.date1 = date1;
    }

    public Date getDate1()
    {
        return date1;
    }

    public void setDate2(Date date2)
    {
        this.date2 = date2;
    }

    public Date getDate2()
    {
        return date2;
    }

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
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public String getEmpName()
    {
        return empName;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCompanyName()
    {
        return companyName;
    }
    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpNo()
    {
        return empNo;
    }
    public void setCardTime(Date cardTime)
    {
        this.cardTime = cardTime;
    }

    public Date getCardTime()
    {
        return cardTime;
    }
    public void setMacCode(String macCode)
    {
        this.macCode = macCode;
    }

    public String getMacCode()
    {
        return macCode;
    }
    public void setMacName(String macName)
    {
        this.macName = macName;
    }

    public String getMacName()
    {
        return macName;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime()
    {
        return checkTime;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("date1", getDate1())
                .append("date2", getDate2())
            .append("id", getId())
            .append("compId", getCompId())
                .append("empName", getEmpName())
            .append("empNo", getEmpNo())
            .append("cardTime", getCardTime())
            .append("macCode", getMacCode())
            .append("createDate", getCreateDate())
            .append("checkTime", getCheckTime())
                .append("companyName",getCompanyName())
                .append("macName",getMacName())
            .toString();
    }
}
