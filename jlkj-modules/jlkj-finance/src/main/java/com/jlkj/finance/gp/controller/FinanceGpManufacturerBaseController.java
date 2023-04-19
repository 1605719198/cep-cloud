package com.jlkj.finance.gp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jlkj.finance.gp.domain.FinanceGpManufacturerBase;
import com.jlkj.finance.gp.service.IFinanceGpManufacturerBaseService;
import com.jlkj.common.core.web.controller.BaseController;

import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 销售管理-厂商基本资料Controller
 *
 * @author 265799
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/finance/manufacturer")
public class FinanceGpManufacturerBaseController extends BaseController
{
    @Autowired
    private IFinanceGpManufacturerBaseService financeGpManufacturerBaseService;

    /**
     * 查询销售管理-厂商基本资料列表
     */
    @GetMapping("/list")
    public TableDataInfo list( FinanceGpManufacturerBase financeGpManufacturerBase)
    {
        startPage();
        List<FinanceGpManufacturerBase> list = financeGpManufacturerBaseService.selectFinanceGpManufacturerBaseList(financeGpManufacturerBase);
        return getDataTable(list);
    }
    /**
     * 弹窗查询厂商基本资料列表
     */
    @GetMapping("/listBaseSelect")
    public TableDataInfo listBaseSelect( FinanceGpManufacturerBase financeGpManufacturerBase)
    {
        startPage();
        List<FinanceGpManufacturerBase> list = financeGpManufacturerBaseService.selectFinanceGpManufacturerBaseListBaseSelect(financeGpManufacturerBase);
        return getDataTable(list);
    }

}
