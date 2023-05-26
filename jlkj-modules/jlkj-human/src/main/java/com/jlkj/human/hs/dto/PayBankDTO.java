package com.jlkj.human.hs.dto;

import com.jlkj.human.hs.domain.PayBank;
import lombok.Data;

import java.util.List;

/**
 * 公司薪酬项目设定表
 * @author HuangBing
 * @TableName human_hs_pay_formation
 */
@Data
public class PayBankDTO {
    /**
     * 薪酬项目List
     */
    private List<PayBank> payBankList;
}