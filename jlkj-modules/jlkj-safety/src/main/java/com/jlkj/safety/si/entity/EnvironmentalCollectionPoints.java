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
 * 环保采集点位
 * @author yzl
 * @create 2022-12-14 09:04:30.931274
 */
@TableName(value ="safe_si_environmental_collection_points")
@Data
public class EnvironmentalCollectionPoints  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 名称 */
	private String deviceName ;

    /** 污染物名称 */
	private String pollutantName ;

    /** 采集点位 */
	private String collectionPoint ;

    /** 排放限值 */
	private String emissionLimit ;

    /** 序号 */
	private Integer sort ;

    /** 单位 */
	private String unit ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

