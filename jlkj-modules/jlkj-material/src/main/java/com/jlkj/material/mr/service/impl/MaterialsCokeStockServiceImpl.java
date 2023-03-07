package com.jlkj.material.mr.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.material.mr.dto.materialscokestock.GetCokeStockDTO;
import com.jlkj.material.mr.dto.materialscokestock.PageMaterialsCokeStockDTO;
import com.jlkj.material.mr.domain.MaterialsCokeStock;
import com.jlkj.material.mr.mapper.MaterialsCokeStockMapper;
import com.jlkj.material.mr.service.MaterialsCokeStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @Description 物料焦场库存
 * @create 2022年6月10日 10:12:26
 */
@Service
@Slf4j
public class MaterialsCokeStockServiceImpl extends ServiceImpl<MaterialsCokeStockMapper, MaterialsCokeStock>
        implements MaterialsCokeStockService {
    @Override
    public IPage<Map<String, String>> getListPage(PageMaterialsCokeStockDTO pageMaterialsCokeStockDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCokeStockDTO.getCurrent(), pageMaterialsCokeStockDTO.getSize());
        return getBaseMapper().getListPage(page, pageMaterialsCokeStockDTO);
    }
    @Override
    public List<Map<String, String>> getCokeStock(GetCokeStockDTO dto) {
        return getBaseMapper().getCokeStock(dto);
    }



    @Override
    public IPage<Map<String, String>> getCokePutStorageList(PageMaterialsCokeStockDTO pageMaterialsCokeStockDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCokeStockDTO.getCurrent(), pageMaterialsCokeStockDTO.getSize());
        return getBaseMapper().getCokePutStorageList(page, pageMaterialsCokeStockDTO);
    }
    @Override
    public IPage<Map<String, String>> getCokeOutStorageList(PageMaterialsCokeStockDTO pageMaterialsCokeStockDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCokeStockDTO.getCurrent(), pageMaterialsCokeStockDTO.getSize());
        return getBaseMapper().getCokeOutStorageList(page, pageMaterialsCokeStockDTO);
    }


}

