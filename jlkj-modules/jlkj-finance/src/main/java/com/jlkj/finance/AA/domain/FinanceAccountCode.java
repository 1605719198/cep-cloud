package com.jlkj.finance.AA.domain;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会计科目基本资料
 * @author wang'mai
 * @TableName t_finance_account_code
 */
@TableName(value ="t_finance_account_code")
@Data
public class FinanceAccountCode implements Serializable {
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
     * 会计科目编号    
     */
    private String accountCode;

    /**
     * 会计科目中文名称
     */
    private String accountChineseName;

    /**
     * 会计科目英文名称
     */
    private String accountEnglishName;

    /**
     * 传票性科目      
     */
    private String isVoucher;

    /**
     * 单位            
     */
    private String isUnit;

    /**
     * 户号类别        
     */
    private String idTypeStus;

    /**
     * 参号类别        
     */
    private String refTypeStus;

    /**
     * 到期日          
     */
    private String isDueDate;

    /**
     * 悬计帐          
     */
    private String isLqdt;

    /**
     * 币别            
     */
    private String isFrnCrcy;

    /**
     * 机器专用        
     */
    private String isPrvlg;

    /**
     * 现金科目        
     */
    private String isCash;

    /**
     * 借/贷方会计科目 
     */
    private String drOrCr;

    /**
     * 级别
     */
    private String level;

    /**
     * 禁用标识        
     */
    private String disabledCode;

    /**
     * 禁用日期        
     */
    private String disabledDate;
    /**
     * 建立时间
     */

    private String inputDate;

    /**
     * 新增人
     */
    private String inputUser;

    /**
     * 修改时间
     */
    private String updateDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}