package com.jlkj.common.entity.finance.AC;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author wang'mai
 */
@Data
public class ManufacturerTreeDTO {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 父键
     */
    private String parentid;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 节点编码
     */
    private String nodeNo;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 建立人员
     */
    private String createUser;

    /**
     * 建立时间
     */
    private Date createTime;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人员姓名
     */
    private String createUserName;

    /**
     * 修改人员姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
