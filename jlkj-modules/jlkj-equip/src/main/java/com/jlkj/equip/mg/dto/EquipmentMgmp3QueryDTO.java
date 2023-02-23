package com.jlkj.equip.mg.dto;
/**
 * @author xin
 */
public class EquipmentMgmp3QueryDTO {
    /**
     * 当前页
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long limit;
    /**
     * 三级机编号
     */
    private String mgmp3No;
    /**
     * 产线编号
     */
    private String mgma1No;
    /**
     * 接收日期
     */
    private String receiveDate;
    /**
     * 纪录状态
     */
    private String mgmp3Stus;
    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public String getMgmp3No() {
        return mgmp3No;
    }

    public void setMgmp3No(String mgmp3No) {
        this.mgmp3No = mgmp3No;
    }

    public String getMgma1No() {
        return mgma1No;
    }

    public void setMgma1No(String mgma1No) {
        this.mgma1No = mgma1No;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getMgmp3Stus() {
        return mgmp3Stus;
    }

    public void setMgmp3Stus(String mgmp3Stus) {
        this.mgmp3Stus = mgmp3Stus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "EquipmentMgmp3QueryDTO{" +
                "page=" + page +
                ", limit=" + limit +
                ", mgmp3No='" + mgmp3No + '\'' +
                ", mgma1No='" + mgma1No + '\'' +
                ", receiveDate='" + receiveDate + '\'' +
                ", mgmp3Stus='" + mgmp3Stus + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
