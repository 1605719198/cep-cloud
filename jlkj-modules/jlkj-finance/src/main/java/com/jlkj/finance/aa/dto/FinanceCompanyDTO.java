package com.jlkj.finance.aa.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author wang'mai
 */
@Data
public class FinanceCompanyDTO {

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
    /**
     * 系统码uuid
     */
    @TableId
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

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
