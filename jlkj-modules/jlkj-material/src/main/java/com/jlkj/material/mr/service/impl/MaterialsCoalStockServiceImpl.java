package com.jlkj.material.mr.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.material.mr.dto.materialscoalstock.GetCoalStockDTO;
import com.jlkj.material.mr.dto.materialscoalstock.PageMaterialsCoalStockDTO;
import com.jlkj.material.mr.domain.MaterialsCoalStock;
import com.jlkj.material.mr.mapper.MaterialsCoalStockMapper;
import com.jlkj.material.mr.service.MaterialsCoalStockService;
import com.jlkj.material.mr.vo.materialscoalstock.ListHomeMaterialAnalysisListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @Description 物料煤场库存
 * @create 2022年6月10日 10:12:26
 */
@Service
@Slf4j
public class MaterialsCoalStockServiceImpl extends ServiceImpl<MaterialsCoalStockMapper, MaterialsCoalStock>
        implements MaterialsCoalStockService {
    @Override
    public IPage<Map<String, String>> getListPage(PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCoalStockDTO.getCurrent(), pageMaterialsCoalStockDTO.getSize());
        return getBaseMapper().getListPage(page, pageMaterialsCoalStockDTO);
    }
    @Override
    public List<Map<String, String>> getCoalStock(GetCoalStockDTO dto) {
        return getBaseMapper().getCoalStock(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListHomeMaterialAnalysisListVO> getHomeMaterialAnalysisList() {
        return getBaseMapper().getHomeMaterialAnalysisList();
    }



    @Override
    public IPage<Map<String, String>> getConveyingCoalList(PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCoalStockDTO.getCurrent(), pageMaterialsCoalStockDTO.getSize());
        return getBaseMapper().getConveyingCoalList(page, pageMaterialsCoalStockDTO);
    }
    @Override
    public IPage<Map<String, String>> getHCoalUnloadingList(PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCoalStockDTO.getCurrent(), pageMaterialsCoalStockDTO.getSize());
        return getBaseMapper().getHCoalUnloadingList(page, pageMaterialsCoalStockDTO);
    }

}

