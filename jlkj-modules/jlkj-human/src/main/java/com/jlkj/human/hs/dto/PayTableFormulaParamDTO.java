package com.jlkj.human.hs.dto;

import com.jlkj.human.hs.domain.PayTableFormulaParam;
import lombok.Data;

import java.util.List;

/**
 * 各公司薪资计算参数DTO
 *
 * @author 266861
 * @date 2023-05-15
 */
@Data
public class PayTableFormulaParamDTO {

    /**
     * 公司别
     */
    private String compId;

    /**
     * 复制到公司别
     */
    private String compIdTo;

    /**
     * 各公司计算参数列表
     */
    private List<PayTableFormulaParam> payTableFormulaParamList;

}
