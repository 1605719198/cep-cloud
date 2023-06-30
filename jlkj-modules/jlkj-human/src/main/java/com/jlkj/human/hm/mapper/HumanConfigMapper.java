package com.jlkj.human.hm.mapper;

import com.jlkj.human.hm.domain.HumanConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数配置 数据层
 *
 * @author jlkj
 */
@Mapper
public interface HumanConfigMapper
{
    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    public HumanConfig selectConfig(HumanConfig config);
}
