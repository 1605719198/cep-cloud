package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 施工单位-施工人员
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiConstructionUnitPersons implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    private String id;

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
     * 工种名称
     */
    private String workTypeName;

    /**
     * 作业证编号
     */
    private String workCardCode;

    /**
     * 是否负责人(0否,1是)
     */
    private Integer isLeader;

    /**
     * 创建人ID
     */
    private String createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime ceateTime;


}
