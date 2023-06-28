package com.jlkj.human.hm.service;

/**
 * 参数配置 服务层
 *
 * @author jlkj
 */
public interface IHumanConfigService
{
    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);
}
