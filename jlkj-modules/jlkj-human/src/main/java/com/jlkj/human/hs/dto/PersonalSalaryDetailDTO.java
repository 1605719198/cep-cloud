package com.jlkj.human.hs.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 薪资核定明细结果DTO
 *
 * @author 266861
 * @date 2023-05-11
 */
@Data
public class PersonalSalaryDetailDTO {
    /** 公司薪酬项目ID */
    @TableId(type = IdType.AUTO)
    private String id;

    /** 集团薪酬项目编码 */
    private String payProCode;

    /** 集团薪酬项目名称 */
    private String payProName;

    /** 标准的内容 */
    private String staCon;

    /** 是否岗位定薪项目 */
    private Long isPostPro;

    /** 是否员工定薪项目 */
    private String isEmpPro;

    /** 是否下拉选单 */
    private String isLov;

    /** 下拉选单的选单ID */
    private String lovConId;

    /** 下拉选单的选单编码 */
    private String defaultValue;

    /** 父结点ID */
    private Long parentid;

    /** 祖籍关系 */
    private String parents;


    /** 该节点是否最最终节点 */
    private String ifEnd;


}
