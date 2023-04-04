package com.jlkj.product.oi.dto.productionoutputperformance;

import com.jlkj.product.oi.dto.BasePage;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-05-11 14:40
 */
@Data
public class GetProductionOutputPerformanceDTO extends BasePage {

    private String shiftName;

    private String className;

    private String startTime;

    private String endTime;
}
