package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PersonnelDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.config.PinyinAPI;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.service.IPersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/personnel/base")
public class PersonnelController {
    @Autowired
    private IPersonnelService humanresourcePersonnelService;

    /**
     * Mq新增人员资料
     */
    @Log(title = "新增人员资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员资料")
    @PostMapping("/addPersonnelData")
    public void addPersonnelData(@RequestBody PersonnelDTO personnelDTO, @RequestHeader("token") String token) {
        humanresourcePersonnelService.saveData(personnelDTO);
    }

    /**
     * Mq修改人员资料
     */
    @Log(title = "修改人员资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改人员资料")
    @PutMapping("/updatePersonnelData")
    public void updatePersonnelData(@RequestBody PersonnelDTO personnelDTO, @RequestHeader("token") String token) {
        humanresourcePersonnelService.updateData(personnelDTO);
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
        humanresourcePersonnelService.removeData(personnelDTO);
    }

    /**
     * 新增人员基本信息
     */
    @Log(title = "新增人员基本信息", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员基本信息")
    @PostMapping("/addPersonnelBasicInfo")
    public Object addPersonnelBasicInfo(@RequestBody Personnel humanresourcePersonnel) {
        // 根据姓名 取得大写首字母
        humanresourcePersonnel.setFullNamePinyin(PinyinAPI.getPinYinHeadChar(humanresourcePersonnel.getFullName()));
        boolean result = humanresourcePersonnelService.saveOrUpdate(humanresourcePersonnel);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取人员基本信息查询列表
     */
    @Log(title = "获取人员基本信息查询列表", businessType = BusinessType.OTHER)
    @Operation(summary = "获取人员基本信息查询列表")
    @GetMapping("/list")
    public Object getPersonnelBasicInfoList(Personnel humanresourcePersonnel) {
        try {
            String compId = humanresourcePersonnel.getCompId();
            String empNo = humanresourcePersonnel.getEmpNo();
            LambdaQueryWrapper<Personnel> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), Personnel::getCompId, compId).eq(Personnel::getEmpNo, empNo);
            List<Personnel> list = humanresourcePersonnelService.list(queryWrapper);
            if (list.isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", list);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}