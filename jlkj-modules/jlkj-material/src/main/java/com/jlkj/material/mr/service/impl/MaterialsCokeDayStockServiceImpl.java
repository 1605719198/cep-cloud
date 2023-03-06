package com.jlkj.material.mr.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialscokedaystock.ListHomeCokeStockDTO;
import com.jlkj.material.mr.domain.MaterialsCokeDayStock;
import com.jlkj.material.mr.domain.MaterialsCokeStock;
import com.jlkj.material.mr.mapper.MaterialsCokeDayStockMapper;
import com.jlkj.material.mr.service.MaterialsCokeDayStockService;
import com.jlkj.material.mr.service.MaterialsCokeStockService;
import com.jlkj.material.mr.vo.materialscokedaystock.ListHomeCokeStockVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 服务实现-物料焦场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:29:24
 */
@Service
public class MaterialsCokeDayStockServiceImpl extends ServiceImpl<MaterialsCokeDayStockMapper, MaterialsCokeDayStock> 
    implements MaterialsCokeDayStockService {

    @Resource
    @Lazy
    private MaterialsCokeDayStockService materialsCokeDayStockService;

    @Resource
    @Lazy
    private MaterialsCokeStockService materialsCokeStockService;

    @Override
    @Transactional(readOnly = true)
    public List<ListHomeCokeStockVO> getHomeCokeStockListData(ListHomeCokeStockDTO listHomeCokeStockDTO) {
        return getBaseMapper().getHomeCokeStockListData(listHomeCokeStockDTO);
    }

    @Override
    public Object initMaterialsCokeDayStockData() {
        List<MaterialsCokeStock> materialsCoalStockList = materialsCokeStockService.list();
        for (MaterialsCokeStock materialsCokeStock: materialsCoalStockList) {
            List<MaterialsCokeDayStock> materialsCoalDayStockList = materialsCokeDayStockService.list(new QueryWrapper<MaterialsCokeDayStock>().lambda()
                    .eq(MaterialsCokeDayStock::getStatDate, DateUtil.today())
                    .eq(MaterialsCokeDayStock::getMaterialsId, materialsCokeStock.getMaterialsId())
            );
            if(materialsCoalDayStockList.size() == 0) {
                MaterialsCokeDayStock materialsCokeDayStock = new MaterialsCokeDayStock();
                materialsCokeDayStock.setId(IdUtil.randomUUID());
                materialsCokeDayStock.setMaterialsId(materialsCokeStock.getMaterialsId());
                materialsCokeDayStock.setMaterialsName(materialsCokeStock.getMaterialsName());
                materialsCokeDayStock.setCategoryId(materialsCokeStock.getCategoryId());
                materialsCokeDayStock.setCategoryName(materialsCokeStock.getCategoryName());
                materialsCokeDayStock.setInventory(materialsCokeStock.getInventory());
                materialsCokeDayStock.setStatDate(new Date());
                materialsCokeDayStock.setCreateTime(new Date());
                materialsCokeDayStockService.save(materialsCokeDayStock);
            }
        }
        return AjaxResult.success("原煤库存-每日初始化成功");
    }
}

