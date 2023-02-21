package com.jlkj.finance.AA.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会计科目树状资料
 * @author wang'mai
 * @TableName t_finance_account_tree
 */
@TableName(value ="t_finance_account_tree")
@Data
public class FinanceAccountTree implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 公司代码                      
     */
    private String compId;

    /**
     * 会计科目编号(组合)            
     */
    private String accountCode;

    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 会计科目关联(父亲)            
     */
    private String parentAccountCode;

    /**
     * 资料夹或叶子名称              
     */
    private String folderName;

    /**
     * 叶子或资料夹(是否可点开取下层)
     */
    private String isFolder;

    /**
     * 创建人
     */
    private String createUser;



    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 建立时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    /**
     * 存会计科目名称
     */
    private String accountName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}