package com.jlkj.product.oi.dto.productionabnormalledger;

import com.jlkj.product.oi.dto.BasePage;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-05-12 8:31
 */
@Data
public class GetProductionAbnormalLedgerDTO extends BasePage {

    private String departmentName;

    private String planStartTime;

    private String planEndTime;
}
