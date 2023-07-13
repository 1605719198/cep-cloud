package com.jlkj.finance.ft.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtCard;
import com.jlkj.finance.ft.service.IFinanceFtCardService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 固定资产卡片主档Controller
 * @Description:
 * @author 116524
 * @date 2023-07-10
 * @version:V1.0
 */
@RestController
@RequestMapping("/ft/card")
public class FinanceFtCardController extends BaseController
{
    @Autowired
    private IFinanceFtCardService financeFtCardService;

    /**
     * 查询固定资产卡片主档列表
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard
     * @return
     */
    @RequiresPermissions("finance:ft:card:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtCard financeFtCard)
    {
        startPage();
        List<FinanceFtCard> list = financeFtCardService.selectFinanceFtCardList(financeFtCard);
        return getDataTable(list);
    }

    /**
     * 导出固定资产卡片主档列表
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard
     * @return
     */
    @RequiresPermissions("finance:ft:card:export")
    @Log(title = "固定资产卡片主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtCard financeFtCard)
    {
        List<FinanceFtCard> list = financeFtCardService.selectFinanceFtCardList(financeFtCard);
        ExcelUtil<FinanceFtCard> util = new ExcelUtil<FinanceFtCard>(FinanceFtCard.class);
        util.exportExcel(response, list, "固定资产卡片主档数据");
    }

    /**
     * 获取固定资产卡片主档详细信息
     * @author 116524
     * @date 2023-07-10
     * @param assetId
     * @return
     */
    @RequiresPermissions("finance:ft:card:query")
    @GetMapping(value = "/{assetId}")
    public AjaxResult getInfo(@PathVariable("assetId") String assetId)
    {
        return success(financeFtCardService.selectFinanceFtCardByAssetId(assetId));
    }

    /**
     * 新增固定资产卡片主档
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard
     * @return
     */
    @RequiresPermissions("finance:ft:card:add")
    @Log(title = "固定资产卡片主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtCard financeFtCard)
    {
        return toAjax(financeFtCardService.insertFinanceFtCard(financeFtCard));
    }

    /**
     * 修改固定资产卡片主档
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard
     * @return
     */
    @RequiresPermissions("finance:ft:card:edit")
    @Log(title = "固定资产卡片主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtCard financeFtCard)
    {
        return toAjax(financeFtCardService.updateFinanceFtCard(financeFtCard));
    }

    /**
     * 删除固定资产卡片主档
     * @author 116524
     * @date 2023-07-10
     * @param assetIds
     * @return
     */
    @RequiresPermissions("finance:ft:card:remove")
    @Log(title = "固定资产卡片主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{assetIds}")
    public AjaxResult remove(@PathVariable String[] assetIds)
    {
        return toAjax(financeFtCardService.deleteFinanceFtCardByAssetIds(assetIds));
    }

    /**
     * 查询固定资产卡片主档列表
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard
     * @return
     */

    @GetMapping("/selectCardList")
    public TableDataInfo selectCardList(FinanceFtCard financeFtCard)
    {
        startPage();
        List<FinanceFtCard> list = financeFtCardService.selectCardListForPop(financeFtCard);
        return getDataTable(list);
    }
}
