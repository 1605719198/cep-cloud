package com.jlkj.human.hs.dto;

import com.jlkj.human.hs.domain.Director;
import lombok.Data;

import java.util.List;

/**
 * 公司薪酬项目设定表
 * @author HuangBing
 * @TableName human_hs_pay_formation
 */
@Data
public class DirectorDTO {
    /**
     * 薪酬项目List
     */
    private List<Director> directorList;
}