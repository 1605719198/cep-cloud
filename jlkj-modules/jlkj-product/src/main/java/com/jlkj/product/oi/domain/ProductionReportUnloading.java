package com.jlkj.product.oi.domain;

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
 * @create 2023-04-14 11:00:33.380529
 */
@TableName(value ="t_production_report_unloading")
@Data
public class ProductionReportUnloading  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 统计日期 */
    private Date statisticsDate ;

    /** 来煤方式 */
    private String coalWay ;

    /** 来煤数量 */
    private Integer coalNum ;

    /** 库存(KG) */
    private BigDecimal stock ;

    /** 卸煤-螺旋卸 */
    private Integer unloadingCoalScrew ;

    /** 卸煤-分卸 */
    private Integer unloadingCoalPart ;

    /** 待卸-螺旋卸 */
    private Integer unloadedScrew ;

    /** 待卸-分卸 */
    private Integer unloadedPart ;

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

