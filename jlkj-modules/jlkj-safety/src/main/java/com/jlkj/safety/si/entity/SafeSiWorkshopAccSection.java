package com.jlkj.safety.si.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 车间明细台账-工段
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiWorkshopAccSection implements Serializable {

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
     * 工段名称
     */
    private String sectionName;


}
