package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 气防站应急救援物资
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeEmergencyReliefMaterials implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物资ID
     */
    private String id;

    /**
     * 名称
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
     * 负责人id
     */
    private String chargePersonId;

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
