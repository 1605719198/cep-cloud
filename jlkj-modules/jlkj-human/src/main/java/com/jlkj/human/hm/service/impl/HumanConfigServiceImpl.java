package com.jlkj.human.hm.service.impl;

import com.jlkj.common.core.constant.CacheConstants;
import com.jlkj.common.core.text.Convert;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.redis.service.RedisService;
import com.jlkj.human.hm.domain.HumanConfig;
import com.jlkj.human.hm.mapper.HumanConfigMapper;
import com.jlkj.human.hm.service.IHumanConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 参数配置 服务层实现
 *
 * @author jlkj
 */
@Service
public class HumanConfigServiceImpl implements IHumanConfigService
{
    @Autowired
    private HumanConfigMapper configMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(redisService.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        HumanConfig config = new HumanConfig();
        config.setConfigKey(configKey);
        HumanConfig retConfig = configMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig))
        {
            redisService.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }
}
