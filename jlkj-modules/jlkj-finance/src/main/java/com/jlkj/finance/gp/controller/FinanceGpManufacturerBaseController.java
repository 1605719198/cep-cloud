package com.jlkj.finance.gp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.gp.domain.FinanceGpManufacturerBase;
import com.jlkj.finance.gp.service.IFinanceGpManufacturerBaseService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
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
    public TableDataInfo list(
            @RequestParam(defaultValue = "") String mainAreaId,
            @RequestParam(defaultValue = "") String detailAreaId,
            @RequestParam(defaultValue = "") String originalManufacturerChineseName,
                              @RequestParam(defaultValue = "") String startDate,
                              @RequestParam(defaultValue = "") String endDate)
    {
        startPage();
        List<FinanceGpManufacturerBase> list = financeGpManufacturerBaseService.selectFinanceGpManufacturerBaseList(mainAreaId,detailAreaId,originalManufacturerChineseName,
                startDate,endDate);
        return getDataTable(list);
    }


}
