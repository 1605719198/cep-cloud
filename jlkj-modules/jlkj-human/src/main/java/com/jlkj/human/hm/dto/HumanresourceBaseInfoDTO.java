package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 选单配置DTO
 * @author HuangBing
 * @since 2023-03-08
 */
@TableName(value ="human_hm_baseinfo")
@Data
public class HumanresourceBaseInfoDTO {

    /**
     * uuid 主键ID
     */
    private String uuid;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
