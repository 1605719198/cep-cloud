package com.jlkj.material.mr.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.CommonConstant;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.changelog.InsertChangeLogDTO;
import com.jlkj.material.mr.dto.materialssilo.AddMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.DeleteMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.PageMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.UpdateMaterialsSiloDTO;
import com.jlkj.material.mr.domain.MaterialsSilo;
import com.jlkj.material.mr.domain.MaterialsStorageSpaces;
import com.jlkj.material.mr.mapper.MaterialsSiloMapper;
import com.jlkj.material.mr.service.ChangeLogService;
import com.jlkj.material.mr.service.MaterialsSiloService;
import com.jlkj.material.mr.service.MaterialsStorageSpacesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-料仓维护
 * @author sudeyou
 */
@Service
public class MaterialsSiloServiceImpl extends ServiceImpl<MaterialsSiloMapper, MaterialsSilo>
    implements MaterialsSiloService {

    @Resource
    private ChangeLogService changeLogService;

    @Resource
    private MaterialsStorageSpacesService materialsStorageSpacesService;

    private boolean hasUsed(String siloId) {
        if(materialsStorageSpacesService.list(new QueryWrapper<MaterialsStorageSpaces>().lambda()
                .eq(MaterialsStorageSpaces::getSiloId, siloId)
                .last(CommonConstant.LIMIT_ONE_ROW)
        ).size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageMaterialsSiloDTO pageMaterialsSiloDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsSiloDTO.getCurrent(), pageMaterialsSiloDTO.getSize());
        return getBaseMapper().getPageData(page, pageMaterialsSiloDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> getListBoxData() {
        return getBaseMapper().getListBoxData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addData(AddMaterialsSiloDTO addMaterialsSiloDTO) {
        List<MaterialsSilo> list = list(new QueryWrapper<MaterialsSilo>().lambda()
                .eq(MaterialsSilo::getSiloName, addMaterialsSiloDTO.getSiloName())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("料仓已存在");
        }
        MaterialsSilo materialsSilo = new MaterialsSilo();
        materialsSilo.setId(IdUtil.randomUUID());
        materialsSilo.setSiloName(addMaterialsSiloDTO.getSiloName());
        materialsSilo.setCreateUserId(addMaterialsSiloDTO.getCreateUserId());
        materialsSilo.setCreateUserName(addMaterialsSiloDTO.getCreateUserName());
        materialsSilo.setCreateTime(new Date());
        materialsSilo.setModifyUserId(addMaterialsSiloDTO.getCreateUserId());
        materialsSilo.setModifyUserName(addMaterialsSiloDTO.getCreateUserName());
        materialsSilo.setModifyTime(new Date());
        materialsSilo.setSiloLength(addMaterialsSiloDTO.getSiloLength());
        save(materialsSilo);
        return AjaxResult.success("料仓增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateData(UpdateMaterialsSiloDTO updateMaterialsSiloDTO) {
        MaterialsSilo materialsSilo = getById(updateMaterialsSiloDTO.getId());
        if (null != materialsSilo) {
            List<MaterialsSilo> list = list(new QueryWrapper<MaterialsSilo>().lambda()
                    .eq(MaterialsSilo::getSiloName, updateMaterialsSiloDTO.getSiloName())
                    .ne(MaterialsSilo::getId, updateMaterialsSiloDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() == 0) {
                StringBuilder content = new StringBuilder();
                if (!materialsSilo.getSiloName().equals(updateMaterialsSiloDTO.getSiloName())) {
                    content.append("[料仓名称：").append(materialsSilo.getSiloName()).append("->").append(updateMaterialsSiloDTO.getSiloName()).append("]");
                }
                if (!materialsSilo.getSiloLength().equals(updateMaterialsSiloDTO.getSiloLength())) {
                    content.append("[料仓长度：").append(materialsSilo.getSiloLength()).append("->").append(updateMaterialsSiloDTO.getSiloLength()).append("]");
                }
                if (content.length() > 0) {
                    InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                    insertChangeLogDTO.setModuleName("物料管理");
                    insertChangeLogDTO.setFunctionName("参数维护->料仓维护");
                    insertChangeLogDTO.setContent("修改：" + content);
                    insertChangeLogDTO.setCreateUserId(updateMaterialsSiloDTO.getModifyUserId());
                    insertChangeLogDTO.setCreateUserName(updateMaterialsSiloDTO.getModifyUserName());
                    changeLogService.insertChangeLogData(insertChangeLogDTO);
                }
                materialsSilo.setSiloName(updateMaterialsSiloDTO.getSiloName());
                materialsSilo.setModifyUserId(updateMaterialsSiloDTO.getModifyUserId());
                materialsSilo.setModifyUserName(updateMaterialsSiloDTO.getModifyUserName());
                materialsSilo.setModifyTime(new Date());
                materialsSilo.setSiloLength(updateMaterialsSiloDTO.getSiloLength());
                updateById(materialsSilo);
                return AjaxResult.success("料仓修改成功");
            }
            else {
                return AjaxResult.error("料仓已存在");
            }
        }
        else {
            return AjaxResult.error("料仓不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteData(DeleteMaterialsSiloDTO deleteMaterialsSiloDTO) {
        MaterialsSilo materialsSilo = getById(deleteMaterialsSiloDTO.getId());
        if (null != materialsSilo) {
            if (hasUsed(materialsSilo.getId())) {
                return AjaxResult.error("料仓已使用，不能删除");
            }
            String content = "删除：" +
                    "[料仓名称：" + materialsSilo.getSiloName() + "]" +
                    "[料仓长度：" + materialsSilo.getSiloLength() + "]";
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("物料管理");
            insertChangeLogDTO.setFunctionName("参数维护->料仓维护");
            insertChangeLogDTO.setContent(content);
            insertChangeLogDTO.setCreateUserId(deleteMaterialsSiloDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteMaterialsSiloDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
            removeById(materialsSilo);
            return AjaxResult.success("料仓删除成功");
        }
        else {
            return AjaxResult.error("料仓不存在或已被删除");
        }
    }
}

