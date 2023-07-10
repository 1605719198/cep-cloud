package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 出差地点记录对象 human_hd_trip_address
 *
 * @author 266861
 * @date 2023-04-24
 */
@Data
public class TripAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 地点 */
    @Excel(name = "地点")
    private String address;

    /** 拼音缩写 */
    @Excel(name = "拼音缩写")
    private String simpl;

    /** 地点属性 */
    @Excel(name = "地点属性")
    private String type;

    /** 国别 */
    @Excel(name = "国别")
    private String nation;

    /** 是否国内 */
    @Excel(name = "是否国内")
    private String isInternal;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

}
