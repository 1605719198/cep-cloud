package com.jlkj.common.dto.quality;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/9 - 14:16
 */
@Data
public class TsEquipBasicInformationDTO {
    /**
     * 唯一标识
     */
    @TableId
    private String id;

    /**
     * 仪器代号
     */
    private String equipNo;

    /**
     * 仪器说明
     */
    private String equipDesc;

    /**
     * 操作类型
     */
    private String workType;

    /**
     * ID接收状态
     */
    private String receiveStatus;

    /**
     * 工作档名
     */
    private String workFileName;

    /**
     * 传出IP
     */
    private String sendIp;

    /**
     * 传出PORT
     */
    private String sendPort;

    /**
     * 接收PORT
     */
    private String receivePort;

    /**
     * 检验站号
     */
    private String inspectionStationNo;

    /**
     * 建档人员
     */
    private String createUserId;

    /**
     * 异动人员
     */
    private String modifyUserId;

    /**
     * 异动日期
     */
    private String modifyTime;

    /**
     * 新增人员
     */
    private String createUser;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
