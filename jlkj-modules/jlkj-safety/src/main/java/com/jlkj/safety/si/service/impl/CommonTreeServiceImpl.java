package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.Empty;
import com.jlkj.safety.si.mapper.CommonTreeMapper;
import com.jlkj.safety.si.service.CommonTreeService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 公共-树型列表 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class CommonTreeServiceImpl extends ServiceImpl<CommonTreeMapper, Empty> implements CommonTreeService {

    @Resource
    private CommonTreeMapper commonTreeMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeConstructionUnitPersonTree(Map<String, Object> params) {
        return ResponseUtil.toResult(params, commonTreeMapper.getSafeConstructionUnitPersonTree(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeConstructionUnitTree(Map<String, Object> params) {
        return ResponseUtil.toResult(params, commonTreeMapper.getSafeConstructionUnitTree(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeKnowledgeCategoryTree(Map<String, Object> params) {
        return ResponseUtil.toResult(params, commonTreeMapper.getSafeKnowledgeCategoryTree(params));
    }

}
