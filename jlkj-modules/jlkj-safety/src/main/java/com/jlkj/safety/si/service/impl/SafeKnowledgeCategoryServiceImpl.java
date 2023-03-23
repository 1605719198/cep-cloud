package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiKnowledgeCategory;
import com.jlkj.safety.si.mapper.SafeKnowledgeCategoryMapper;
import com.jlkj.safety.si.service.SafeKnowledgeCategoryService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 知识类别 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class SafeKnowledgeCategoryServiceImpl extends ServiceImpl<SafeKnowledgeCategoryMapper, SafeSiKnowledgeCategory> implements SafeKnowledgeCategoryService {

    @Resource
    private SafeKnowledgeCategoryMapper safeKnowledgeCategoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeKnowledgeCategoryPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeKnowledgeCategoryMapper.getSafeKnowledgeCategoryPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeKnowledgeCategory(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("knowledge_category_name", params.get("knowledge_category_name").toString());
        if (listByMap(query).size() == 0) {
            SafeSiKnowledgeCategory safeSiKnowledgeCategory = new SafeSiKnowledgeCategory();
            safeSiKnowledgeCategory.setId(IdUtil.randomUUID());
            safeSiKnowledgeCategory.setKnowledgeCategoryName(params.get("knowledge_category_name").toString());
            safeSiKnowledgeCategory.setParentKnowledgeCategoryId(params.get("parent_knowledge_category_id").toString());
            safeSiKnowledgeCategory.setSort(Integer.parseInt(params.get("sort").toString()));
            safeSiKnowledgeCategory.setCreateUserId(params.get("create_user_id").toString());
            safeSiKnowledgeCategory.setCreateUserName(params.get("create_user_name").toString());
            safeSiKnowledgeCategory.setCeateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (save(safeSiKnowledgeCategory)) {
                return ResponseUtil.toResult(params, "知识类别保存成功");
            } else {
                return ResponseUtil.toError(params, "知识类别保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "知识类别名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeKnowledgeCategory(Map<String, Object> params) {
        SafeSiKnowledgeCategory safeSiKnowledgeCategory = getById(params.get("id").toString());
        if (null != safeSiKnowledgeCategory) {
            QueryWrapper<SafeSiKnowledgeCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("knowledge_category_name", params.get("knowledge_category_name").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiKnowledgeCategory.setKnowledgeCategoryName(params.get("knowledge_category_name").toString());
                safeSiKnowledgeCategory.setParentKnowledgeCategoryId(params.get("parent_knowledge_category_id").toString());
                safeSiKnowledgeCategory.setSort(Integer.parseInt(params.get("sort").toString()));
                if (updateById(safeSiKnowledgeCategory)) {
                    return ResponseUtil.toResult(params, "知识类别保存成功");
                } else {
                    return ResponseUtil.toError(params, "知识类别保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "知识类别名称已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "知识类别不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeKnowledgeCategory(Map<String, Object> params) {
        SafeSiKnowledgeCategory safeSiKnowledgeCategory = getById(params.get("id").toString());
        if (null != safeSiKnowledgeCategory) {
            if (removeById(safeSiKnowledgeCategory.getId())) {
                return ResponseUtil.toResult(params, "知识类别删除成功");
            } else {
                return ResponseUtil.toError(params, "知识类别删除失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "知识类别不存在");
        }
    }
}
