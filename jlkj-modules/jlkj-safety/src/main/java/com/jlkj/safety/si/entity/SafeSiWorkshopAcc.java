package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 车间明细台账
 * </p>
 *
 * @author su
 * @since 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiWorkshopAcc implements Serializable {

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
     * 名称
     */
    private String materialName;

    /**
     * 01消防类/02防护类/03防汛类
     */
    private String type;

    /**
     * 型号
     */
    private String specification;

    /**
     * 数量
     */
    private Float amount;

    /**
     * 位置
     */
    private String position;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建人工号
     */
    private String createUserNo;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    private String modifyUserId;

    /**
     * 修改人工号
     */
    private String modifyUserNo;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 责任人id
     */
    private String personLiableId;

    /**
     * 责任人工号
     */
    private String personLiableNo;

    /**
     * 责任人姓名
     */
    private String personLiableName;

    /**
     * 责任人电话
     */
    private String personLiableTel;


}
