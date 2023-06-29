package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaDetail;
import org.springframework.stereotype.Repository;

/**
 * 系统选单-明细设定Mapper接口
 * 
 * @author 265799
 * @date 2023-04-19
 */
@Repository
public interface FinanceAaDetailMapper 
{
    /**
     * 查询系统选单-明细设定
     * 
     * @param id 系统选单-明细设定主键
     * @return 系统选单-明细设定
     */
    public FinanceAaDetail selectFinanceAaDetailById(String id);

    /**
     * 查询系统选单-明细设定列表
     * 
     * @param financeAaDetail 系统选单-明细设定
     * @return 系统选单-明细设定集合
     */
    public List<FinanceAaDetail> selectFinanceAaDetailList(FinanceAaDetail financeAaDetail);

    /**
     * 新增系统选单-明细设定
     * 
     * @param financeAaDetail 系统选单-明细设定
     * @return 结果
     */
    public int insertFinanceAaDetail(FinanceAaDetail financeAaDetail);

    /**
     * 修改系统选单-明细设定
     * 
     * @param financeAaDetail 系统选单-明细设定
     * @return 结果
     */
    public int updateFinanceAaDetail(FinanceAaDetail financeAaDetail);

    /**
     * 删除系统选单-明细设定
     * 
     * @param id 系统选单-明细设定主键
     * @return 结果
     */
    public int deleteFinanceAaDetailById(String id);

    /**
     * 批量删除系统选单-明细设定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaDetailByIds(String[] ids);
}
