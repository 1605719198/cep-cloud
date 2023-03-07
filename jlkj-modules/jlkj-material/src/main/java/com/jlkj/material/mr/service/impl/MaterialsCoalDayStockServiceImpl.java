package com.jlkj.material.mr.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialscoaldaystock.ListHomeCoalStockDTO;
import com.jlkj.material.mr.domain.MaterialsCoalDayStock;
import com.jlkj.material.mr.domain.MaterialsCoalStock;
import com.jlkj.material.mr.mapper.MaterialsCoalDayStockMapper;
import com.jlkj.material.mr.service.MaterialsCoalDayStockService;
import com.jlkj.material.mr.service.MaterialsCoalStockService;
import com.jlkj.material.mr.vo.materialscoaldaystock.ListHomeCoalStockVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 服务实现-物料煤场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:55:24
 */
@Service
public class MaterialsCoalDayStockServiceImpl extends ServiceImpl<MaterialsCoalDayStockMapper, MaterialsCoalDayStock> 
    implements MaterialsCoalDayStockService {

    @Resource
    @Lazy
    private MaterialsCoalDayStockService materialsCoalDayStockService;

    @Resource
    @Lazy
    private MaterialsCoalStockService materialsCoalStockService;


    @Override
    @Transactional(readOnly = true)
    public List<ListHomeCoalStockVO> getHomeCoalStockListData(ListHomeCoalStockDTO listHomeCoalStockDTO) {
        return getBaseMapper().getHomeCoalStockListData(listHomeCoalStockDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object initMaterialsCoalDayStockData() {
        List<MaterialsCoalStock> materialsCoalStockList = materialsCoalStockService.list();
        for (MaterialsCoalStock materialsCoalStock: materialsCoalStockList) {
            List<MaterialsCoalDayStock> materialsCoalDayStockList = materialsCoalDayStockService.list(new QueryWrapper<MaterialsCoalDayStock>().lambda()
                    .eq(MaterialsCoalDayStock::getStatDate, DateUtil.today())
                    .eq(MaterialsCoalDayStock::getMaterialsId, materialsCoalStock.getMaterialsId())
            );
            if(materialsCoalDayStockList.size() == 0) {
                MaterialsCoalDayStock materialsCoalDayStock = new MaterialsCoalDayStock();
                materialsCoalDayStock.setId(IdUtil.randomUUID());
                materialsCoalDayStock.setMaterialsId(materialsCoalStock.getMaterialsId());
                materialsCoalDayStock.setMaterialsName(materialsCoalStock.getMaterialsName());
                materialsCoalDayStock.setCategoryId(materialsCoalStock.getCategoryId());
                materialsCoalDayStock.setCategoryName(materialsCoalStock.getCategoryName());
                materialsCoalDayStock.setStorageSpacesId(materialsCoalStock.getStorageSpacesId());
                materialsCoalDayStock.setStorageSpacesName(materialsCoalStock.getStorageSpacesName());
                materialsCoalDayStock.setTodayUnloadedWeight(new BigDecimal("0"));
                materialsCoalDayStock.setTodayLoadingWeight(new BigDecimal("0"));
                materialsCoalDayStock.setInventory(materialsCoalStock.getInventory());
                materialsCoalDayStock.setStatDate(new Date());
                materialsCoalDayStock.setCreateTime(new Date());
                materialsCoalDayStockService.save(materialsCoalDayStock);
            }
        }
        return AjaxResult.success("原煤库存-每日初始化成功");
    }
}

