package com.jlkj.finance.ap.dto;

import com.jlkj.finance.ap.domain.FinanceApItemComp;
import lombok.Data;

import java.util.List;

@Data
public class FinanceApItemCompDTO {

    /**
     * 公司表List
     */
    private List<FinanceApItemComp> itemDetailCompanyList;
}
