package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaProjectFormula;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 集团管理-纵向印项目公式Mapper接口
 * 
 * @author 116514
 * @date 2023-05-10
 */
@Repository
public interface FinanceAaProjectFormulaMapper 
{
    /**
     * 查询集团管理-纵向印项目公式
     * 
     * @param id 集团管理-纵向印项目公式主键
     * @return 集团管理-纵向印项目公式
     */
    public FinanceAaProjectFormula selectFinanceAaProjectFormulaById(String id);

    /**
     * 查询集团管理-纵向印项目公式列表
     * 
     * @param financeAaProjectFormula 集团管理-纵向印项目公式
     * @return 集团管理-纵向印项目公式集合
     */
    public List<FinanceAaProjectFormula> selectFinanceAaProjectFormulaList(FinanceAaProjectFormula financeAaProjectFormula);

    /**
     * 新增集团管理-纵向印项目公式
     * 
     * @param financeAaProjectFormula 集团管理-纵向印项目公式
     * @return 结果
     */
    public int insertFinanceAaProjectFormula(FinanceAaProjectFormula financeAaProjectFormula);

    /**
     * 修改集团管理-纵向印项目公式
     * 
     * @param financeAaProjectFormula 集团管理-纵向印项目公式
     * @return 结果
     */
    public int updateFinanceAaProjectFormula(FinanceAaProjectFormula financeAaProjectFormula);

    /**
     * 删除集团管理-纵向印项目公式
     * 
     * @param id 集团管理-纵向印项目公式主键
     * @return 结果
     */
    public int deleteFinanceAaProjectFormulaById(String id);

    /**
     * 批量删除集团管理-纵向印项目公式
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaProjectFormulaByIds(String[] ids);

    /**
     * 数据重复捡核
     * @param financeAaProjectFormula
     * @return
     */
    public int updateCheckProjectFormula(FinanceAaProjectFormula financeAaProjectFormula);

}
