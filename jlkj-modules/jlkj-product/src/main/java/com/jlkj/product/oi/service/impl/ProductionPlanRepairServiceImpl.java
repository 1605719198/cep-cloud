package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.HumanresourceOrganization;
import com.jlkj.product.oi.domain.ProductionPlanRepair;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplanrepair.*;
import com.jlkj.product.oi.mapper.ProductionPlanRepairMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.HumanresourceOrganizationService;
import com.jlkj.product.oi.service.ProductionPlanRepairService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
*@description: 针对表【product_oi_plan_repair(计划管理--维修计划)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/11 8:37
*/
@Service
public class ProductionPlanRepairServiceImpl extends ServiceImpl<ProductionPlanRepairMapper, ProductionPlanRepair>
    implements ProductionPlanRepairService {

    @Resource
    ChangeLogService changeLogService;

    @Resource
    HumanresourceOrganizationService humanresourceOrganizationService;

    /**
     * 分页列表查询
     * @param pageProductionPlanRepairDTO 查询条件dto
     * @return
     */
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionPlanRepairDTO pageProductionPlanRepairDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionPlanRepairDTO.getCurrent(), pageProductionPlanRepairDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionPlanRepairDTO);
    }

    private void saveNewLog(AddProductionPlanRepairDTO addProductionPlanRepairDTO) {
        HumanresourceOrganization humanresourceOrganization = humanresourceOrganizationService.getById(addProductionPlanRepairDTO.getDepartmentId());
        String repairTypeName = addProductionPlanRepairDTO.getRepairTypeId() == 1 ? "大修" :
                                addProductionPlanRepairDTO.getRepairTypeId() == 2 ? "中修" :
                                addProductionPlanRepairDTO.getRepairTypeId() == 3 ? "小修" :
                                addProductionPlanRepairDTO.getRepairTypeId() == 4 ? "临修" :
                                addProductionPlanRepairDTO.getRepairTypeId() == 5 ? "设备消缺" :
                                addProductionPlanRepairDTO.getRepairTypeId() == 6 ? "工艺技改" :
                                addProductionPlanRepairDTO.getRepairTypeId() == 7 ? "零星修缮" :
                                addProductionPlanRepairDTO.getRepairTypeId() == 8 ? "环保项目" : "";
        StringBuilder content = new StringBuilder();
        content.append("新增：" + "[计划编号：").append(addProductionPlanRepairDTO.getPlanCode()).append("],")
                .append("[开始时间：").append(addProductionPlanRepairDTO.getPlanStartDate()).append("],")
                .append("[部门：").append(humanresourceOrganization.getOrganizationName()).append("]")
                .append("[结束时间：").append(addProductionPlanRepairDTO.getPlanEndDate()).append("]")
                .append("[设备名称：").append(addProductionPlanRepairDTO.getEquipmentName()).append("]")
                .append("[主体责任人：").append(addProductionPlanRepairDTO.getPersonMainLiableName()).append("]")
                .append("[维修类型：").append(repairTypeName).append("]")
                .append("[专业责任人：").append(addProductionPlanRepairDTO.getPersonProfessionLiableName()).append("]")
                .append("[维修内容：").append(addProductionPlanRepairDTO.getRepairContent()).append("]")
                .append("[影响产量：").append(addProductionPlanRepairDTO.getIsAffectOutput() == 1 ? "影响" : "未影响").append("]")
                .append("[预提醒天数：").append(addProductionPlanRepairDTO.getRemindDays()).append("]");
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("生产计划->维修计划");
        insertChangeLogDTO.setContent(content.toString());
        insertChangeLogDTO.setCreateUserId(addProductionPlanRepairDTO.getCreateUserId());
        insertChangeLogDTO.setCreateUserName(addProductionPlanRepairDTO.getCreateUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);
    }

    private void saveUpdateLog(UpdateProductionPlanRepairDTO updateProductionPlanRepairDTO, ProductionPlanRepair productionPlanRepair) {
        StringBuilder content = new StringBuilder();
        if (!productionPlanRepair.getDepartmentId().equals(updateProductionPlanRepairDTO.getDepartmentId())) {
            HumanresourceOrganization oldHumanresourceOrganization = humanresourceOrganizationService.getById(productionPlanRepair.getDepartmentId());
            HumanresourceOrganization humanresourceOrganization = humanresourceOrganizationService.getById(updateProductionPlanRepairDTO.getDepartmentId());
            content.append(" [部门：").append(oldHumanresourceOrganization.getOrganizationName()).append("->").append(humanresourceOrganization.getOrganizationName()).append("]");
        }
        if (!productionPlanRepair.getRepairTypeId().equals(updateProductionPlanRepairDTO.getRepairTypeId())) {
            String oldRepairTypeName = productionPlanRepair.getRepairTypeId() == 1 ? "大修" :
                    productionPlanRepair.getRepairTypeId() == 2 ? "中修" :
                    productionPlanRepair.getRepairTypeId() == 3 ? "小修" :
                    productionPlanRepair.getRepairTypeId() == 4 ? "临修" :
                    productionPlanRepair.getRepairTypeId() == 5 ? "设备消缺" :
                    productionPlanRepair.getRepairTypeId() == 6 ? "工艺技改" :
                    productionPlanRepair.getRepairTypeId() == 7 ? "零星修缮" :
                    productionPlanRepair.getRepairTypeId() == 8 ? "环保项目" : "";
            String repairTypeName = updateProductionPlanRepairDTO.getRepairTypeId() == 1 ? "大修" :
                    updateProductionPlanRepairDTO.getRepairTypeId() == 2 ? "中修" :
                    updateProductionPlanRepairDTO.getRepairTypeId() == 3 ? "小修" :
                    updateProductionPlanRepairDTO.getRepairTypeId() == 4 ? "临修" :
                    updateProductionPlanRepairDTO.getRepairTypeId() == 5 ? "设备消缺" :
                    updateProductionPlanRepairDTO.getRepairTypeId() == 6 ? "工艺技改" :
                    updateProductionPlanRepairDTO.getRepairTypeId() == 7 ? "零星修缮" :
                    updateProductionPlanRepairDTO.getRepairTypeId() == 8 ? "环保项目" : "";
            content.append(" [维修类型：").append(oldRepairTypeName).append("->").append(repairTypeName).append("]");
        }
        if (!productionPlanRepair.getPersonMainLiableName().equals(updateProductionPlanRepairDTO.getPersonMainLiableName())) {
            content.append(" [主体责任人：").append(productionPlanRepair.getPersonMainLiableName()).append("->").append(updateProductionPlanRepairDTO.getPersonMainLiableName()).append("]");
        }
        if (!productionPlanRepair.getPersonProfessionLiableName().equals(updateProductionPlanRepairDTO.getPersonProfessionLiableName())) {
            content.append(" [专业责任人：").append(productionPlanRepair.getPersonProfessionLiableName()).append("->").append(updateProductionPlanRepairDTO.getPersonProfessionLiableName()).append("]");
        }
        if (!productionPlanRepair.getEquipmentName().equals(updateProductionPlanRepairDTO.getEquipmentName())) {
            content.append(" [设备名称：").append(productionPlanRepair.getEquipmentName()).append("->").append(updateProductionPlanRepairDTO.getEquipmentName()).append("]");
        }
        if (!DateUtil.formatDate(productionPlanRepair.getPlanStartDate()).equals(updateProductionPlanRepairDTO.getPlanStartDate())) {
            content.append(" [开始时间：").append(DateUtil.formatDate(productionPlanRepair.getPlanStartDate())).append("->").append(updateProductionPlanRepairDTO.getPlanStartDate()).append("]");
        }
        if (!DateUtil.formatDate(productionPlanRepair.getPlanEndDate()).equals(updateProductionPlanRepairDTO.getPlanEndDate())) {
            content.append(" [结束时间：").append(DateUtil.formatDate(productionPlanRepair.getPlanEndDate())).append("->").append(updateProductionPlanRepairDTO.getPlanEndDate()).append("]");
        }
        if (!productionPlanRepair.getRepairContent().equals(updateProductionPlanRepairDTO.getRepairContent())) {
            content.append(" [维修内容：").append(productionPlanRepair.getRepairContent()).append("->").append(updateProductionPlanRepairDTO.getRepairContent()).append("]");
        }
        if (!productionPlanRepair.getIsAffectOutput().equals(updateProductionPlanRepairDTO.getIsAffectOutput())) {
            content.append(" [影响产量：").append(productionPlanRepair.getIsAffectOutput() == 1 ? "影响" : "未影响").append("->").append(updateProductionPlanRepairDTO.getIsAffectOutput() == 1 ? "影响" : "未影响").append("]");
        }
        if (!productionPlanRepair.getRemindDays().equals(updateProductionPlanRepairDTO.getRemindDays())) {
            content.append(" [预提醒天数：").append(productionPlanRepair.getRemindDays()).append("->").append(updateProductionPlanRepairDTO.getRemindDays()).append("]");
        }
        if (content.length() > 0) {
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("生产计划->维修计划");
            insertChangeLogDTO.setContent("修改：" + content);
            insertChangeLogDTO.setCreateUserId(updateProductionPlanRepairDTO.getModifyUserId());
            insertChangeLogDTO.setCreateUserName(updateProductionPlanRepairDTO.getModifyUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
        }
    }

    /**
     * 新增
     * @param addProductionPlanRepairDTO
     */
    @Override
    public void insert(AddProductionPlanRepairDTO addProductionPlanRepairDTO) {
        ProductionPlanRepair productionPlanRepair = new ProductionPlanRepair();
        BeanUtils.copyProperties(addProductionPlanRepairDTO,productionPlanRepair);
        productionPlanRepair.setId(IdUtil.randomUUID());
        productionPlanRepair.setPlanStartDate(DateUtil.parseDate(addProductionPlanRepairDTO.getPlanStartDate()));
        productionPlanRepair.setPlanEndDate(DateUtil.parseDate(addProductionPlanRepairDTO.getPlanEndDate()));
        productionPlanRepair.setPlanState(0);
        productionPlanRepair.setCreateTime(new Date());
        productionPlanRepair.setModifyTime(new Date());
        save(productionPlanRepair);
        saveNewLog(addProductionPlanRepairDTO);
    }

    /**
     * 修改
     * @param updateProductionPlanRepairDTO
     */
    @Override
    public void updateCustom(@Valid @RequestBody UpdateProductionPlanRepairDTO updateProductionPlanRepairDTO) {
        ProductionPlanRepair productionPlanRepair = getById(updateProductionPlanRepairDTO.getId());
        if (null != productionPlanRepair) {
            if (DateUtil.compare(new Date(), productionPlanRepair.getPlanStartDate()) > 0 || productionPlanRepair.getPlanState() == 1) {
                throw new ServiceException("计划已经开始或完成，不能修改");
            }
            saveUpdateLog(updateProductionPlanRepairDTO, productionPlanRepair);
            productionPlanRepair.setDepartmentId(updateProductionPlanRepairDTO.getDepartmentId());
            productionPlanRepair.setRepairTypeId(updateProductionPlanRepairDTO.getRepairTypeId());
            productionPlanRepair.setPersonMainLiableId(updateProductionPlanRepairDTO.getPersonMainLiableId());
            productionPlanRepair.setPersonMainLiableName(updateProductionPlanRepairDTO.getPersonMainLiableName());
            productionPlanRepair.setPersonProfessionLiableId(updateProductionPlanRepairDTO.getPersonProfessionLiableId());
            productionPlanRepair.setPersonProfessionLiableName(updateProductionPlanRepairDTO.getPersonProfessionLiableName());
            productionPlanRepair.setEquipmentNumber(updateProductionPlanRepairDTO.getEquipmentNumber());
            productionPlanRepair.setEquipmentName(updateProductionPlanRepairDTO.getEquipmentName());
            productionPlanRepair.setPlanStartDate(DateUtil.parseDate(updateProductionPlanRepairDTO.getPlanStartDate()));
            productionPlanRepair.setPlanEndDate(DateUtil.parseDate(updateProductionPlanRepairDTO.getPlanEndDate()));
            productionPlanRepair.setRepairContent(updateProductionPlanRepairDTO.getRepairContent());
            productionPlanRepair.setIsAffectOutput(updateProductionPlanRepairDTO.getIsAffectOutput());
            productionPlanRepair.setRemindDays(updateProductionPlanRepairDTO.getRemindDays());
            productionPlanRepair.setModifyUserId(updateProductionPlanRepairDTO.getModifyUserId());
            productionPlanRepair.setModifyUserName(updateProductionPlanRepairDTO.getModifyUserName());
            productionPlanRepair.setModifyTime(new Date());
            updateById(productionPlanRepair);
        }
        else {
            throw new ServiceException("设备维修计划不存在");
        }
    }

    /**
     * 删除
     * @param deleteProductionPlanRepairDTO
     */
    @Override
    public void delete(DeleteProductionPlanRepairDTO deleteProductionPlanRepairDTO) {
        ProductionPlanRepair productionPlanRepair = getBaseMapper().selectById(deleteProductionPlanRepairDTO.getId());
        if (null != productionPlanRepair) {
            if (DateUtil.compare(new Date(), productionPlanRepair.getPlanStartDate()) > 0 || productionPlanRepair.getPlanState() == 1) {
                throw new ServiceException("计划已经开始或完成，不能删除");
            }
            HumanresourceOrganization humanresourceOrganization = humanresourceOrganizationService.getById(productionPlanRepair.getDepartmentId());
            String repairTypeName = productionPlanRepair.getRepairTypeId() == 1 ? "大修" :
            productionPlanRepair.getRepairTypeId() == 2 ? "中修" :
            productionPlanRepair.getRepairTypeId() == 3 ? "小修" :
            productionPlanRepair.getRepairTypeId() == 4 ? "临修" :
            productionPlanRepair.getRepairTypeId() == 5 ? "设备消缺" :
            productionPlanRepair.getRepairTypeId() == 6 ? "工艺技改" :
            productionPlanRepair.getRepairTypeId() == 7 ? "零星修缮" :
            productionPlanRepair.getRepairTypeId() == 8 ? "环保项目" : "";
            StringBuilder content = new StringBuilder();
            content.append("删除：" + "[计划编号：").append(productionPlanRepair.getPlanCode()).append("],")
                    .append("[开始时间：").append(productionPlanRepair.getPlanStartDate()).append("],")
                    .append("[部门：").append(humanresourceOrganization.getOrganizationName()).append("]")
                    .append("[结束时间：").append(productionPlanRepair.getPlanEndDate()).append("]")
                    .append("[设备名称：").append(productionPlanRepair.getEquipmentName()).append("]")
                    .append("[主体责任人：").append(productionPlanRepair.getPersonMainLiableName()).append("]")
                    .append("[维修类型：").append(repairTypeName).append("]")
                    .append("[专业责任人：").append(productionPlanRepair.getPersonProfessionLiableName()).append("]")
                    .append("[维修内容：").append(productionPlanRepair.getRepairContent()).append("]")
                    .append("[影响产量：").append(productionPlanRepair.getIsAffectOutput() == 1 ? "影响" : "未影响").append("]")
                    .append("[预提醒天数：").append(productionPlanRepair.getRemindDays()).append("]");
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("生产计划->维修计划");
            insertChangeLogDTO.setContent(content.toString());
            insertChangeLogDTO.setCreateUserId(deleteProductionPlanRepairDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteProductionPlanRepairDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
            getBaseMapper().deleteById(productionPlanRepair);
        }
        else {
            throw new ServiceException("设备维修计划不存在或已被删除");
        }
    }

    /**
     * 完成操作
     * @param finishedProductionPlanRepairDTO
     */
    @Override
    public void completeOpa(FinishedProductionPlanRepairDTO finishedProductionPlanRepairDTO) {
        ProductionPlanRepair productionPlanRepair = baseMapper.selectById(finishedProductionPlanRepairDTO.getId());
        if (null != productionPlanRepair) {
            if (productionPlanRepair.getPlanState() == 1) {
                throw new ServiceException("计划已完成，不能操作");
            }
            productionPlanRepair.setPlanState(1);
            productionPlanRepair.setRealStartDate(DateUtil.parseDate(finishedProductionPlanRepairDTO.getRealStartDate()));
            productionPlanRepair.setRealEndDate(DateUtil.parseDate(finishedProductionPlanRepairDTO.getRealEndDate()));
            productionPlanRepair.setCompleteUserId(finishedProductionPlanRepairDTO.getCompleteUserId());
            productionPlanRepair.setCompleteUserName(finishedProductionPlanRepairDTO.getCompleteUserName());
            productionPlanRepair.setRemark(finishedProductionPlanRepairDTO.getRemark());
            baseMapper.updateById(productionPlanRepair);
        }
        else {
            throw new ServiceException("设备维修计划不存在");
        }
    }
}




