package com.jlkj.human.pa.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人绩效主档对象 human_pa_person_performance
 *
 * @author 267383
 * @date 2023-06-15
 */
public class DeptEvaluationQuery extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "序号")
    private String id;

    /** 公司别 */
    private String compId;

    /** 考评类别（1-月度，2-季度，3-年度） */
    private String meritType;

    /** 考评年月 */
    private String meritMonth;

    private String month1;

    private String month2;

    /** 员工ID */
    private String empId;

    /** 岗位类别（岗位管理作业） */
    private String deptType;

    /** 一级单位 */
    @Excel(name = "一级单位")
    private String dept;

    /** 岗位 */
    private String postId;

    /** 团队绩效（0-是，1-否） */
    private String isTeam;

    /** 团队绩效比例 */
    private Long teamRatio;

    /** 团队绩效分数 */
    private BigDecimal teamScore;

    /** 是否重点工作（0-是，1-否） */
    private String isPoint;

    /** 重点工作比例 */
    private Long pointRatio;

    /** 重点工作分数 */
    private BigDecimal pointScore;

    /** 固定考核项目 */
    private String isFix;

    /** 固定考核项目比例 */
    private Long fixRatio;

    /** 固定考核项目分数 */
    private BigDecimal fixScore;

    /** 固定考核启动ID（固定考核启动时给值） */
    private String fixId;

    /** KPI指标 */
    private String isKpi;

    /** KPI指标比例 */
    private Long kpiRatio;

    /** KPI指标分数 */
    private BigDecimal kpiScore;

    /** 自述 */
    private String selfAppr;

    /** 主管评语 */
    private String admAppr;

    /** 出勤加扣分项 */
    private BigDecimal pdAdjust;

    /** 奖惩加扣分项 */
    private BigDecimal pbAdjust;

    /** 其他加扣分 */
    private BigDecimal otherAdjust;

    /** 考评得分 */
    private BigDecimal score;

    /** 自评得分 */
    private BigDecimal selfScore;

    /** 主管考评得分 */
    private BigDecimal admScore;

    /** 等第 */
    private String rank;

    /** 考评状态 */
    @Excel(name = "考评状态")
    private String meritStatus;

    @Excel(name = "人数")
    private String people;

    /** 比例 */
    @Excel(name = "比例")
    private String proportion;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    private Date createDate;

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
    public void setMeritType(String meritType)
    {
        this.meritType = meritType;
    }

    public String getMeritType()
    {
        return meritType;
    }
    public void setMeritMonth(String meritMonth)
    {
        this.meritMonth = meritMonth;
    }

    public String getMeritMonth()
    {
        return meritMonth;
    }
    public void setMonth1(String month1)
    {
        this.month1 = month1;
    }

    public String getMonth1()
    {
        return month1;
    }
    public void setMonth2(String month2)
    {
        this.month2 = month2;
    }

    public String getMonth2()
    {
        return month2;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setDeptType(String deptType)
    {
        this.deptType = deptType;
    }

    public String getDeptType()
    {
        return deptType;
    }
    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public String getDept()
    {
        return dept;
    }
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
    }
    public void setIsTeam(String isTeam)
    {
        this.isTeam = isTeam;
    }

    public String getIsTeam()
    {
        return isTeam;
    }
    public void setTeamRatio(Long teamRatio)
    {
        this.teamRatio = teamRatio;
    }

    public Long getTeamRatio()
    {
        return teamRatio;
    }
    public void setTeamScore(BigDecimal teamScore)
    {
        this.teamScore = teamScore;
    }

    public BigDecimal getTeamScore()
    {
        return teamScore;
    }
    public void setIsPoint(String isPoint)
    {
        this.isPoint = isPoint;
    }

    public void setPeople(String people)
    {
        this.people = people;
    }
    public String getPeople()
    {
        return people;
    }

    public void setProportion(String proportion)
    {
        this.proportion = proportion;
    }
    public String getProportion()
    {
        return proportion;
    }

    public String getIsPoint()
    {
        return isPoint;
    }
    public void setPointRatio(Long pointRatio)
    {
        this.pointRatio = pointRatio;
    }

    public Long getPointRatio()
    {
        return pointRatio;
    }
    public void setPointScore(BigDecimal pointScore)
    {
        this.pointScore = pointScore;
    }

    public BigDecimal getPointScore()
    {
        return pointScore;
    }
    public void setIsFix(String isFix)
    {
        this.isFix = isFix;
    }

    public String getIsFix()
    {
        return isFix;
    }
    public void setFixRatio(Long fixRatio)
    {
        this.fixRatio = fixRatio;
    }

    public Long getFixRatio()
    {
        return fixRatio;
    }
    public void setFixScore(BigDecimal fixScore)
    {
        this.fixScore = fixScore;
    }

    public BigDecimal getFixScore()
    {
        return fixScore;
    }
    public void setFixId(String fixId)
    {
        this.fixId = fixId;
    }

    public String getFixId()
    {
        return fixId;
    }
    public void setIsKpi(String isKpi)
    {
        this.isKpi = isKpi;
    }

    public String getIsKpi()
    {
        return isKpi;
    }
    public void setKpiRatio(Long kpiRatio)
    {
        this.kpiRatio = kpiRatio;
    }

    public Long getKpiRatio()
    {
        return kpiRatio;
    }
    public void setKpiScore(BigDecimal kpiScore)
    {
        this.kpiScore = kpiScore;
    }

    public BigDecimal getKpiScore()
    {
        return kpiScore;
    }
    public void setSelfAppr(String selfAppr)
    {
        this.selfAppr = selfAppr;
    }

    public String getSelfAppr()
    {
        return selfAppr;
    }
    public void setAdmAppr(String admAppr)
    {
        this.admAppr = admAppr;
    }

    public String getAdmAppr()
    {
        return admAppr;
    }
    public void setPdAdjust(BigDecimal pdAdjust)
    {
        this.pdAdjust = pdAdjust;
    }

    public BigDecimal getPdAdjust()
    {
        return pdAdjust;
    }
    public void setPbAdjust(BigDecimal pbAdjust)
    {
        this.pbAdjust = pbAdjust;
    }

    public BigDecimal getPbAdjust()
    {
        return pbAdjust;
    }
    public void setOtherAdjust(BigDecimal otherAdjust)
    {
        this.otherAdjust = otherAdjust;
    }

    public BigDecimal getOtherAdjust()
    {
        return otherAdjust;
    }
    public void setScore(BigDecimal score)
    {
        this.score = score;
    }

    public BigDecimal getScore()
    {
        return score;
    }
    public void setSelfScore(BigDecimal selfScore)
    {
        this.selfScore = selfScore;
    }

    public BigDecimal getSelfScore()
    {
        return selfScore;
    }
    public void setAdmScore(BigDecimal admScore)
    {
        this.admScore = admScore;
    }

    public BigDecimal getAdmScore()
    {
        return admScore;
    }
    public void setRank(String rank)
    {
        this.rank = rank;
    }

    public String getRank()
    {
        return rank;
    }
    public void setMeritStatus(String meritStatus)
    {
        this.meritStatus = meritStatus;
    }

    public String getMeritStatus()
    {
        return meritStatus;
    }
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId()
    {
        return creatorId;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("month1", getMonth1())
                .append("month2", getMonth2())
            .append("id", getId())
            .append("compId", getCompId())
            .append("meritType", getMeritType())
            .append("meritMonth", getMeritMonth())
            .append("empId", getEmpId())
            .append("deptType", getDeptType())
            .append("dept", getDept())
            .append("postId", getPostId())
            .append("isTeam", getIsTeam())
            .append("teamRatio", getTeamRatio())
            .append("teamScore", getTeamScore())
            .append("isPoint", getIsPoint())
            .append("pointRatio", getPointRatio())
            .append("pointScore", getPointScore())
            .append("isFix", getIsFix())
            .append("fixRatio", getFixRatio())
            .append("fixScore", getFixScore())
            .append("fixId", getFixId())
            .append("isKpi", getIsKpi())
            .append("kpiRatio", getKpiRatio())
            .append("kpiScore", getKpiScore())
            .append("selfAppr", getSelfAppr())
            .append("admAppr", getAdmAppr())
            .append("pdAdjust", getPdAdjust())
            .append("pbAdjust", getPbAdjust())
            .append("otherAdjust", getOtherAdjust())
            .append("score", getScore())
            .append("selfScore", getSelfScore())
            .append("admScore", getAdmScore())
            .append("rank", getRank())
            .append("meritStatus", getMeritStatus())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
                .append("people", getPeople())
                .append("proportion", getProportion())
            .toString();
    }
}
