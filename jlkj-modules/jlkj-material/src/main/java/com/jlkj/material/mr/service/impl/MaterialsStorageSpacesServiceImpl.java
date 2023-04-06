package com.jlkj.material.mr.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.CommonConstant;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.changelog.InsertChangeLogDTO;
import com.jlkj.material.mr.dto.materialssiloareahistory.AddMaterialsSiloAreaHistoryDTO;
import com.jlkj.material.mr.dto.materialsstoragespaces.*;
import com.jlkj.material.mr.domain.MaterialsSilo;
import com.jlkj.material.mr.domain.MaterialsSiloArea;
import com.jlkj.material.mr.domain.MaterialsSiloAreaHistory;
import com.jlkj.material.mr.domain.MaterialsStorageSpaces;
import com.jlkj.material.mr.mapper.MaterialsStorageSpacesMapper;
import com.jlkj.material.mr.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-储位维护表
 * @author sudeyou
 */
@Service
public class MaterialsStorageSpacesServiceImpl extends ServiceImpl<MaterialsStorageSpacesMapper, MaterialsStorageSpaces>
        implements MaterialsStorageSpacesService {

    @Resource
    private MaterialsSiloAreaService materialsSiloAreaService;

    @Resource
    @Lazy
    private MaterialsSiloService materialsSiloService;

    @Resource
    private MaterialsSiloAreaHistoryService materialsSiloAreaHistoryService;

    @Resource
    private ChangeLogService changeLogService;

    private boolean hasUsed(String storageSpacesId) {
        if(materialsSiloAreaService.list(new QueryWrapper<MaterialsSiloArea>().lambda()
                .eq(MaterialsSiloArea::getStorageSpacesId, storageSpacesId)
                .last(CommonConstant.LIMIT_ONE_ROW)
        ).size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageMaterialsStorageSpacesDTO pageMaterialsStorageSpacesDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsStorageSpacesDTO.getCurrent(), pageMaterialsStorageSpacesDTO.getSize());
        return getBaseMapper().getPageData(page, pageMaterialsStorageSpacesDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> getListData(ListMaterialsStorageSpacesDTO listMaterialsStorageSpacesDTO) {
        return getBaseMapper().getListData(listMaterialsStorageSpacesDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addData(AddMaterialsStorageSpacesDTO addMaterialsStorageSpacesDTO) {
        List<MaterialsStorageSpaces> list = list(new QueryWrapper<MaterialsStorageSpaces>().lambda()
                .eq(MaterialsStorageSpaces::getStorageSpacesName, addMaterialsStorageSpacesDTO.getStorageSpacesName())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("储位名称已存在");
        }
        MaterialsSilo materialsSilo = materialsSiloService.getById(addMaterialsStorageSpacesDTO.getSiloId());
        String content = "新增：" +
                "[料仓名称：" + materialsSilo.getSiloName() + "]" +
                "[储位名称：" + addMaterialsStorageSpacesDTO.getStorageSpacesName() + "]";
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("物料管理");
        insertChangeLogDTO.setFunctionName("参数维护->储位维护");
        insertChangeLogDTO.setContent(content);
        insertChangeLogDTO.setCreateUserId(addMaterialsStorageSpacesDTO.getCreateUserId());
        insertChangeLogDTO.setCreateUserName(addMaterialsStorageSpacesDTO.getCreateUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);

        MaterialsStorageSpaces materialsStorageSpaces = new MaterialsStorageSpaces();
        materialsStorageSpaces.setId(IdUtil.randomUUID());
        materialsStorageSpaces.setSiloId(addMaterialsStorageSpacesDTO.getSiloId());
        materialsStorageSpaces.setMaterialCategoryId(addMaterialsStorageSpacesDTO.getMaterialCategoryId());
        materialsStorageSpaces.setStorageSpacesName(addMaterialsStorageSpacesDTO.getStorageSpacesName());
        materialsStorageSpaces.setCreateUserId(addMaterialsStorageSpacesDTO.getCreateUserId());
        materialsStorageSpaces.setCreateUserName(addMaterialsStorageSpacesDTO.getCreateUserName());
        materialsStorageSpaces.setCreateTime(new Date());
        materialsStorageSpaces.setModifyUserId(addMaterialsStorageSpacesDTO.getCreateUserId());
        materialsStorageSpaces.setModifyUserName(addMaterialsStorageSpacesDTO.getCreateUserName());
        materialsStorageSpaces.setModifyTime(new Date());
        materialsStorageSpaces.setMaterialsSmallCode(addMaterialsStorageSpacesDTO.getMaterialsSmallCode());
        materialsStorageSpaces.setCoalSort(addMaterialsStorageSpacesDTO.getCoalSort());
        save(materialsStorageSpaces);
        return AjaxResult.success("储位增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateData(UpdateMaterialsStorageSpacesDTO updateMaterialsStorageSpacesDTO) {
        MaterialsStorageSpaces materialsStorageSpaces = getById(updateMaterialsStorageSpacesDTO.getId());
        if (null != materialsStorageSpaces) {
            List<MaterialsStorageSpaces> list = list(new QueryWrapper<MaterialsStorageSpaces>().lambda()
                    .eq(MaterialsStorageSpaces::getStorageSpacesName, updateMaterialsStorageSpacesDTO.getStorageSpacesName())
                    .ne(MaterialsStorageSpaces::getId, updateMaterialsStorageSpacesDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() == 0) {
                StringBuilder content = new StringBuilder();
                if (!materialsStorageSpaces.getSiloId().equals(updateMaterialsStorageSpacesDTO.getSiloId())) {
                    MaterialsSilo materialsSiloOld = materialsSiloService.getById(materialsStorageSpaces.getSiloId());
                    MaterialsSilo materialsSiloNew = materialsSiloService.getById(updateMaterialsStorageSpacesDTO.getSiloId());
                    content.append("[料仓名称：").append(materialsSiloOld.getSiloName()).append("->").append(materialsSiloNew.getSiloName()).append("]");
                }
                if (!materialsStorageSpaces.getStorageSpacesName().equals(updateMaterialsStorageSpacesDTO.getStorageSpacesName())) {
                    content.append("[储位名称：").append(materialsStorageSpaces.getStorageSpacesName()).append("->").append(updateMaterialsStorageSpacesDTO.getStorageSpacesName()).append("]");
                }
                if (content.length() > 0) {
                    InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                    insertChangeLogDTO.setModuleName("物料管理");
                    insertChangeLogDTO.setFunctionName("参数维护->储位维护");
                    insertChangeLogDTO.setContent("修改：" + content);
                    insertChangeLogDTO.setCreateUserId(updateMaterialsStorageSpacesDTO.getModifyUserId());
                    insertChangeLogDTO.setCreateUserName(updateMaterialsStorageSpacesDTO.getModifyUserName());
                    changeLogService.insertChangeLogData(insertChangeLogDTO);
                }
                if (!materialsStorageSpaces.getMaterialCategoryId().equals(updateMaterialsStorageSpacesDTO.getMaterialCategoryId())) {
                    List<MaterialsSiloAreaHistory> listMaterialsSiloAreaHistory = materialsSiloAreaHistoryService.list(new QueryWrapper<MaterialsSiloAreaHistory>().lambda()
                            .eq(MaterialsSiloAreaHistory::getSiloId, materialsStorageSpaces.getSiloId())
                            .eq(MaterialsSiloAreaHistory::getStorageSpacesId, updateMaterialsStorageSpacesDTO.getId())
                            .orderByDesc(MaterialsSiloAreaHistory::getEndTime)
                            .last(CommonConstant.LIMIT_ONE_ROW)
                    );
                    AddMaterialsSiloAreaHistoryDTO addMaterialsSiloAreaHistoryDTO = new AddMaterialsSiloAreaHistoryDTO();
                    addMaterialsSiloAreaHistoryDTO.setSiloId(materialsStorageSpaces.getSiloId());
                    addMaterialsSiloAreaHistoryDTO.setStorageSpacesId(updateMaterialsStorageSpacesDTO.getId());
                    addMaterialsSiloAreaHistoryDTO.setMaterialsId(materialsStorageSpaces.getMaterialCategoryId());
                    addMaterialsSiloAreaHistoryDTO.setMaterialsName(materialsStorageSpaces.getStorageSpacesName());
                    addMaterialsSiloAreaHistoryDTO.setStartTime(listMaterialsSiloAreaHistory.size() > 0 ? DateUtil.formatDateTime(listMaterialsSiloAreaHistory.get(0).getEndTime()) : null);
                    addMaterialsSiloAreaHistoryDTO.setEndTime(DateUtil.formatDateTime(new Date()));
                    materialsSiloAreaHistoryService.addData(addMaterialsSiloAreaHistoryDTO);
                }

                materialsStorageSpaces.setSiloId(updateMaterialsStorageSpacesDTO.getSiloId());
                materialsStorageSpaces.setMaterialCategoryId(updateMaterialsStorageSpacesDTO.getMaterialCategoryId());
                materialsStorageSpaces.setStorageSpacesName(updateMaterialsStorageSpacesDTO.getStorageSpacesName());
                materialsStorageSpaces.setModifyUserId(updateMaterialsStorageSpacesDTO.getModifyUserId());
                materialsStorageSpaces.setModifyUserName(updateMaterialsStorageSpacesDTO.getModifyUserName());
                materialsStorageSpaces.setModifyTime(new Date());
                materialsStorageSpaces.setMaterialsSmallCode(updateMaterialsStorageSpacesDTO.getMaterialsSmallCode());
                materialsStorageSpaces.setCoalSort(updateMaterialsStorageSpacesDTO.getCoalSort());
                updateById(materialsStorageSpaces);
                return AjaxResult.success("储位修改成功");
            }
            else {
                return AjaxResult.error("储位名称已存在");
            }
        }
        else {
            return AjaxResult.error("储位不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteData(DeleteMaterialsStorageSpacesDTO deleteMaterialsStorageSpacesDTO) {
        MaterialsStorageSpaces materialsStorageSpaces = getById(deleteMaterialsStorageSpacesDTO.getId());
        if (null != materialsStorageSpaces) {
            if (hasUsed(materialsStorageSpaces.getId())) {
                return AjaxResult.error("储位已使用，不能删除");
            }
            MaterialsSilo materialsSilo = materialsSiloService.getById(materialsStorageSpaces.getSiloId());
            String content = "删除：" +
                    "[料仓名称：" + materialsSilo.getSiloName() + "]" +
                    "[储位名称：" + materialsStorageSpaces.getStorageSpacesName() + "]";
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("物料管理");
            insertChangeLogDTO.setFunctionName("参数维护->储位维护");
            insertChangeLogDTO.setContent(content);
            insertChangeLogDTO.setCreateUserId(deleteMaterialsStorageSpacesDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteMaterialsStorageSpacesDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
            removeById(materialsStorageSpaces);
            return AjaxResult.success("储位删除成功");
        }
        else {
            return AjaxResult.error("储位不存在或已被删除");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> getListDataBox() {
        return getBaseMapper().getListDataBox();
    }
}

