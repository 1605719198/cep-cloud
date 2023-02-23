package com.jlkj.common.dto.dto.qualityprovider;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/17 - 11:54
 */
@Data
public class TsElementTableDescriptionDTO {
    /**
     * 唯一标识
     */
    @TableId
    private String id;

    /**
     * 表格代号
     */
    private String formatId;

    /**
     * 表格说明
     */
    private String formatMemo;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
