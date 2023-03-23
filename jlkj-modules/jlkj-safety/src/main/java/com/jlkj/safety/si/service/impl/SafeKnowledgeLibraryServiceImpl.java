package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiKnowledgeLibrary;
import com.jlkj.safety.si.mapper.SafeKnowledgeLibraryMapper;
import com.jlkj.safety.si.service.SafeKnowledgeLibraryService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 知识库 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class SafeKnowledgeLibraryServiceImpl extends ServiceImpl<SafeKnowledgeLibraryMapper, SafeSiKnowledgeLibrary> implements SafeKnowledgeLibraryService {

    @Resource
    private SafeKnowledgeLibraryMapper safeKnowledgeLibraryMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeKnowledgeLibraryPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeKnowledgeLibraryMapper.getSafeKnowledgeLibraryPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeKnowledgeLibraryFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeKnowledgeLibraryMapper.getSafeKnowledgeLibraryFileList(params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeKnowledgeLibrary(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("title", params.get("title").toString());
        if (listByMap(query).size() == 0) {
            SafeSiKnowledgeLibrary safeSiKnowledgeLibrary = new SafeSiKnowledgeLibrary();
            safeSiKnowledgeLibrary.setId(IdUtil.randomUUID());
            safeSiKnowledgeLibrary.setKnowledgeCategoryId(params.get("knowledge_category_id").toString());
            safeSiKnowledgeLibrary.setTitle(params.get("title").toString());
            safeSiKnowledgeLibrary.setKeywords(params.get("keywords").toString());
            safeSiKnowledgeLibrary.setContents(params.get("contents").toString());
            safeSiKnowledgeLibrary.setCreateUserId(params.get("create_user_id").toString());
            safeSiKnowledgeLibrary.setCreateUserName(params.get("create_user_name").toString());
            safeSiKnowledgeLibrary.setCeateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (save(safeSiKnowledgeLibrary)) {
                return ResponseUtil.toResult(params, "知识库保存成功", safeSiKnowledgeLibrary);
            } else {
                return ResponseUtil.toError(params, "知识库保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "标题已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeKnowledgeLibrary(Map<String, Object> params) {
        SafeSiKnowledgeLibrary safeSiKnowledgeLibrary = getById(params.get("id").toString());
        if (null != safeSiKnowledgeLibrary) {
            QueryWrapper<SafeSiKnowledgeLibrary> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("title", params.get("title").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiKnowledgeLibrary.setKnowledgeCategoryId(params.get("knowledge_category_id").toString());
                safeSiKnowledgeLibrary.setTitle(params.get("title").toString());
                safeSiKnowledgeLibrary.setKeywords(params.get("keywords").toString());
                safeSiKnowledgeLibrary.setContents(params.get("contents").toString());
                if (updateById(safeSiKnowledgeLibrary)) {
                    return ResponseUtil.toResult(params, "知识库保存成功");
                } else {
                    return ResponseUtil.toError(params, "知识库保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "标题已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "知识库不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeKnowledgeLibrary(Map<String, Object> params) {
        SafeSiKnowledgeLibrary safeSiKnowledgeLibrary = getById(params.get("id").toString());
        if (null != safeSiKnowledgeLibrary) {
            if (removeById(safeSiKnowledgeLibrary.getId())) {
                safeKnowledgeLibraryMapper.deleteSafeKnowledgeLibraryFiles(params);
                return ResponseUtil.toResult(params, "知识库删除成功");
            } else {
                return ResponseUtil.toError(params, "知识库删除失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "知识库不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeKnowledgeLibraryFiles(Map<String, Object> params) {
        safeKnowledgeLibraryMapper.deleteSafeKnowledgeLibraryFiles(params);
        return true;
    }
}
