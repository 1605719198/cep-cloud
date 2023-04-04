package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.HumanresourceOrganization;
import com.jlkj.product.oi.domain.ProductionPlanRepair;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplanrepair.AddProductionPlanRepairDTO;
import com.jlkj.product.oi.dto.productionplanrepair.PageProductionPlanRepairDTO;
import com.jlkj.product.oi.dto.productionplanrepair.UpdateProductionPlanRepairDTO;
import com.jlkj.product.oi.mapper.ProductionPlanRepairMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.HumanresourceOrganizationService;
import com.jlkj.product.oi.service.ProductionPlanRepairService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_repair(计划管理--维修计划)】的数据库操作Service实现
* @createDate 2022-04-28 13:43:40
*/
@Service
public class ProductionPlanRepairServiceImpl extends ServiceImpl<ProductionPlanRepairMapper, ProductionPlanRepair>
    implements ProductionPlanRepairService {

    @Resource
    ChangeLogService changeLogService;

    @Resource
    HumanresourceOrganizationService humanresourceOrganizationService;

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

    public Object insert(AddProductionPlanRepairDTO addProductionPlanRepairDTO) {
        ProductionPlanRepair productionPlanRepair = new ProductionPlanRepair();
        productionPlanRepair.setId(IdUtil.randomUUID());
        productionPlanRepair.setPlanCode(addProductionPlanRepairDTO.getPlanCode());
        productionPlanRepair.setDepartmentId(addProductionPlanRepairDTO.getDepartmentId());
        productionPlanRepair.setRepairTypeId(addProductionPlanRepairDTO.getRepairTypeId());
        productionPlanRepair.setPersonMainLiableId(addProductionPlanRepairDTO.getPersonMainLiableId());
        productionPlanRepair.setPersonMainLiableName(addProductionPlanRepairDTO.getPersonMainLiableName());
        productionPlanRepair.setPersonProfessionLiableId(addProductionPlanRepairDTO.getPersonProfessionLiableId());
        productionPlanRepair.setPersonProfessionLiableName(addProductionPlanRepairDTO.getPersonProfessionLiableName());
        productionPlanRepair.setEquipmentNumber(addProductionPlanRepairDTO.getEquipmentNumber());
        productionPlanRepair.setEquipmentName(addProductionPlanRepairDTO.getEquipmentName());
        productionPlanRepair.setPlanStartDate(DateUtil.parseDate(addProductionPlanRepairDTO.getPlanStartDate()));
        productionPlanRepair.setPlanEndDate(DateUtil.parseDate(addProductionPlanRepairDTO.getPlanEndDate()));
        productionPlanRepair.setRepairContent(addProductionPlanRepairDTO.getRepairContent());
        productionPlanRepair.setIsAffectOutput(addProductionPlanRepairDTO.getIsAffectOutput());
        productionPlanRepair.setRemindDays(addProductionPlanRepairDTO.getRemindDays());
        productionPlanRepair.setPlanState(0);
        productionPlanRepair.setCreateUserId(addProductionPlanRepairDTO.getCreateUserId());
        productionPlanRepair.setCreateUserName(addProductionPlanRepairDTO.getCreateUserName());
        productionPlanRepair.setCreateTime(new Date());
        productionPlanRepair.setModifyUserId(addProductionPlanRepairDTO.getCreateUserId());
        productionPlanRepair.setModifyUserName(addProductionPlanRepairDTO.getCreateUserName());
        productionPlanRepair.setModifyTime(new Date());
        save(productionPlanRepair);
        saveNewLog(addProductionPlanRepairDTO);
        return AjaxResult.success("设备维修计划增加成功");
    }

    public Object update(@Valid @RequestBody UpdateProductionPlanRepairDTO updateProductionPlanRepairDTO) {
        ProductionPlanRepair productionPlanRepair = getById(updateProductionPlanRepairDTO.getId());
        if (null != productionPlanRepair) {
            if (DateUtil.compare(new Date(), productionPlanRepair.getPlanStartDate()) > 0 || productionPlanRepair.getPlanState() == 1) {
                return AjaxResult.error("计划已经开始或完成，不能修改");
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
            return AjaxResult.success("设备维修计划修改成功");
        }
        else {
            return AjaxResult.error("设备维修计划不存在");
        }
    }
}




