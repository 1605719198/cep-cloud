package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PersonnelDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.config.PinYinApi;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.service.IPersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 人员数据 前端控制器
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/personnel/base")
public class PersonnelController extends BaseController {
    private final IPersonnelService personnelService;

    /**
     * Mq新增人员资料
     */
    @Log(title = "新增人员资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员资料")
    @PostMapping("/addPersonnelData")
    public void addPersonnelData(@RequestBody PersonnelDTO personnelDTO, @RequestHeader("token") String token) {
        personnelService.saveData(personnelDTO);
    }

    /**
     * Mq修改人员资料
     */
    @Log(title = "修改人员资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改人员资料")
    @PutMapping("/updatePersonnelData")
    public void updatePersonnelData(@RequestBody PersonnelDTO personnelDTO, @RequestHeader("token") String token) {
        personnelService.updateData(personnelDTO);
    }

    /**
     * Mq删除人员资料
     */
    @Log(title = "删除人员资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除人员资料")
    @DeleteMapping("/deletePersonnelData")
    public void deletePersonnelData(@RequestBody PersonnelDTO personnelDTO, @RequestHeader("token") String token) {
        Personnel personnel = new Personnel();
        BeanUtils.copyProperties(personnelDTO, personnel);
        personnelService.removeData(personnelDTO);
    }

    /**
     * 新增人员基本信息
     */
    @RequiresPermissions("human:affairsBaseInfo:save")
    @Log(title = "新增人员基本信息", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员基本信息")
    @PostMapping("/addPersonnelBasicInfo")
    public Object addPersonnelBasicInfo(@RequestBody Personnel personnel) {
        // 根据姓名 取得大写首字母
        personnel.setFullNamePinyin(PinYinApi.getPinYinHeadChar(personnel.getFullName()));
        boolean result = personnelService.saveOrUpdate(personnel);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取人员基本信息查询列表
     */
    @RequiresPermissions("human:personnelBasicInfo:query")
    @Log(title = "获取人员基本信息查询列表", businessType = BusinessType.OTHER)
    @Operation(summary = "获取人员基本信息查询列表")
    @GetMapping("/list")
    public Object getPersonnelBasicInfoList(Personnel personnel) {
        try {
            String compId = personnel.getCompId();
            String empNo = personnel.getEmpNo();
            LambdaQueryWrapper<Personnel> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), Personnel::getCompId, compId).eq(Personnel::getEmpNo, empNo);
            List<Personnel> list = personnelService.list(queryWrapper);
            if (list.isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", list);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 人员基本信息弹窗分页列表查询
     */
    @Log(title = "人员基本信息弹窗分页列表查询",businessType = BusinessType.OTHER)
    @Operation(summary = "人员基本信息弹窗分页列表查询")
    @GetMapping("/getAllUserList")
    public Object getAllUserList(HumanresourcePersonnelInfoDTO humanresourcePersonnelInfoDTO) {
        try {
            startPage();
            String compId = humanresourcePersonnelInfoDTO.getCompId();
            String empNo = humanresourcePersonnelInfoDTO.getEmpNo();
            String departmentId = humanresourcePersonnelInfoDTO.getDepartmentId();
            LambdaQueryWrapper<Personnel> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), Personnel::getCompId, compId)
                    .eq(StringUtils.isNotBlank(empNo), Personnel::getEmpNo, empNo)
                    .eq(StringUtils.isNotBlank(departmentId), Personnel::getDepartmentId, departmentId);
            List<Personnel> list = personnelService.list(queryWrapper);
            return AjaxResult.success("查询成功", getDataTable(list));
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 职位人员明细列表查询
     */
    @Log(title = "职位人员明细列表查询",businessType = BusinessType.OTHER)
    @Operation(summary = "职位人员明细列表查询")
    @GetMapping("/getPostLevelEmployee")
    public Object getPostLevelEmployee(HumanresourcePersonnelInfoDTO humanresourcePersonnelInfoDTO) {
        try {
            startPage();
            String compId = humanresourcePersonnelInfoDTO.getCompId();
            String postId = humanresourcePersonnelInfoDTO.getPostId();
            LambdaQueryWrapper<Personnel> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), Personnel::getCompId, compId)
                    .eq(StringUtils.isNotBlank(postId), Personnel::getPostId, postId);
            List<Personnel> list = personnelService.list(queryWrapper);
            return AjaxResult.success("查询成功", getDataTable(list));
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
