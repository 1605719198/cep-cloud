package com.jlkj.finance.aa.service.impl;

import java.util.List;
import java.util.Map;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaCompanyGroupMapper;
import com.jlkj.finance.aa.domain.FinanceAaCompanyGroup;
import com.jlkj.finance.aa.service.IFinanceAaCompanyGroupService;

/**
 * 集团管理-会计公司维护Service业务层处理
 *
 * @author 116514
 * @date 2023-04-23
 */
@Service
public class FinanceAaCompanyGroupServiceImpl implements IFinanceAaCompanyGroupService
{
    @Autowired
    private FinanceAaCompanyGroupMapper financeAaCompanyGroupMapper;

    /**
     * 查询集团管理-会计公司维护
     *
     * @param id 集团管理-会计公司维护主键
     * @return 集团管理-会计公司维护
     */
    @Override
    public FinanceAaCompanyGroup selectFinanceAaCompanyGroupById(String id)
    {
        return financeAaCompanyGroupMapper.selectFinanceAaCompanyGroupById(id);
    }

    /**
     * 查询集团管理-会计公司维护列表
     *
     * @param financeAaCompanyGroup 集团管理-会计公司维护
     * @return 集团管理-会计公司维护
     */
    @Override
    public List<FinanceAaCompanyGroup> selectFinanceAaCompanyGroupList(FinanceAaCompanyGroup financeAaCompanyGroup)
    {
        return financeAaCompanyGroupMapper.selectFinanceAaCompanyGroupList(financeAaCompanyGroup);
    }

    /**
     * 新增集团管理-会计公司维护
     *
     * @param financeAaCompanyGroup 集团管理-会计公司维护
     * @return 结果
     */
    @Override
    public int insertFinanceAaCompanyGroup(FinanceAaCompanyGroup financeAaCompanyGroup)
    {
        financeAaCompanyGroup.setId(IdUtils.simpleUUID());
        financeAaCompanyGroup.setCreateBy(SecurityUtils.getUsername());
        financeAaCompanyGroup.setCreateName(SecurityUtils.getNickName());
        financeAaCompanyGroup.setCreateTime(DateUtils.getNowDate());
        return financeAaCompanyGroupMapper.insertFinanceAaCompanyGroup(financeAaCompanyGroup);
    }

    /**
     * 修改集团管理-会计公司维护
     *
     * @param financeAaCompanyGroup 集团管理-会计公司维护
     * @return 结果
     */
    @Override
    public int updateFinanceAaCompanyGroup(FinanceAaCompanyGroup financeAaCompanyGroup)
    {
        financeAaCompanyGroup.setUpdateBy(SecurityUtils.getUsername());
        financeAaCompanyGroup.setUpdateName(SecurityUtils.getNickName());
        financeAaCompanyGroup.setUpdateTime(DateUtils.getNowDate());
        return financeAaCompanyGroupMapper.updateFinanceAaCompanyGroup(financeAaCompanyGroup);
    }

    /**
     * 批量删除集团管理-会计公司维护
     *
     * @param ids 需要删除的集团管理-会计公司维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCompanyGroupByIds(String[] ids)
    {
        return financeAaCompanyGroupMapper.deleteFinanceAaCompanyGroupByIds(ids);
    }

    /**
     * 删除集团管理-会计公司维护信息
     *
     * @param id 集团管理-会计公司维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCompanyGroupById(String id)
    {
        return financeAaCompanyGroupMapper.deleteFinanceAaCompanyGroupById(id);
    }

    /**
     * 查询会计公司列表（下拉选单用）
     * @return
     */
    @Override
    public List<Map<String,String>> selectCompanyList(){
        return financeAaCompanyGroupMapper.selectCompanyList();
    }
}
