package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.constants.CommonConstant;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.DeleteProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.InsertProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.PageProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.mapper.ProductionOutputPerformanceManualBakeMapper;
import com.jlkj.product.oi.service.*;
import com.jlkj.product.oi.vo.productionoutputperformancemanualbake.CurrentCokeLevelCodeVO;
import com.jlkj.product.oi.vo.productionoutputperformancemanualbake.PageProductionOutputPerformanceManualBakeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
*@description: 服务实现-生产管理-炼焦实绩-出炉实绩-手动出炉
*@Author: 265823
*@date: 2023/7/10 17:02
*/
@Service
public class ProductionOutputPerformanceManualBakeServiceImpl extends ServiceImpl<ProductionOutputPerformanceManualBakeMapper, ProductionOutputPerformanceManualBake>
    implements ProductionOutputPerformanceManualBakeService {

    @Resource
    private ProductionOutputPerformanceManualCoalService productionOutputPerformanceManualCoalService;

    @Resource
    private ProductionInformationConfigurationService productionInformationConfigurationService;

    @Resource
    private ProductionCokeRecordsService productionCokeRecordsService;

    @Resource
    private ProductionCoalTowerStockService productionCoalTowerStockService;

    /**
     * 服务接口-生产管理-炼焦实绩-出炉实绩-手动出炉
     * @param pageProductionOutputPerformanceManualBakeDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionOutputPerformanceManualBakeVO> getProductionOutputPerformanceManualBakePageData(PageProductionOutputPerformanceManualBakeDTO pageProductionOutputPerformanceManualBakeDTO) {
        Page<Map<String, Object>> page = new Page<>(pageProductionOutputPerformanceManualBakeDTO.getCurrent(), pageProductionOutputPerformanceManualBakeDTO.getSize());
        return getBaseMapper().getProductionOutputPerformanceManualBakePageData(page, pageProductionOutputPerformanceManualBakeDTO);
    }

    /**
     * 生产管理-炼焦实绩-出炉实绩-手动出炉-新增
     * @param insertProductionOutputPerformanceManualBakeDTO 新增dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertProductionOutputPerformanceManualBakeData(InsertProductionOutputPerformanceManualBakeDTO insertProductionOutputPerformanceManualBakeDTO) {
        List<ProductionOutputPerformanceManualCoal> listCoal = productionOutputPerformanceManualCoalService.list(new QueryWrapper<ProductionOutputPerformanceManualCoal>()
                .lambda()
                .eq(ProductionOutputPerformanceManualCoal::getCoalTowerNumber, insertProductionOutputPerformanceManualBakeDTO.getCoalTowerNumber())
                .orderByDesc(ProductionOutputPerformanceManualCoal::getLoadingCoalAfter)
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if(listCoal.size() == 0) {
            throw new ServiceException("储煤塔最后一次装煤记录不存在");
        }
        ProductionOutputPerformanceManualCoal lastCoal = listCoal.get(0);
        /*
        建龙不用这种方法获取配煤实际，注掉，存档
        List<ProductionConfigureCoalSpeciesPerformance> listProductionConfigureCoalSpeciesPerformance = productionConfigureCoalSpeciesPerformanceService.list(new QueryWrapper<ProductionConfigureCoalSpeciesPerformance>()
                .lambda()
                .eq(ProductionConfigureCoalSpeciesPerformance::getCoalTowerNumber, insertProductionOutputPerformanceManualBakeDTO.getCoalTowerNumber())
                .ge(ProductionConfigureCoalSpeciesPerformance::getAfterStock, lastCoal.getLoadingCoalAfter().add(insertProductionOutputPerformanceManualBakeDTO.getLoadingCoal()))
                .orderByAsc(ProductionConfigureCoalSpeciesPerformance::getAfterStock)
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if(listProductionConfigureCoalSpeciesPerformance.size() == 0) {
            throw new ServiceException("配煤记录不存在");
        }
        ProductionConfigureCoalSpeciesPerformance productionConfigureCoalSpeciesPerformance = listProductionConfigureCoalSpeciesPerformance.get(0);
        ProductionPlanConfigCoke productionPlanConfigCoke = productionPlanConfigCokeService.getById(productionConfigureCoalSpeciesPerformance.getPlanId());
        if(null == productionPlanConfigCoke) {
            throw new ServiceException("配煤计划主记录不存在");
        }
         */
        ProductionConfigureCoalSpeciesPerformance productionConfigureCoalSpeciesPerformance = new ProductionConfigureCoalSpeciesPerformance();
        CurrentCokeLevelCodeVO currentCokeLevelCodeVO = getBaseMapper().getCurrentCokeLevelCodeData(insertProductionOutputPerformanceManualBakeDTO);
        if(null == currentCokeLevelCodeVO) {
            throw new ServiceException("配煤记录不存在");
        }
        productionConfigureCoalSpeciesPerformance.setCoalLevelMaterialsCode(currentCokeLevelCodeVO.getCokeLevelMaterialsCode());
        productionConfigureCoalSpeciesPerformance.setCoalLevelMaterialsName(currentCokeLevelCodeVO.getCokeLevelMaterialsName());
        productionConfigureCoalSpeciesPerformance.setPlanId(currentCokeLevelCodeVO.getPlanNumber());
        productionConfigureCoalSpeciesPerformance.setId(currentCokeLevelCodeVO.getId());
        ProductionPlanConfigCoke productionPlanConfigCoke = new ProductionPlanConfigCoke();
        productionPlanConfigCoke.setPlanNumber(currentCokeLevelCodeVO.getPlanNumber());
        List<ProductionInformationConfiguration> infoList = productionInformationConfigurationService.list(new QueryWrapper<ProductionInformationConfiguration>()
                .lambda()
                .eq(ProductionInformationConfiguration::getMaterialsName, productionConfigureCoalSpeciesPerformance.getCoalLevelMaterialsName())
                .eq(ProductionInformationConfiguration::getQuenchingMethod, insertProductionOutputPerformanceManualBakeDTO.getCokeType())
        );
        if(infoList.size() == 0) {
            throw new ServiceException("单孔产量配置记录不存在");
        }
        ProductionInformationConfiguration productionInformationConfiguration = infoList.get(0);

        ProductionCoalTowerStock productionCoalTowerStock = productionCoalTowerStockService.getOne(new QueryWrapper<ProductionCoalTowerStock>().lambda()
                .eq(ProductionCoalTowerStock::getCoalTowerNumber, insertProductionOutputPerformanceManualBakeDTO.getCoalTowerNumber())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if(null == productionCoalTowerStock) {
            throw new ServiceException("储煤塔库存记录不存在");
        }
        if(productionCoalTowerStock.getCoalSurplus().compareTo(lastCoal.getLoadingCoal()) < 0) {
            throw new ServiceException("储煤塔库存余量不足");
        }
        ProductionOutputPerformanceManualCoal nextCoal = saveProductionCoalTowerBlendedCoalRecord(productionPlanConfigCoke, insertProductionOutputPerformanceManualBakeDTO, lastCoal, productionConfigureCoalSpeciesPerformance);
        saveProductionCokeRecords(insertProductionOutputPerformanceManualBakeDTO, productionInformationConfiguration);
        saveProductionOutputPerformanceManualBake(insertProductionOutputPerformanceManualBakeDTO, lastCoal, productionInformationConfiguration, productionConfigureCoalSpeciesPerformance, nextCoal, currentCokeLevelCodeVO);
        updateCoalTowerStock(insertProductionOutputPerformanceManualBakeDTO, productionCoalTowerStock);
    }

    /**
     * 生产管理-炼焦实绩-出炉实绩-手动出炉-删除
     * @param deleteProductionOutputPerformanceManualBakeDTO 删除dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProductionOutputPerformanceManualBakeData(DeleteProductionOutputPerformanceManualBakeDTO deleteProductionOutputPerformanceManualBakeDTO) {
        ProductionOutputPerformanceManualBake productionOutputPerformanceManualBake = getById(deleteProductionOutputPerformanceManualBakeDTO.getId());
        if (null != productionOutputPerformanceManualBake) {
            List<ProductionOutputPerformanceManualBake> listBake = list(new QueryWrapper<ProductionOutputPerformanceManualBake>()
                    .lambda()
                    .orderByDesc(ProductionOutputPerformanceManualBake::getCreateTime)
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if(listBake.size() == 0) {
                throw new ServiceException("出炉记录不存在");
            }
            ProductionOutputPerformanceManualBake lastBake = listBake.get(0);
            if(!deleteProductionOutputPerformanceManualBakeDTO.getId().equals(lastBake.getId())) {
                throw new ServiceException("只能删除最后一次出炉记录");
            }
            ProductionOutputPerformanceManualCoal nextCoal = productionOutputPerformanceManualCoalService.getById(productionOutputPerformanceManualBake.getNextCoalRecordId());
            if(null == nextCoal) {
                throw new ServiceException("出炉后装煤记录不存在");
            }
            else {
                productionCokeRecordsService.update(new UpdateWrapper<ProductionCokeRecords>().lambda()
                        .setSql("material_weight = material_weight - " + productionOutputPerformanceManualBake.getSingleHoleProduction().stripTrailingZeros().toPlainString())
                        .eq(ProductionCokeRecords::getShiftName, productionOutputPerformanceManualBake.getShiftName())
                        .eq(ProductionCokeRecords::getClassName, productionOutputPerformanceManualBake.getClassName())
                        .le(ProductionCokeRecords::getClassStartTime, DateUtil.formatDate(productionOutputPerformanceManualBake.getProductionDate()) + " " + DateUtil.formatTime(productionOutputPerformanceManualBake.getActualProductionTime()))
                        .ge(ProductionCokeRecords::getClassEndTime, DateUtil.formatDate(productionOutputPerformanceManualBake.getProductionDate()) + " " + DateUtil.formatTime(productionOutputPerformanceManualBake.getActualProductionTime()))
                        .eq(ProductionCokeRecords::getMaterialName, productionOutputPerformanceManualBake.getCokeType() == 1 ? "干熄焦" : "湿熄焦")
                );
                productionOutputPerformanceManualCoalService.removeById(nextCoal);
                removeById(productionOutputPerformanceManualBake);
            }
        }
        else {
            throw new ServiceException("手动出炉记录不存在或已被删除");
        }
    }

    private ProductionOutputPerformanceManualCoal saveProductionCoalTowerBlendedCoalRecord(ProductionPlanConfigCoke productionPlanConfigCoke, InsertProductionOutputPerformanceManualBakeDTO insertProductionOutputPerformanceManualBakeDTO, ProductionOutputPerformanceManualCoal lastCoal, ProductionConfigureCoalSpeciesPerformance productionConfigureCoalSpeciesPerformance) {
        ProductionOutputPerformanceManualCoal productionOutputPerformanceManualCoal = new ProductionOutputPerformanceManualCoal();
        productionOutputPerformanceManualCoal.setId(IdUtil.randomUUID());
        productionOutputPerformanceManualCoal.setShiftName(insertProductionOutputPerformanceManualBakeDTO.getShiftName());
        productionOutputPerformanceManualCoal.setClassName(insertProductionOutputPerformanceManualBakeDTO.getClassName());
        productionOutputPerformanceManualCoal.setCokeOvenNumber(insertProductionOutputPerformanceManualBakeDTO.getCokeOvenNumber());
        productionOutputPerformanceManualCoal.setCarbonizationChamberNumber(insertProductionOutputPerformanceManualBakeDTO.getCarbonizationChamberNumber());
        productionOutputPerformanceManualCoal.setCoalTowerNumber(insertProductionOutputPerformanceManualBakeDTO.getCoalTowerNumber());
        productionOutputPerformanceManualCoal.setProductionDate(DateUtil.parseDate(insertProductionOutputPerformanceManualBakeDTO.getProductionDate()));
        productionOutputPerformanceManualCoal.setActualLoadCoalTime(DateUtil.parseTime(insertProductionOutputPerformanceManualBakeDTO.getActualLoadCoalTime()));
        productionOutputPerformanceManualCoal.setLoadingCoal(insertProductionOutputPerformanceManualBakeDTO.getLoadingCoal());
        productionOutputPerformanceManualCoal.setLoadingCoalAfter(lastCoal.getLoadingCoalAfter().add(insertProductionOutputPerformanceManualBakeDTO.getLoadingCoal()));
        productionOutputPerformanceManualCoal.setDataSources((short)0);
        productionOutputPerformanceManualCoal.setCoalPlanNumber(productionPlanConfigCoke.getPlanNumber());
        productionOutputPerformanceManualCoal.setMaterialsCode(productionConfigureCoalSpeciesPerformance.getCoalLevelMaterialsCode());
        productionOutputPerformanceManualCoal.setMaterialsName(trim(productionConfigureCoalSpeciesPerformance.getCoalLevelMaterialsName()));
        productionOutputPerformanceManualCoal.setCreateUserId(insertProductionOutputPerformanceManualBakeDTO.getCreateUserId());
        productionOutputPerformanceManualCoal.setCreateUserName(insertProductionOutputPerformanceManualBakeDTO.getCreateUserName());
        productionOutputPerformanceManualCoal.setCreateTime(new Date());
        productionOutputPerformanceManualCoalService.save(productionOutputPerformanceManualCoal);
        return productionOutputPerformanceManualCoal;
    }
    private static String trim(String str) {
        String dest = "";
        if (str != null) {
            String test = "\\s*|\\t|\\r|\\n";
            Pattern p = Pattern.compile(test);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    private void saveProductionCokeRecords(InsertProductionOutputPerformanceManualBakeDTO insertProductionOutputPerformanceManualBakeDTO, ProductionInformationConfiguration productionInformationConfiguration) {
        List<ProductionCokeRecords> listCokeRecords = productionCokeRecordsService.list(new QueryWrapper<ProductionCokeRecords>()
                .lambda()
                .eq(ProductionCokeRecords::getShiftName, insertProductionOutputPerformanceManualBakeDTO.getShiftName())
                .eq(ProductionCokeRecords::getClassName, insertProductionOutputPerformanceManualBakeDTO.getClassName())
                .eq(ProductionCokeRecords::getClassStartTime, insertProductionOutputPerformanceManualBakeDTO.getClassStartTime())
                .eq(ProductionCokeRecords::getClassEndTime, insertProductionOutputPerformanceManualBakeDTO.getClassEndTime())
                .eq(ProductionCokeRecords::getMaterialName, insertProductionOutputPerformanceManualBakeDTO.getCokeType() == 1 ? "干熄焦" : "湿熄焦")
        );
        ProductionCokeRecords productionCokeRecords;
        if(listCokeRecords.size() == 0) {
            productionCokeRecords = new ProductionCokeRecords();
            productionCokeRecords.setId(IdUtil.randomUUID());
            productionCokeRecords.setClassName(insertProductionOutputPerformanceManualBakeDTO.getClassName());
            productionCokeRecords.setShiftName(insertProductionOutputPerformanceManualBakeDTO.getShiftName());
            productionCokeRecords.setClassStartTime(DateUtil.parseDateTime(insertProductionOutputPerformanceManualBakeDTO.getClassStartTime()));
            productionCokeRecords.setClassEndTime(DateUtil.parseDateTime(insertProductionOutputPerformanceManualBakeDTO.getClassEndTime()));
            productionCokeRecords.setMaterialCode(insertProductionOutputPerformanceManualBakeDTO.getCokeType() == 1 ? "0000000001" : "0000000002");
            productionCokeRecords.setMaterialName(insertProductionOutputPerformanceManualBakeDTO.getCokeType() == 1 ? "干熄焦" : "湿熄焦");
            productionCokeRecords.setMaterialWeight(productionInformationConfiguration.getSingleHoleWeight());
            productionCokeRecordsService.save(productionCokeRecords);
        }
        else {
            productionCokeRecords = listCokeRecords.get(0);
            productionCokeRecords.setMaterialWeight(productionCokeRecords.getMaterialWeight().add(productionInformationConfiguration.getSingleHoleWeight()));
            productionCokeRecordsService.updateById(productionCokeRecords);
        }
    }

    private void saveProductionOutputPerformanceManualBake(InsertProductionOutputPerformanceManualBakeDTO insertProductionOutputPerformanceManualBakeDTO, ProductionOutputPerformanceManualCoal lastCoal, ProductionInformationConfiguration productionInformationConfiguration, ProductionConfigureCoalSpeciesPerformance productionConfigureCoalSpeciesPerformance, ProductionOutputPerformanceManualCoal nextCoal, CurrentCokeLevelCodeVO currentCokeLevelCodeVO) {
        ProductionOutputPerformanceManualBake productionOutputPerformanceManualBake = new ProductionOutputPerformanceManualBake();
        productionOutputPerformanceManualBake.setId(IdUtil.randomUUID());
        productionOutputPerformanceManualBake.setShiftName(insertProductionOutputPerformanceManualBakeDTO.getShiftName());
        productionOutputPerformanceManualBake.setClassName(insertProductionOutputPerformanceManualBakeDTO.getClassName());
        productionOutputPerformanceManualBake.setCokeOvenNumber(insertProductionOutputPerformanceManualBakeDTO.getCokeOvenNumber());
        productionOutputPerformanceManualBake.setCarbonizationChamberNumber(insertProductionOutputPerformanceManualBakeDTO.getCarbonizationChamberNumber());
        productionOutputPerformanceManualBake.setCoalRecordId(lastCoal.getId());
        productionOutputPerformanceManualBake.setProductionDate(DateUtil.parseDate(insertProductionOutputPerformanceManualBakeDTO.getProductionDate()));
        productionOutputPerformanceManualBake.setActualProductionTime(DateUtil.parseTime(insertProductionOutputPerformanceManualBakeDTO.getActualProductionTime()));
        productionOutputPerformanceManualBake.setCokeType(insertProductionOutputPerformanceManualBakeDTO.getCokeType());
        productionOutputPerformanceManualBake.setSingleHoleProduction(productionInformationConfiguration.getSingleHoleWeight());
        productionOutputPerformanceManualBake.setCoalPlanNumber(productionConfigureCoalSpeciesPerformance.getPlanId());
        productionOutputPerformanceManualBake.setMaterialsCode(productionConfigureCoalSpeciesPerformance.getCoalLevelMaterialsCode());
        productionOutputPerformanceManualBake.setMaterialsName(productionConfigureCoalSpeciesPerformance.getCoalLevelMaterialsName());
        productionOutputPerformanceManualBake.setCreateUserId(insertProductionOutputPerformanceManualBakeDTO.getCreateUserId());
        productionOutputPerformanceManualBake.setCreateUserName(insertProductionOutputPerformanceManualBakeDTO.getCreateUserName());
        productionOutputPerformanceManualBake.setCreateTime(new Date());
        productionOutputPerformanceManualBake.setCoalPerformanceId(productionConfigureCoalSpeciesPerformance.getId());
        productionOutputPerformanceManualBake.setNextCoalRecordId(nextCoal.getId());
        productionOutputPerformanceManualBake.setCoalDetailStartTime(currentCokeLevelCodeVO.getCoalDetailStartTime());
        save(productionOutputPerformanceManualBake);
    }

    private void updateCoalTowerStock(InsertProductionOutputPerformanceManualBakeDTO insertProductionOutputPerformanceManualBakeDTO, ProductionCoalTowerStock productionCoalTowerStock) {
        productionCoalTowerStock.setCoalSurplus(productionCoalTowerStock.getCoalSurplus().subtract(insertProductionOutputPerformanceManualBakeDTO.getLoadingCoal()));
        productionCoalTowerStockService.updateById(productionCoalTowerStock);
    }

}
