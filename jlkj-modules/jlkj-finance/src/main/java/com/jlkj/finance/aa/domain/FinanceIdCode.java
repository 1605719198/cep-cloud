package com.jlkj.finance.aa.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会计科目户号资料
 * @author wang'mai
 * @TableName t_finance_id_code
 */
@TableName(value ="t_finance_id_code")
@Data
public class FinanceIdCode implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 户号类别编码
     */
    private String idTypeNo;

    /**
     * 户号类型编码
     */
    private String idKindNo;

    /**
     * 户号规则
     */
    private String idTypeRule;

    /**
     * 户号检核规则
     */
    private String idTypeCheck;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 建立日期
     */

    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 最后异动日期
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

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}