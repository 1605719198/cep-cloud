package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.redis.service.RedisService;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoPara;
import com.jlkj.finance.ao.mapper.FinanceAoParaMapper;
import com.jlkj.finance.ao.service.IFinanceAoParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报支参数维护Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-06
 */
@Service
public class FinanceAoParaServiceImpl implements IFinanceAoParaService
{
    @Autowired
    private FinanceAoParaMapper financeAoParaMapper;
    @Autowired
    private RedisService redisService;

    /**
     * 查询报支参数维护
     *
     * @param id 报支参数维护主键
     * @return 报支参数维护
     */
    @Override
    public FinanceAoPara selectFinanceAoParaById(String id)
    {
        return financeAoParaMapper.selectFinanceAoParaById(id);
    }

    /**
     * 查询报支参数维护列表
     *
     * @param financeAoPara 报支参数维护
     * @return 报支参数维护
     */
    @Override
    public List<FinanceAoPara> selectFinanceAoParaList(FinanceAoPara financeAoPara)
    {
        return financeAoParaMapper.selectFinanceAoParaList(financeAoPara);
    }

    /**
     * 新增报支参数维护
     *
     * @param financeAoPara 报支参数维护
     * @return 结果
     */
    @Override
    public int insertFinanceAoPara(FinanceAoPara financeAoPara)
    {
        List<FinanceAoPara> financeAoParas = financeAoParaMapper.selectFinanceAoParaByCompIdAndParameterValue(financeAoPara);
        if (financeAoParas.isEmpty()){
            financeAoPara.setId(IdUtils.fastSimpleUUID());
            financeAoPara.setCreateTime(DateUtils.getNowDate());
            financeAoPara.setCreateBy(SecurityUtils.getUsername());
            financeAoPara.setCreateName(SecurityUtils.getNickName());
            return financeAoParaMapper.insertFinanceAoPara(financeAoPara);
        } else {
            throw new ServiceException("参数键名："+financeAoPara.getParameterKey()+"已经存在，不可重复新增！");
        }
    }

    /**
     * 修改报支参数维护
     *
     * @param financeAoPara 报支参数维护
     * @return 结果
     */
    @Override
    public int updateFinanceAoPara(FinanceAoPara financeAoPara)
    {
        financeAoPara.setUpdateTime(DateUtils.getNowDate());
        financeAoPara.setUpdateBy(SecurityUtils.getUsername());
        financeAoPara.setUpdateName(SecurityUtils.getNickName());
        return financeAoParaMapper.updateFinanceAoPara(financeAoPara);
    }

    /**
     * 批量删除报支参数维护
     *
     * @param ids 需要删除的报支参数维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoParaByIds(String[] ids)
    {
        return financeAoParaMapper.deleteFinanceAoParaByIds(ids);
    }

    /**
     * 删除报支参数维护信息
     *
     * @param id 报支参数维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoParaById(String id)
    {
        return financeAoParaMapper.deleteFinanceAoParaById(id);
    }

//    /**
//     * 加载参数缓存数据
//     */
//    @Override
//    public void loadingAoParaCache()
//    {
//        List<FinanceAoPara> aoParaList = financeAoParaMapper.selectFinanceAoParaList()List(new FinanceAoPara());
//        for (FinanceAoPara config : aoParaList)
//        {
//            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
//        }
//    }
//
//    /**
//     * 清空参数缓存数据
//     */
//    @Override
//    public void clearAoParaCache()
//    {
//        Collection<String> keys = redisService.keys(CacheConstants.SYS_CONFIG_KEY + "*");
//        redisService.deleteObject(keys);
//    }
//
//    /**
//     * 重置参数缓存数据
//     */
//    @Override
//    public void resetAoParaCache()
//    {
//        clearAoParaCache();
//        loadingAoParaCache();
//    }
//    /**
//     * 设置cache key
//     *
//     * @param configKey 参数键
//     * @return 缓存键key
//     */
//    private String getCacheKey(String configKey)
//    {
//        return CacheConstants.SYS_CONFIG_KEY + configKey;
//    }
}
