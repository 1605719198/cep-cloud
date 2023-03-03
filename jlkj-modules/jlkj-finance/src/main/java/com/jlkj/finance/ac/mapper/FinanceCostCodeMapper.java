package com.jlkj.finance.ac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.finance.ac.domain.FinanceCostCode;
import com.jlkj.finance.ip.dto.FinanceCostDownTypeTreeDTO;

import java.util.List;


/**
* @author wang'mai
* @description 针对表【t_finance_cost_code(成本科目基本资料)】的数据库操作Mapper
* @createDate 2022-05-23 15:03:18
* @Entity com.jljl.financeprovider.entity.FinanceCostCode
*/
public interface FinanceCostCodeMapper extends BaseMapper<FinanceCostCode> {
    /**
     * 查询成本科目基本资料
     *
     * @param financeCostDownTypeTreeDTO 成本科目基本资料
     * @return 成本科目基本资料
     */
     List<FinanceCostDownTypeTreeDTO> getFinanceCostDownTypeTree(FinanceCostDownTypeTreeDTO financeCostDownTypeTreeDTO);

}




