package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.PayFormation;
import com.jlkj.human.hs.dto.PayFormationDTO;
import com.jlkj.human.hs.service.IPayFormationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-05-11 11:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payFormation")
public class PayFormationController extends BaseController {

    private final IPayFormationService iPayFormationService;

    /**
     * 查询薪酬项目列表
     */
    @RequiresPermissions("human:payFormation:list")
    @Log(title = "查询薪酬项目列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询薪酬项目列表")
    @GetMapping("/list")
    public Object listPayFormation(PayFormation payFormation) {
        startPage();
        List<PayFormation> list = iPayFormationService.lambdaQuery().eq(PayFormation::getCompId, payFormation.getCompId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存薪酬项目列表
     */
    @RequiresPermissions("human:payFormation:add")
    @Log(title = "保存薪酬项目列表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存薪酬项目列表")
    @PostMapping
    public Object addPayFormation(@RequestBody PayFormationDTO payFormationDTO) {
        for (PayFormation item : payFormationDTO.getPayFormationList()) {
            List<PayFormation> list = iPayFormationService.lambdaQuery()
                    .eq(PayFormation::getCompId, item.getCompId())
                    .eq(PayFormation::getPayProCode, item.getPayProCode()).list();
            if (StringUtils.isNotNull(list)){
                return AjaxResult.error("薪酬项目不能重复保存");
            }
            item.setCreator(SecurityUtils.getNickName());
            item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        }
        return iPayFormationService.saveOrUpdateBatch(payFormationDTO.getPayFormationList());
    }

    /**
     * 删除薪酬项目信息
     */
    @RequiresPermissions("human:payFormation:remove")
    @Operation(summary = "删除薪酬项目信息")
    @DeleteMapping("/delPayFormation")
    @Log(title = "删除薪酬项目信息", businessType = BusinessType.DELETE)
    public Object delPayFormation(@RequestParam List<String> uuid) {
        iPayFormationService.removeBatchByIds(uuid);
        return AjaxResult.success("删除成功");
    }

    /**
     * 查询薪酬项目列表接口
     */
    @GetMapping("/getListPayFormation")
    public List<PayFormation> getListPayFormation(PayFormation payFormation) {
        startPage();
        List<PayFormation> list = iPayFormationService.lambdaQuery()
                .eq(PayFormation::getCompId, payFormation.getCompId()).list();
        return list;
    }
}
