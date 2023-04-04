package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.constants.CommonConstant;
import com.jlkj.product.oi.domain.ProductionSingleHoleYield;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.AddProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.DeleteProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.PageProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.UpdateProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.mapper.ProductionSingleHoleYieldMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.ProductionSingleHoleYieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sudeyou
 */
@Service
public class ProductionSingleHoleYieldServiceImpl extends ServiceImpl<ProductionSingleHoleYieldMapper, ProductionSingleHoleYield>
    implements ProductionSingleHoleYieldService {

    @Resource
    ChangeLogService changeLogService;

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getListPage(PageProductionSingleHoleYieldDTO pageProductionSingleHoleYieldDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionSingleHoleYieldDTO.getCurrent(), pageProductionSingleHoleYieldDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionSingleHoleYieldDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Object addProductionSingleHoleYield(AddProductionSingleHoleYieldDTO addProductionSingleHoleYieldDTO) {
        List<ProductionSingleHoleYield> list = list(new QueryWrapper<ProductionSingleHoleYield>().lambda()
                .ge(ProductionSingleHoleYield::getCreateTime, DateUtil.format(new Date(), CommonConstant.DATEFORMAT_YYYY_MM_DD) + " " + CommonConstant.TIME_BEGIN)
                .le(ProductionSingleHoleYield::getCreateTime, DateUtil.format(new Date(), CommonConstant.DATEFORMAT_YYYY_MM_DD) + " " + CommonConstant.TIME_END)
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("当天只能新增一次单孔产量记录");
        }
        ProductionSingleHoleYield productionSingleHoleYield = new ProductionSingleHoleYield();
        productionSingleHoleYield.setId(IdUtil.randomUUID());
        productionSingleHoleYield.setYield(addProductionSingleHoleYieldDTO.getYield());
        productionSingleHoleYield.setCreateUserId(addProductionSingleHoleYieldDTO.getCreateUserId());
        productionSingleHoleYield.setCreateUserName(addProductionSingleHoleYieldDTO.getCreateUserName());
        productionSingleHoleYield.setCreateTime(new Date());
        productionSingleHoleYield.setModifyUserId(addProductionSingleHoleYieldDTO.getCreateUserId());
        productionSingleHoleYield.setModifyUserName(addProductionSingleHoleYieldDTO.getCreateUserName());
        productionSingleHoleYield.setModifyTime(new Date());
        save(productionSingleHoleYield);

        String content = "新增：" +
                "[产量：" + addProductionSingleHoleYieldDTO.getYield().stripTrailingZeros().toPlainString() + "]";
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("参数维护->焦炉单孔产量");
        insertChangeLogDTO.setContent(content);
        insertChangeLogDTO.setCreateUserId(addProductionSingleHoleYieldDTO.getCreateUserId());
        insertChangeLogDTO.setCreateUserName(addProductionSingleHoleYieldDTO.getCreateUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);

        return AjaxResult.success("焦炉单孔产量增加成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public Object editProductionSingleHoleYield(UpdateProductionSingleHoleYieldDTO updateProductionSingleHoleYieldDTO) {
        ProductionSingleHoleYield productionSingleHoleYield = getById(updateProductionSingleHoleYieldDTO.getId());
        if (null != productionSingleHoleYield) {
            if (!DateUtil.format(productionSingleHoleYield.getCreateTime(), CommonConstant.DATEFORMAT_YYYY_MM_DD).equals(DateUtil.format(new Date(), CommonConstant.DATEFORMAT_YYYY_MM_DD))) {
                return AjaxResult.error("只能编辑当天的焦炉单孔产量记录");
            }

            StringBuilder content = new StringBuilder();
            if (productionSingleHoleYield.getYield().compareTo(updateProductionSingleHoleYieldDTO.getYield()) != 0) {
                content.append("[产量：").append(productionSingleHoleYield.getYield().stripTrailingZeros().toPlainString()).append("->").append(updateProductionSingleHoleYieldDTO.getYield().stripTrailingZeros().toPlainString()).append("]");
            }

            if (content.length() > 0) {
                InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                insertChangeLogDTO.setModuleName("生产管理");
                insertChangeLogDTO.setFunctionName("参数维护->焦炉单孔产量");
                insertChangeLogDTO.setContent("修改：" + content);
                insertChangeLogDTO.setCreateUserId(updateProductionSingleHoleYieldDTO.getModifyUserId());
                insertChangeLogDTO.setCreateUserName(updateProductionSingleHoleYieldDTO.getModifyUserName());
                changeLogService.insertChangeLogData(insertChangeLogDTO);
            }

            productionSingleHoleYield.setYield(updateProductionSingleHoleYieldDTO.getYield());
            productionSingleHoleYield.setModifyUserId(updateProductionSingleHoleYieldDTO.getModifyUserId());
            productionSingleHoleYield.setModifyUserName(updateProductionSingleHoleYieldDTO.getModifyUserName());
            productionSingleHoleYield.setModifyTime(new Date());
            updateById(productionSingleHoleYield);
            return AjaxResult.success("焦炉单孔产量修改成功");
        }
        else {
            return AjaxResult.error("焦炉单孔产量不存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Object delProductionSingleHoleYield(DeleteProductionSingleHoleYieldDTO deleteProductionSingleHoleYieldDTO) {
        ProductionSingleHoleYield productionSingleHoleYield = getById(deleteProductionSingleHoleYieldDTO.getId());
        if (null != productionSingleHoleYield) {
            if (!DateUtil.format(productionSingleHoleYield.getCreateTime(), CommonConstant.DATEFORMAT_YYYY_MM_DD).equals(DateUtil.format(new Date(), CommonConstant.DATEFORMAT_YYYY_MM_DD))) {
                return AjaxResult.error("只能删除当天的焦炉单孔产量记录");
            }

            String content = "删除：" +
                    "[产量：" + productionSingleHoleYield.getYield().stripTrailingZeros().toPlainString() + "]";
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("参数维护->焦炉单孔产量");
            insertChangeLogDTO.setContent(content);
            insertChangeLogDTO.setCreateUserId(deleteProductionSingleHoleYieldDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteProductionSingleHoleYieldDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);

            removeById(productionSingleHoleYield);
            return AjaxResult.success("焦炉单孔产量删除成功");
        }
        else {
            return AjaxResult.error("焦炉单孔产量不存在或已被删除");
        }
    }
}




