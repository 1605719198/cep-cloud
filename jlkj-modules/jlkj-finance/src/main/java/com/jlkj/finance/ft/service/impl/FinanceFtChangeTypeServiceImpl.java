package com.jlkj.finance.ft.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtChangeType;
import com.jlkj.finance.ft.mapper.FinanceFtChangeTypeMapper;
import com.jlkj.finance.ft.service.IFinanceFtChangeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 固定资产变动方式Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-26
 */
@Service
public class FinanceFtChangeTypeServiceImpl implements IFinanceFtChangeTypeService
{
    @Autowired
    private FinanceFtChangeTypeMapper financeFtChangeTypeMapper;

    /**
     * 查询固定资产变动方式
     *
     * @param uuid 固定资产变动方式主键
     * @return 固定资产变动方式
     */
    @Override
    public FinanceFtChangeType selectFinanceFtChangeTypeByUuid(String uuid)
    {
        return financeFtChangeTypeMapper.selectFinanceFtChangeTypeByUuid(uuid);
    }

    /**
     * 查询固定资产变动方式列表
     *
     * @param financeFtChangeType 固定资产变动方式
     * @return 固定资产变动方式
     */
    @Override
    public List<FinanceFtChangeType> selectFinanceFtChangeTypeList(FinanceFtChangeType financeFtChangeType)
    {
        return financeFtChangeTypeMapper.selectFinanceFtChangeTypeList(financeFtChangeType);
    }

    /**
     * 新增固定资产变动方式
     *
     * @param financeFtChangeType 固定资产变动方式
     * @return 结果
     */
    @Override
    public int insertFinanceFtChangeType(FinanceFtChangeType financeFtChangeType)
    {
        List<FinanceFtChangeType> financeFtChangeTypes = financeFtChangeTypeMapper.selectFinanceFtChangeTypeByCompIdAndChangeWay(financeFtChangeType);
        if (financeFtChangeTypes.isEmpty()){
            financeFtChangeType.setUuid(IdUtils.fastSimpleUUID());
            financeFtChangeType.setCreateTime(DateUtils.getNowDate());
            financeFtChangeType.setCreateBy(SecurityUtils.getUsername());
            financeFtChangeType.setCreateName(SecurityUtils.getNickName());
            return financeFtChangeTypeMapper.insertFinanceFtChangeType(financeFtChangeType);
        } else {
            throw new ServiceException("变动方式代码或名称："+financeFtChangeType.getChangeWay()+financeFtChangeType.getChangeName()+"已经存在，不可重复新增！");
        }
    }

    /**
     * 修改固定资产变动方式
     *
     * @param financeFtChangeType 固定资产变动方式
     * @return 结果
     */
    @Override
    public int updateFinanceFtChangeType(FinanceFtChangeType financeFtChangeType)
    {
        financeFtChangeType.setUpdateTime(DateUtils.getNowDate());
        financeFtChangeType.setUpdateBy(SecurityUtils.getUsername());
        financeFtChangeType.setUpdateName(SecurityUtils.getNickName());
        return financeFtChangeTypeMapper.updateFinanceFtChangeType(financeFtChangeType);
    }

    /**
     * 批量删除固定资产变动方式
     *
     * @param uuids 需要删除的固定资产变动方式主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtChangeTypeByUuids(String[] uuids)
    {
        return financeFtChangeTypeMapper.deleteFinanceFtChangeTypeByUuids(uuids);
    }

    /**
     * 删除固定资产变动方式信息
     *
     * @param uuid 固定资产变动方式主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtChangeTypeByUuid(String uuid)
    {
        return financeFtChangeTypeMapper.deleteFinanceFtChangeTypeByUuid(uuid);
    }
    /**
     * @Description:获取变动方式下拉选单
     * @param:companyId 公司别
     * @param:billNature 单据性质
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @Author: 116524_wxj
     * @Date: 2023/7/8 14:08
     */
    public List<Map<String,String>> selectChangeTypeList(String companyId, String billNature){
        return financeFtChangeTypeMapper.selectChangeTypeList(companyId,billNature);
    }
}
