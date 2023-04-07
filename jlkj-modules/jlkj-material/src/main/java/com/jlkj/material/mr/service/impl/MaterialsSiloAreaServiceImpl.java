package com.jlkj.material.mr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.CommonConstant;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.changelog.InsertChangeLogDTO;
import com.jlkj.material.mr.dto.materialssiloarea.DeleteMaterialsSiloAreaDTO;
import com.jlkj.material.mr.dto.materialssiloarea.ListMaterialsSiloAreaDTO;
import com.jlkj.material.mr.dto.materialssiloarea.SaveMaterialsSiloAreaDTO;
import com.jlkj.material.mr.dto.materialssiloarea.UpdateMaterialsSiloAreaDTO;
import com.jlkj.material.mr.domain.MaterialsSilo;
import com.jlkj.material.mr.domain.MaterialsSiloArea;
import com.jlkj.material.mr.domain.MaterialsStorageSpaces;
import com.jlkj.material.mr.mapper.MaterialsSiloAreaMapper;
import com.jlkj.material.mr.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-料仓区域维护
 * @author sudeyou
 */
@Service
public class MaterialsSiloAreaServiceImpl extends ServiceImpl<MaterialsSiloAreaMapper, MaterialsSiloArea>
        implements MaterialsSiloAreaService {

    @Lazy
    @Resource
    private MaterialsSiloService materialsSiloService;

    @Resource
    private MaterialsSiloAreaHistoryService materialsSiloAreaHistoryService;

    @Resource
    @Lazy
    private MaterialsStorageSpacesService materialsStorageSpacesService;

    @Resource
    private ChangeLogService changeLogService;

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> getListData(ListMaterialsSiloAreaDTO listMaterialsSiloAreaDTO) {
        return getBaseMapper().getListData(listMaterialsSiloAreaDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateData(SaveMaterialsSiloAreaDTO saveMaterialsSiloAreaDTO) {
        int len = 0;
        MaterialsSilo materialsSilo = materialsSiloService.getById(saveMaterialsSiloAreaDTO.getSiloId());
        for (UpdateMaterialsSiloAreaDTO updateMaterialsSiloAreaDTO: saveMaterialsSiloAreaDTO.getStorageSpaces()) {
            List<MaterialsSiloArea> list = list(new QueryWrapper<MaterialsSiloArea>().lambda() .ne(MaterialsSiloArea::getSiloId, saveMaterialsSiloAreaDTO.getSiloId()) .ne(MaterialsSiloArea::getStorageSpacesId, updateMaterialsSiloAreaDTO.getStorageSpacesId()) .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                MaterialsSiloArea materialsSiloArea = list.get(0);
                if (null != materialsSiloArea.getStorageQuantity() && materialsSiloArea.getStorageQuantity().doubleValue() > 0 && materialsSiloArea.getStartLocation().equals(materialsSiloArea.getEndLocation())) {
                    return AjaxResult.error("不能把储量大于零的储位长度设置为零");
                }
            }
            len += updateMaterialsSiloAreaDTO.getEndLocation() - updateMaterialsSiloAreaDTO.getStartLocation();
        }
        if (len > materialsSilo.getSiloLength()) {
            return AjaxResult.error("储位总长度不能超过料仓长度");
        }
        for (UpdateMaterialsSiloAreaDTO updateMaterialsSiloAreaDTO: saveMaterialsSiloAreaDTO.getStorageSpaces()) {
            List<MaterialsSiloArea> list = list(new QueryWrapper<MaterialsSiloArea>().lambda()
                    .eq(MaterialsSiloArea::getSiloId, saveMaterialsSiloAreaDTO.getSiloId())
                    .eq(MaterialsSiloArea::getStorageSpacesId, updateMaterialsSiloAreaDTO.getStorageSpacesId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() == 0) {
                MaterialsStorageSpaces materialsStorageSpaces = materialsStorageSpacesService.getById(updateMaterialsSiloAreaDTO.getStorageSpacesId());
                String content = "新增：" + "[储位名称：" + materialsStorageSpaces.getStorageSpacesName() + "]" + "[颜色值：" + updateMaterialsSiloAreaDTO.getColorValue() + "]" + "[起始位置：" + updateMaterialsSiloAreaDTO.getStartLocation() + "]" + "[结束位置：" + updateMaterialsSiloAreaDTO.getEndLocation() + "]";
                InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                insertChangeLogDTO.setModuleName("物料管理");
                insertChangeLogDTO.setFunctionName("区域配置");
                insertChangeLogDTO.setContent(content);
                insertChangeLogDTO.setCreateUserId(saveMaterialsSiloAreaDTO.getCreateUserId());
                insertChangeLogDTO.setCreateUserName(saveMaterialsSiloAreaDTO.getCreateUserName());
                changeLogService.insertChangeLogData(insertChangeLogDTO);
                MaterialsSiloArea materialsSiloArea = new MaterialsSiloArea();
                materialsSiloArea.setSiloId(saveMaterialsSiloAreaDTO.getSiloId());
                materialsSiloArea.setStorageSpacesId(updateMaterialsSiloAreaDTO.getStorageSpacesId());
                materialsSiloArea.setStartLocation(updateMaterialsSiloAreaDTO.getStartLocation());
                materialsSiloArea.setEndLocation(updateMaterialsSiloAreaDTO.getEndLocation());
                materialsSiloArea.setColorValue(updateMaterialsSiloAreaDTO.getColorValue());
                materialsSiloArea.setCreateUserId(saveMaterialsSiloAreaDTO.getCreateUserId());
                materialsSiloArea.setCreateUserName(saveMaterialsSiloAreaDTO.getCreateUserName());
                materialsSiloArea.setCreateTime(new Date());
                materialsSiloArea.setModifyUserId(saveMaterialsSiloAreaDTO.getCreateUserId());
                materialsSiloArea.setModifyUserName(saveMaterialsSiloAreaDTO.getCreateUserName());
                materialsSiloArea.setModifyTime(new Date());
                save(materialsSiloArea);
            } else {
                MaterialsSiloArea materialsSiloArea = list.get(0);
                StringBuilder content = new StringBuilder();
                if (!materialsSiloArea.getStartLocation().equals(updateMaterialsSiloAreaDTO.getStartLocation())) {
                    content.append("[起始位置：").append(materialsSiloArea.getStartLocation()).append("->").append(updateMaterialsSiloAreaDTO.getStartLocation()).append("]");
                }
                if (!materialsSiloArea.getEndLocation().equals(updateMaterialsSiloAreaDTO.getEndLocation())) {
                    content.append("[结束位置：").append(materialsSiloArea.getEndLocation()).append("->").append(updateMaterialsSiloAreaDTO.getEndLocation()).append("]");
                }
                if (content.length() > 0) {
                    MaterialsStorageSpaces materialsStorageSpaces = materialsStorageSpacesService.getById(updateMaterialsSiloAreaDTO.getStorageSpacesId());
                    InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                    insertChangeLogDTO.setModuleName("物料管理");
                    insertChangeLogDTO.setFunctionName("区域配置");
                    insertChangeLogDTO.setContent("修改：[储位：" + materialsStorageSpaces.getStorageSpacesName() + "]" + content);
                    insertChangeLogDTO.setCreateUserId(saveMaterialsSiloAreaDTO.getCreateUserId());
                    insertChangeLogDTO.setCreateUserName(saveMaterialsSiloAreaDTO.getCreateUserName());
                    changeLogService.insertChangeLogData(insertChangeLogDTO);
                }
                materialsSiloArea.setSiloId(saveMaterialsSiloAreaDTO.getSiloId());
                materialsSiloArea.setStorageSpacesId(updateMaterialsSiloAreaDTO.getStorageSpacesId());
                materialsSiloArea.setStartLocation(updateMaterialsSiloAreaDTO.getStartLocation());
                materialsSiloArea.setEndLocation(updateMaterialsSiloAreaDTO.getEndLocation());
                materialsSiloArea.setColorValue(updateMaterialsSiloAreaDTO.getColorValue());
                materialsSiloArea.setModifyUserId(saveMaterialsSiloAreaDTO.getCreateUserId());
                materialsSiloArea.setModifyUserName(saveMaterialsSiloAreaDTO.getCreateUserName());
                materialsSiloArea.setModifyTime(new Date());
                updateById(materialsSiloArea);
            }
        }
        return AjaxResult.success("料仓区域保存成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteMaterialsSiloAreaData(DeleteMaterialsSiloAreaDTO deleteMaterialsSiloAreaDTO) {
        MaterialsSiloArea materialsSiloArea = getById(deleteMaterialsSiloAreaDTO.getId());
        if (null != materialsSiloArea) {
            String content = "删除：" +
                    "[煤种：" + materialsSiloArea.getMaterialsName() + "]" +
                    "[起始位置：" + materialsSiloArea.getStartLocation() + "]" +
                    "[结束位置：" + materialsSiloArea.getEndLocation() + "]";
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("物料管理");
            insertChangeLogDTO.setFunctionName("区域配置");
            insertChangeLogDTO.setContent(content);
            insertChangeLogDTO.setCreateUserId(deleteMaterialsSiloAreaDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteMaterialsSiloAreaDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
            removeById(materialsSiloArea);
            return AjaxResult.success("料仓区域维护删除成功");
        }
        else {
            return AjaxResult.error("料仓区域维护不存在或已被删除");
        }
    }
}

