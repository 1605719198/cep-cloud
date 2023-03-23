package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 安全承诺公告牌
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeBulletinBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 班组风险研判
     */
    private String teamRiskAssessment;

    /**
     * 班组承诺
     */
    private String teamCommitment;

    /**
     * 班组主要负责人
     */
    private String principalResponsiblePerson;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUserName;


}
