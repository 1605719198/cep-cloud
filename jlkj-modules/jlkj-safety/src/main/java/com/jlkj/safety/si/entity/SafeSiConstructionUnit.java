package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 外委施工单位
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiConstructionUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 施工单位ID
     */
    private String id;

    /**
     * 施工单位名称
     */
    private String constructionUnitName;

    /**
     * 施工内容
     */
    private String content;

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
