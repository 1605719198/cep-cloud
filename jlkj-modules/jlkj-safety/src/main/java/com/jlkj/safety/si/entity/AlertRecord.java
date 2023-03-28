package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;

/**
 * @author yzl
 * @create 2022-12-14 09:36:40.865321
 */
@TableName(value ="safe_si_alert_record")
@Data
public class AlertRecord  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 报警类型id */
	private String typeId ;

    /** 报警名称 */
	private String name ;

    /** 报警源id */
    private String sourcehId ;

    /** 报警源名称 */
	private String sourcehName ;

    /** 报警类型 */
	private String type ;

    /** 报警等级 */
	private Integer alarmLevel ;

    /** 报警推送方式 */
	private String pushWay ;

    /** 报警内容 */
	private String context ;

    /** 用户id或者角色id */
	private String userId ;

    /** 报警推送时间 */
	private Date pushTime ;

    /** 报警消息状态,是否已读 */
	private String status ;

    /** 报警推给用户还是角色 */
	private String isuserorrole ;

    /** 报警上限制 */
	private String upperLimit ;

    /** 报警下限 */
	private String lowerLimit ;

    /** 报警结果 */
	private String alarmValue ;

    /** 报警原因 */
	private String alarmReson ;

    /** 报警设备或部门 */
	private String equipmentOrInventory ;

    /** 创建时间 */
	private Date createTime ;

    /** 创建人 */
	private String createBy ;

    /** 修改时间 */
	private Date updateTime ;

    /** 修改人 */
	private String updateBy ;

    /** 是否处理（0否1是） */
	private Integer isHandle ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

