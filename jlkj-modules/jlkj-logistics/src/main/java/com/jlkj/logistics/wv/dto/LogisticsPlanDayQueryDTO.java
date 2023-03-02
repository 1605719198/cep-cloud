package com.jlkj.logistics.wv.dto;
/**
 * @author xin
 */
public class LogisticsPlanDayQueryDTO {
    /**
     * 计划年月
     */
    private String planDay;
    /**
     * 当前页
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long limit;

    public LogisticsPlanDayQueryDTO() {
    }

    public LogisticsPlanDayQueryDTO(String planDay, Long page, Long limit) {
        this.planDay = planDay;
        this.page = page;
        this.limit = limit;
    }

    public String getPlanDay() {
        return planDay;
    }

    public void setPlanDay(String planDay) {
        this.planDay = planDay;
    }

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

    @Override
    public String toString() {
        return "LogisticsPlanMonthQueryDTO{" +
                "planDay='" + planDay + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
