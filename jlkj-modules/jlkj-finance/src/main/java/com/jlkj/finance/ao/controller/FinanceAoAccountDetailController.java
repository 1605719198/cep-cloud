package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoAccountDetail;
import com.jlkj.finance.ao.dto.FinanceAoAccountDto;
import com.jlkj.finance.ao.service.IFinanceAoAccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会计处理明细Controller
 * @Description:
 * @author 265799
 * @date 2023-06-25
 * @version:V1.0
 */
@RestController
@RequestMapping("/detailAccount")
public class FinanceAoAccountDetailController extends BaseController
{
    @Autowired
    private IFinanceAoAccountDetailService financeAoAccountDetailService;

    /**
     * 查询会计处理明细列表
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDetail
     * @return
     */
    @RequiresPermissions("ao:detailAccount:query")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoAccountDetail financeAoAccountDetail)
    {
        startPage();
        List<FinanceAoAccountDetail> list = financeAoAccountDetailService.selectFinanceAoAccountDetailList(financeAoAccountDetail);
        return getDataTable(list);
    }

    /**
     * 导出会计处理明细列表
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDetail
     * @return
     */
    @RequiresPermissions("ao:detailAccount:query")
    @Log(title = "会计处理明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoAccountDetail financeAoAccountDetail)
    {
        List<FinanceAoAccountDetail> list = financeAoAccountDetailService.selectFinanceAoAccountDetailList(financeAoAccountDetail);
        ExcelUtil<FinanceAoAccountDetail> util = new ExcelUtil<FinanceAoAccountDetail>(FinanceAoAccountDetail.class);
        util.exportExcel(response, list, "会计处理明细数据");
    }

    /**
     * 获取会计处理明细详细信息
     * @author 265799
     * @date 2023-06-25

     * @return
     */
    @RequiresPermissions("ao:detailAccount:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoAccountDetailService.selectFinanceAoAccountDetailById(id));
    }
    /**
     * 获取会计处理明细详细信息
     * @author 265799
     * @date 2023-06-25

     * @return
     */
    @RequiresPermissions("ao:detailAccount:query")
    @GetMapping(value = "/listLoanADetailAccount")
    public AjaxResult listLoanADetailAccount(FinanceAoAccountDetail financeAoAccountDetail)
    {
        return success(financeAoAccountDetailService.selectListLoanADetail(financeAoAccountDetail));
    }
    /**
     * 新增会计处理明细
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDto
     * @return
     */
    @RequiresPermissions("ao:detailAccount:query")
    @Log(title = "会计处理明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoAccountDto financeAoAccountDto)
    {
        return toAjax(financeAoAccountDetailService.insertFinanceAoAccountDetail(financeAoAccountDto));
    }

    /**
     * 修改会计处理明细
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDto
     * @return
     */
    @RequiresPermissions("ao:detailAccount:query")
    @Log(title = "会计处理明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoAccountDto financeAoAccountDto)
    {
        return toAjax(financeAoAccountDetailService.updateFinanceAoAccountDetail(financeAoAccountDto));
    }

    /**
     * 删除会计处理明细
     * @author 265799
     * @date 2023-06-25
     * @return
     */
    @RequiresPermissions("ao:detailAccount:query")
    @Log(title = "会计处理明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoAccountDetailService.deleteFinanceAoAccountDetailByIds(ids));
    }
}
