package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaProjectFormula;
import com.jlkj.finance.aa.mapper.FinanceAaProjectFormulaMapper;
import com.jlkj.finance.aa.service.IFinanceAaProjectFormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 集团管理-纵向印项目公式Service业务层处理
 *
 * @author 116514
 * @date 2023-05-10
 */
@Service
public class FinanceAaProjectFormulaServiceImpl implements IFinanceAaProjectFormulaService
{
    @Autowired
    private FinanceAaProjectFormulaMapper financeAaProjectFormulaMapper;

    /**
     * 查询集团管理-纵向印项目公式
     *
     * @param id 集团管理-纵向印项目公式主键
     * @return 集团管理-纵向印项目公式
     */
    @Override
    public FinanceAaProjectFormula selectFinanceAaProjectFormulaById(String id)
    {
        return financeAaProjectFormulaMapper.selectFinanceAaProjectFormulaById(id);
    }

    /**
     * 查询集团管理-纵向印项目公式列表
     *
     * @param financeAaProjectFormula 集团管理-纵向印项目公式
     * @return 集团管理-纵向印项目公式
     */
    @Override
    public List<FinanceAaProjectFormula> selectFinanceAaProjectFormulaList(FinanceAaProjectFormula financeAaProjectFormula)
    {
        return financeAaProjectFormulaMapper.selectFinanceAaProjectFormulaList(financeAaProjectFormula);
    }

    /**
     * 新增集团管理-纵向印项目公式
     *
     * @param financeAaProjectFormula 集团管理-纵向印项目公式
     * @return 结果
     */
    @Override
    public int insertFinanceAaProjectFormula(FinanceAaProjectFormula financeAaProjectFormula)
    {
        financeAaProjectFormula.setCreateTime(DateUtils.getNowDate());
        return financeAaProjectFormulaMapper.insertFinanceAaProjectFormula(financeAaProjectFormula);
    }


    /**
     * 多笔新增
     * @param dataList
     * @return
     */
    @Override
    public int insertFinanceAaProjectFormulaItem( List<FinanceAaProjectFormula> dataList)
    {
        int count = 0;
        for ( FinanceAaProjectFormula financeAaCashflowItem: dataList){
            if (financeAaCashflowItem.getId()!=null){
                // 印表项目编码不允许修改
                FinanceAaProjectFormula financeAaProjectFormula = financeAaProjectFormulaMapper.selectFinanceAaProjectFormulaById(financeAaCashflowItem.getId());
                if(!financeAaProjectFormula.getItemCode().equals(financeAaCashflowItem.getItemCode())){
                    throw new ServiceException("印表项目编码："+financeAaCashflowItem.getItemCode()+"不可修改！");
                }
                financeAaCashflowItem.setUpdateBy(SecurityUtils.getUsername());
                financeAaCashflowItem.setUpdateName(SecurityUtils.getNickName());
                financeAaCashflowItem.setUpdateTime(DateUtils.getNowDate());
                count =  financeAaProjectFormulaMapper.updateFinanceAaProjectFormula(financeAaCashflowItem);
            }else {
                // 资料重复捡核
                int num = financeAaProjectFormulaMapper.updateCheckProjectFormula(financeAaCashflowItem);
                if(num>0){
                    throw new ServiceException("印表项目编码："+financeAaCashflowItem.getItemCode()+"已经存在，不可重复新增！");
                }
                financeAaCashflowItem.setId(IdUtils.simpleUUID());
                financeAaCashflowItem.setCreateBy(SecurityUtils.getUsername());
                financeAaCashflowItem.setCreateName(SecurityUtils.getNickName());
                financeAaCashflowItem.setCreateTime(DateUtils.getNowDate());
                count = financeAaProjectFormulaMapper.insertFinanceAaProjectFormula(financeAaCashflowItem);
            }
        }
        return count;
    }

    /**
     * 修改集团管理-纵向印项目公式
     *
     * @param financeAaProjectFormula 集团管理-纵向印项目公式
     * @return 结果
     */
    @Override
    public int updateFinanceAaProjectFormula(FinanceAaProjectFormula financeAaProjectFormula)
    {
        financeAaProjectFormula.setUpdateTime(DateUtils.getNowDate());
        return financeAaProjectFormulaMapper.updateFinanceAaProjectFormula(financeAaProjectFormula);
    }

    /**
     * 批量删除集团管理-纵向印项目公式
     *
     * @param ids 需要删除的集团管理-纵向印项目公式主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaProjectFormulaByIds(String[] ids)
    {
        return financeAaProjectFormulaMapper.deleteFinanceAaProjectFormulaByIds(ids);
    }

    /**
     * 删除集团管理-纵向印项目公式信息
     *
     * @param id 集团管理-纵向印项目公式主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaProjectFormulaById(String id)
    {
        return financeAaProjectFormulaMapper.deleteFinanceAaProjectFormulaById(id);
    }
}
