package com.jlkj.finance.ac.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author wang'mai
 */
@Data
public class FinanceCostAddNodeDTO {
    /**
     * 当前页
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long limit;
    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 父类
     */
    private String parentId;

    /**
     * 上层代码
     */
    private String upperCodex;
    /**
     * 上层代码名称
     */
    private String  upperName;
    /**
     * 阶层代码
     */
    private String levelCode;

    /**
     * 阶层名称
     */
    private String levelName;
    /**
     * 节点类别
     */
    private String nodeType;
    /**
     * 创建人
     */
    private String creatUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 创建人姓名
     */
    private String createUserName;
}
