package com.jlkj.workflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.constant.UserConstants;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.workflow.domain.WfCategory;
import com.jlkj.workflow.domain.bo.WfCategoryBo;
import com.jlkj.workflow.domain.vo.WfCategoryVo;
import com.jlkj.workflow.mapper.WfCategoryMapper;
import com.jlkj.workflow.service.IWfCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 流程分类Service业务层处理
 *
 * @author xin
 * @date 2022-01-15
 */
@RequiredArgsConstructor
@Service
public class WfCategoryServiceImpl implements IWfCategoryService {

    private final WfCategoryMapper baseMapper;

    @Override
    public WfCategoryVo queryById(Long categoryId){
        return baseMapper.selectVoById(categoryId);
    }

    @Override
    public TableDataInfoPlus<WfCategoryVo> queryPageList(WfCategoryBo categoryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<WfCategory> lqw = buildQueryWrapper(categoryBo);
        Page<WfCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfoPlus.build(result);
    }

    @Override
    public List<WfCategoryVo> queryList(WfCategoryBo categoryBo) {
        LambdaQueryWrapper<WfCategory> lqw = buildQueryWrapper(categoryBo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfCategory> buildQueryWrapper(WfCategoryBo categoryBo) {
        Map<String, Object> params = categoryBo.getParams();
        LambdaQueryWrapper<WfCategory> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(categoryBo.getCategoryName()), WfCategory::getCategoryName, categoryBo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(categoryBo.getCode()), WfCategory::getCode, categoryBo.getCode());
        return lqw;
    }

    @Override
    public int insertCategory(WfCategoryBo categoryBo) {
        WfCategory add = BeanUtil.toBean(categoryBo, WfCategory.class);
        return baseMapper.insert(add);
    }

    @Override
    public int updateCategory(WfCategoryBo categoryBo) {
        WfCategory update = BeanUtil.toBean(categoryBo, WfCategory.class);
        return baseMapper.updateById(update);
    }

    @Override
    public int deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids);
    }

    /**
     * 校验分类编码是否唯一
     *
     * @param code 分类编码
     * @return 结果
     */
    @Override
    public String checkCategoryCodeUnique(String code,Long categoryId) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<WfCategory>().eq(WfCategory::getCode, code).ne(WfCategory::getCategoryId,categoryId));
        if (exist) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
