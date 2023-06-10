package com.jlkj.human.hd.dto;

import lombok.Data;

import java.util.List;

/**
 * 选单查询条件
 * @author 266861
 * @date 2023-03-08
 */

@Data
public class OptionTypeDTO {
    /**
     * uuid 主键ID
     */
    private String uuid;

    /**
     * id 主键ID
     */
    private Long id;

    /**
     * 选单数据list
     */
    private List<String> optionsType;

    /**
     * 公司别
     */
    private String compId;
}
