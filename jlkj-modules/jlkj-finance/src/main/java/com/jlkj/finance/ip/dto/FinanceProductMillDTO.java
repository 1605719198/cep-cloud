package com.jlkj.finance.ip.dto;

import com.jlkj.finance.ip.domain.FinanceProductMillActivity;
import com.jlkj.finance.ip.domain.FinanceProductMillAttri;
import com.jlkj.finance.ip.domain.FinanceProductMillProduct;
import com.jlkj.finance.ip.domain.FinanceProductMillSystem;
import lombok.Data;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/31 - 15:53
 */
@Data
public class FinanceProductMillDTO {
    /**
     * 属性表id
     */
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 产线代码
     */
    private String millId;

    /**
     * 产线名称
     */
    private String millName;

    /**
     * 状态
     */
    private String status;

    /**
     * 属性页签属性值
     */
    private String attriValue;

    /**
     * 属性页签属性名称
     */
    private String itemA;

    /**
     * 属性页签属性名称
     */
    private String dataA;

    /**
     * 相关系统页签系统代码
     */
    private String sysId;

    /**
     * 相关系统页签新增人
     */
    private String createUser;

    /**
     * 相关系统页签新增时间
     */
    private String createDate;

    /**
     * 相关活动页签系统代码
     */
    private String activitySysId;

    /**
     * 相关活动页签活动代码
     */
    private String activityId;

    /**
     * 相关活动页签新增人
     */
    private String activityCreateUser;

    /**
     * 相关活动页签新增时间
     */
    private String activityCreateDate;

    /**
     * 产出产副品页签产副品编码
     */
    private String productCode;

    /**
     * 产出产副品页签产副品名称
     */
    private String productCodeDesc;

    /**
     * 系统配置明细资料属性名称
     */
    private String attriName;

    /**
     * 系统配置明细资料属性说明
     */
    private String attriDesc;

    /**
     * 属性页签多笔资料
     */
    private List<FinanceProductMillAttri> attriList;

    /**
     * 相关系统页签多笔资料
     */
    private List<FinanceProductMillSystem> systemList;

    /**
     * 相关活动页签多笔资料
     */
    private List<FinanceProductMillActivity> activityList;

    /**
     * 产出产副品页签多笔资料
     */
    private List<FinanceProductMillProduct> productList;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
