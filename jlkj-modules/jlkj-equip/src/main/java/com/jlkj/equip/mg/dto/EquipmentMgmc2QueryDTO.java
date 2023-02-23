package com.jlkj.equip.mg.dto;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 停机原因数据
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@TableName("t_equipment_mgmc2")
public class EquipmentMgmc2QueryDTO {

    /**
     * 当前页
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long limit;
    /**
     * 停机原因群码
     */
    private String groupcode;

    /**
     * 停机类型编号
     */
    private String mgmc1no;

    public EquipmentMgmc2QueryDTO(Long page, Long limit, String groupcode, String mgmc1no) {
        this.page = page;
        this.limit = limit;
        this.groupcode = groupcode;
        this.mgmc1no = mgmc1no;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getMgmc1no() {
        return mgmc1no;
    }

    public void setMgmc1no(String mgmc1no) {
        this.mgmc1no = mgmc1no;
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
        return "EquipmentMgmc2QueryDTO{" +
                "page=" + page +
                ", limit=" + limit +
                ", groupcode='" + groupcode + '\'' +
                ", mgmc1no='" + mgmc1no + '\'' +
                '}';
    }
}
