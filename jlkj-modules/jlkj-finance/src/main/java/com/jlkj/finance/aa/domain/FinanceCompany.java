package com.jlkj.finance.aa.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会计公司设定
 * @author wang'mai
 * @TableName t_finance_company
 */
@TableName(value ="finance_aa_company")
@Data
public class FinanceCompany implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 母公司代码
     */
    private String parentCompId;

    /**
     * 公司代码
     */
    private String compId;

    /**
     * 中文全名
     */
    private String companyChineseName;

    /**
     * 英文名称
     */
    private String companyEnglishName;

    /**
     * 缩写
     */
    private String companyShortName;

    /**
     * 币别
     */
    private String crcy;

    /**
     * 过账时点
     */
    private String postingMark;

    /**
     * 统一编号
     */
    private String taxNo;

    /**
     * 开账会计周期
     */
    private String startPeriod;

    /**
     * 输入年别
     */
    private String yearType;

    /**
     * 会计科目大类
     */
    private String acctGroupMax;

    /**
     * 会计科目中类
     */
    private String acctGroupMiddle;

    /**
     * 会计科目细项
     */
    private String acctGroupMin;

    /**
     * 创建人
     */
    private String creatUser;

    /**
     * 建立时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

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