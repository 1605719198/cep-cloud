package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 * 人员卡钟对象 human_hd_person_colock
 *
 * @author 266861
 * @date 2023-03-29
 */
@Data
public class PersonColock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 类别**/
    private String type;

    /** 公司别 */
    private String compId;

    /** 工号 */
    @Excel(name = "工号/机构ID号")
    private String empId;

    /** 组织机构记录ID*/
    private String orgColockId;

    /** 是否刷卡 */
    @Excel(name = "是否刷卡")
    private String checkcard;

    /** 一级单位名称 */
    private String firstDeptName;

    /** 一级单位ID */
    private String firstDeptId;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 是否生效 */
    private String isEffect;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 卡钟编号数组 */
    private ArrayList<String> colockList;

}
