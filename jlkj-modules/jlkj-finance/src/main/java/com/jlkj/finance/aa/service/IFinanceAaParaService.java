package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaPara;

/**
 * 会计系统参数维护Service接口
 *
 * @author 265799
 * @date 2023-04-13
 */
public interface IFinanceAaParaService
{
    /**
     * 查询会计系统参数维护
     *
     * @param id 会计系统参数维护主键
     * @return 会计系统参数维护
     */
    public FinanceAaPara selectFinanceAaParaById(String id);

    /**
     * 查询会计系统参数维护列表
     *
     * @param financeAaPara 会计系统参数维护
     * @return 会计系统参数维护集合
     */
    public List<FinanceAaPara> selectFinanceAaParaList(FinanceAaPara financeAaPara);

    /**
     * 新增会计系统参数维护
     *
     * @param financeAaPara 会计系统参数维护
     * @return 结果
     */
    public int insertFinanceAaPara(FinanceAaPara financeAaPara);

    /**
     * 修改会计系统参数维护
     *
     * @param financeAaPara 会计系统参数维护
     * @return 结果
     */
    public int updateFinanceAaPara(FinanceAaPara financeAaPara);

    /**
     * 批量删除会计系统参数维护
     *
     * @param ids 需要删除的会计系统参数维护主键集合
     * @return 结果
     */
    public int deleteFinanceAaParaByIds(String[] ids);

    /**
     * 删除会计系统参数维护信息
     *
     * @param id 会计系统参数维护主键
     * @return 结果
     */
    public int deleteFinanceAaParaById(String id);
}
