package com.jlkj.finance.ip.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wang'mai
 */
@Data
public class FinanceProductAccountDTO {
    /**
     * 会计科目中文名称
     */
    private String accountChineseName;
    /**
     * 当前页
     */
    private Long pageNum;
    /**
     * 节点指示码
     */
    private String leafId;
    /**
     * 成本科目编码
     */
    private String costCodeAccount;
    /**
     * 每页记录数
     */
    private Long pageSize;
    /**
     * 项次一
     */
    private String itemA;

    /**
     * 项次二
     */
    private String itemB;

    /**
     * 项次三
     */
    private String itemC;

    /**
     * 项次四
     */
    private String itemD;

    /**
     * 项次五
     */
    private String itemE;

    /**
     * 资料一
     */
    private String dataA;

    /**
     * 资料二
     */
    private String dataB;

    /**
     * 资料三
     */
    private String dataC;

    /**
     * 资料四
     */
    private String dataD;

    /**
     * 资料五
     */
    private String dataE;

    /**
     * 资料六
     */
    private String dataF;

    /**
     * 资料七
     */
    private BigDecimal dataG;

    /**
     * 资料排列指示
     */
    private String dataSeq;
    private List<FinanceProductAccountDTO> propertydata;
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
     * 存货特性
     */
    private String productInventoryNature;

    /**
     * 产副品编码
     */
    private String productCode;

    /**
     * 帐务名称
     */
    private String accountName;

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
     * 会计科目户号指示
     */
    private String accountIdIdex;

    /**
     * 会计科目参号指示
     */
    private String accountRefnoIndex;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 异动人
     */
    private String updateUser;

    /**
     * 异动日期
     */
    private String updateDate;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    /**
     * 户号类别编码
     */
    private String idTypeNo;

    /**
     * 户号类型编码
     */
    private String idKindNo;
    /**
     * 参号类别编码
     */

    private String refTypeNum;
    /**
     * 户号类别
     */
    private String idTypeStus;
    /**
     * 参号类型编码
     */
    private String refKindNum;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
