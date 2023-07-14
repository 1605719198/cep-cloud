package com.jlkj.material.mr.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.CommonConstant;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialscode.*;
import com.jlkj.material.mr.domain.MaterialsCode;
import com.jlkj.material.mr.mapper.MaterialsCodeMapper;
import com.jlkj.material.mr.service.MaterialsCodeService;
import com.jlkj.material.mr.vo.materialscode.ListMaterialsCodeByTypeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-物料代码维护
 * @author sudeyou
 */
@Service
public class MaterialsCodeServiceImpl extends ServiceImpl<MaterialsCodeMapper, MaterialsCode>
        implements MaterialsCodeService {

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageMaterialsCodeDTO pageMaterialsCodeDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCodeDTO.getCurrent(), pageMaterialsCodeDTO.getSize());
        return getBaseMapper().getPageData(page, pageMaterialsCodeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> getListBoxData() {
        return getBaseMapper().getListBoxData();
    }

    @Override
    public List<Map<String, String>> getListBoxByCataIdData(ListMaterialsCodeDTO listMaterialsCodeDTO) {
        return getBaseMapper().getListBoxByCataIdData(listMaterialsCodeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getListBoxDataM() {
        List<Map<String, Object>> list = listMaps(new LambdaQueryWrapper<MaterialsCode>()
                .eq(MaterialsCode::getMtrltype, "00")
                .eq(MaterialsCode::getDeleteFlag, 0)
                .orderByDesc(MaterialsCode::getCreateTime));
        return list;
    }

    /**
     * 查询-列表-物料代码-下拉-焦
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getListBoxDataJ() {
        List<Map<String, Object>> list = listMaps(new LambdaQueryWrapper<MaterialsCode>()
                .eq(MaterialsCode::getMtrltype, "01")
                .eq(MaterialsCode::getDeleteFlag, 0)
                .orderByDesc(MaterialsCode::getCreateTime));
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addData(AddMaterialsCodeDTO addMaterialsCodeDTO) {
        List<MaterialsCode> list = list(new QueryWrapper<MaterialsCode>().lambda()
                .eq(MaterialsCode::getMaterialsName, addMaterialsCodeDTO.getMaterialsName())
                .eq(MaterialsCode::getDeleteFlag, 0)
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("物料名称已存在");
        }
        list = list(new QueryWrapper<MaterialsCode>().lambda()
                .eq(MaterialsCode::getMaterialsCode, addMaterialsCodeDTO.getMaterialsCode())
                .eq(MaterialsCode::getDeleteFlag, 0)
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("物料代码已存在");
        }
        MaterialsCode materialsCode = new MaterialsCode();
        materialsCode.setId(IdUtil.randomUUID());
        materialsCode.setCategoryId(addMaterialsCodeDTO.getCategoryId());
        materialsCode.setMaterialsName(addMaterialsCodeDTO.getMaterialsName());
        materialsCode.setMaterialsCode(addMaterialsCodeDTO.getMaterialsCode());
        materialsCode.setMeasurementUnit(addMaterialsCodeDTO.getMeasurementUnit());
        materialsCode.setDeleteFlag(0);
        materialsCode.setCreateUserId(addMaterialsCodeDTO.getCreateUserId());
        materialsCode.setCreateUserName(addMaterialsCodeDTO.getCreateUserName());
        materialsCode.setCreateTime(new Date());
        materialsCode.setModifyUserId(addMaterialsCodeDTO.getCreateUserId());
        materialsCode.setModifyUserName(addMaterialsCodeDTO.getCreateUserName());
        materialsCode.setModifyTime(new Date());
        save(materialsCode);
        return AjaxResult.success("物料代码增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateData(UpdateMaterialsCodeDTO updateMaterialsCodeDTO) {
        MaterialsCode materialsCode = getById(updateMaterialsCodeDTO.getId());
        if (null != materialsCode) {
            List<MaterialsCode> list = list(new QueryWrapper<MaterialsCode>().lambda()
                    .eq(MaterialsCode::getMaterialsName, updateMaterialsCodeDTO.getMaterialsName())
                    .eq(MaterialsCode::getDeleteFlag, 0)
                    .ne(MaterialsCode::getId, updateMaterialsCodeDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error("物料名称已存在");
            }
            list = list(new QueryWrapper<MaterialsCode>().lambda()
                    .eq(MaterialsCode::getMaterialsCode, updateMaterialsCodeDTO.getMaterialsCode())
                    .eq(MaterialsCode::getDeleteFlag, 0)
                    .ne(MaterialsCode::getId, updateMaterialsCodeDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error("物料代码已存在");
            }
            materialsCode.setCategoryId(updateMaterialsCodeDTO.getCategoryId());
            materialsCode.setMaterialsName(updateMaterialsCodeDTO.getMaterialsName());
            materialsCode.setMaterialsCode(updateMaterialsCodeDTO.getMaterialsCode());
            materialsCode.setMeasurementUnit(updateMaterialsCodeDTO.getMeasurementUnit());
            materialsCode.setModifyUserId(updateMaterialsCodeDTO.getModifyUserId());
            materialsCode.setModifyUserName(updateMaterialsCodeDTO.getModifyUserName());
            materialsCode.setModifyTime(new Date());
            updateById(materialsCode);
            return AjaxResult.success("物料代码修改成功");
        }
        else {
            return AjaxResult.error("物料代码不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteData(DeleteMaterialsCodeDTO deleteMaterialsCodeDTO) {
        MaterialsCode materialsCode = getById(deleteMaterialsCodeDTO.getId());
        if (null != materialsCode) {
            if (materialsCode.getDeleteFlag() == 1) {
                return AjaxResult.error("物料代码已删除");
            }
            materialsCode.setDeleteFlag(1);
            updateById(materialsCode);
            return AjaxResult.success("物料代码删除成功");
        }
        else {
            return AjaxResult.error("物料代码不存在");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListMaterialsCodeByTypeVO> getMaterialsCodeListByTypeData(ListMaterialsCodeByTypeDTO listMaterialsCodeByTypeDTO) {
        return getBaseMapper().getMaterialsCodeListByTypeData(listMaterialsCodeByTypeDTO);
    }
}

