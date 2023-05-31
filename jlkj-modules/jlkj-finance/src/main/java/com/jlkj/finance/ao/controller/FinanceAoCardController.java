package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoCard;
import com.jlkj.finance.ao.service.IFinanceAoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 个人信息设置Controller
 *
 * @author SunXuTong
 * @date 2023-05-08
 */
@RestController
@RequestMapping("/card")
public class FinanceAoCardController extends BaseController
{
    @Autowired
    private IFinanceAoCardService financeAoCardService;


    /**
     * 获取个人信息设置详细信息
     */
    @RequiresPermissions("finance:card:query")
    @GetMapping(value = "/{userNo}")
    public AjaxResult getInfo(@PathVariable("userNo") String userNo)
    {
        return success(financeAoCardService.selectFinanceAoCardByUserNo(userNo));
    }

    /**
     * 新增个人信息设置
     */
    @RequiresPermissions("finance:card:add")
    @Log(title = "个人信息设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoCard financeAoCard)
    {
        FinanceAoCard result = financeAoCardService.selectFinanceAoCardByUserNo(financeAoCard.getUserNo());
        if (result.getUuid().isEmpty()) {
            return toAjax(financeAoCardService.insertFinanceAoCard(financeAoCard));
        } else {
            return toAjax(financeAoCardService.updateFinanceAoCard(financeAoCard));
        }

    }

}
