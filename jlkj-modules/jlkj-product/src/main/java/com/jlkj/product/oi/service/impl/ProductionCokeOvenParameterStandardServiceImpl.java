package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.ProductionCokeOvenParameterStandard;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.AddProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.PageProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.UpdateProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productionparametertargetitem.DeleteProductionParameterTargetItemDTO;
import com.jlkj.product.oi.mapper.ProductionCokeOvenParameterStandardMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.ProductionCokeOvenParameterStandardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_coke_oven_parameter_standard(生产参数-焦炉参数标准)】的数据库操作Service实现
* @createDate 2022-04-26 09:12:50
*/
@Service
public class ProductionCokeOvenParameterStandardServiceImpl extends ServiceImpl<ProductionCokeOvenParameterStandardMapper, ProductionCokeOvenParameterStandard>
    implements ProductionCokeOvenParameterStandardService {

    @Resource
    ChangeLogService changeLogService;

    /**
     * K值标准查询
     * @param pageProductionCokeOvenParameterStandardDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getListPage(PageProductionCokeOvenParameterStandardDTO pageProductionCokeOvenParameterStandardDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionCokeOvenParameterStandardDTO.getCurrent(), pageProductionCokeOvenParameterStandardDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionCokeOvenParameterStandardDTO);
    }

    /**
     * 新增K值标准
     * @param addProductionCokeOvenParameterStandardDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCustom(AddProductionCokeOvenParameterStandardDTO addProductionCokeOvenParameterStandardDTO) {
        ProductionCokeOvenParameterStandard productionCokeOvenParameterStandard = new ProductionCokeOvenParameterStandard();
        productionCokeOvenParameterStandard.setId(IdUtil.randomUUID());
        productionCokeOvenParameterStandard.setStandardCoalLoading(addProductionCokeOvenParameterStandardDTO.getStandardCoalLoading());
        productionCokeOvenParameterStandard.setCokingCycle(addProductionCokeOvenParameterStandardDTO.getCokingCycle());
        productionCokeOvenParameterStandard.setFireTemperature(addProductionCokeOvenParameterStandardDTO.getFireTemperature());
        productionCokeOvenParameterStandard.setZhiHangTemperature(addProductionCokeOvenParameterStandardDTO.getZhiHangTemperature());
        productionCokeOvenParameterStandard.setCoalLoadingFactor(addProductionCokeOvenParameterStandardDTO.getCoalLoadingFactor());
        productionCokeOvenParameterStandard.setPlanningFactor(addProductionCokeOvenParameterStandardDTO.getPlanningFactor());
        productionCokeOvenParameterStandard.setAverageCoefficient(addProductionCokeOvenParameterStandardDTO.getAverageCoefficient());
        productionCokeOvenParameterStandard.setStabilityFactor(addProductionCokeOvenParameterStandardDTO.getStabilityFactor());
        productionCokeOvenParameterStandard.setCreateUserId(addProductionCokeOvenParameterStandardDTO.getCreateUserId());
        productionCokeOvenParameterStandard.setCreateUserName(addProductionCokeOvenParameterStandardDTO.getCreateUserName());
        productionCokeOvenParameterStandard.setCreateTime(new Date());
        productionCokeOvenParameterStandard.setModifyUserId(addProductionCokeOvenParameterStandardDTO.getCreateUserId());
        productionCokeOvenParameterStandard.setModifyUserName(addProductionCokeOvenParameterStandardDTO.getCreateUserName());
        productionCokeOvenParameterStandard.setModifyTime(new Date());
        save(productionCokeOvenParameterStandard);

        String content = "新增：" +
                "[规定装煤量：" + addProductionCokeOvenParameterStandardDTO.getStandardCoalLoading().stripTrailingZeros().toPlainString() + "]" +
                "[规定结焦时间：" + addProductionCokeOvenParameterStandardDTO.getCokingCycle().stripTrailingZeros().toPlainString() + "]" +
                "[焦侧标准火道温度：" + addProductionCokeOvenParameterStandardDTO.getFireTemperature().stripTrailingZeros().toPlainString() + "]" +
                "[直行标准温度：" + addProductionCokeOvenParameterStandardDTO.getZhiHangTemperature().stripTrailingZeros().toPlainString() + "]" +
                "[K装正负：" + addProductionCokeOvenParameterStandardDTO.getCoalLoadingFactor().stripTrailingZeros().toPlainString() + "]" +
                "[K计正负：" + addProductionCokeOvenParameterStandardDTO.getPlanningFactor().stripTrailingZeros().toPlainString() + "]" +
                "[K均正负：" + addProductionCokeOvenParameterStandardDTO.getAverageCoefficient().stripTrailingZeros().toPlainString() + "]" +
                "[K安正负：" + addProductionCokeOvenParameterStandardDTO.getStabilityFactor().stripTrailingZeros().toPlainString() + "]";
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("参数维护->指标项维护");
        insertChangeLogDTO.setContent(content);
        insertChangeLogDTO.setCreateUserId(addProductionCokeOvenParameterStandardDTO.getCreateUserId());
        insertChangeLogDTO.setCreateUserName(addProductionCokeOvenParameterStandardDTO.getCreateUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);
    }

    /**
     * 修改K值标准
     * @param updateProductionCokeOvenParameterStandardDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustom(UpdateProductionCokeOvenParameterStandardDTO updateProductionCokeOvenParameterStandardDTO) {
        ProductionCokeOvenParameterStandard productionCokeOvenParameterStandard = getById(updateProductionCokeOvenParameterStandardDTO.getId());
        if (null != productionCokeOvenParameterStandard) {
            StringBuilder content = new StringBuilder();
            if (updateProductionCokeOvenParameterStandardDTO.getStandardCoalLoading().compareTo(productionCokeOvenParameterStandard.getStandardCoalLoading()) != 0) {
                content.append("[规定装煤量：").append(productionCokeOvenParameterStandard.getStandardCoalLoading().stripTrailingZeros().toPlainString()).append("->").append(updateProductionCokeOvenParameterStandardDTO.getStandardCoalLoading().stripTrailingZeros().toPlainString()).append("]");
            }
            if (updateProductionCokeOvenParameterStandardDTO.getCokingCycle().compareTo(productionCokeOvenParameterStandard.getCokingCycle()) != 0) {
                content.append("[规定结焦时间：").append(productionCokeOvenParameterStandard.getCokingCycle().stripTrailingZeros().toPlainString()).append("->").append(updateProductionCokeOvenParameterStandardDTO.getCokingCycle().stripTrailingZeros().toPlainString()).append("]");
            }
            if (updateProductionCokeOvenParameterStandardDTO.getFireTemperature().compareTo(productionCokeOvenParameterStandard.getFireTemperature()) != 0) {
                content.append("[焦侧标准火道温度：").append(productionCokeOvenParameterStandard.getFireTemperature().stripTrailingZeros().toPlainString()).append("->").append(updateProductionCokeOvenParameterStandardDTO.getFireTemperature().stripTrailingZeros().toPlainString()).append("]");
            }
            if (updateProductionCokeOvenParameterStandardDTO.getZhiHangTemperature().compareTo(productionCokeOvenParameterStandard.getZhiHangTemperature()) != 0) {
                content.append("[直行标准温度：").append(productionCokeOvenParameterStandard.getZhiHangTemperature().stripTrailingZeros().toPlainString()).append("->").append(updateProductionCokeOvenParameterStandardDTO.getZhiHangTemperature().stripTrailingZeros().toPlainString()).append("]");
            }
            if (updateProductionCokeOvenParameterStandardDTO.getCoalLoadingFactor().compareTo(productionCokeOvenParameterStandard.getCoalLoadingFactor()) != 0) {
                content.append("[K装正负：").append(productionCokeOvenParameterStandard.getCoalLoadingFactor().stripTrailingZeros().toPlainString()).append("->").append(updateProductionCokeOvenParameterStandardDTO.getCoalLoadingFactor().stripTrailingZeros().toPlainString()).append("]");
            }
            if (updateProductionCokeOvenParameterStandardDTO.getPlanningFactor().compareTo(productionCokeOvenParameterStandard.getPlanningFactor()) != 0) {
                content.append("[K计正负：").append(productionCokeOvenParameterStandard.getPlanningFactor().stripTrailingZeros().toPlainString()).append("->").append(updateProductionCokeOvenParameterStandardDTO.getPlanningFactor().stripTrailingZeros().toPlainString()).append("]");
            }
            if (updateProductionCokeOvenParameterStandardDTO.getAverageCoefficient().compareTo(productionCokeOvenParameterStandard.getAverageCoefficient()) != 0) {
                content.append("[K均正负：").append(productionCokeOvenParameterStandard.getAverageCoefficient().stripTrailingZeros().toPlainString()).append("->").append(updateProductionCokeOvenParameterStandardDTO.getAverageCoefficient().stripTrailingZeros().toPlainString()).append("]");
            }
            if (updateProductionCokeOvenParameterStandardDTO.getStabilityFactor().compareTo(productionCokeOvenParameterStandard.getStabilityFactor()) != 0) {
                content.append("[K安正负：").append(productionCokeOvenParameterStandard.getStabilityFactor().stripTrailingZeros().toPlainString()).append("->").append(updateProductionCokeOvenParameterStandardDTO.getStabilityFactor().stripTrailingZeros().toPlainString()).append("]");
            }
            if (content.length() > 0) {
                InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                insertChangeLogDTO.setModuleName("生产管理");
                insertChangeLogDTO.setFunctionName("参数维护->指标项维护");
                insertChangeLogDTO.setContent("修改：" + content);
                insertChangeLogDTO.setCreateUserId(updateProductionCokeOvenParameterStandardDTO.getModifyUserId());
                insertChangeLogDTO.setCreateUserName(updateProductionCokeOvenParameterStandardDTO.getModifyUserName());
                changeLogService.insertChangeLogData(insertChangeLogDTO);
            }

            productionCokeOvenParameterStandard.setStandardCoalLoading(updateProductionCokeOvenParameterStandardDTO.getStandardCoalLoading());
            productionCokeOvenParameterStandard.setCokingCycle(updateProductionCokeOvenParameterStandardDTO.getCokingCycle());
            productionCokeOvenParameterStandard.setFireTemperature(updateProductionCokeOvenParameterStandardDTO.getFireTemperature());
            productionCokeOvenParameterStandard.setZhiHangTemperature(updateProductionCokeOvenParameterStandardDTO.getZhiHangTemperature());
            productionCokeOvenParameterStandard.setCoalLoadingFactor(updateProductionCokeOvenParameterStandardDTO.getCoalLoadingFactor());
            productionCokeOvenParameterStandard.setPlanningFactor(updateProductionCokeOvenParameterStandardDTO.getPlanningFactor());
            productionCokeOvenParameterStandard.setAverageCoefficient(updateProductionCokeOvenParameterStandardDTO.getAverageCoefficient());
            productionCokeOvenParameterStandard.setStabilityFactor(updateProductionCokeOvenParameterStandardDTO.getStabilityFactor());
            productionCokeOvenParameterStandard.setModifyUserId(updateProductionCokeOvenParameterStandardDTO.getModifyUserId());
            productionCokeOvenParameterStandard.setModifyUserName(updateProductionCokeOvenParameterStandardDTO.getModifyUserName());
            productionCokeOvenParameterStandard.setModifyTime(new Date());
            updateById(productionCokeOvenParameterStandard);
        }
        else {
            throw new ServiceException("K值标准不存在");
        }
    }

    /**
     * 删除k值标准
     * @param deleteProductionParameterTargetItemDTO
     * @return
     */
    @Override
    public void delete(DeleteProductionParameterTargetItemDTO deleteProductionParameterTargetItemDTO) {
        ProductionCokeOvenParameterStandard productionCokeOvenParameterStandard = getById(deleteProductionParameterTargetItemDTO.getId());
        if (null != productionCokeOvenParameterStandard) {

            String content = "删除：" +
                    "[规定装煤量：" + productionCokeOvenParameterStandard.getStandardCoalLoading().stripTrailingZeros().toPlainString() + "]" +
                    "[规定结焦时间：" + productionCokeOvenParameterStandard.getCokingCycle().stripTrailingZeros().toPlainString() + "]" +
                    "[焦侧标准火道温度：" + productionCokeOvenParameterStandard.getFireTemperature().stripTrailingZeros().toPlainString() + "]" +
                    "[直行标准温度：" + productionCokeOvenParameterStandard.getZhiHangTemperature().stripTrailingZeros().toPlainString() + "]" +
                    "[K装正负：" + productionCokeOvenParameterStandard.getCoalLoadingFactor().stripTrailingZeros().toPlainString() + "]" +
                    "[K计正负：" + productionCokeOvenParameterStandard.getPlanningFactor().stripTrailingZeros().toPlainString() + "]" +
                    "[K均正负：" + productionCokeOvenParameterStandard.getAverageCoefficient().stripTrailingZeros().toPlainString() + "]" +
                    "[K安正负：" + productionCokeOvenParameterStandard.getStabilityFactor().stripTrailingZeros().toPlainString() + "]";
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("参数维护->指标项维护");
            insertChangeLogDTO.setContent(content);
            insertChangeLogDTO.setCreateUserId(deleteProductionParameterTargetItemDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteProductionParameterTargetItemDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);

            removeById(productionCokeOvenParameterStandard);
        }
        else {
            throw new ServiceException("K值标准不存在或已被删除");
        }
    }
}




