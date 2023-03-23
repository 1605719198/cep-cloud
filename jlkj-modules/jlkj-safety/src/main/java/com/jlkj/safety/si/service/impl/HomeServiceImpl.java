package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.Empty;
import com.jlkj.safety.si.mapper.HomeMapper;
import com.jlkj.safety.si.service.HomeService;
import com.jlkj.safety.si.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安环看板 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
@Slf4j
public class HomeServiceImpl extends ServiceImpl<HomeMapper, Empty> implements HomeService {

    @Resource
    private HomeMapper homeMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getInfoSafeJobCount(Map<String, Object> params) {
        return ResponseUtil.toResult(params, homeMapper.getSafeJobCount(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getInfoConstructionUnitCount(Map<String, Object> params) {
        return ResponseUtil.toResult(params, homeMapper.getConstructionUnitCount(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getInfoConstructionUnitPersonsCount(Map<String, Object> params) {
        return ResponseUtil.toResult(params, homeMapper.getConstructionUnitPersonsCount(params));
    }

    @Override
    public Object getInfoCheckPersonsCount(Map<String, Object> params) {
        return ResponseUtil.toResult(params, homeMapper.getCheckPersonsCount(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAreaJobList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, homeMapper.getSafeAreaJobList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAreaCheckList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, homeMapper.getSafeAreaCheckList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAreaPersonList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, homeMapper.getSafeAreaPersonList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAreaAlarmsList(Map<String, Object> params) {
        try {
            Map<String, Object> out = new HashMap<>(1);
            List<Map<String, Object>> list = homeMapper.getSafeAreaAlarmsList(params);
            log.info("报警配置列表长度为: " + list.size());
            for (Map<String, Object> item : list) {
                out.put(String.valueOf(item.get("PositionNumber")), item);
            }
            return ResponseUtil.toResult(params, out);
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全报警采集配置获取失败");
        }
    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Object getSafeJobPersonList(Map<String, Object> params) {
//        return ResponseUtil.toResult(params, homeMapper.getSafeJobPersonList(params));
//    }

}
