package com.jlkj.safety.si.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 吊装安全作业证-作业人员
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobHoistingPersons implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    private String id;

    /**
     * 作业证ID
     */
    private String jobId;

    /**
     * 所属单位ID
     */
    private String constructionUnitId;

    /**
     * 所属单位名称
     */
    private String constructionUnitName;

    /**
     * 所属单位类型(1本厂,2外委)
     */
    private Integer type;

    /**
     * 人员ID
     */
    private String personId;

    /**
     * 人员姓名
     */
    private String personName;

    /**
     * 工种
     */
    private String workTypeName;

    /**
     * 作业证编号
     */
    private String workCardCode;

    /**
     * 人员类型(0吊装人员,1吊装指挥)
     */
    private Integer personType;


}
