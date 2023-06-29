package com.jlkj.finance.aa.mapper;

import java.util.List;
import java.util.Map;

import com.jlkj.finance.aa.domain.FinanceAaCompanyGroup;
import org.springframework.stereotype.Repository;

/**
 * 集团管理-会计公司维护Mapper接口
 * 
 * @author 116514
 * @date 2023-04-23
 */
@Repository
public interface FinanceAaCompanyGroupMapper 
{
    /**
     * 查询集团管理-会计公司维护
     * 
     * @param id 集团管理-会计公司维护主键
     * @return 集团管理-会计公司维护
     */
    public FinanceAaCompanyGroup selectFinanceAaCompanyGroupById(String id);

    /**
     * 查询集团管理-会计公司维护列表
     * 
     * @param financeAaCompanyGroup 集团管理-会计公司维护
     * @return 集团管理-会计公司维护集合
     */
    public List<FinanceAaCompanyGroup> selectFinanceAaCompanyGroupList(FinanceAaCompanyGroup financeAaCompanyGroup);

    /**
     * 新增集团管理-会计公司维护
     * 
     * @param financeAaCompanyGroup 集团管理-会计公司维护
     * @return 结果
     */
    public int insertFinanceAaCompanyGroup(FinanceAaCompanyGroup financeAaCompanyGroup);

    /**
     * 修改集团管理-会计公司维护
     * 
     * @param financeAaCompanyGroup 集团管理-会计公司维护
     * @return 结果
     */
    public int updateFinanceAaCompanyGroup(FinanceAaCompanyGroup financeAaCompanyGroup);

    /**
     * 删除集团管理-会计公司维护
     * 
     * @param id 集团管理-会计公司维护主键
     * @return 结果
     */
    public int deleteFinanceAaCompanyGroupById(String id);

    /**
     * 批量删除集团管理-会计公司维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCompanyGroupByIds(String[] ids);


    /**
     * 查询会计公司列表（下拉选单用）
     * @return
     */
    public List<Map<String,String>> selectCompanyList();

}
