package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.Contract;
import com.jlkj.human.hm.service.IContractService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-28 13:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/laborContract")
public class LaborContractController extends BaseController {
    
    private final IContractService iContractService;

    /**
     * 查询劳动合同信息列表
     */
    @RequiresPermissions("human:employeeEncourage:list")
    @Log(title = "查询劳动合同信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询劳动合同信息列表")
    @GetMapping("/list")
    public Object listLaborContract(Contract contract) {
        startPage();
        List<Contract> list = iContractService.lambdaQuery()
                .eq(StringUtils.isNotBlank(contract.getCompId()), Contract::getCompId, contract.getCompId())
                .eq(Contract::getEmpNo, contract.getEmpNo()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 根据uuid查询劳动合同详细信息
     */
    @RequiresPermissions("human:employeeEncourage:query")
    @Operation(summary = "根据uuid查询劳动合同详细信息")
    @GetMapping("/{uuid}")
    @Log(title = "根据uuid查询劳动合同详细信息", businessType = BusinessType.OTHER)
    public Object getLaborContract(@PathVariable String uuid) {
        Contract contract = iContractService.lambdaQuery().eq(Contract::getUuid, uuid).one();
        return AjaxResult.success("查询成功", contract);
    }

    /**
     * 添加劳动合同信息
     */
    @RequiresPermissions("human:employeeEncourage:add")
    @Operation(summary = "添加劳动合同信息")
    @PostMapping
    @Log(title = "添加劳动合同信息", businessType = BusinessType.INSERT)
    public Object addLaborContract(@RequestBody Contract contract) {
        List<Contract> list = iContractService.lambdaQuery().eq(Contract::getEmpNo, contract.getEmpNo())
                .orderBy(true, false, Contract::getVersionNo)
                .last("limit 1")
                .list();
        if (list.isEmpty()) {
            contract.setVersionNo(1);
        } else {
            Integer versionNo = list.get(0).getVersionNo();
            contract.setVersionNo(versionNo + 1);
        }
        contract.setCreator(SecurityUtils.getNickName());
        iContractService.save(contract);
        return AjaxResult.success("新增成功");
    }

    /**
     * 修改劳动合同信息
     */
    @RequiresPermissions("human:employeeEncourage:edit")
    @Operation(summary = "修改劳动合同信息")
    @PutMapping
    @Log(title = "修改劳动合同信息", businessType = BusinessType.UPDATE)
    public Object updateLaborContract(@RequestBody Contract contract) {
        iContractService.updateById(contract);
        return AjaxResult.success("修改成功");
    }

    /**
     * 删除劳动合同信息
     */
    @RequiresPermissions("human:employeeEncourage:remove")
    @Operation(summary = "删除劳动合同信息")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除劳动合同信息", businessType = BusinessType.DELETE)
    public Object delLaborContract(@PathVariable String uuid) {
        iContractService.lambdaUpdate().eq(Contract::getUuid, uuid).remove();
        return AjaxResult.success("删除成功");
    }
}
