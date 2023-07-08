package com.jlkj.finance.ap.dto;

import com.jlkj.finance.ap.domain.FinanceApItemComp;
import lombok.Data;

import java.util.List;


/**
* @Description
* @Param * @Param null:
* @return * @return: null
* @Author Sunxutong
* @Date 2023/7/7 8:20
**/
@Data
public class FinanceApItemCompDTO {

    /**
     * 公司表List
     */
    private List<FinanceApItemComp> itemDetailCompanyList;
}
