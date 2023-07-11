package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.IncomeTax;
import com.jlkj.human.hs.service.IIncomeTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 各公司所得税起征点设定Controller
 *
 * @author 116519
 * @date 2023-05-16
 */
@RestController
@RequestMapping("/incomeTax")
public class IncomeTaxController extends BaseController
{
    @Autowired
    private IIncomeTaxService incomeTaxService;
    /**
     * 查询各公司所得税起征点设定列表
     * @param incomeTax
     * @return
     */
    @RequiresPermissions("human:incomeTax:list")
    @GetMapping("/list")
    public TableDataInfo list(IncomeTax incomeTax)
    {
        startPage();
        List<IncomeTax> list = incomeTaxService.selectIncomeTaxList(incomeTax);
        return getDataTable(list);
    }

    /**
    * @Description
    * @Param 导出各公司所得税起征点设定列表
    * @return
    * @Author 你的名字或工号
    * @Date 2023-06-26 15:41
    **/
    @RequiresPermissions("human:incomeTax:export")
    @Log(title = "各公司所得税起征点设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IncomeTax incomeTax)
    {
        List<IncomeTax> list = incomeTaxService.selectIncomeTaxList(incomeTax);
        ExcelUtil<IncomeTax> util = new ExcelUtil<IncomeTax>(IncomeTax.class);
        util.exportExcel(response, list, "各公司所得税起征点设定数据");
    }

    /**
     * 获取各公司所得税起征点设定详细信息
     */
    @RequiresPermissions("human:incomeTax:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(incomeTaxService.selectIncomeTaxByUuid(uuid));
    }

    /**
     * 新增各公司所得税起征点设定
     */
    @RequiresPermissions("human:incomeTax:add")
    @Log(title = "各公司所得税起征点设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IncomeTax incomeTax)
    {
        int count =incomeTaxService.insertIncomeTax(incomeTax);
        if(count>0){
            return success("新增成功");
        }else{
            return error("新增失败");
        }
    }

    /**
     * 修改各公司所得税起征点设定
     */
    @RequiresPermissions("human:incomeTax:edit")
    @Log(title = "各公司所得税起征点设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IncomeTax incomeTax)
    {
        return toAjax(incomeTaxService.updateIncomeTax(incomeTax));
    }

    /**
     * 删除各公司所得税起征点设定
     */
    @RequiresPermissions("human:incomeTax:remove")
    @Log(title = "各公司所得税起征点设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(incomeTaxService.deleteIncomeTaxByUuids(uuids));
    }


    /**
     * 获取版本列表
     */
    @GetMapping("/selectVersion/{compId}")
    public AjaxResult selectVersion(@PathVariable String compId)
    {
        return AjaxResult.success(incomeTaxService.getVersionList(compId));
    }
}
