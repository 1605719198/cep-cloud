package com.jlkj.human.hd.dto;

import lombok.Data;

/**
 * 人事选单
 * @author 266861
 * @date 2023-03-08
 */

@Data
public class BasisOptionsDTO {

    /** 主键 */
    private Long id;

    /** 资料编码 */
    private String dicNo;

    /** 资料名称 */
    private String dicName;

    /** 状态 */
    private String status;

    /** 公司别 */
    private String compId;

//    /** 是否显示编码 */
//    private String isShowno;
//
//    /** 默认值 */
//    private String isChecked;
//
//    /** 排序序号 */
//    private Long orderNum;
//
//    /** 父节点ID */
//    private Long parentid;
//
//    /** 祖籍关系 */
//    private String parents;
//
//    /** 输入人 */
//    private String creator;
//
//    /** 输入人id */
//    private String creatorId;
//
//    /** 输入日期 */
//    private Date createDate;
//
//    /** 子部门 */
//    private List<Attendencebasis> children ;
}
