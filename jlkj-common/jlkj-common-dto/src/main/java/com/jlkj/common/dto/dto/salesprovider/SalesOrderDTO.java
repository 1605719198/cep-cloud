package com.jlkj.common.dto.dto.salesprovider;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 * @author xk
 * @TableName t_sales_order
 */
@TableName(value ="t_sales_order")
@Data
public class SalesOrderDTO implements Serializable {

    /**
     * uuid
     */
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单变更版次
     */
    private String orderVer;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 客户编码
     */
    private String custNo;

    /**
     * 销售渠道
     */
    private String contHannel;

    /**
     * 合同月别
     */
    private String orderMonth;

    /**
     * 订单建立日期
     */
    private String createDate;

    /**
     * 排产方式
     */
    private String schProdMode;

    /**
     * 客户采购案号
     */
    private String custPurchaseNo;

    /**
     * 销售区域
     */
    private String saleRegion;

    /**
     * 销售地区
     */
    private String areaNo;

    /**
     * 销售部门
     */
    private String salesUnit;

    /**
     * 销售人员
     */
    private String salesmanNo;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 生产厂
     */
    private String factory;

    /**
     * 收款方式
     */
    private String paymentTerm;

    /**
     * 付款天数(days)
     */
    private String payDay;

    /**
     * 订单最后交期
     */
    private String shippedDate;

    /**
     * 内部定单来源
     */
    private String innerOrder;

    /**
     * 内外销别(系统内
     */
    private String inOutSale;

    /**
     * 订单总量(MT)
     */
    private BigDecimal orderWeight;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 修改人员
     */
    private String updateUserNo;

    /**
     * 修改日期
     */
    private String updateDate;

    /**
     * update识别值
     */
    private String timeStampId;

    /**
     * 订货方式
     */
    private String orderWay;

    /**
     * 增值税率
     */
    private BigDecimal addTaxRate;

    /**
     * 运费预估金额
     */
    private BigDecimal freightAmount;

    /**
     * 货款金额
     */
    private BigDecimal prodAmount;

    /**
     * 原订单变更版次
     */
    private String usedOrderVer;

    /**
     * 建立人员
     */
    private String createUserNo;

    /**
     * 定价区域
     */
    private String priceAear;

    /**
     * 是否均价
     */
    private String isArrPrice;

    /**
     * 取价日期种类
     */
    private String getPriceType;

    /**
     * 延时类别
     */
    private String delayType;

    /**
     * 延时天数
     */
    private Integer delayDate;

    /**
     * 是否工作日取价
     */
    private String isWorkDate;

    /**
     * 订单数量
     */
    private BigDecimal orderQty;

    /**
     * 订单体积
     */
    private BigDecimal orderVolumn;

    /**
     * 计价单位
     */
    private String priceUnit;

    /**
     * 订单单价
     */
    private BigDecimal orderPrice;

    /**
     * 运费
     */
    private BigDecimal freightPrice;

    /**
     * 牌号
     */
    private String prodSpecNo;

    /**
     * 标准全名
     */
    private String standName;

    /**
     * 是否新产品
     */
    private String newProd;

    /**
     * 确认日期
     */
    private String confirmDate;

    /**
     * 价格版次
     */
    private String priceVer;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 最终用户
     */
    private String endUst;

    /**
     * 合同变更版次
     */
    private String contHangeVer;

    /**
     * 变更原因
     */
    private String chderc;

    /**
     * 变更标志
     */
    private String org;

    /**
     * 起点代码
     */
    private String beginNo;

    /**
     * 执行价格
     */
    private String executePrice;

    /**
     * 运费是否贴息
     */
    private String freightType;

    /**
     * lineup状态
     */
    private String lineupStatus;

    /**
     * 销售区域
     */
    private String saleArea;

    /**
     * 销售区域版次
     */
    private String saleAreaVer;

    /**
     * 产品型态
     */
    private String prodType;

    /**
     * 产品规范编号
     */
    private String psrNo;

    /**
     * 产品大类
     */
    private String prodLass;

    /**
     * 生产优先顺序
     */
    private String prodPriority;

    /**
     * 计重方式
     */
    private String countWgtMode;

    /**
     * 品质分类
     */
    private String qualityLass;

    /**
     * 交货公差上限
     */
    private BigDecimal shipTolMax;

    /**
     * 交货公差下限
     */
    private BigDecimal shipTolMin;

    /**
     * 品证书份数
     */
    private String numOfTestErt;

    /**
     * 运输方式
     */
    private String transType;

    /**
     * 运费担当
     */
    private String freightUnit;

    /**
     * 是否盖雨布
     */
    private String awningFlag;

    /**
     * 是否可亏吨
     */
    private String shortLoadFlag;

    /**
     * 收货人编号
     */
    private String recNo;

    /**
     * 收货人名称
     */
    private String recName;

    /**
     * 地点代码
     */
    private String recOrderNo;

    /**
     * 专用线
     */
    private String custLine;

    /**
     * 终到地点
     */
    private String endLocNo;

    /**
     * 收货人序号
     */
    private String recSeqNo;

    /**
     * 抵扣单位编号
     */
    private String freightUstNo;

    /**
     * 是否海运
     */
    private String isship;

    /**
     * 终到码头代码
     */
    private String endport;

    /**
     * 终到码头
     */
    private String endportName;

    /**
     * 起点码头
     */
    private String startport;

    /**
     * 起点码头中文名
     */
    private String startportName;

    /**
     * 汽运终到地点
     */
    private String vehicleEndSiteNo;

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

}
