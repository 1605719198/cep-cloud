package com.jlkj.finance.ac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.ac.domain.FinanceCostTree;
import com.jlkj.finance.ip.dto.FinanceCostDownTypeTreeDTO;

import java.util.List;

/**
* @author wang'mai
* @description 针对表【t_finance_cost_tree(成本科目阶层)】的数据库操作Service
*/
public interface FinanceCostTreeService extends IService<FinanceCostTree> {
    /**
     * 获取成本科目树结构
     * @return
     */
    List<FinanceCostDownTypeTreeDTO> getEquipmentTypeTreeList();
    /**
     * 获取成本科目树结构
     * @return
     */
    List<FinanceCostDownTypeTreeDTO> getEquipmentTypeAllTreeList();
}
