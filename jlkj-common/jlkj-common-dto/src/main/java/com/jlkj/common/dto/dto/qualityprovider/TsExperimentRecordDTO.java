package com.jlkj.common.dto.dto.qualityprovider;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 265675
 * @Description
 * @date 2022/09/13/8:40
 */
@Data
public class TsExperimentRecordDTO {
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 样品分类代号
     */
    private String code;

    /**
     * 传输格式
     */
    private String formatId;

    /**
     * 车牌
     */
    private String licensEplate;

    /**
     * 元素数量
     */
    private Integer num;

    /**
     * 接收时间
     */
    private String receiveTime;

    /**
     * 样品编号
     */
    private String sampleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人工号
     */
    private String createUser;

    /**
     * 修改人工号
     */
    private String updateUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * mq修改时间
     */
    private String sendTime;

    /**
     * 样品值
     */
    private List<Map<String, String>> sampleValues;

    /**
     * 传送字符串
     */
    private String jsonString;

    /**
     * 班别
     */
    private String shift;

    /**
     * 元素名称
     */
    private String element;

    /**
     * 元素代号
     */
    private String elementId;

    /**
     * 元素值
     */
    private BigDecimal elementValue;

    /**
     * 元素类别
     */
    private String type;

    /**
     * 元素细分
     */
    private String typeA;

    /**
     * 查询日期开始值
     */
    private String startDate;

    /**
     * 查询日期结束值
     */
    private String endDate;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
