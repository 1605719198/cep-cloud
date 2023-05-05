package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hm.domain.ChangeDetail;
import com.jlkj.human.hm.domain.ChangeMaster;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.ChangeMasterDTO;
import com.jlkj.human.hm.service.IChangeDetailService;
import com.jlkj.human.hm.service.IChangeMasterService;
import com.jlkj.human.hm.service.IPersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-16 13:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employeeInduction")
public class EmployeeInductionController extends BaseController {

    private final IChangeMasterService changeMasterService;

    private final IChangeDetailService changeDetailService;

    private final IPersonnelService personnelService;

    /**
     * 查询员工入职作业列表
     */
    @RequiresPermissions("human:employeeInduction:list")
    @Log(title = "查询员工入职作业列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询员工入职作业列表")
    @GetMapping("/getList")
    public Object getEmployeeInductionList(ChangeMaster changeMaster) {
        LambdaQueryWrapper<ChangeMaster> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(changeMaster.getCompId()), ChangeMaster::getCompId, changeMaster.getCompId())
                    .eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo());
        List<ChangeMaster> list = changeMasterService.list(queryWrapper);
        return AjaxResult.success("查询成功", list);
   }

    /**
     * 添加员工入职作业
     */
    @RequiresPermissions("human:employeeInduction:add")
    @Log(title = "添加员工入职作业", businessType = BusinessType.INSERT)
    @Operation(summary = "添加员工入职作业")
    @PostMapping("/addEmployeeInduction")
    public Object addEmployeeInduction(@RequestBody ChangeMasterDTO changeMasterDTO) {
        ChangeMaster changeMaster = new ChangeMaster();
        BeanUtils.copyProperties(changeMasterDTO, changeMaster);
        List<ChangeDetail> employeeInductionList = changeMasterDTO.getEmployeeInductionList();
        Date effectDate = changeMaster.getEffectDate();
        changeMaster.setChangeTypeId("08");
        changeMaster.setVersionNo(1);
        changeMaster.setUuid(IdUtils.fastSimpleUUID());
        for (ChangeDetail item : employeeInductionList) {
            item.setParentId(changeMaster.getUuid());
            if ("01".equals(item.getPostTypeId())) {
                changeMasterDTO.setPostId(item.getNewPostId());
                changeMasterDTO.setPostName(item.getNewPostName());
            }
        }
        LambdaQueryWrapper<ChangeMaster> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChangeMaster::getEffectDate, effectDate)
                    .eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo());
        LambdaQueryWrapper<ChangeDetail> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(ChangeDetail::getParentId, changeMaster.getUuid())
                     .eq(ChangeDetail::getPostTypeId, "01");
        List<ChangeMaster> list = changeMasterService.list(queryWrapper);
        List<ChangeMaster> list2 = changeMasterService.lambdaQuery().eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo()).list();
        List<ChangeDetail> list1 = changeDetailService.list(queryWrapper1);
        if (!list2.isEmpty()) {
            return AjaxResult.error("入职资料已存在");
        }
        if (list1.size() > 1) {
            return AjaxResult.error("只能存在一笔主岗位资料！！！");
        }
        if (!list.isEmpty()) {
            return AjaxResult.error("员工同一生效日期已存在异动信息，新增失败！");
        }
        boolean result = changeMasterService.save(changeMaster);
        changeDetailService.saveOrUpdateBatch(employeeInductionList);
        if (result) {
            personnelService.lambdaUpdate()
                    .set(Personnel::getPostName, changeMasterDTO.getPostName())
                    .set(Personnel::getPostId, changeMasterDTO.getPostId())
                    .set(Personnel::getPostLevelName, changeMasterDTO.getPostLevel())
                    .set(Personnel::getDepartmentName, changeMasterDTO.getDepartmentName())
                    .eq(Personnel::getEmpNo, changeMaster.getEmpNo()).update();
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error("保存失败");
        }
    }

    /**
     * 根据工号查询员工入职详细信息
     */
    @RequiresPermissions("human:employeeInduction:query")
    @Operation(summary = "根据工号查询员工入职详细信息")
    @GetMapping("/queryEmployeeInductionByUuid")
    @Log(title = "根据工号查询员工入职详细信息", businessType = BusinessType.OTHER)
    public Object queryEmployeeInductionByUuid(@RequestParam String uuid) {
        List<ChangeMaster> list = changeMasterService.query().eq("uuid", uuid).list();
        List<ChangeDetail> employeeInductionList = changeDetailService.query().eq("parent_id", uuid).list();
        Map<String,Object> dataMap = new HashMap<>(16);
        dataMap.put("list", list);
        dataMap.put("employeeInductionList", employeeInductionList);
        return AjaxResult.success("查询成功", dataMap);
    }

    /**
     * 根据工号删除员工入职信息
     */
    @RequiresPermissions("human:employeeInduction:remove")
    @Operation(summary = "根据工号删除员工入职信息")
    @DeleteMapping("/delEmployeeInduction")
    @Log(title = "根据工号删除员工入职信息", businessType = BusinessType.DELETE)
    public Object delEmployeeInduction(@RequestParam String uuid, String empNo) {
        List<ChangeDetail> list = changeDetailService.lambdaQuery().eq(ChangeDetail::getParentId, uuid).list();
        if (!list.isEmpty()) {
            return AjaxResult.error("入职信息存在岗位信息，不可删除！");
        }
        boolean result = changeMasterService.lambdaUpdate().lt(ChangeMaster::getCreateDate, new Date()).eq(ChangeMaster::getUuid, uuid).remove();
        if (result) {
            personnelService.lambdaUpdate()
                    .set(Personnel::getPostName, null)
                    .set(Personnel::getPostId, null)
                    .set(Personnel::getDepartmentName, null)
                    .eq(Personnel::getEmpNo, empNo).update();
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 修改员工入职作业数据
     */
    @RequiresPermissions("human:employeeInduction:edit")
    @Operation(summary = "修改员工入职作业数据")
    @PostMapping("/updateEmployeeInduction")
    @Log(title = "修改员工入职作业数据", businessType = BusinessType.UPDATE)
    public Object updateEmployeeInduction(@RequestBody ChangeMasterDTO changeMasterDTO) {
        ChangeMaster changeMaster = new ChangeMaster();
        BeanUtils.copyProperties(changeMasterDTO, changeMaster);
        LambdaUpdateWrapper<ChangeMaster> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.lt(ChangeMaster::getCreateDate, new Date())
                     .eq(ChangeMaster::getUuid, changeMasterDTO.getUuid());
        boolean result = changeMasterService.saveOrUpdate(changeMaster, updateWrapper);
        for (ChangeDetail item : changeMasterDTO.getEmployeeInductionList()) {
            item.setParentId(changeMaster.getUuid());
        }
        changeDetailService.saveOrUpdateBatch(changeMasterDTO.getEmployeeInductionList());
        if (result) {
            personnelService.lambdaUpdate()
                    .set(Personnel::getPostName, changeMasterDTO.getPostName())
                    .set(Personnel::getPostId, changeMasterDTO.getPostId())
                    .set(Personnel::getDepartmentName, changeMasterDTO.getDepartmentName())
                    .eq(Personnel::getEmpNo, changeMasterDTO.getEmpNo()).update();
        }
        return AjaxResult.success("修改成功");
    }

    /**
     * 根据uuid删除员工入职信息明细
     */
    @Operation(summary = "根据uuid删除员工入职信息明细")
    @DeleteMapping("/delEmployeeInductionDetail")
    @Log(title = "根据uuid删除员工入职信息明细", businessType = BusinessType.DELETE)
    public Object delEmployeeInductionDetail(@RequestParam List<String> uuid) {
        changeDetailService.removeBatchByIds(uuid);
        return AjaxResult.success("删除成功");
    }
}
