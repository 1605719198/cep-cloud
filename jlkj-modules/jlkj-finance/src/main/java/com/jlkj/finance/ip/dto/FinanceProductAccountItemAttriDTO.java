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
public class FinanceProductAccountItemAttriDTO {
    private List<FinanceProductAccountItemAttriDTO> propertydata;

    /**
     * 系统码uuid
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 账务代码
     */
    private String accountItemCode;

    /**
     * 属性名称
     */
    private String accountItemAttriName;

    /**
     * 属性值
     */
    private String accountItemAttriValue;

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
     * 属性说明
     */
    private String label;

    /**
     * 异动日期
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
     * 节点指示码
     */
    private String leafId;

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


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
