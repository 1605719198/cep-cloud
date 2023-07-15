package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.constants.CommonConstant;
import com.jlkj.product.oi.domain.ProductionPlanConfigCoke;
import com.jlkj.product.oi.domain.ProductionPlanConfigCokeDetail;
import com.jlkj.product.oi.dto.productionplanconfigcokedetail.*;
import com.jlkj.product.oi.mapper.ProductionPlanConfigCokeDetailMapper;
import com.jlkj.product.oi.service.ProductionPlanConfigCokeDetailService;
import com.jlkj.product.oi.service.ProductionPlanConfigCokeService;
import com.jlkj.product.oi.vo.productionplanconfigcokedetail.InfoProductionPlanConfigCokeDetailByWarehouseNumberVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*@description: 服务实现-配煤计划配煤记录
*@Author: 265823
*@date: 2023/7/11 8:10
*/
@Service
public class ProductionPlanConfigCokeDetailServiceImpl extends ServiceImpl<ProductionPlanConfigCokeDetailMapper, ProductionPlanConfigCokeDetail>
    implements ProductionPlanConfigCokeDetailService {

    @Lazy
    @Resource
    private ProductionPlanConfigCokeService productionPlanConfigCokeService;

    /**
     * 查询-分页-配煤计划配煤记录
     * @param pageProductionPlanConfigCokeDetailDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageProductionPlanConfigCokeDetailDTO pageProductionPlanConfigCokeDetailDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionPlanConfigCokeDetailDTO.getCurrent(), pageProductionPlanConfigCokeDetailDTO.getSize());
        return getBaseMapper().getPageData(page, pageProductionPlanConfigCokeDetailDTO);
    }

    /**
     * 新增-配煤计划配煤记录
     * @param addProductionPlanConfigCokeDetailDTO 新增dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addData(AddProductionPlanConfigCokeDetailDTO addProductionPlanConfigCokeDetailDTO) {
        List<ProductionPlanConfigCokeDetail> list = list(new QueryWrapper<ProductionPlanConfigCokeDetail>().lambda()
                .eq(ProductionPlanConfigCokeDetail::getPlanId, addProductionPlanConfigCokeDetailDTO.getPlanId())
                .eq(ProductionPlanConfigCokeDetail::getWarehouseNumber, addProductionPlanConfigCokeDetailDTO.getWarehouseNumber())
                .eq(ProductionPlanConfigCokeDetail::getCoalTypeId, addProductionPlanConfigCokeDetailDTO.getCoalTypeId())
                .eq(ProductionPlanConfigCokeDetail::getMaterialsSmallCode, addProductionPlanConfigCokeDetailDTO.getMaterialsSmallCode())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("配煤计划配煤记录已存在");
        }
        ProductionPlanConfigCoke productionPlanConfigCoke = productionPlanConfigCokeService.getById(addProductionPlanConfigCokeDetailDTO.getPlanId());
        if (null == productionPlanConfigCoke) {
            return AjaxResult.error("配煤计划主记录不存在");
        }
        ProductionPlanConfigCokeDetail productionPlanConfigCokeDetail = new ProductionPlanConfigCokeDetail();
        productionPlanConfigCokeDetail.setId(IdUtil.randomUUID());
        productionPlanConfigCokeDetail.setPlanId(addProductionPlanConfigCokeDetailDTO.getPlanId());
        productionPlanConfigCokeDetail.setWarehouseNumber(addProductionPlanConfigCokeDetailDTO.getWarehouseNumber());
        productionPlanConfigCokeDetail.setCoalTypeId(addProductionPlanConfigCokeDetailDTO.getCoalTypeId());
        productionPlanConfigCokeDetail.setCoalTypeName(addProductionPlanConfigCokeDetailDTO.getCoalTypeName());

        productionPlanConfigCokeDetail.setMaterialsSmallCode(addProductionPlanConfigCokeDetailDTO.getMaterialsSmallCode());
        productionPlanConfigCokeDetail.setMaterialsSmallName(addProductionPlanConfigCokeDetailDTO.getMaterialsSmallName());

        productionPlanConfigCokeDetail.setProportion(addProductionPlanConfigCokeDetailDTO.getProportion());
        productionPlanConfigCokeDetail.setCreateUserId(addProductionPlanConfigCokeDetailDTO.getCreateUserId());
        productionPlanConfigCokeDetail.setCreateUserName(addProductionPlanConfigCokeDetailDTO.getCreateUserName());
        productionPlanConfigCokeDetail.setCreateTime(new Date());
        productionPlanConfigCokeDetail.setModifyUserId(addProductionPlanConfigCokeDetailDTO.getCreateUserId());
        productionPlanConfigCokeDetail.setModifyUserName(addProductionPlanConfigCokeDetailDTO.getCreateUserName());
        productionPlanConfigCokeDetail.setModifyTime(new Date());
        save(productionPlanConfigCokeDetail);
        return AjaxResult.success("配煤计划保存成功！");
    }

    /**
     * 修改-配煤计划配煤记录
     * @param updateProductionPlanConfigCokeDetailDTO 修改dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateData(UpdateProductionPlanConfigCokeDetailDTO updateProductionPlanConfigCokeDetailDTO) {
        ProductionPlanConfigCokeDetail productionPlanConfigCokeDetail = getById(updateProductionPlanConfigCokeDetailDTO.getId());
        if (null != productionPlanConfigCokeDetail) {
            List<ProductionPlanConfigCokeDetail> list = list(new QueryWrapper<ProductionPlanConfigCokeDetail>().lambda()
                    .eq(ProductionPlanConfigCokeDetail::getPlanId, productionPlanConfigCokeDetail.getPlanId())
                    .eq(ProductionPlanConfigCokeDetail::getWarehouseNumber, updateProductionPlanConfigCokeDetailDTO.getWarehouseNumber())
                    .eq(ProductionPlanConfigCokeDetail::getCoalTypeId, updateProductionPlanConfigCokeDetailDTO.getCoalTypeId())
                    .eq(ProductionPlanConfigCokeDetail::getMaterialsSmallCode, updateProductionPlanConfigCokeDetailDTO.getMaterialsSmallCode())
                    .ne(ProductionPlanConfigCokeDetail::getId, updateProductionPlanConfigCokeDetailDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() == 0) {
                ProductionPlanConfigCoke productionPlanConfigCoke = productionPlanConfigCokeService.getById(productionPlanConfigCokeDetail.getPlanId());
                if (null == productionPlanConfigCoke) {
                    return AjaxResult.error("配煤计划主记录不存在");
                }
                if (productionPlanConfigCoke.getPlanState() != 1) {
                    return AjaxResult.error("只能修改未确认的记录");
                }
                productionPlanConfigCokeDetail.setWarehouseNumber(updateProductionPlanConfigCokeDetailDTO.getWarehouseNumber());
                productionPlanConfigCokeDetail.setCoalTypeId(updateProductionPlanConfigCokeDetailDTO.getCoalTypeId());
                productionPlanConfigCokeDetail.setCoalTypeName(updateProductionPlanConfigCokeDetailDTO.getCoalTypeName());

                productionPlanConfigCokeDetail.setMaterialsSmallCode(updateProductionPlanConfigCokeDetailDTO.getMaterialsSmallCode());
                productionPlanConfigCokeDetail.setMaterialsSmallName(updateProductionPlanConfigCokeDetailDTO.getMaterialsSmallName());

                productionPlanConfigCokeDetail.setProportion(updateProductionPlanConfigCokeDetailDTO.getProportion());
                productionPlanConfigCokeDetail.setModifyUserId(updateProductionPlanConfigCokeDetailDTO.getModifyUserId());
                productionPlanConfigCokeDetail.setModifyUserName(updateProductionPlanConfigCokeDetailDTO.getModifyUserName());
                productionPlanConfigCokeDetail.setModifyTime(new Date());
                updateById(productionPlanConfigCokeDetail);
            }
            else {
                return AjaxResult.error("配煤计划配煤记录已存在");
            }
        }
        else {
            return AjaxResult.error("配煤计划配煤记录不存在");
        }
        return AjaxResult.success("配煤计划配煤记录修改成功");
    }

    /**
     * 删除-配煤计划配煤记录
     * @param deleteProductionPlanConfigCokeDetailDTO 删除dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(DeleteProductionPlanConfigCokeDetailDTO deleteProductionPlanConfigCokeDetailDTO) {
        ProductionPlanConfigCokeDetail productionPlanConfigCokeDetail = getById(deleteProductionPlanConfigCokeDetailDTO.getId());
        if (null != productionPlanConfigCokeDetail) {
            ProductionPlanConfigCoke productionPlanConfigCoke = productionPlanConfigCokeService.getById(productionPlanConfigCokeDetail.getPlanId());
            if (null == productionPlanConfigCoke) {
                throw new ServiceException("配煤计划主记录不存在");
            }
            if (productionPlanConfigCoke.getPlanState() != 1) {
                throw new ServiceException("只能删除未确认的记录");
            }
            removeById(productionPlanConfigCokeDetail);
        }
        else {
            throw new ServiceException("配煤计划配煤记录不存在或已被删除");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteDataByPlanId(String planId) {
        remove(new QueryWrapper<ProductionPlanConfigCokeDetail>().lambda()
                .eq(ProductionPlanConfigCokeDetail::getPlanId, planId));
        return true;
    }

    /**
     * 获取配煤仓配煤计划信息
     * @param infoProductionPlanConfigCokeDetailByWarehouseNumberDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public InfoProductionPlanConfigCokeDetailByWarehouseNumberVO getProductionPlanConfigCokeDetailInfoByWarehouseNumberData(InfoProductionPlanConfigCokeDetailByWarehouseNumberDTO infoProductionPlanConfigCokeDetailByWarehouseNumberDTO) {
        return getBaseMapper().getProductionPlanConfigCokeDetailInfoByWarehouseNumberData(infoProductionPlanConfigCokeDetailByWarehouseNumberDTO);
    }
}

