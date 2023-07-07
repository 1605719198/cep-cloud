package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoAccountDetail;
import com.jlkj.finance.ao.dto.FinanceAoAccountDto;

import java.util.List;

/**
 * 会计处理明细Service接口
 * 
 * @author 265799
 * @date 2023-06-25
 */
public interface IFinanceAoAccountDetailService 
{
    /**
     * 查询会计处理明细
     * 
     * @param id 会计处理明细主键
     * @return 会计处理明细
     */
    public FinanceAoAccountDetail selectFinanceAoAccountDetailById(String id);
    /**
     * 查询会计处理明细列表
     *
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDetail 会计处理明细
     * @return 会计处理明细集合
     */
    public FinanceAoAccountDetail selectListLoanADetail(FinanceAoAccountDetail financeAoAccountDetail);

    /**
     * 查询会计处理明细列表
     *
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDetail 会计处理明细
     * @return 会计处理明细集合
     */
    public List<FinanceAoAccountDetail> selectFinanceAoAccountDetailList(FinanceAoAccountDetail financeAoAccountDetail);

    /**
     * 新增会计处理明细
     *
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDto 会计处理明细
     * @return 结果
     */
    public int insertFinanceAoAccountDetail(FinanceAoAccountDto financeAoAccountDto);

    /**
     * 修改会计处理明细
     *
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDto 会计处理明细
     * @return 结果
     */
    public int updateFinanceAoAccountDetail(FinanceAoAccountDto financeAoAccountDto);

    /**
     * 批量删除会计处理明细
     *
     * @author 265799
     * @date 2023-06-25
     * @param ids 需要删除的会计处理明细主键集合
     * @return 结果
     */
    public int deleteFinanceAoAccountDetailByIds(String[] ids);

    /**
     * 删除会计处理明细信息
     *
     * @author 265799
     * @date 2023-06-25
     * @param id 会计处理明细主键
     * @return 结果
     */
    public int deleteFinanceAoAccountDetailById(String id);
}
