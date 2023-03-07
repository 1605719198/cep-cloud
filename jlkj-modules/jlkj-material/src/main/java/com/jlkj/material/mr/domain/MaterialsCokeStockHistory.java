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
 * @Description 物料焦场库存调整记录
 * @create 2022年6月10日 10:12:26
 */
@TableName(value ="materials_mr_coke_stock_history")
@Data
public class MaterialsCokeStockHistory implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 焦场库存id */
    @JsonProperty("coke_stock_id")
    private String cokeStockId ;

    /** 调整前库存量 */
    @JsonProperty("before_inventory")
	private BigDecimal beforeInventory ;

    /** 调整后库存量 */
    @JsonProperty("after_inventory")
	private BigDecimal afterInventory ;

    /** 修改人id */
    @JsonProperty("modify_user_id")
	private String modifyUserId ;

    /** 修改人 */
    @JsonProperty("modify_user_name")
	private String modifyUserName ;

    /** 修改时间 */
    @JsonProperty("modify_time")
	private Date modifyTime ;

    /** 备注 */
	private String remark ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

