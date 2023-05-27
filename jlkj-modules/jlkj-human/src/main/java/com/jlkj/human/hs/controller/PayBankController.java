package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.PayBank;
import com.jlkj.human.hs.dto.PayBankDTO;
import com.jlkj.human.hs.service.IPayBankService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuangBing
 * @Description
 * @create 2023-05-11 11:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payBank")
public class PayBankController extends BaseController {

    private final IPayBankService iPayBankService;

    /**
     * 查询各公司薪资薪资支付银行维护列表
     */
    @RequiresPermissions("human:payBank:list")
    @Log(title = "查询各公司薪资薪资支付银行维护列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询各公司薪资薪资支付银行维护列表")
    @GetMapping("/list")
    public Object listPayBank(PayBank payBank) {
        startPage();
        List<PayBank> list = iPayBankService.lambdaQuery()
                .eq(PayBank::getCompId, payBank.getCompId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存各公司薪资薪资支付银行维护列表
     */
    @RequiresPermissions("human:payBank:add")
    @Log(title = "保存各公司薪资薪资支付银行维护列表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存各公司薪资薪资支付银行维护列表")
    @PostMapping
    public Object addPayBank(@RequestBody PayBankDTO payBankDTO) {
        for (PayBank item : payBankDTO.getPayBankList()) {
            item.setCreator(SecurityUtils.getNickName());
            item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        }
        return iPayBankService.saveOrUpdateBatch(payBankDTO.getPayBankList().stream().filter(item -> null != item.getBankCode() && null != item.getBankName()).filter(item -> !item.getBankCode().isEmpty() && !item.getBankName().isEmpty()).collect(Collectors.toList()));
    }


    /**
     * 删除各公司薪资薪资支付银行维护信息
     */
    @RequiresPermissions("human:payBank:remove")
    @Operation(summary = "删除各公司薪资薪资支付银行维护信息")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除各公司薪资薪资支付银行维护信息", businessType = BusinessType.DELETE)
    public Object delPayBank(@PathVariable String uuid) {
        iPayBankService.lambdaUpdate().eq(PayBank::getUuid, uuid).remove();
        return AjaxResult.success("删除成功");
    }
}
