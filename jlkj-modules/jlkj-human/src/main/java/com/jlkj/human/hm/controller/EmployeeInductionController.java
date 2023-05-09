package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
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
        List<ChangeMaster> list = changeMasterService.lambdaQuery().eq(StringUtils.isNotBlank(changeMaster.getCompId()), ChangeMaster::getCompId, changeMaster.getCompId())
                .eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo()).list();
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
        return changeMasterService.addEmployeeInduction(changeMasterDTO);
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
                    .set(Personnel::getPostLevelName, changeMasterDTO.getPostLevel())
                    .set(Personnel::getDepartmentName, changeMasterDTO.getDepartmentName())
                    .set(Personnel::getDepartmentId, changeMasterDTO.getDepartmentId())
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
