package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaPara;
import org.springframework.stereotype.Repository;

/**
 * 会计系统参数维护Mapper接口
 *
 * @author 265799
 * @date 2023-04-13
 */
@Repository
public interface FinanceAaParaMapper
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
     * 删除会计系统参数维护
     *
     * @param id 会计系统参数维护主键
     * @return 结果
     */
    public int deleteFinanceAaParaById(String id);

    /**
     * 批量删除会计系统参数维护
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaParaByIds(String[] ids);
}
