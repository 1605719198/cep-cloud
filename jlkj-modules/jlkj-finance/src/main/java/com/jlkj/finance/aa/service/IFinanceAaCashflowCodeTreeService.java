package com.jlkj.finance.aa.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCode;
import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;

import java.util.List;

/**
 * 现金流量代码Service接口
 *
 * @author 265799
 * @date 2023-04-17
 */
public interface IFinanceAaCashflowCodeTreeService extends IService<FinanceAaCashflowCode> {

    /**
     * 获取选单配置树
     * @return
     */
    List<FinanceAaCashflowCodeDTO> getBaseInfoTreeList();
}
