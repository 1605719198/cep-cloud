package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-生产信息配置
 *
 * @author sudeyou
 * @since 2022-10-18 14:47:44
 */
@TableName(value ="product_oi_information_configuration")
@Data
public class ProductionInformationConfiguration implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 物料代码
     */
    private String materialsCode;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 熄焦方式(1:干熄;2:湿熄)
     */
    private String quenchingMethod;

    /**
     * 单孔理论重量
     */
    private BigDecimal singleHoleWeight;

    /**
     * 理论单耗
     */
    private BigDecimal singleConsumption;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private String modifyUserId;

    /**
     * 修改人
     */
    private String modifyUserName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

