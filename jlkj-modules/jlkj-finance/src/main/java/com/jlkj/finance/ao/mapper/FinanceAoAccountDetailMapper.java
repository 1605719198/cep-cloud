package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoAccountDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会计处理明细Mapper接口
 * 
 * @author 265799
 * @date 2023-06-25
 */
@Repository
public interface FinanceAoAccountDetailMapper 
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
     * @param financeAoAccountDetail 会计处理明细
     * @return 会计处理明细集合
     */
    public List<FinanceAoAccountDetail> selectFinanceAoAccountDetailList(FinanceAoAccountDetail financeAoAccountDetail);

    /**
     * 新增会计处理明细
     * 
     * @param financeAoAccountDetail 会计处理明细
     * @return 结果
     */
    public int insertFinanceAoAccountDetail(FinanceAoAccountDetail financeAoAccountDetail);

    /**
     * 修改会计处理明细
     * 
     * @param financeAoAccountDetail 会计处理明细
     * @return 结果
     */
    public int updateFinanceAoAccountDetail(FinanceAoAccountDetail financeAoAccountDetail);

    /**
     * 删除会计处理明细
     * 
     * @param id 会计处理明细主键
     * @return 结果
     */
    public int deleteFinanceAoAccountDetailById(String id);

    /**
     * 批量删除会计处理明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoAccountDetailByIds(String[] ids);
}
