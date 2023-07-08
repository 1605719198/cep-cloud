package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName human_pa_position_personnel
 */
@TableName(value ="human_pa_position_personnel")
@Data
public class PositionPersonnel implements Serializable {
    /**
     * 工号
     */
    private String empNo;

    /**
     * 姓名
     */
    private String empName;

    /**
     * 岗位id
     */
    private Long postId;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 职位类别
     */
    private String deptType;

    /**
     * 资料名称
     */
    private String typeName;

    /**
     * 上级岗位ID
     */
    private Long parentPostId;

    /**
     * 上级岗位名称
     */
    private String parentPostName;

    /**
     * 工号
     */
    private String leaderNo;

    /**
     * 姓名
     */
    private String leaderName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}