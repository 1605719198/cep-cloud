package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiWorkshopAccSection;
import com.jlkj.safety.si.mapper.SafeWorkshopAccSectionMapper;
import com.jlkj.safety.si.service.SafeWorkshopAccSectionService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.ID;

/**
 * <p>
 * 车间明细台账-工段 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-06-04
 */
@Service
public class SafeWorkshopAccSectionServiceImpl extends ServiceImpl<SafeWorkshopAccSectionMapper, SafeSiWorkshopAccSection> implements SafeWorkshopAccSectionService {
    @Resource
    private SafeWorkshopAccSectionMapper safeWorkshopAccSectionMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeWorkshopAccSectionPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeWorkshopAccSectionMapper.getSafeWorkshopAccSectionPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeWorkshopAccSection(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("workshop_name", params.get("workshop_name").toString());
        query.put("section_name", params.get("section_name").toString());
        if (listByMap(query).size() == 0) {
            SafeSiWorkshopAccSection safeSiWorkshopAccSection = new SafeSiWorkshopAccSection();
            safeSiWorkshopAccSection.setId(IdUtil.randomUUID());
            safeSiWorkshopAccSection.setSectionName(params.get("section_name").toString());
            safeSiWorkshopAccSection.setWorkshopName(params.get("workshop_name").toString());
            if (save(safeSiWorkshopAccSection)) {
                return ResponseUtil.toResult(params, "工段保存成功");
            } else {
                return ResponseUtil.toError(params, "工段保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "工段已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeWorkshopAccSection(Map<String, Object> params) {
        SafeSiWorkshopAccSection safeSiWorkshopAccSection = getById(params.get("id").toString());
        if (null != safeSiWorkshopAccSection) {
            QueryWrapper<SafeSiWorkshopAccSection> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("workshop_name", params.get("workshop_name").toString())
                    .eq("section_name", params.get("section_name").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiWorkshopAccSection.setSectionName(params.get("section_name").toString());
                if (updateById(safeSiWorkshopAccSection)) {
                    return ResponseUtil.toResult(params, "工段保存成功");
                } else {
                    return ResponseUtil.toError(params, "工段保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "工段已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "工段记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeWorkshopAccSection(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            return ResponseUtil.toResult(params, "工段删除成功");
        }
        else {
            return ResponseUtil.toError(params, "工段删除失败");
        }
    }
}
