package com.jlkj.material.mr.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.material.mr.dto.materialssiloareahistory.AddMaterialsSiloAreaHistoryDTO;
import com.jlkj.material.mr.dto.materialssiloareahistory.PageMaterialsSiloAreaHistoryDTO;
import com.jlkj.material.mr.domain.MaterialsSiloAreaHistory;
import com.jlkj.material.mr.mapper.MaterialsSiloAreaHistoryMapper;
import com.jlkj.material.mr.service.MaterialsSiloAreaHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 服务实现-料仓区域历史表
 * @author sudeyou
 */
@Service
public class MaterialsSiloAreaHistoryServiceImpl extends ServiceImpl<MaterialsSiloAreaHistoryMapper, MaterialsSiloAreaHistory>
    implements MaterialsSiloAreaHistoryService {

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageMaterialsSiloAreaHistoryDTO pageMaterialsSiloAreaHistoryDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsSiloAreaHistoryDTO.getCurrent(), pageMaterialsSiloAreaHistoryDTO.getSize());
        return getBaseMapper().getPageData(page, pageMaterialsSiloAreaHistoryDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addData(AddMaterialsSiloAreaHistoryDTO addMaterialsSiloAreaHistoryDTO) {
        MaterialsSiloAreaHistory materialsSiloAreaHistory = new MaterialsSiloAreaHistory();
        materialsSiloAreaHistory.setId(IdUtil.randomUUID());
        materialsSiloAreaHistory.setSiloId(addMaterialsSiloAreaHistoryDTO.getSiloId());
        materialsSiloAreaHistory.setStorageSpacesId(addMaterialsSiloAreaHistoryDTO.getStorageSpacesId());
        materialsSiloAreaHistory.setMaterialsId(addMaterialsSiloAreaHistoryDTO.getMaterialsId());
        materialsSiloAreaHistory.setMaterialsName(addMaterialsSiloAreaHistoryDTO.getMaterialsName());
        materialsSiloAreaHistory.setStartTime(null != addMaterialsSiloAreaHistoryDTO.getStartTime() ? DateUtil.parseDateTime(addMaterialsSiloAreaHistoryDTO.getStartTime()) : null);
        materialsSiloAreaHistory.setEndTime(DateUtil.parseDateTime(addMaterialsSiloAreaHistoryDTO.getEndTime()));
        save(materialsSiloAreaHistory);
        return true;
    }
}

