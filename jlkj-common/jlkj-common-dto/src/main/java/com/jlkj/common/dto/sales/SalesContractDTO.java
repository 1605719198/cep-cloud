package com.jlkj.common.dto.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同表
 * @author xk
 * @TableName t_sales_contract
 */
@Data
public class SalesContractDTO {
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 订单变更版次
     */
    private String orderVer;

    /**
     * 客户编号
     */
    private String custNo;

    /**
     * 合同月别
     */
    private String orderMonth;

    /**
     * 销售方式
     */
    private String salesType;

    /**
     * 内外销别
     */
    private String inOutSale;

    /**
     * 产品大类
     */
    private String prodLass;

    /**
     * 合同状态
     */
    private String contStatus;

    /**
     * 客户采购案号
     */
    private String custPurchaseNo;

    /**
     * 合同变更版次
     */
    private String contHangeVer;

    /**
     * 销售区域
     */
    private String saleRegion;

    /**
     * 销售地区(省份)
     */
    private String areaNo;

    /**
     * 销售人员工号
     */
    private String salesmanNo;

    /**
     * 销售人员名称
     */
    private String salesManName;

    /**
     * 合同建立日期
     */
    private String contReateDate;

    /**
     * 币别
     */
    private String crcy;

    /**
     * 合同变更日期
     */
    private String contHangeDate;

    /**
     * 签约地点
     */
    private String contOffice;

    /**
     * 税率
     */
    private BigDecimal taxNo;

    /**
     * 货款总额
     */
    private BigDecimal prodAmount;

    /**
     * 货运预估总额
     */
    private BigDecimal freightAmount;

    /**
     * 合同总金额
     */
    private BigDecimal contAmount;

    /**
     * 合同总量(MT)
     */
    private BigDecimal contWeight;

    /**
     * 资源管制量
     */
    private BigDecimal sourceQuota;

    /**
     * 备注
     */
    private String attentionMemo;

    /**
     * 内部订单来源
     */
    private String innerOrder;

    /**
     * 合同样版号
     */
    private String contractModel;

    /**
     * 合同条款版次
     */
    private String contractSeq;

    /**
     * 是否协议户
     */
    private String isprotoUst;

    /**
     * 执行价格
     */
    private String executePrice;

    /**
     * 第三方客户
     */
    private String thirdUst;

    /**
     * 是否第三方
     */
    private String isThird;

    /**
     * 到款天数
     */
    private Integer addData;

    /**
     * 到款百分比
     */
    private BigDecimal countPercent;

    /**
     * 协议合同量
     */
    private BigDecimal protoWeight;

    /**
     * 订单来源
     */
    private String orderOrigin;

    /**
     * 销售组别
     */
    private String saleOrg;

    /**
     * 合同建立人员
     */
    private String creatEmpNo;

    /**
     * 最后维护人员
     */
    private String updateEmpNo;

    /**
     * 最后维护日期
     */
    private String updateDate;

    /**
     * 计价单位
     */
    private String priceUnit;

    /**
     * 生效日期
     */
    private String effectDate;

    /**
     * 是否送审
     */
    private String isSend;

    /**
     * 协议合同量
     */
    private BigDecimal agreeMentWeight;

    /**
     * 补充条款
     */
    private String bcArticle;

    /**
     * 上限
     */
    private BigDecimal shipTolMax;

    /**
     * 下限
     */
    private BigDecimal shipTolMin;

    /**
     * 变更原因
     */
    private String chderc;

    /**
     * 送审说明
     */
    private String checkChDerc;

    /**
     * 是否为新产品
     */
    private String newProd;

    /**
     * 取消送审说明
     */
    private String offcheckChDerc;

    /**
     * 发票类别
     */
    private String billsType;

    /**
     * 归档状态
     */
    private String gdstatus;

    /**
     * 归档日期
     */
    private String gddate;

    /**
     * 发货区域
     */
    private String area;

    /**
     * 结算方式
     */
    private String ssType;

    /**
     * 客户全称
     */
    private String custFullName;

    /**
     * 第三方客户名称
     */
    private String thirdUstName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    /**
     * 公司地址
     */
    private String companyAddress;

}
