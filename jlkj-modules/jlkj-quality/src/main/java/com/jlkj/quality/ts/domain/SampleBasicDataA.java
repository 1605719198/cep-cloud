package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 样品类别标准资料档A
 * @TableName t_ts_sample_basic_data_a
 * @author 265675
 */
@TableName(value ="t_ts_sample_basic_data_a")
@Data
public class SampleBasicDataA implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 样品类别
     */
    private String sampleClass;

    /**
     * 碳硫结果发送状态
     */
    private String csResultCheck;

    /**
     * 碳硫设备代码
     */
    private String csEquipmentNumber;

    /**
     * 碳元素代号
     */
    private String celementIndex;

    /**
     * 直读仪器碳能力下限
     */
    private String cequipmentMin;

    /**
     * 直读碳仪器能力上限
     */
    private String cequipmentMax;

    /**
     * 硫元素代号
     */
    private String selementIndex;

    /**
     * 直读硫仪器能力下限
     */
    private String sequipmentMin;

    /**
     * 直读硫仪器能力上限
     */
    private String sequipmentMax;

    /**
     * 氮氧结果发送状态
     */
    private String noResultCheck;

    /**
     * 氮氧设备代码
     */
    private String noEquipmentNumber;

    /**
     * 氮元素代号
     */
    private String nelementIndex;

    /**
     * 直读氮仪器能力下限
     */
    private String nequipmentMin;

    /**
     * 直读氮仪器能力上限
     */
    private String nequipmentMax;

    /**
     * 氧元素代号
     */
    private String oelementIndex;

    /**
     * 直读氧仪器能力下限
     */
    private String oequipmentMin;

    /**
     * 直读氧仪器能力上限
     */
    private String oequipmentMax;

    /**
     * 直读仪检量线代码
     */
    private String equipmenteAlloy;

    /**
     * X荧光检量线代码
     */
    private String equipmentxGroup;

    /**
     * 统计权数
     */
    private BigDecimal staticNumber;

    /**
     * 默认显示格式
     */
    private String defaultFormat;

    /**
     * 结果检查形式
     */
    private String resultCheckType;

    /**
     * 直读、荧光结果发送状态
     */
    private String allResultCheck;

    /**
     * 取样工位
     */
    private String sampleFrom;

    /**
     * 样品测试成本
     */
    private BigDecimal sampleCost;

    /**
     * 样品类别描述
     */
    private String sampleDesc;

    /**
     * 是否自动传输
     */
    private String methodPrepare;

    /**
     * 料号
     */
    private String materialNo;

    /**
     * 是否辐射检验
     */
    private String rayCheck;

    /**
     * 同一熔炼号样品类别顺序
     */
    private String sampleDisplaySequence;

    /**
     * 各样品存盘对象名称
     */
    private String sampleClassification;

    /**
     * 直读仪设备代码1
     */
    private String equipmenteNumberA;

    /**
     * 直读仪设备代码2
     */
    private String equipmenteNumberB;

    /**
     * 直读仪设备代码3
     */
    private String equipmenteNumberC;

    /**
     * 直读仪设备代码4
     */
    private String equipmenteNumberD;

    /**
     * 直读仪设备代码5
     */
    private String equipmenteNumberE;

    /**
     * 直读仪设备代码6
     */
    private String equipmenteNumberF;

    /**
     * X荧光设备代码1
     */
    private String equipmentxNumberA;

    /**
     * X荧光设备代码2
     */
    private String equipmentxNumberB;

    /**
     * X荧光设备代码3
     */
    private String equipmentxNumberC;

    /**
     * X荧光设备代码4
     */
    private String equipmentxNumberD;

    /**
     * X荧光设备代码5
     */
    private String equipmentxNumberE;

    /**
     * X荧光设备代码6
     */
    private String equipmentxNumberF;

    /**
     * 修改人职工编号
     */
    private String modifyEmployNo;

    /**
     * 修改日期
     */
    private String modifyDate;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 新增人职工编号
     */
    private String createEmployNo;

    /**
     * 新增日期
     */
    private String createDate;

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
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
