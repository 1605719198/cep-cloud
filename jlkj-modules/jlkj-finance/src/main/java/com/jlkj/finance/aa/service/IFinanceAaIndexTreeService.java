package com.jlkj.finance.aa.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.jlkj.finance.aa.domain.FinanceAaIndex;

import com.jlkj.finance.aa.dto.FinanceAaIndexDTO;

import java.util.List;

/**
 * 系统选单-索引设定Service接口
 *
 * @author 265799
 * @date 2023-04-19
 */
public interface IFinanceAaIndexTreeService extends IService<FinanceAaIndex> {
    /**
     * 获取选单配置树
     * @param  companyId 公司别
     * @return
     */
    List<FinanceAaIndexDTO> getBaseInfoTreeList(String companyId);
}
