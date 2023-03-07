package com.jlkj.material.mr.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.CommonConstant;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.DeleteMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.InsertMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.ListHomeCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.PageMaterialsCokeSaleWriteOffRealDto;
import com.jlkj.material.mr.dto.sysdictdata.ListSysDictDataDTO;
import com.jlkj.material.mr.domain.MaterialsCode;
import com.jlkj.material.mr.domain.MaterialsCokeSaleWriteOffReal;
import com.jlkj.material.mr.domain.ProductionCokeFactoryPerformance;
import com.jlkj.material.mr.mapper.MaterialsCokeSaleWriteOffRealMapper;
import com.jlkj.material.mr.service.MaterialsCodeService;
import com.jlkj.material.mr.service.MaterialsCokeSaleWriteOffRealService;
import com.jlkj.material.mr.service.ProductionCokeFactoryPerformanceService;
import com.jlkj.material.mr.service.SysDictDataService;
import com.jlkj.material.mr.vo.sysdictdata.ListDictDataVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-焦炭外售销账实际
 * @author sudeyou
 * @since 2022-08-19 13:58:42
 */
@Service
public class MaterialsCokeSaleWriteOffRealServiceImpl extends ServiceImpl<MaterialsCokeSaleWriteOffRealMapper, MaterialsCokeSaleWriteOffReal> 
    implements MaterialsCokeSaleWriteOffRealService {

    @Resource
    private ProductionCokeFactoryPerformanceService productionCokeFactoryPerformanceService;

    @Resource
    private MaterialsCodeService materialsCodeService;

    @Resource
    private SysDictDataService sysDictDataService;

//    @Resource
//    private MaterialsCokeStockService materialsCokeStockService;
//
//    @Resource
//    private MaterialsCokeDayStockService materialsCokeDayStockService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertMaterialsCokeSaleWriteOffRealData(InsertMaterialsCokeSaleWriteOffRealDTO insertMaterialsCokeSaleWriteOffRealDTO) {
        List<MaterialsCokeSaleWriteOffReal> list = list(new QueryWrapper<MaterialsCokeSaleWriteOffReal>().lambda()
                .eq(MaterialsCokeSaleWriteOffReal::getPlanListNo, insertMaterialsCokeSaleWriteOffRealDTO.getPlanListNo())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("焦炭外售销账实际已存在");
        }
        MaterialsCokeSaleWriteOffReal materialsCokeSaleWriteOffReal = new MaterialsCokeSaleWriteOffReal();
        materialsCokeSaleWriteOffReal.setId(IdUtil.randomUUID());
        materialsCokeSaleWriteOffReal.setPlanListNo(insertMaterialsCokeSaleWriteOffRealDTO.getPlanListNo());
        materialsCokeSaleWriteOffReal.setCarrierType(insertMaterialsCokeSaleWriteOffRealDTO.getCarrierType());
        materialsCokeSaleWriteOffReal.setCarrierNo(insertMaterialsCokeSaleWriteOffRealDTO.getCarrierNo());
        materialsCokeSaleWriteOffReal.setMtrlNo(insertMaterialsCokeSaleWriteOffRealDTO.getMtrlNo());
        materialsCokeSaleWriteOffReal.setFromCode(insertMaterialsCokeSaleWriteOffRealDTO.getFromCode());
        materialsCokeSaleWriteOffReal.setToCode(insertMaterialsCokeSaleWriteOffRealDTO.getToCode());
        materialsCokeSaleWriteOffReal.setToDesc(insertMaterialsCokeSaleWriteOffRealDTO.getToDesc());
        materialsCokeSaleWriteOffReal.setPchrNo(insertMaterialsCokeSaleWriteOffRealDTO.getPchrNo());
        materialsCokeSaleWriteOffReal.setIsLoad(insertMaterialsCokeSaleWriteOffRealDTO.getIsLoad());
        materialsCokeSaleWriteOffReal.setLoadStation(insertMaterialsCokeSaleWriteOffRealDTO.getLoadStation());
        materialsCokeSaleWriteOffReal.setIssueDept(insertMaterialsCokeSaleWriteOffRealDTO.getIssueDept());
        materialsCokeSaleWriteOffReal.setIssueDeptName(insertMaterialsCokeSaleWriteOffRealDTO.getIssueDeptName());
        materialsCokeSaleWriteOffReal.setIssueEmpNo(insertMaterialsCokeSaleWriteOffRealDTO.getIssueEmpNo());
        materialsCokeSaleWriteOffReal.setIssueEmpName(insertMaterialsCokeSaleWriteOffRealDTO.getIssueEmpName());
        materialsCokeSaleWriteOffReal.setIssueDate(insertMaterialsCokeSaleWriteOffRealDTO.getIssueDate());
        materialsCokeSaleWriteOffReal.setWgtListNo(insertMaterialsCokeSaleWriteOffRealDTO.getWgtListNo());
        materialsCokeSaleWriteOffReal.setNetWgt(insertMaterialsCokeSaleWriteOffRealDTO.getNetWgt());
        materialsCokeSaleWriteOffReal.setCloseTime(insertMaterialsCokeSaleWriteOffRealDTO.getCloseTime());
        save(materialsCokeSaleWriteOffReal);
        MaterialsCode materialsCode = materialsCodeService.getOne(new QueryWrapper<MaterialsCode>().lambda()
                .eq(MaterialsCode::getMaterialsCode, materialsCokeSaleWriteOffReal.getMtrlNo())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        // 取消班组成员，增加提交人issueEmpName、issueEmpNo
//        Map<String, Object> shift = getShift(parseDateString(materialsCokeSaleWriteOffReal.getCloseTime()) + " " + DateUtil.formatTime(new Date()));
        ProductionCokeFactoryPerformance productionCokeFactoryPerformance = new ProductionCokeFactoryPerformance();
        productionCokeFactoryPerformance.setId(IdUtil.randomUUID());
        productionCokeFactoryPerformance.setPlanListNo(materialsCokeSaleWriteOffReal.getPlanListNo());
//        productionCokeFactoryPerformance.setShiftName(null == shift ? "" : shift.get("shift").toString());
//        productionCokeFactoryPerformance.setClassName(null == shift ? "" : shift.get("class_type").toString());
//        productionCokeFactoryPerformance.setPerson(null == shift ? "" : shift.get("full_name").toString());
        productionCokeFactoryPerformance.setClientId(materialsCokeSaleWriteOffReal.getToCode());
        productionCokeFactoryPerformance.setClientName(materialsCokeSaleWriteOffReal.getToDesc());
        productionCokeFactoryPerformance.setMaterialCode(materialsCokeSaleWriteOffReal.getMtrlNo());
        productionCokeFactoryPerformance.setMaterialsName(materialsCode.getMaterialsName());
        productionCokeFactoryPerformance.setVehicleNumber(materialsCokeSaleWriteOffReal.getCarrierNo());
        productionCokeFactoryPerformance.setLoadingTime(DateUtil.parseDate(parseDateString(materialsCokeSaleWriteOffReal.getCloseTime())));
        productionCokeFactoryPerformance.setNetWgt(new BigDecimal(materialsCokeSaleWriteOffReal.getNetWgt()));

        productionCokeFactoryPerformance.setIssueEmpNo(materialsCokeSaleWriteOffReal.getIssueEmpNo());
        productionCokeFactoryPerformance.setIssueEmpName(materialsCokeSaleWriteOffReal.getIssueEmpName());

        productionCokeFactoryPerformanceService.save(productionCokeFactoryPerformance);
//        materialsCokeStockService.update(new UpdateWrapper<MaterialsCokeStock>().lambda()
//                .setSql("inventory = inventory - " + productionCokeFactoryPerformance.getNetWgt().stripTrailingZeros().toPlainString())
//                .eq(MaterialsCokeStock::getMaterialsId, materialsCokeSaleWriteOffReal.getMtrlNo())
//        );
//        List<MaterialsCokeStock> materialsCokeStockList = materialsCokeStockService.list(new QueryWrapper<MaterialsCokeStock>().lambda()
//                .eq(MaterialsCokeStock::getMaterialsId, materialsCokeSaleWriteOffReal.getMtrlNo())
//        );
//        MaterialsCokeStock materialsCokeStock = materialsCokeStockList.get(0);
//        List<MaterialsCokeDayStock> materialsCokeDayStockList = materialsCokeDayStockService.list(new QueryWrapper<MaterialsCokeDayStock>().lambda()
//                .eq(MaterialsCokeDayStock::getStatDate, DateUtil.today())
//                .eq(MaterialsCokeDayStock::getMaterialsId, materialsCokeSaleWriteOffReal.getMtrlNo())
//        );
//        MaterialsCokeDayStock materialsCokeDayStock;
//        if(materialsCokeDayStockList.size() > 0) {
//            materialsCokeDayStock = materialsCokeDayStockList.get(0);
//            materialsCokeDayStock.setInventory(materialsCokeDayStock.getInventory().subtract(new BigDecimal(materialsCokeSaleWriteOffReal.getNetWgt())));
//            materialsCokeDayStockService.updateById(materialsCokeDayStock);
//        }
//        else {
//            materialsCokeDayStock = new MaterialsCokeDayStock();
//            materialsCokeDayStock.setId(IdUtil.randomUUID());
//            materialsCokeDayStock.setMaterialsId(materialsCokeStock.getMaterialsId());
//            materialsCokeDayStock.setMaterialsName(materialsCokeStock.getMaterialsName());
//            materialsCokeDayStock.setCategoryId(materialsCokeStock.getCategoryId());
//            materialsCokeDayStock.setCategoryName(materialsCokeStock.getCategoryName());
//            materialsCokeDayStock.setInventory(materialsCokeStock.getInventory());
//            materialsCokeDayStock.setStatDate(new Date());
//            materialsCokeDayStock.setCreateTime(new Date());
//            materialsCokeDayStockService.save(materialsCokeDayStock);
//        }
        return AjaxResult.success("焦炭外售销账实际增加成功");
    }

    private Map<String, Object> getShift(String time) {
        String postId = "none";
        ListSysDictDataDTO listSysDictDataDTO = new ListSysDictDataDTO();
        listSysDictDataDTO.setDictType("il01");
        listSysDictDataDTO.setDictLabel("postId");
        List<ListDictDataVO> listDic = sysDictDataService.getSysDictDataListData(listSysDictDataDTO);
        if(listDic.size() > 0) {
            postId = listDic.get(0).getDictValue();
        }
        Map<String, String> map = new HashMap<>(1);
        map.put("postId", postId);
        map.put("time", time);
        return getBaseMapper().getShift(map);
    }

    private static String parseDateString(String dateStr) {
        return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteMaterialsCokeSaleWriteOffRealData(DeleteMaterialsCokeSaleWriteOffRealDTO deleteMaterialsCokeSaleWriteOffRealDTO) {
        List<MaterialsCokeSaleWriteOffReal> list = list(new QueryWrapper<MaterialsCokeSaleWriteOffReal>().lambda()
                .eq(MaterialsCokeSaleWriteOffReal::getPlanListNo, deleteMaterialsCokeSaleWriteOffRealDTO.getPlanListNo())
        );
        if(list.size() > 0) {
            MaterialsCokeSaleWriteOffReal materialsCokeSaleWriteOffReal = list.get(0);
            boolean succeed = remove(new QueryWrapper<MaterialsCokeSaleWriteOffReal>().lambda()
                    .eq(MaterialsCokeSaleWriteOffReal::getPlanListNo, deleteMaterialsCokeSaleWriteOffRealDTO.getPlanListNo()));
            if (succeed) {
                productionCokeFactoryPerformanceService.remove(new QueryWrapper<ProductionCokeFactoryPerformance>().lambda()
                        .eq(ProductionCokeFactoryPerformance::getPlanListNo, deleteMaterialsCokeSaleWriteOffRealDTO.getPlanListNo()));
//                materialsCokeStockService.update(new UpdateWrapper<MaterialsCokeStock>().lambda()
//                        .setSql("inventory = inventory + " + new BigDecimal(materialsCokeSaleWriteOffReal.getNetWgt()))
//                        .eq(MaterialsCokeStock::getMaterialsId, materialsCokeSaleWriteOffReal.getMtrlNo())
//                );
//                List<MaterialsCokeStock> materialsCokeStockList = materialsCokeStockService.list(new QueryWrapper<MaterialsCokeStock>().lambda()
//                        .eq(MaterialsCokeStock::getMaterialsId, materialsCokeSaleWriteOffReal.getMtrlNo())
//                );
//                MaterialsCokeStock materialsCokeStock = materialsCokeStockList.get(0);
//                List<MaterialsCokeDayStock> materialsCokeDayStockList = materialsCokeDayStockService.list(new QueryWrapper<MaterialsCokeDayStock>().lambda()
//                        .eq(MaterialsCokeDayStock::getStatDate, DateUtil.today())
//                        .eq(MaterialsCokeDayStock::getMaterialsId, materialsCokeSaleWriteOffReal.getMtrlNo())
//                );
//                MaterialsCokeDayStock materialsCokeDayStock;
//                if(materialsCokeDayStockList.size() > 0) {
//                    materialsCokeDayStock = materialsCokeDayStockList.get(0);
//                    materialsCokeDayStock.setInventory(materialsCokeDayStock.getInventory().add(new BigDecimal(materialsCokeSaleWriteOffReal.getNetWgt())));
//                    materialsCokeDayStockService.updateById(materialsCokeDayStock);
//                }
//                else {
//                    materialsCokeDayStock = new MaterialsCokeDayStock();
//                    materialsCokeDayStock.setId(IdUtil.randomUUID());
//                    materialsCokeDayStock.setMaterialsId(materialsCokeStock.getMaterialsId());
//                    materialsCokeDayStock.setMaterialsName(materialsCokeStock.getMaterialsName());
//                    materialsCokeDayStock.setCategoryId(materialsCokeStock.getCategoryId());
//                    materialsCokeDayStock.setCategoryName(materialsCokeStock.getCategoryName());
//                    materialsCokeDayStock.setInventory(materialsCokeStock.getInventory());
//                    materialsCokeDayStock.setStatDate(new Date());
//                    materialsCokeDayStock.setCreateTime(new Date());
//                    materialsCokeDayStockService.save(materialsCokeDayStock);
//                }
                return AjaxResult.success("焦炭外售销账实际删除成功");
            } else {
                return AjaxResult.error("数据不存在或已被删除");
            }
        }
        else {
            return AjaxResult.error("数据不存在或已被删除");
        }
    }

    @Override
    public IPage<Map<String, String>> getListPage(PageMaterialsCokeSaleWriteOffRealDto dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getListPage(page, dto);
    }
    @Override
    public List<Map<String, String>> getHomeCokeOut(ListHomeCokeSaleWriteOffRealDTO dto) {
        return getBaseMapper().getHomeCokeOut(dto);
    }
}

