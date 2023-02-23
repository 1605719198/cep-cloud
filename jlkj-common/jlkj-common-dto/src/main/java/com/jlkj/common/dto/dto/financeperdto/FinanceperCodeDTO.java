package com.jlkj.common.dto.dto.financeperdto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author wang'mai
 */
@Data
public class FinanceperCodeDTO {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 成本科目
     */
    private String costCode;

    /**
     * 成本科目中文说明
     */
    private String costDesc;

    /**
     * 会计科目
     */
    private String accountCode;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * AA抛AC码
     */
    private String assignMark;

    /**
     * 费用来源属性
     */
    private String expenseSource;

    /**
     * 费用性质属性
     */
    private String expenseProperty;

    /**
     * 生管费用科目属性
     */
    private String prodManaCostAttri;

    /**
     * 费用分类属性
     */
    private String expenseType;

    /**
     * 状态
     */
    private String stusCode;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 最后修改日期
     */
    private String updateDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
