package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.Empty;
import com.jlkj.safety.si.mapper.CommonListMapper;
import com.jlkj.safety.si.service.CommonListService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 公共-数据列表 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class CommonListServiceImpl extends ServiceImpl<CommonListMapper, Empty> implements CommonListService {

    @Resource
    private CommonListMapper commonListMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeConstructionUnitPersonList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, commonListMapper.getSafeConstructionUnitPersonList(params));
    }

}
