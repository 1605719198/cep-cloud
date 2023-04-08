package com.jlkj.product.oi.dto.coalconfigmanual;

import com.jlkj.product.oi.dto.BasePage;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-12-12 9:11
 */
@Data
public class GetPageDTO extends BasePage {
    private Integer tower;

    private String tower_id;
}
