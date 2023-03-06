package com.jlkj.material.mr.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.CommonConstant;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialscategory.AddMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.DeleteMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.PageMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.UpdateMaterialsCategoryDTO;
import com.jlkj.material.mr.domain.MaterialsCategory;
import com.jlkj.material.mr.mapper.MaterialsCategoryMapper;
import com.jlkj.material.mr.service.MaterialsCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-物料类别维护
 * @author sudeyou
 */
@Service
public class MaterialsCategoryServiceImpl extends ServiceImpl<MaterialsCategoryMapper, MaterialsCategory>
        implements MaterialsCategoryService {

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageMaterialsCategoryDTO pageMaterialsCategoryDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCategoryDTO.getCurrent(), pageMaterialsCategoryDTO.getSize());
        return getBaseMapper().getPageData(page, pageMaterialsCategoryDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> getListBoxData() {
        return getBaseMapper().getListBoxData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addData(AddMaterialsCategoryDTO addMaterialsCategoryDTO) {
        List<MaterialsCategory> list = list(new QueryWrapper<MaterialsCategory>().lambda()
                .eq(MaterialsCategory::getCategoryName, addMaterialsCategoryDTO.getCategoryName())
                .eq(MaterialsCategory::getDeleteFlag, 0)
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("类别名称已存在");
        }
        list = list(new QueryWrapper<MaterialsCategory>().lambda()
                .eq(MaterialsCategory::getCategoryCode, addMaterialsCategoryDTO.getCategoryCode())
                .eq(MaterialsCategory::getDeleteFlag, 0)
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("类别代码已存在");
        }
        MaterialsCategory materialsCategory = new MaterialsCategory();
        materialsCategory.setId(IdUtil.randomUUID());
        materialsCategory.setCategoryName(addMaterialsCategoryDTO.getCategoryName());
        materialsCategory.setCategoryCode(addMaterialsCategoryDTO.getCategoryCode());
        materialsCategory.setDeleteFlag(0);
        materialsCategory.setCreateUserId(addMaterialsCategoryDTO.getCreateUserId());
        materialsCategory.setCreateUserName(addMaterialsCategoryDTO.getCreateUserName());
        materialsCategory.setCreateTime(new Date());
        materialsCategory.setModifyUserId(addMaterialsCategoryDTO.getCreateUserId());
        materialsCategory.setModifyUserName(addMaterialsCategoryDTO.getCreateUserName());
        materialsCategory.setModifyTime(new Date());
        save(materialsCategory);
        return AjaxResult.success("物料类别增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateData(UpdateMaterialsCategoryDTO updateMaterialsCategoryDTO) {
        MaterialsCategory materialsCategory = getById(updateMaterialsCategoryDTO.getId());
        if (null != materialsCategory) {
            List<MaterialsCategory> list = list(new QueryWrapper<MaterialsCategory>().lambda()
                    .eq(MaterialsCategory::getCategoryName, updateMaterialsCategoryDTO.getCategoryName())
                    .eq(MaterialsCategory::getDeleteFlag, 0)
                    .ne(MaterialsCategory::getId, updateMaterialsCategoryDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error("类别名称已存在");
            }
            list = list(new QueryWrapper<MaterialsCategory>().lambda()
                    .eq(MaterialsCategory::getCategoryCode, updateMaterialsCategoryDTO.getCategoryCode())
                    .eq(MaterialsCategory::getDeleteFlag, 0)
                    .ne(MaterialsCategory::getId, updateMaterialsCategoryDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error("类别代码已存在");
            }
            materialsCategory.setCategoryName(updateMaterialsCategoryDTO.getCategoryName());
            materialsCategory.setCategoryCode(updateMaterialsCategoryDTO.getCategoryCode());
            materialsCategory.setModifyUserId(updateMaterialsCategoryDTO.getModifyUserId());
            materialsCategory.setModifyUserName(updateMaterialsCategoryDTO.getModifyUserName());
            materialsCategory.setModifyTime(new Date());
            updateById(materialsCategory);
            return AjaxResult.success("物料类别修改成功");
        }
        else {
            return AjaxResult.error("物料类别不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteData(DeleteMaterialsCategoryDTO deleteMaterialsCategoryDTO) {
        MaterialsCategory materialsCategory = getById(deleteMaterialsCategoryDTO.getId());
        if (null != materialsCategory) {
            if (materialsCategory.getDeleteFlag() == 1) {
                return AjaxResult.error("物料类别已删除");
            }
            materialsCategory.setDeleteFlag(1);
            updateById(materialsCategory);
            return AjaxResult.success("物料类别删除成功");
        }
        else {
            return AjaxResult.error("物料类别不存在");
        }
    }
}

