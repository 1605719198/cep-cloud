package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jlkj.common.core.utils.DateUtils;
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

import java.util.*;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-16 13:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employeeTurnover")
public class EmployeeTurnoverController extends BaseController {

    private final IChangeMasterService changeMasterService;

    private final IChangeDetailService changeDetailService;

    private final IPersonnelService personnelService;

    /**
     * 查询员工异动作业列表
     */
    @RequiresPermissions("human:employeeTurnover:list")
    @Log(title = "查询员工异动作业列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询员工异动作业列表")
    @GetMapping("/getList")
    public Object getEmployeeTurnoverList(ChangeMaster changeMaster) {
        List<ChangeMaster> list = changeMasterService.lambdaQuery().eq(StringUtils.isNotBlank(changeMaster.getCompId()), ChangeMaster::getCompId, changeMaster.getCompId())
                .eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo()).list();
        return AjaxResult.success("查询成功", list);
    }

    /**
     * 根据工号查询异动信息主表
     */
    @Log(title = "根据工号查询异动信息主表",businessType = BusinessType.OTHER)
    @Operation(summary = "根据工号查询异动信息主表")
    @GetMapping("/queryNewPostNameAndChangeDetail")
    public Object queryNewPostNameAndChangeDetail(ChangeMaster changeMaster) {
        LambdaQueryWrapper<ChangeMaster> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo())
                    .orderBy(true, false, ChangeMaster::getVersionNo)
                    .last("limit 1");
        List<ChangeMaster> list = changeMasterService.list(queryWrapper);
        if (list.isEmpty()) {
            return AjaxResult.error("查无资料");
        } else {
            LambdaQueryWrapper<ChangeDetail> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ChangeDetail::getParentId, list.get(0).getUuid())
                    .eq(ChangeDetail::getPostTypeId, "01");
            List<ChangeDetail> list1 = changeDetailService.list(queryWrapper1);
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("list", list);
            dataMap.put("list1", list1);
            return AjaxResult.success("查询成功", dataMap);
        }
   }

    /**
     * 添加员工异动信息作业
     */
    @RequiresPermissions("human:employeeTurnover:add")
    @Log(title = "添加员工异动信息作业", businessType = BusinessType.INSERT)
    @Operation(summary = "添加员工异动信息作业")
    @PostMapping("/addEmployeeTurnover")
    public Object addEmployeeTurnover(@RequestBody ChangeMasterDTO changeMasterDTO) {
        ChangeMaster changeMaster = new ChangeMaster();
        BeanUtils.copyProperties(changeMasterDTO, changeMaster);
        List<ChangeDetail> employeeTurnoverList = changeMasterDTO.getEmployeeTurnoverList();
        LambdaQueryWrapper<ChangeMaster> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo())
                .orderBy(true, false, ChangeMaster::getVersionNo)
                .last("limit 1");
        List<ChangeMaster> list = changeMasterService.list(queryWrapper);
        if (changeMasterDTO.getEffectDate().after(list.get(0).getEffectDate())) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 10);
            Date date = calendar.getTime();
            boolean compareResult = changeMasterDTO.getEffectDate().after(date);
            if (compareResult) {
                changeMaster.setUuid(IdUtils.fastSimpleUUID());
                changeMaster.setVersionNo(changeMasterDTO.getVersionNo() + 1);
                for (ChangeDetail item : employeeTurnoverList) {
                    item.setParentId(changeMaster.getUuid());
                    if ("01".equals(item.getPostTypeId())) {
                        changeMasterDTO.setPostId(item.getNewPostId());
                        changeMasterDTO.setPostName(item.getNewPostName());
                    }
                }
                LambdaQueryWrapper<ChangeDetail> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(ChangeDetail::getParentId, changeMaster.getUuid())
                        .eq(ChangeDetail::getPostTypeId, "01");
                List<ChangeDetail> list1 = changeDetailService.list(queryWrapper1);
                if (list1.size() > 1) {
                    return AjaxResult.error("只能存在一笔主岗位资料！！！");
                }
                changeMasterService.save(changeMaster);
                changeDetailService.saveOrUpdateBatch(employeeTurnoverList);
                if (changeMasterDTO.getEffectDate().after(new Date())) {
                    return AjaxResult.error("生效日期大于当前日期，不更新人员基本资料表");
                } else {
                    personnelService.lambdaUpdate()
                            .set(Personnel::getPostName, changeMasterDTO.getPostName())
                            .set(Personnel::getPostId, changeMasterDTO.getPostId())
                            .set(Personnel::getPostLevelName, changeMasterDTO.getPostLevel())
                            .set(Personnel::getDepartmentName, changeMasterDTO.getDepartmentName())
                            .set(Personnel::getDepartmentId, changeMasterDTO.getDepartmentId())
                            .eq(Personnel::getEmpNo, changeMaster.getEmpNo()).update();
                    return AjaxResult.success("保存成功");
                }
            } else {
                return AjaxResult.error("生效日期小于薪资日期");
            }
        } else {
            return AjaxResult.error("生效日期必须大于上一版本生效日期");
        }
    }

    /**
     * 修改员工异动信息作业
     */
    @RequiresPermissions("human:employeeTurnover:edit")
    @Operation(summary = "修改员工异动信息作业")
    @PostMapping("/updateEmployeeTurnover")
    @Log(title = "修改员工异动信息作业", businessType = BusinessType.UPDATE)
    public Object updateEmployeeTurnover(@RequestBody ChangeMasterDTO changeMasterDTO) {
        ChangeMaster changeMaster = new ChangeMaster();
        BeanUtils.copyProperties(changeMasterDTO, changeMaster);
        LambdaUpdateWrapper<ChangeMaster> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.lt(ChangeMaster::getCreateDate, new Date())
                .eq(ChangeMaster::getUuid, changeMasterDTO.getUuid());
        if (DateUtils.parseDate(DateUtils.getDate()).compareTo(changeMasterDTO.getEffectDate()) > 0) {
            return AjaxResult.error("当前日期超过生效日期，不允许修改");
        } else {
            boolean result = changeMasterService.saveOrUpdate(changeMaster, updateWrapper);
            for (ChangeDetail item : changeMasterDTO.getEmployeeTurnoverList()) {
                if (item.getOldPostName().isEmpty()){
                    item.setParentId(changeMaster.getUuid());
                } else {
                    return AjaxResult.error("异动前岗位资料不为空，不能删除");
                }

            }
            changeDetailService.lambdaUpdate().eq(ChangeDetail::getParentId, changeMasterDTO.getUuid()).remove();
            changeDetailService.saveBatch(changeMasterDTO.getEmployeeTurnoverList());
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
    }

    /**
     * 根据工号删除员工异动信息
     */
    @RequiresPermissions("human:employeeTurnover:remove")
    @Operation(summary = "根据工号删除员工异动信息")
    @DeleteMapping("/delEmployeeTurnover")
    @Log(title = "根据工号删除员工异动信息", businessType = BusinessType.DELETE)
    public Object delEmployeeTurnover(@RequestParam String uuid, String empNo) {
        LambdaQueryWrapper<ChangeMaster> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChangeMaster::getEmpNo, empNo)
                .orderBy(true, false, ChangeMaster::getVersionNo)
                .last("limit 1");
        List<ChangeMaster> list = changeMasterService.list(queryWrapper);
        if (DateUtils.parseDate(DateUtils.getDate()).compareTo(list.get(0).getEffectDate()) > 0) {
            return AjaxResult.error("当前日期超过生效日期，不允许删除");
        } else {
            if (uuid.equals(list.get(0).getUuid())) {
                boolean result = changeMasterService.lambdaUpdate().lt(ChangeMaster::getCreateDate, new Date()).eq(ChangeMaster::getUuid, uuid).remove();
                changeDetailService.lambdaUpdate().eq(ChangeDetail::getParentId, uuid).remove();
                if (result) {
                    personnelService.lambdaUpdate()
                            .set(Personnel::getPostName, null)
                            .set(Personnel::getPostId, null)
                            .set(Personnel::getDepartmentName, null)
                            .set(Personnel::getPostLevelName, null)
                            .eq(Personnel::getEmpNo, empNo).update();
                }
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("只可删除当前最大版次异动资料");
            }
        }
    }
}
