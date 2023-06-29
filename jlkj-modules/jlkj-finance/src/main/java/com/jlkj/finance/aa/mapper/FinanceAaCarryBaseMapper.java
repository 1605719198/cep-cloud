package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCarryBase;
import org.springframework.stereotype.Repository;

/**
 * 自动结转规则-基本资料-集团Mapper接口
 * 
 * @author 116514
 * @date 2023-04-24
 */
@Repository
public interface FinanceAaCarryBaseMapper 
{
    /**
     * 查询自动结转规则-基本资料-集团
     * 
     * @param id 自动结转规则-基本资料-集团主键
     * @return 自动结转规则-基本资料-集团
     */
    public FinanceAaCarryBase selectFinanceAaCarryBaseById(String id);

    /**
     * 查询自动结转规则-基本资料-集团列表
     * 
     * @param financeAaCarryBase 自动结转规则-基本资料-集团
     * @return 自动结转规则-基本资料-集团集合
     */
    public List<FinanceAaCarryBase> selectFinanceAaCarryBaseList(FinanceAaCarryBase financeAaCarryBase);

    /**
     * 新增自动结转规则-基本资料-集团
     * 
     * @param financeAaCarryBase 自动结转规则-基本资料-集团
     * @return 结果
     */
    public int insertFinanceAaCarryBase(FinanceAaCarryBase financeAaCarryBase);

    /**
     * 修改自动结转规则-基本资料-集团
     * 
     * @param financeAaCarryBase 自动结转规则-基本资料-集团
     * @return 结果
     */
    public int updateFinanceAaCarryBase(FinanceAaCarryBase financeAaCarryBase);

    /**
     * 删除自动结转规则-基本资料-集团
     * 
     * @param id 自动结转规则-基本资料-集团主键
     * @return 结果
     */
    public int deleteFinanceAaCarryBaseById(String id);

    /**
     * 批量删除自动结转规则-基本资料-集团
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCarryBaseByIds(String[] ids);
}
