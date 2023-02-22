package com.jlkj.finance.gp.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 厂商树本资料
 * @author wang'mai
 * @TableName finance_gp_manufacturer_tree
 */
@TableName(value ="finance_gp_manufacturer_tree")
@Data
public class ManufacturerTree implements Serializable {
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