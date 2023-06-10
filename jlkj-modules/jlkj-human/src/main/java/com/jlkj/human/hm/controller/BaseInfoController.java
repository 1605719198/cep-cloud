package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.Baseinfo;
import com.jlkj.human.hm.dto.BaseInfoDTO;
import com.jlkj.human.hm.service.IBaseinfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-07 18:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/baseInfo")
public class BaseInfoController extends BaseController {

    private final IBaseinfoService baseinfoService;

    /**
     * 获取选单配置树
     */
    @Log(title = "选单配置树",businessType = BusinessType.OTHER)
    @Operation(summary = "选单配置树")
    @GetMapping("/tree")
    public Object getDepartmentTree() {
        List<Baseinfo> list = baseinfoService.getBaseInfoTreeList();
        return AjaxResult.success(list);
    }

    /**
     * 获取子节点查询列表
     */
    @Log(title = "获取子节点查询列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取子节点查询列表")
    @GetMapping("/list")
    public Object getChildrenList(BaseInfoDTO baseInfoDTO) {
        try {
            startPage();
            List<Baseinfo> list = baseinfoService.query()
                    .eq(StringUtils.isNotBlank(baseInfoDTO.getUuid()), "parent_id", baseInfoDTO.getUuid())
                    .orderByAsc("dic_no+1").list();
            if (list.isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", getDataTable(list));
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 添加选单数据
     */
    @Operation(summary = "添加选单数据")
    @PostMapping("/add")
    @Log(title = "添加选单数据", businessType = BusinessType.INSERT)
    public Object addBaseInfo(@RequestBody Baseinfo baseinfo) {
        try {
            baseinfo.setUpdateEmp(SecurityUtils.getNickName());
            List<Baseinfo> list = baseinfoService.lambdaQuery()
                    .eq(Baseinfo::getDicNo, baseinfo.getDicNo())
                    .eq(Baseinfo::getDicName, baseinfo.getDicName()).list();
            if (!list.isEmpty()) {
                return AjaxResult.error("数据已存在，不允许重复！！！");
            }
            boolean result = baseinfoService.save(baseinfo);
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 修改选单数据
     */
    @Operation(summary = "修改选单数据")
    @PostMapping("/update")
    @Log(title = "选单数据编辑", businessType = BusinessType.UPDATE)
    public Object updateBaseInfo(@RequestBody Baseinfo baseinfo) {
        try {
            boolean result = baseinfoService.lambdaUpdate()
                    .eq(StringUtils.isNotBlank(baseinfo.getUuid()), Baseinfo::getUuid, baseinfo.getUuid()).update();
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除选单数据
     */
    @Operation(summary = "删除选单数据")
    @DeleteMapping("/delete")
    @Log(title = "删除选单数据", businessType = BusinessType.DELETE)
    public Object deleteBaseInfo(@RequestParam String uuid) {
        try {
            List<Baseinfo> list = baseinfoService.lambdaQuery()
                    .eq(Baseinfo::getParentId, uuid).list();
            if (!list.isEmpty()) {
                return AjaxResult.error("存在子类选单资料，不可删除资料");
            }
            boolean delete = baseinfoService.lambdaUpdate().eq(Baseinfo::getUuid, uuid).remove();
            return AjaxResult.success(delete);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取专业下拉选单列表
     */
    @Log(title = "获取专业下拉选单列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取专业下拉选单列表")
    @GetMapping("/getDegreeMajor")
    public Object getDegreeMajor(BaseInfoDTO humanresourceBaseInfoDTO) {
        try {
            return baseinfoService.getDegreeMajor(humanresourceBaseInfoDTO);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取专业细分下拉选单列表
     */
    @Log(title = "获取专业细分下拉选单列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取专业细分下拉选单列表")
    @GetMapping("/getDegreeMajorSpecialization")
    public Object getDegreeMajorSpecialization(BaseInfoDTO baseInfoDTO) {
        try {
            List<Baseinfo> list = baseinfoService.lambdaQuery()
                    .eq(Baseinfo::getParentId, baseInfoDTO.getUuid()).list();
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取各公司主管查询下属薪资授权设定岗位类别下拉选单
     */
    @Log(title = "获取各公司主管查询下属薪资授权设定岗位类别下拉选单",businessType = BusinessType.OTHER)
    @Operation(summary = "获取各公司主管查询下属薪资授权设定岗位类别下拉选单")
    @GetMapping("/getPostTypeId")
    public Object getPostTypeId() {
        try {
            List<Baseinfo> list = baseinfoService.lambdaQuery()
                    .eq(Baseinfo::getParentId, "1635868462557556737").list();
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
