package com.jlkj.logistics.wv.dto;
/**
 * @author xin
 */
public class LogisticsPlanMonthQueryDTO {
    /**
     * 计划年月
     */
    private String planMonth;
    /**
     * 当前页
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long limit;

    public LogisticsPlanMonthQueryDTO() {
    }

    public LogisticsPlanMonthQueryDTO(String planMonth, Long page, Long limit) {
        this.planMonth = planMonth;
        this.page = page;
        this.limit = limit;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
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
                "planMonth='" + planMonth + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
