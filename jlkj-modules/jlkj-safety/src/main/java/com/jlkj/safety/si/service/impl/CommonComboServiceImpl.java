package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.Empty;
import com.jlkj.safety.si.mapper.CommonComboMapper;
import com.jlkj.safety.si.service.CommonComboService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 公共-下拉列表 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class CommonComboServiceImpl extends ServiceImpl<CommonComboMapper, Empty> implements CommonComboService {

    @Resource
    private CommonComboMapper commonComboMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAreaDropList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, commonComboMapper.getSafeAreaDropList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeWorkTypeDropList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, commonComboMapper.getSafeWorkTypeDropList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeCheckTypeDropList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, commonComboMapper.getSafeCheckTypeDropList(params));
    }

	@Override
	@Transactional(readOnly = true)
	public Object getSafeDangerTypeDropList(Map<String, Object> params) {
		return ResponseUtil.toResult(params, commonComboMapper.getSafeDangerTypeDropList(params));
	}

    @Override
    public Object getSafeWorkshopSectionDropList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, commonComboMapper.getSafeWorkshopSectionDropList(params));
    }


}
