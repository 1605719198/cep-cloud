package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 灭火器台账
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeWorkshopFireExtinguisherAcc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    private String id;

    /**
     * 车间名称
     */
    private String workshopName;

    /**
     * 地点位置
     */
    private String position;

    /**
     * 消防灭火器类型
     */
    private String materialName;

    /**
     * 型号
     */
    private String specification;

    /**
     * 数量
     */
    private Float amount;

    /**
     * 出厂日期
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime outDate;

    /**
     * 首次充装日期
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime firstDate;

    /**
     * 下次充装日期
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime nextDate;

    /**
     * 报废时间
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime badDate;

    /**
     * 负责人
     */
    private String chargePerson;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
