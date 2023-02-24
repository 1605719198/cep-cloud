package com.jlkj.quality.ts.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.jlkj.quality.ts.domain.SampleBasicDataB;
import lombok.Data;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/27 - 13:35
 */
@Data
public class TsSampleBasicDatabDTO {
    /**
     * 元素编号ggh
     * 元素编号和经济
     */
    @TableId
    private String id;

    /**
     * 样品类别
     */
    private String sampleClass;

    /**
     * 结果接收站
     */
    private String sendStation;

    /**
     * 发送格式
     */
    private String sendFormat;

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
    private String createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 从表多笔
     */
    private List<SampleBasicDataB> sendValues;
}
