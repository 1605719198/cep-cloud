package com.jlkj.product.oi.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 生产实绩-储煤塔存量
* @TableName product_oi_coal_tower_stock
*/
@Data
public class ProductionCoalTowerStockSwagger implements Serializable {

@Schema(title = "主键标识;UUID")
@TableId
private String id;

@Schema(title = "储煤塔号")
private String coal_tower_number;

@Schema(title = "煤余量")
private String coal_surplus;

@Schema(title = "料位")
private String material_level;

}
