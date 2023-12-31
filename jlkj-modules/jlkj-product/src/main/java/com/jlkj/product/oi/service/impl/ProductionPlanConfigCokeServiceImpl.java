package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.ConvertUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.constants.CommonConstant;
import com.jlkj.product.oi.domain.MaterialsCode;
import com.jlkj.product.oi.domain.ProductionPlanConfigCoke;
import com.jlkj.product.oi.domain.ProductionPlanConfigCokeDetail;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplanconfigcoke.*;
import com.jlkj.product.oi.dto.productionplanconfigcokedetail.AddProductionPlanConfigCokeDetailDTO;
import com.jlkj.product.oi.dto.productionplanconfigcokedetail.UpdateProductionPlanConfigCokeDetailDTO;
import com.jlkj.product.oi.mapper.ProductionPlanConfigCokeMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.MaterialsCodeService;
import com.jlkj.product.oi.service.ProductionPlanConfigCokeDetailService;
import com.jlkj.product.oi.service.ProductionPlanConfigCokeService;
import com.jlkj.product.oi.vo.productionplanconfigcoke.PlanNumberVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*@description: 服务实现-配煤计划主记录
*@Author: 265823
*@date: 2023/7/11 8:05
*/
@Service
public class ProductionPlanConfigCokeServiceImpl extends ServiceImpl<ProductionPlanConfigCokeMapper, ProductionPlanConfigCoke>
    implements ProductionPlanConfigCokeService {

    @Lazy
    @Resource
    private ProductionPlanConfigCokeDetailService productionPlanConfigCokeDetailService;

    @Resource
    ChangeLogService changeLogService;

    @Resource
    MaterialsCodeService materialsCodeService;

    private static final String SUCCESS = "200";

    /**
     * 查询-分页-配煤计划主记录
     * @param pageProductionPlanConfigCokeDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageProductionPlanConfigCokeDTO pageProductionPlanConfigCokeDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionPlanConfigCokeDTO.getCurrent(), pageProductionPlanConfigCokeDTO.getSize());
        return getBaseMapper().getPageData(page, pageProductionPlanConfigCokeDTO);
    }

    /**
     * 新增-配煤计划主记录
     * @param addProductionPlanConfigCokeDTO 新增dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addData(AddProductionPlanConfigCokeDTO addProductionPlanConfigCokeDTO) {
        List<ProductionPlanConfigCoke> list = list(new QueryWrapper<ProductionPlanConfigCoke>().lambda()
                .eq(ProductionPlanConfigCoke::getPlanNumber, addProductionPlanConfigCokeDTO.getPlanNumber())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            throw new ServiceException("计划编号已存在");
        }
        BigDecimal proportion = new BigDecimal(0);
        for (AddProductionPlanConfigCokeDetailDTO addProductionPlanConfigCokeDetailDTO : addProductionPlanConfigCokeDTO.getDetailList()) {
            proportion = proportion.add(addProductionPlanConfigCokeDetailDTO.getProportion());
        }
        int num = 100;
        if (proportion.compareTo(new BigDecimal(num)) != 0) {
            throw new ServiceException("配煤比总计必须为100");
        }
        MaterialsCode materialsCode = materialsCodeService.getById(addProductionPlanConfigCokeDTO.getMaterialId());
        ProductionPlanConfigCoke productionPlanConfigCoke = new ProductionPlanConfigCoke();
        productionPlanConfigCoke.setId(IdUtil.randomUUID());
        productionPlanConfigCoke.setPlanNumber(addProductionPlanConfigCokeDTO.getPlanNumber());
        productionPlanConfigCoke.setPlanStartTime(DateUtil.parseDateTime(addProductionPlanConfigCokeDTO.getPlanStartTime()));
        productionPlanConfigCoke.setPlanState(1);
        productionPlanConfigCoke.setCreateUserId(addProductionPlanConfigCokeDTO.getCreateUserId());
        productionPlanConfigCoke.setCreateUserName(addProductionPlanConfigCokeDTO.getCreateUserName());
        productionPlanConfigCoke.setCreateTime(new Date());
        productionPlanConfigCoke.setModifyUserId(addProductionPlanConfigCokeDTO.getCreateUserId());
        productionPlanConfigCoke.setModifyUserName(addProductionPlanConfigCokeDTO.getCreateUserName());
        productionPlanConfigCoke.setModifyTime(new Date());
        productionPlanConfigCoke.setMaterialId(addProductionPlanConfigCokeDTO.getMaterialId());
        productionPlanConfigCoke.setMaterialName(addProductionPlanConfigCokeDTO.getMaterialName());
        productionPlanConfigCoke.setMaterialCode(materialsCode.getMaterialsCode());
///        productionPlanConfigCoke.setTowerNumber(addProductionPlanConfigCokeDTO.getTowerNumber());
        save(productionPlanConfigCoke);
        for (AddProductionPlanConfigCokeDetailDTO addProductionPlanConfigCokeDetailDTO : addProductionPlanConfigCokeDTO.getDetailList()) {
            addProductionPlanConfigCokeDetailDTO.setPlanId(productionPlanConfigCoke.getId());
            AjaxResult result = (AjaxResult)productionPlanConfigCokeDetailService.addData(addProductionPlanConfigCokeDetailDTO);
            if (!SUCCESS.equals(result.get("code").toString())) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        saveNewLog(addProductionPlanConfigCokeDTO);
    }

    private void saveNewLog(AddProductionPlanConfigCokeDTO addProductionPlanConfigCokeDTO) {
        StringBuilder content = new StringBuilder();
        content.append("新增：" + "[计划编号：").append(addProductionPlanConfigCokeDTO.getPlanNumber()).append("],")
                .append("[开始时间：").append(addProductionPlanConfigCokeDTO.getPlanStartTime()).append("],")
                .append("[焦炭等级：").append(addProductionPlanConfigCokeDTO.getMaterialName()).append("]")
//                .append("[储煤塔号：").append(addProductionPlanConfigCokeDTO.getTowerNumber()).append("]")
                .append("配煤明细：");
        for (int y = 0; y < addProductionPlanConfigCokeDTO.getDetailList().size(); y++) {
            content.append("[配煤仓号：").append(addProductionPlanConfigCokeDTO.getDetailList().get(y).getWarehouseNumber()).append("],")
                    .append("[煤种：").append(addProductionPlanConfigCokeDTO.getDetailList().get(y).getCoalTypeName()).append("],")
                    .append("[配煤比例：").append(addProductionPlanConfigCokeDTO.getDetailList().get(y).getProportion().stripTrailingZeros().toPlainString()).append("],");
        }
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("生产计划->配煤计划");
        insertChangeLogDTO.setContent(content.toString());
        insertChangeLogDTO.setCreateUserId(addProductionPlanConfigCokeDTO.getCreateUserId());
        insertChangeLogDTO.setCreateUserName(addProductionPlanConfigCokeDTO.getCreateUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);
    }

    public void saveUpdateLog(UpdateProductionPlanConfigCokeDTO updateProductionPlanConfigCokeDTO, ProductionPlanConfigCoke productionPlanConfigCoke) {
        StringBuilder content = new StringBuilder();
        if (!productionPlanConfigCoke.getPlanNumber().equals(updateProductionPlanConfigCokeDTO.getPlanNumber())) {
            content.append("[计划编号：").append(productionPlanConfigCoke.getPlanNumber()).append("->").append(updateProductionPlanConfigCokeDTO.getPlanNumber()).append("],");
        }
        if (!DateUtil.formatDateTime(productionPlanConfigCoke.getPlanStartTime()).equals(updateProductionPlanConfigCokeDTO.getPlanStartTime())) {
            content.append("[开始时间：").append(productionPlanConfigCoke.getPlanStartTime()).append("->").append(updateProductionPlanConfigCokeDTO.getPlanStartTime()).append("],");
        }
        if (!productionPlanConfigCoke.getMaterialName().equals(updateProductionPlanConfigCokeDTO.getMaterialName())) {
            content.append("[焦炭等级：").append(productionPlanConfigCoke.getMaterialName()).append("->").append(updateProductionPlanConfigCokeDTO.getMaterialName()).append("],");
        }
///        if (!productionPlanConfigCoke.getTowerNumber().equals(updateProductionPlanConfigCokeDTO.getTowerNumber())) {
///            content.append("[储煤塔号：").append(productionPlanConfigCoke.getTowerNumber()).append("->").append(updateProductionPlanConfigCokeDTO.getTowerNumber()).append("],");
///        }
        StringBuilder contentDetail = new StringBuilder();
        for (UpdateProductionPlanConfigCokeDetailDTO updateProductionPlanConfigCokeDetailDTO : updateProductionPlanConfigCokeDTO.getDetailList()) {
            if ("".equals(updateProductionPlanConfigCokeDetailDTO.getId())) {
                contentDetail.append("新增： [配煤仓号：").append(updateProductionPlanConfigCokeDetailDTO.getWarehouseNumber()).append("],")
                        .append("[煤种：").append(updateProductionPlanConfigCokeDetailDTO.getCoalTypeName()).append("],")
                        .append("[配煤比例：").append(updateProductionPlanConfigCokeDetailDTO.getProportion().stripTrailingZeros().toPlainString()).append("],");
            }
            else {
                ProductionPlanConfigCokeDetail productionPlanConfigCokeDetail = productionPlanConfigCokeDetailService.getById(updateProductionPlanConfigCokeDetailDTO.getId());
                if (updateProductionPlanConfigCokeDetailDTO.getDeleteFlag() == 1) {
                    contentDetail.append("删除： [配煤仓号：").append(productionPlanConfigCokeDetail.getWarehouseNumber()).append("],")
                            .append("[煤种：").append(productionPlanConfigCokeDetail.getCoalTypeName()).append("],")
                            .append("[配煤比例：").append(productionPlanConfigCokeDetail.getProportion().stripTrailingZeros().toPlainString()).append("],");
                }
                else {
                    StringBuilder contentDetailUpdate = new StringBuilder();
                    if (!productionPlanConfigCokeDetail.getWarehouseNumber().equals(updateProductionPlanConfigCokeDetailDTO.getWarehouseNumber())) {
                        contentDetailUpdate.append("[配煤仓号：").append(productionPlanConfigCokeDetail.getWarehouseNumber()).append("->").append(updateProductionPlanConfigCokeDetailDTO.getWarehouseNumber()).append("],");
                    }
                    if (!productionPlanConfigCokeDetail.getCoalTypeName().equals(updateProductionPlanConfigCokeDetailDTO.getCoalTypeName())) {
                        contentDetailUpdate.append("[煤种：").append(productionPlanConfigCokeDetail.getCoalTypeName()).append("->").append(updateProductionPlanConfigCokeDetailDTO.getCoalTypeName()).append("],");
                    }
                    if (productionPlanConfigCokeDetail.getProportion().compareTo(updateProductionPlanConfigCokeDetailDTO.getProportion()) != 0) {
                        contentDetailUpdate.append("[配煤比例：").append(productionPlanConfigCokeDetail.getProportion().stripTrailingZeros().toPlainString()).append("->").append(updateProductionPlanConfigCokeDetailDTO.getProportion().stripTrailingZeros().toPlainString()).append("],");
                    }
                    if (contentDetailUpdate.length() > 0) {
                        contentDetail.append("修改：").append(contentDetailUpdate);
                    }
                }
            }
        }
        if (content.length() > 0 || contentDetail.length() > 0) {
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("生产计划->配煤计划");
            insertChangeLogDTO.setContent("修改：" + content + contentDetail);
            insertChangeLogDTO.setCreateUserId(updateProductionPlanConfigCokeDTO.getModifyUserId());
            insertChangeLogDTO.setCreateUserName(updateProductionPlanConfigCokeDTO.getModifyUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
        }
    }

    /**
     * 修改-配煤计划主记录
     * @param updateProductionPlanConfigCokeDTO 修改dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateData(UpdateProductionPlanConfigCokeDTO updateProductionPlanConfigCokeDTO) {
        ProductionPlanConfigCoke productionPlanConfigCoke = getById(updateProductionPlanConfigCokeDTO.getId());
        if (null != productionPlanConfigCoke) {
            List<ProductionPlanConfigCoke> list = list(new QueryWrapper<ProductionPlanConfigCoke>().lambda()
                    .eq(ProductionPlanConfigCoke::getPlanNumber, updateProductionPlanConfigCokeDTO.getPlanNumber())
                    .ne(ProductionPlanConfigCoke::getId, updateProductionPlanConfigCokeDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() == 0) {
                if (productionPlanConfigCoke.getPlanState() != 1) {
                    throw new ServiceException("只能修改未确认的记录");
                }
                BigDecimal proportion = new BigDecimal(0);
                for (UpdateProductionPlanConfigCokeDetailDTO updateProductionPlanConfigCokeDetailDTO : updateProductionPlanConfigCokeDTO.getDetailList()) {
                    if (updateProductionPlanConfigCokeDetailDTO.getDeleteFlag() == 0) {
                        proportion = proportion.add(updateProductionPlanConfigCokeDetailDTO.getProportion());
                    }
                }
                int num = 100;
                if (proportion.compareTo(new BigDecimal(num)) != 0) {
                    throw new ServiceException("配煤比总计必须为100");
                }
                MaterialsCode materialsCode = materialsCodeService.getById(updateProductionPlanConfigCokeDTO.getMaterialId());
                saveUpdateLog(updateProductionPlanConfigCokeDTO, productionPlanConfigCoke);
                productionPlanConfigCoke.setPlanNumber(updateProductionPlanConfigCokeDTO.getPlanNumber());
                productionPlanConfigCoke.setPlanStartTime(DateUtil.parseDateTime(updateProductionPlanConfigCokeDTO.getPlanStartTime()));
                productionPlanConfigCoke.setModifyUserId(updateProductionPlanConfigCokeDTO.getModifyUserId());
                productionPlanConfigCoke.setModifyUserName(updateProductionPlanConfigCokeDTO.getModifyUserName());
                productionPlanConfigCoke.setModifyTime(new Date());
                productionPlanConfigCoke.setMaterialId(updateProductionPlanConfigCokeDTO.getMaterialId());
                productionPlanConfigCoke.setMaterialName(updateProductionPlanConfigCokeDTO.getMaterialName());
                productionPlanConfigCoke.setMaterialCode(materialsCode.getMaterialsCode());
///                productionPlanConfigCoke.setTowerNumber(updateProductionPlanConfigCokeDTO.getTowerNumber());
                updateById(productionPlanConfigCoke);
                for (UpdateProductionPlanConfigCokeDetailDTO updateProductionPlanConfigCokeDetailDTO : updateProductionPlanConfigCokeDTO.getDetailList()) {
                    if ("".equals(updateProductionPlanConfigCokeDetailDTO.getId())) {
                        AddProductionPlanConfigCokeDetailDTO addProductionPlanConfigCokeDetailDTO = ConvertUtil.convertValue(updateProductionPlanConfigCokeDetailDTO, AddProductionPlanConfigCokeDetailDTO.class);
                        addProductionPlanConfigCokeDetailDTO.setPlanId(productionPlanConfigCoke.getId());
                        addProductionPlanConfigCokeDetailDTO.setCreateUserId(updateProductionPlanConfigCokeDetailDTO.getModifyUserId());
                        addProductionPlanConfigCokeDetailDTO.setCreateUserName(updateProductionPlanConfigCokeDetailDTO.getModifyUserName());
                        AjaxResult result = (AjaxResult)productionPlanConfigCokeDetailService.addData(addProductionPlanConfigCokeDetailDTO);
                        if (!SUCCESS.equals(result.get("code").toString())) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    }
                    else {
                        if (updateProductionPlanConfigCokeDetailDTO.getDeleteFlag() == 1) {
                            productionPlanConfigCokeDetailService.removeById(updateProductionPlanConfigCokeDetailDTO.getId());
                        }
                        else {
                            AjaxResult result = (AjaxResult) productionPlanConfigCokeDetailService.updateData(updateProductionPlanConfigCokeDetailDTO);
                            if (!SUCCESS.equals(result.get("code").toString())) {
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            }
                        }
                    }
                }
            }
            else {
                throw new ServiceException("计划编号已存在");
            }
        }
        else {
            throw new ServiceException("配煤计划主记录不存在");
        }
    }

    /**
     * 删除-配煤计划主记录
     * @param deleteProductionPlanConfigCokeDTO 删除dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(DeleteProductionPlanConfigCokeDTO deleteProductionPlanConfigCokeDTO) {
        ProductionPlanConfigCoke productionPlanConfigCoke = getById(deleteProductionPlanConfigCokeDTO.getId());
        if (null != productionPlanConfigCoke) {
            if (productionPlanConfigCoke.getPlanState() != 1) {
                throw new ServiceException("只能删除未确认的记录");
            }

            List<ProductionPlanConfigCokeDetail> detailList = productionPlanConfigCokeDetailService.list(new QueryWrapper<ProductionPlanConfigCokeDetail>().lambda()
                    .eq(ProductionPlanConfigCokeDetail::getPlanId, deleteProductionPlanConfigCokeDTO.getId())
            );

            StringBuilder content = new StringBuilder();
            content.append("删除：" + "[计划编号：").append(productionPlanConfigCoke.getPlanNumber()).append("],")
                    .append("[开始时间：").append(productionPlanConfigCoke.getPlanStartTime()).append("],")
                    .append("[焦炭等级：").append(productionPlanConfigCoke.getMaterialName()).append("]")
//                    .append("[储煤塔号：").append(productionPlanConfigCoke.getTowerNumber()).append("]")
                    .append("配煤明细：");

            for (int y = 0; y < detailList.size(); y++) {
                content.append("[配煤仓号：").append(detailList.get(y).getWarehouseNumber()).append("],")
                        .append("[煤种：").append(detailList.get(y).getCoalTypeName()).append("],")
                        .append("[配煤比例：").append(detailList.get(y).getProportion().stripTrailingZeros().toPlainString()).append("],");
            }
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("生产计划->配煤计划");
            insertChangeLogDTO.setContent(content.toString());
            insertChangeLogDTO.setCreateUserId(deleteProductionPlanConfigCokeDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteProductionPlanConfigCokeDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);

            productionPlanConfigCokeDetailService.deleteDataByPlanId(productionPlanConfigCoke.getId());
            removeById(productionPlanConfigCoke);
        }
        else {
            throw new ServiceException("配煤计划主记录不存在或已被删除");
        }
    }

    /**
     * 确认-配煤计划主记录
     * @param confirmProductionPlanConfigCokeDTO 修改dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmData(ConfirmProductionPlanConfigCokeDTO confirmProductionPlanConfigCokeDTO) {
        ProductionPlanConfigCoke productionPlanConfigCoke = getById(confirmProductionPlanConfigCokeDTO.getId());
        if (null != productionPlanConfigCoke) {
            if (productionPlanConfigCoke.getPlanState() != 1) {
                throw new ServiceException("只能对未确认的记录进行确认操作");
            }
            productionPlanConfigCoke.setPlanState(2);
            productionPlanConfigCoke.setReceiveUserId(confirmProductionPlanConfigCokeDTO.getReceiveUserId());
            productionPlanConfigCoke.setReceiveUserName(confirmProductionPlanConfigCokeDTO.getReceiveUserName());
            productionPlanConfigCoke.setReceiveTime(new Date());
            updateById(productionPlanConfigCoke);
        }
        else {
            throw new ServiceException("配煤计划主记录不存在");
        }
    }

    /**
     * 配煤计划主记录-计划编号
     * @return
     */
    @Override
    public PlanNumberVO getPlanCodeData() {
        PlanNumberVO planNumberVO = new PlanNumberVO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date=new Date();
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(date));
        Map<String, Object> params = new HashMap<>(1);
        params.put("codeHeader", sb.toString());
        Map<String, Object> planCodeData = getBaseMapper().getPlanCodeData(params);
        if (null != planCodeData) {
            String supplierCode = planCodeData.get("plan_number").toString();
            int value = Integer.parseInt(supplierCode.replace(sb.toString(), ""));
            value = value + 1;
            planNumberVO.setPlanNumber(sb.append(String.format("%03d", value)).toString());
        }
        else {
            planNumberVO.setPlanNumber(sb.append("001").toString());
        }
        return planNumberVO;
    }

    /**
     * 手动切配煤计划-配煤计划主记录
     * @param dto dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeProductionPlanCfgCokeConfirm(ChangeProductionPlanCfgCokeDTO dto) {
        ProductionPlanConfigCoke productionPlanConfigCoke = getById(dto.getId());
        if (null != productionPlanConfigCoke) {
            List<ProductionPlanConfigCoke> list = list(new QueryWrapper<ProductionPlanConfigCoke>().lambda()
                    .eq(ProductionPlanConfigCoke::getPlanState, 3)
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            Date date = new Date();
            if (list.size() > 0) {
                ProductionPlanConfigCoke productionPlanConfigCokeOld = list.get(0);
                productionPlanConfigCokeOld.setPlanState(4);
                productionPlanConfigCokeOld.setPlanEndTime(date);
                updateById(productionPlanConfigCokeOld);
            }
            productionPlanConfigCoke.setPlanState(3);
            productionPlanConfigCoke.setPlanStartTime(date);
            updateById(productionPlanConfigCoke);
        }
        else {
            throw new ServiceException("切换的配煤计划主记录不存在");
        }
    }
}

