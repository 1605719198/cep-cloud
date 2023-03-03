package com.jlkj.finance.ac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.finance.ac.domain.FinanceCostCode;
import com.jlkj.finance.ac.mapper.FinanceCostCodeMapper;
import com.jlkj.finance.ac.service.FinanceCostCodeService;
import com.jlkj.finance.ip.dto.FinanceCostDownTypeTreeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wang'mai
* @description 针对表【t_finance_cost_code(成本科目基本资料)】的数据库操作Service实现
* @createDate 2022-05-23 15:03:18
*/
@Service
public class FinanceCostCodeServiceImpl extends ServiceImpl<FinanceCostCodeMapper, FinanceCostCode>
    implements FinanceCostCodeService{
    @Autowired
    private FinanceCostCodeMapper financeCostCodeMapper;
    /**
     * 成本科目基本资料查询会计科目并组树
     */
    @Override
    public List<FinanceCostDownTypeTreeDTO> getFinanceCostDownTypeTree (FinanceCostDownTypeTreeDTO financeCostDownTypeTreeDTO) {

        return financeCostCodeMapper.getFinanceCostDownTypeTree(financeCostDownTypeTreeDTO);
    }

}




