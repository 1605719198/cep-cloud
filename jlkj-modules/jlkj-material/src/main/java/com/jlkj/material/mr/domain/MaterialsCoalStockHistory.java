package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @Description 物料煤场库存调整记录
 * @create 2022年6月10日 10:12:26
 */
@TableName(value ="materials_mr_coal_stock_history")
@Data
public class MaterialsCoalStockHistory implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 煤场库存id */
    @JsonProperty("coal_stock_id")
	private String coalStockId ;

    /** 调整前库存量 */
    @JsonProperty("before_inventory")
	private BigDecimal beforeInventory ;

    /** 调整后库存量 */
    @JsonProperty("after_inventory")
	private BigDecimal afterInventory ;

    /** 调整人编号 */
    @JsonProperty("modify_user_id")
	private String modifyUserId ;

    /** 调整人姓名 */
    @JsonProperty("modify_user_name")
	private String modifyUserName ;

    /** 调整时间 */
    @JsonProperty("modify_time")
	private Date modifyTime ;

    /** 备注 */
    @JsonProperty("remark")
	private String remark ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

