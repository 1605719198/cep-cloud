package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCarryBaseCorp;

/**
 * 自动结转规则-基本资料-集团Service接口
 * 
 * @author 116514
 * @date 2023-04-24
 */
public interface IFinanceAaCarryBaseCorpService 
{
    /**
     * 查询自动结转规则-基本资料-集团
     * 
     * @param id 自动结转规则-基本资料-集团主键
     * @return 自动结转规则-基本资料-集团
     */
    public FinanceAaCarryBaseCorp selectFinanceAaCarryBaseCorpById(String id);

    /**
     * 查询自动结转规则-基本资料-集团列表
     * 
     * @param financeAaCarryBaseCorp 自动结转规则-基本资料-集团
     * @return 自动结转规则-基本资料-集团集合
     */
    public List<FinanceAaCarryBaseCorp> selectFinanceAaCarryBaseCorpList(FinanceAaCarryBaseCorp financeAaCarryBaseCorp);

    /**
     * 新增自动结转规则-基本资料-集团
     * 
     * @param financeAaCarryBaseCorp 自动结转规则-基本资料-集团
     * @return 结果
     */
    public int insertFinanceAaCarryBaseCorp(FinanceAaCarryBaseCorp financeAaCarryBaseCorp);

    /**
     * 修改自动结转规则-基本资料-集团
     * 
     * @param financeAaCarryBaseCorp 自动结转规则-基本资料-集团
     * @return 结果
     */
    public int updateFinanceAaCarryBaseCorp(FinanceAaCarryBaseCorp financeAaCarryBaseCorp);

    /**
     * 批量删除自动结转规则-基本资料-集团
     * 
     * @param ids 需要删除的自动结转规则-基本资料-集团主键集合
     * @return 结果
     */
    public int deleteFinanceAaCarryBaseCorpByIds(String[] ids);

    /**
     * 删除自动结转规则-基本资料-集团信息
     * 
     * @param id 自动结转规则-基本资料-集团主键
     * @return 结果
     */
    public int deleteFinanceAaCarryBaseCorpById(String id);
}
