package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yzl
 * @create 2022-09-28 14:27:05.808763
 */
@TableName(value ="product_oi_logs_name")
@Data
public class ProductionLogsName  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 日志项名称 */
	private String logsName ;

    /** 创建人 */
	private String createUserId ;

    /** 创建人 */
	private String createUserName ;

    /** 创建时间 */
	private Date createTime ;

    /** 修改人id */
	private String modifyUserId ;

    /** 修改人 */
	private String modifyUserName ;

    /** 修改时间 */
	private Date modifyTime ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

