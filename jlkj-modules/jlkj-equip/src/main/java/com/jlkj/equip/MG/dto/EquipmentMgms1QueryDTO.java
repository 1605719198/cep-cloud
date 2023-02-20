package com.jlkj.equip.MG.dto;

import java.util.List;

/**
 * @author xin
 */
public class EquipmentMgms1QueryDTO {
    /**
     * 当前页
     */
    private Long pageNum;
    /**
     * 每页记录数
     */
    private Long pageSize;
    /**
     * 停机纪录编号
     */
    private String mgms1No;
    /**
     * 停机纪录状态
     */
    private String mgms1Stus;
    /**
     * 产线名称
     */
    private String mgma1Name;
    /**
     * 停机主类型名称
     */
    private String mgmc1Name;
    /**
     * 责任单位
     */
    private String respDeptNo;
    /**
     * 班别
     */
    private String deptclassMgms1No;
    /**
     * 日期区间
     */
    private List<String> datetimeRange;

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getMgms1No() {
        return mgms1No;
    }

    public void setMgms1No(String mgms1No) {
        this.mgms1No = mgms1No;
    }

    public String getMgms1Stus() {
        return mgms1Stus;
    }

    public void setMgms1Stus(String mgms1Stus) {
        this.mgms1Stus = mgms1Stus;
    }

    public String getMgma1Name() {
        return mgma1Name;
    }

    public void setMgma1Name(String mgma1Name) {
        this.mgma1Name = mgma1Name;
    }

    public String getMgmc1Name() {
        return mgmc1Name;
    }

    public void setMgmc1Name(String mgmc1Name) {
        this.mgmc1Name = mgmc1Name;
    }

    public String getRespDeptNo() {
        return respDeptNo;
    }

    public void setRespDeptNo(String respDeptNo) {
        this.respDeptNo = respDeptNo;
    }

    public List<String> getDatetimeRange() {
        return datetimeRange;
    }

    public void setDatetimeRange(List<String> datetimeRange) {
        this.datetimeRange = datetimeRange;
    }

    public String getDeptclassMgms1No() {
        return deptclassMgms1No;
    }

    public void setDeptclassMgms1No(String deptclassMgms1No) {
        this.deptclassMgms1No = deptclassMgms1No;
    }

    @Override
    public String toString() {
        return "EquipmentMgms1QueryDTO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", mgms1No='" + mgms1No + '\'' +
                ", mgms1Stus='" + mgms1Stus + '\'' +
                ", mgma1Name='" + mgma1Name + '\'' +
                ", mgmc1Name='" + mgmc1Name + '\'' +
                ", respDeptNo='" + respDeptNo + '\'' +
                ", deptclassMgms1No='" + deptclassMgms1No + '\'' +
                ", datetimeRange=" + datetimeRange +
                '}';
    }
}
