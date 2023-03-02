package com.jlkj.logistics.wv.dto;
/**
 * @author xin
 */
public class LogisticsTempDayQueryDTO {
    /**
     * 申请单号
     */
    private String applyNo;
    /**
     * 当前页
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long limit;

    public LogisticsTempDayQueryDTO() {
    }

    public LogisticsTempDayQueryDTO(String applyNo, Long page, Long limit) {
        this.applyNo = applyNo;
        this.page = page;
        this.limit = limit;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
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

    @Override
    public String toString() {
        return "LogisticsTempDayQueryDTO{" +
                "applyNo='" + applyNo + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }

    public void setLimit(Long limit) {
        this.limit = limit;

    }
}
