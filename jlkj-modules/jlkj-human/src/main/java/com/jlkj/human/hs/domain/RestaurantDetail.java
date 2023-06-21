package com.jlkj.human.hs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 餐饮消费明细对象 human_hs_restaurant_detail
 *
 * @author 266861
 * @date 2023-06-19
 */
@Data
public class RestaurantDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String compId;

    /** 员工ID */
    private String empId;

    /** 员工姓名 */
    @Excel(name = "员工姓名")
    private String empName;

    /** 员工工号 */
    @Excel(name = "员工工号")
    @NotBlank(message = "员工工号不能为空")
    private String empNo;

    /** 岗位ID */
    private String postId;

    /** 岗位名称 */
    private String postFullName;

    /** 刷卡时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "刷卡时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date slotCardTime;

    /** 消费金额 */
    @Excel(name = "消费金额")
    private BigDecimal money;

    /** 刷卡钟ID */
    private String clockId;

    /** 刷卡钟编码 */
    private String clockCode;

    /** 刷卡钟名称 */
    private String clockName;

    /** 输入人工号 */
    private String createNo;

    /** 输入人ID */
    private String createId;

    /** 开始日期 */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd ")
    private Date startDate;

    /** 结束日期 */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd ")
    private Date endDate;

}
