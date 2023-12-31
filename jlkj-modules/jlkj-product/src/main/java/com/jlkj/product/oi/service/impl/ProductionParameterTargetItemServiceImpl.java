package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.constants.CommonConstant;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionparametertargetitem.*;
import com.jlkj.product.oi.mapper.ProductionParameterTargetItemMapper;
import com.jlkj.product.oi.mapper.ProductionPlanOutputDateMapper;
import com.jlkj.product.oi.mapper.ProductionPlanOutputMonthMapper;
import com.jlkj.product.oi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_parameter_target_item(生产参数--指标项维护)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/10 17:15
*/
@Service
public class ProductionParameterTargetItemServiceImpl extends ServiceImpl<ProductionParameterTargetItemMapper, ProductionParameterTargetItem>
    implements ProductionParameterTargetItemService {

    @Autowired
    ProductionPlanTargetYearService productionPlanTargetYearService;

    @Autowired
    ProductionPlanTargetMonthService productionPlanTargetMonthService;

    @Autowired
    ProductionPlanTargetDateService productionPlanTargetDateService;

    @Autowired
    ProductionPlanOutputYearService productionPlanOutputYearService;

    @Resource
    ProductionPlanOutputMonthMapper monthMapper;

    @Resource
    ProductionPlanOutputDateMapper dateMapper;

    @Resource
    ChangeLogService changeLogService;

    /**
     * 指标项查询
     * @param pageProductionParameterTargetItemDTO 查询条件dto
     * @return 分页列表
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionParameterTargetItemDTO pageProductionParameterTargetItemDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionParameterTargetItemDTO.getCurrent(), pageProductionParameterTargetItemDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionParameterTargetItemDTO);
    }

    /**
     * 新增指标项
     * @param addProductionParameterTargetItemDTO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addProductionTargetItem(AddProductionParameterTargetItemDTO addProductionParameterTargetItemDTO) {
        List<ProductionParameterTargetItem> list = list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                .eq(ProductionParameterTargetItem::getTargetItemTypeId, addProductionParameterTargetItemDTO.getTargetItemTypeId())
                .eq(ProductionParameterTargetItem::getTargetItemName, addProductionParameterTargetItemDTO.getTargetItemName())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            throw new ServiceException("指标项已存在");
        }
        ProductionParameterTargetItem productionParameterTargetItem = new ProductionParameterTargetItem();
        productionParameterTargetItem.setId(IdUtil.randomUUID());
        productionParameterTargetItem.setTargetItemTypeId(addProductionParameterTargetItemDTO.getTargetItemTypeId());
        productionParameterTargetItem.setTargetItemTypeName(addProductionParameterTargetItemDTO.getTargetItemTypeId() == 1 ? "焦化" : addProductionParameterTargetItemDTO.getTargetItemTypeId() == 2 ? "发电" : "未知");
        productionParameterTargetItem.setTargetItemName(addProductionParameterTargetItemDTO.getTargetItemName());
        productionParameterTargetItem.setCalcUnitId(addProductionParameterTargetItemDTO.getCalcUnitId());
        productionParameterTargetItem.setCalcUnitName(addProductionParameterTargetItemDTO.getCalcUnitId() == 1 ? "百分比" : addProductionParameterTargetItemDTO.getCalcUnitId() == 2 ? "量值" : "未知");
        productionParameterTargetItem.setMeasuringUnit(addProductionParameterTargetItemDTO.getMeasuringUnit());
        productionParameterTargetItem.setMaterialId(addProductionParameterTargetItemDTO.getMaterialId());
        productionParameterTargetItem.setMaterialName(addProductionParameterTargetItemDTO.getMaterialName());
        productionParameterTargetItem.setCreateUserId(addProductionParameterTargetItemDTO.getCreateUserId());
        productionParameterTargetItem.setCreateUserName(addProductionParameterTargetItemDTO.getCreateUserName());
        productionParameterTargetItem.setCreateTime(new Date());
        productionParameterTargetItem.setModifyUserId(addProductionParameterTargetItemDTO.getCreateUserId());
        productionParameterTargetItem.setModifyUserName(addProductionParameterTargetItemDTO.getCreateUserName());
        productionParameterTargetItem.setModifyTime(new Date());
        save(productionParameterTargetItem);

        String content = "新增：" +
                "[指标项名称：" + addProductionParameterTargetItemDTO.getTargetItemName() + "]" +
                "[指标类型：" + (addProductionParameterTargetItemDTO.getTargetItemTypeId() == 1 ? "焦化" : addProductionParameterTargetItemDTO.getTargetItemTypeId() == 2 ? "发电" : "未知") + "]" +
                "[计算单位名称：" + (addProductionParameterTargetItemDTO.getCalcUnitId() == 1 ? "百分比" : addProductionParameterTargetItemDTO.getCalcUnitId() == 2 ? "量值" : "未知") + "]" +
                "[计量单位：" + addProductionParameterTargetItemDTO.getMeasuringUnit() + "]" +
                "[物料代码：" + addProductionParameterTargetItemDTO.getMaterialName() + "]";
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("参数维护->指标项维护");
        insertChangeLogDTO.setContent(content);
        insertChangeLogDTO.setCreateUserId(addProductionParameterTargetItemDTO.getCreateUserId());
        insertChangeLogDTO.setCreateUserName(addProductionParameterTargetItemDTO.getCreateUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);
    }

    /**
     * 修改指标项
     * @param updateProductionParameterTargetItemDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editProductionTargetItem(UpdateProductionParameterTargetItemDTO updateProductionParameterTargetItemDTO) {
        ProductionParameterTargetItem productionParameterTargetItem = getById(updateProductionParameterTargetItemDTO.getId());
        if (null != productionParameterTargetItem) {
            List<ProductionParameterTargetItem> list = list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                    .eq(ProductionParameterTargetItem::getTargetItemTypeId, updateProductionParameterTargetItemDTO.getTargetItemTypeId())
                    .eq(ProductionParameterTargetItem::getTargetItemName, updateProductionParameterTargetItemDTO.getTargetItemName())
                    .ne(ProductionParameterTargetItem::getId, updateProductionParameterTargetItemDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() == 0) {
                if (!productionParameterTargetItem.getCalcUnitId().equals(updateProductionParameterTargetItemDTO.getCalcUnitId()) && hasUsed(updateProductionParameterTargetItemDTO.getId())) {
                    throw new ServiceException("该指标项已经使用，不允许修改计算单位");
                }

                StringBuilder content = new StringBuilder();
                if (!updateProductionParameterTargetItemDTO.getTargetItemName().equals(productionParameterTargetItem.getTargetItemName())) {
                    content.append("[指标项名称：").append(productionParameterTargetItem.getTargetItemName()).append("->").append(updateProductionParameterTargetItemDTO.getTargetItemName()).append("]");
                }
                if (!updateProductionParameterTargetItemDTO.getTargetItemTypeId().equals(productionParameterTargetItem.getTargetItemTypeId())) {
                    content.append("[指标类型：").append(productionParameterTargetItem.getTargetItemTypeId() == 1 ? "焦化" : productionParameterTargetItem.getTargetItemTypeId() == 2 ? "发电" : "未知").append("->").append(updateProductionParameterTargetItemDTO.getTargetItemTypeId() == 1 ? "焦化" : updateProductionParameterTargetItemDTO.getTargetItemTypeId() == 2 ? "发电" : "未知").append("]");
                }
                if (!updateProductionParameterTargetItemDTO.getCalcUnitId().equals(productionParameterTargetItem.getCalcUnitId())) {
                    content.append("[计算单位名称：").append(productionParameterTargetItem.getCalcUnitId() == 1 ? "百分比" : productionParameterTargetItem.getCalcUnitId() == 2 ? "量值" : "未知").append("->").append(updateProductionParameterTargetItemDTO.getCalcUnitId() == 1 ? "百分比" : updateProductionParameterTargetItemDTO.getCalcUnitId() == 2 ? "量值" : "未知").append("]");
                }
                if (!updateProductionParameterTargetItemDTO.getMeasuringUnit().equals(productionParameterTargetItem.getMeasuringUnit())) {
                    content.append("[计量单位：").append(productionParameterTargetItem.getMeasuringUnit()).append("->").append(updateProductionParameterTargetItemDTO.getMeasuringUnit()).append("]");
                }
                if (!updateProductionParameterTargetItemDTO.getMaterialName().equals(productionParameterTargetItem.getMaterialName())) {
                    content.append("[物料代码：").append(productionParameterTargetItem.getMaterialName()).append("->").append(updateProductionParameterTargetItemDTO.getMaterialName()).append("]");
                }
                if (content.length() > 0) {
                    InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                    insertChangeLogDTO.setModuleName("生产管理");
                    insertChangeLogDTO.setFunctionName("参数维护->指标项维护");
                    insertChangeLogDTO.setContent("修改：" + content);
                    insertChangeLogDTO.setCreateUserId(updateProductionParameterTargetItemDTO.getModifyUserId());
                    insertChangeLogDTO.setCreateUserName(updateProductionParameterTargetItemDTO.getModifyUserName());
                    changeLogService.insertChangeLogData(insertChangeLogDTO);
                }

                productionParameterTargetItem.setTargetItemTypeId(updateProductionParameterTargetItemDTO.getTargetItemTypeId());
                productionParameterTargetItem.setTargetItemTypeName(updateProductionParameterTargetItemDTO.getTargetItemTypeId() == 1 ? "焦化" : updateProductionParameterTargetItemDTO.getTargetItemTypeId() == 2 ? "发电" : "未知");
                productionParameterTargetItem.setTargetItemName(updateProductionParameterTargetItemDTO.getTargetItemName());
                productionParameterTargetItem.setCalcUnitId(updateProductionParameterTargetItemDTO.getCalcUnitId());
                productionParameterTargetItem.setCalcUnitName(updateProductionParameterTargetItemDTO.getCalcUnitId() == 1 ? "百分比" : updateProductionParameterTargetItemDTO.getCalcUnitId() == 2 ? "量值" : "未知");
                productionParameterTargetItem.setMeasuringUnit(updateProductionParameterTargetItemDTO.getMeasuringUnit());
                productionParameterTargetItem.setMaterialId(updateProductionParameterTargetItemDTO.getMaterialId());
                productionParameterTargetItem.setMaterialName(updateProductionParameterTargetItemDTO.getMaterialName());
                productionParameterTargetItem.setModifyUserId(updateProductionParameterTargetItemDTO.getModifyUserId());
                productionParameterTargetItem.setModifyUserName(updateProductionParameterTargetItemDTO.getModifyUserName());
                productionParameterTargetItem.setModifyTime(new Date());
                updateById(productionParameterTargetItem);
            }
            else {
                throw new ServiceException("指标项已存在");
            }
        }
        else {
            throw new ServiceException("指标项不存在");
        }
    }

    /**
     * 删除指标项
     * @param deleteProductionParameterTargetItemDTO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delProductionTargetItem(DeleteProductionParameterTargetItemDTO deleteProductionParameterTargetItemDTO) {
        ProductionParameterTargetItem productionParameterTargetItem = getById(deleteProductionParameterTargetItemDTO.getId());
        if (null != productionParameterTargetItem) {
            if (hasUsed(deleteProductionParameterTargetItemDTO.getId())) {
                throw new ServiceException("该指标项已经使用，不允许删除");
            }

            String content = "删除：" +
                    "[指标项名称：" + productionParameterTargetItem.getTargetItemName() + "]" +
                    "[指标类型：" + (productionParameterTargetItem.getTargetItemTypeId() == 1 ? "焦化" : productionParameterTargetItem.getTargetItemTypeId() == 2 ? "发电" : "未知") + "]" +
                    "[计算单位名称：" + (productionParameterTargetItem.getCalcUnitId() == 1 ? "百分比" : productionParameterTargetItem.getCalcUnitId() == 2 ? "量值" : "未知") + "]" +
                    "[计量单位：" + productionParameterTargetItem.getMeasuringUnit() + "]" +
                    "[物料代码：" + productionParameterTargetItem.getMaterialName() + "]";
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("参数维护->指标项维护");
            insertChangeLogDTO.setContent(content);
            insertChangeLogDTO.setCreateUserId(deleteProductionParameterTargetItemDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteProductionParameterTargetItemDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);

            removeById(productionParameterTargetItem);
        }
        else {
            throw new ServiceException("指标项不存在或已被删除");
        }
    }

    /**
     * 获取指标项列表
     * @param dto
     * @return
     */
    @Override
    public List<ProductionParameterTargetItem> getList(GetProductionParameterTargetItemDTO dto) {
        LambdaQueryWrapper<ProductionParameterTargetItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dto.getTargetItemTypeId() != 0, ProductionParameterTargetItem::getTargetItemTypeId, dto.getTargetItemTypeId());
        return getBaseMapper().selectList(wrapper);
    }


    public boolean hasUsed(String targetItemId) {
        if(productionPlanTargetYearService.list(new QueryWrapper<ProductionPlanTargetYear>().lambda()
                .eq(ProductionPlanTargetYear::getTargetItemId, targetItemId)
                .last(CommonConstant.LIMIT_ONE_ROW)
        ).size() > 0) {
            return true;
        }
        if(productionPlanTargetMonthService.list(new QueryWrapper<ProductionPlanTargetMonth>().lambda()
                .eq(ProductionPlanTargetMonth::getTargetItemId, targetItemId)
                .last(CommonConstant.LIMIT_ONE_ROW)
        ).size() > 0) {
            return true;
        }
        if(productionPlanTargetDateService.list(new QueryWrapper<ProductionPlanTargetDate>().lambda()
                .eq(ProductionPlanTargetDate::getTargetItemId, targetItemId)
                .last(CommonConstant.LIMIT_ONE_ROW)
        ).size() > 0) {
            return true;
        }

        if(productionPlanOutputYearService.list(new QueryWrapper<ProductionPlanOutputYear>().lambda()
                .eq(ProductionPlanOutputYear::getTargetItemId, targetItemId)
                .last(CommonConstant.LIMIT_ONE_ROW)
        ).size() > 0) {
            return true;
        }
        if(monthMapper.selectList(new QueryWrapper<ProductionPlanOutputMonth>().lambda()
                .eq(ProductionPlanOutputMonth::getTargetItemId, targetItemId)
                .last(CommonConstant.LIMIT_ONE_ROW)
        ).size() > 0) {
            return true;
        }
        if(dateMapper.selectList(new QueryWrapper<ProductionPlanOutputDate>().lambda()
                .eq(ProductionPlanOutputDate::getTargetItemId, targetItemId)
                .last(CommonConstant.LIMIT_ONE_ROW)
        ).size() > 0) {
            return true;
        }
        return false;
    }
}




