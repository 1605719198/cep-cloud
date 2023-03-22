package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 选单配置DTO
 * @author HuangBing
 * @since 2023-03-08
 */
@TableName(value ="human_hm_baseinfo")
@Data
public class BaseInfoDTO {

    /**
     * uuid 主键ID
     */
    private String uuid;

    /**
     * 选单数据list
     */
    private List<String> baseInfoList;
}
