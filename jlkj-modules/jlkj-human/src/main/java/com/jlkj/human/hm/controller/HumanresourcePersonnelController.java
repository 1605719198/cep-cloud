package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourcePersonnelDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.config.PinyinAPI;
import com.jlkj.human.hm.domain.HumanresourcePersonnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.service.IHumanresourcePersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class HumanresourcePersonnelController {
    @Autowired
    private IHumanresourcePersonnelService humanresourcePersonnelService;
    /**
     * Mq新增人员资料
     */
    @Log(title = "新增人员资料",businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员资料")
    @PostMapping("/addPersonnelData")
    public void addPersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO, @RequestHeader("token") String token){
        humanresourcePersonnelService.saveData(humanresourcePersonnelDTO);
    }
    /**
     * Mq修改人员资料
     */
    @Log(title = "修改人员资料",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改人员资料")
    @PutMapping("/updatePersonnelData")
    public void updatePersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO,@RequestHeader("token") String token){
        humanresourcePersonnelService.updateData(humanresourcePersonnelDTO);
    }
    /**
     * Mq删除人员资料
     */
    @Log(title = "删除人员资料",businessType = BusinessType.DELETE)
    @Operation(summary = "删除人员资料")
    @DeleteMapping("/deletePersonnelData")
    public void deletePersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO,@RequestHeader("token") String token){
        HumanresourcePersonnel humanresourcePersonnel = new HumanresourcePersonnel();
        BeanUtils.copyProperties(humanresourcePersonnelDTO,humanresourcePersonnel);
        humanresourcePersonnelService.removeData(humanresourcePersonnelDTO);
    }

    /**
     * 新增人员基本信息
     */
    @Log(title = "新增人员基本信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员基本信息")
    @PostMapping("/addPersonnelBasicInfo")
    public Object addPersonnelBasicInfo(@RequestBody HumanresourcePersonnel humanresourcePersonnel) throws ParseException {
        // 根据姓名 取得大写首字母
        humanresourcePersonnel.setFullNamePinyin(PinyinAPI.getPinYinHeadChar(humanresourcePersonnel.getFullName()));
        Date birthTwoDate = new SimpleDateFormat("yyyyMMdd").parse(humanresourcePersonnel.getCertificateNumber().substring(6, 14));
        humanresourcePersonnel.setBirthTwoDate(birthTwoDate);
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
    @Log(title = "获取人员基本信息查询列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取人员基本信息查询列表")
    @GetMapping("/list")
    public Object getPersonnelBasicInfoList(HumanresourcePersonnel humanresourcePersonnel) {
        try {
            String compId = humanresourcePersonnel.getCompId();
            String empNo = humanresourcePersonnel.getEmpNo();
            LambdaQueryWrapper<HumanresourcePersonnel> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), HumanresourcePersonnel::getCompId, compId)
                        .eq(HumanresourcePersonnel::getEmpNo, empNo);
            List<HumanresourcePersonnel> list = humanresourcePersonnelService.list(queryWrapper);
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
            String compId = humanresourcePersonnelInfoDTO.getCompId();
            String empNo = humanresourcePersonnelInfoDTO.getEmpNo();
            Long pageNum = humanresourcePersonnelInfoDTO.getPageNum();
            Long pageSize = humanresourcePersonnelInfoDTO.getPageSize();
            LambdaQueryWrapper<HumanresourcePersonnel> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), HumanresourcePersonnel::getCompId, compId)
                        .eq(StringUtils.isNotBlank(empNo), HumanresourcePersonnel::getEmpNo, empNo);
            Page<HumanresourcePersonnel> page = humanresourcePersonnelService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            List<HumanresourcePersonnel> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("搜索成功,为您找到" + total + "笔资料", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
