package com.jlkj.human.hd.dto;

import lombok.Data;

import java.util.List;

/**
 *
 * @author 266861
 * @date 2023-03-08
 */

@Data
public class OptinonTypeDTO {
    /**
     * uuid 主键ID
     */
    private String uuid;

    /**
     * 选单数据list
     */
    private List<String> optionsType;
}
