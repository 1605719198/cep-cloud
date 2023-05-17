package com.jlkj.finance.aa.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.finance.aa.domain.FinanceAcctcodeGroup;
import com.jlkj.finance.aa.service.IFinanceAcctcodeGroupService;
import com.jlkj.finance.aa.service.IFinanceCaltypeService;
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
import com.jlkj.finance.aa.domain.FinanceAaAcctcodeCorp;
import com.jlkj.finance.aa.service.IFinanceAaAcctcodeCorpService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 会计科目-公司级Controller
 *
 * @author 116514
 * @date 2023-04-19
 */
@RestController
@RequestMapping("/acctcodeCorp")
public class FinanceAaAcctcodeCorpController extends BaseController
{
    @Autowired
    private IFinanceAaAcctcodeCorpService financeAaAcctcodeCorpService;
    @Autowired
    private IFinanceAcctcodeGroupService financeAcctcodeGroupService;
    @Autowired
    private IFinanceCaltypeService financeCaltypeService;
    /**
     * 查询会计科目-公司级列表
     */
    @RequiresPermissions("finance:acctcodeCorp:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        startPage();
        List<FinanceAaAcctcodeCorp> list = financeAaAcctcodeCorpService.selectFinanceAaAcctcodeCorpList(financeAaAcctcodeCorp);
        return getDataTable(list);
    }

    /**
     * 导出会计科目-公司级列表
     */
    @RequiresPermissions("finance:acctcodeCorp:export")
    @Log(title = "会计科目-公司级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        List<FinanceAaAcctcodeCorp> list = financeAaAcctcodeCorpService.selectFinanceAaAcctcodeCorpList(financeAaAcctcodeCorp);
        List<Map<String, String>> calTypes = financeCaltypeService.selectCalTypeList();
        Map<Object, Object> calTypeMap = calTypes.stream().collect(Collectors.toMap((Map value) -> value.get("value"), (Map label) -> label.get("label")));
        for (FinanceAaAcctcodeCorp aaAcctcodeCorp : list) {
            aaAcctcodeCorp.setCalTypeCodea((String)calTypeMap.get(aaAcctcodeCorp.getCalTypeCodea()));
            aaAcctcodeCorp.setCalTypeCodeb((String)calTypeMap.get(aaAcctcodeCorp.getCalTypeCodeb()));
            aaAcctcodeCorp.setCalTypeCodec((String)calTypeMap.get(aaAcctcodeCorp.getCalTypeCodec()));
            aaAcctcodeCorp.setCalTypeCoded((String)calTypeMap.get(aaAcctcodeCorp.getCalTypeCoded()));
        }
        ExcelUtil<FinanceAaAcctcodeCorp> util = new ExcelUtil<FinanceAaAcctcodeCorp>(FinanceAaAcctcodeCorp.class);
        util.exportExcel(response, list, "会计科目-公司级数据");
    }

    /**
     * 获取会计科目-公司级详细信息
     */
    @RequiresPermissions("finance:acctcodeCorp:query")
    @GetMapping(value = "/{groupAcctId}")
    public AjaxResult getInfo(@PathVariable("groupAcctId") String groupAcctId)
    {
        return success(financeAaAcctcodeCorpService.selectFinanceAaAcctcodeCorpByGroupAcctId(groupAcctId));
    }

    /**
     * 新增会计科目-公司级
     */
    @RequiresPermissions("finance:acctcodeCorp:add")
    @Log(title = "会计科目-公司级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        return toAjax(financeAaAcctcodeCorpService.insertFinanceAaAcctcodeCorp(financeAaAcctcodeCorp));
    }

    /**
     * 修改会计科目-公司级
     */
    @RequiresPermissions("finance:acctcodeCorp:edit")
    @Log(title = "会计科目-公司级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        return toAjax(financeAaAcctcodeCorpService.updateFinanceAaAcctcodeCorp(financeAaAcctcodeCorp));
    }

    /**
     * 删除会计科目-公司级
     */
    @RequiresPermissions("finance:acctcodeCorp:remove")
    @Log(title = "会计科目-公司级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupAcctIds}")
    public AjaxResult remove(@PathVariable String[] groupAcctIds)
    {
        return toAjax(financeAaAcctcodeCorpService.deleteFinanceAaAcctcodeCorpByGroupAcctIds(groupAcctIds));
    }

    /**
     * 获取部门树列表
     */
    @RequiresPermissions("finance:acctcodeCorp:list")
    @GetMapping("/codeTree")
    public AjaxResult codeTree(FinanceAaAcctcodeCorp codeCorp)
    {
        return success(financeAaAcctcodeCorpService.selectCodeTreeList(codeCorp));
    }


    /**
     * 查询集团级会计科目列表
     */
    @RequiresPermissions("finance:acctcodeCorp:list")
    @GetMapping(value = "/codeGroupList/{acctCode}/{companyId}")
    public TableDataInfo codeGroupList(@PathVariable("acctCode") String acctCode,@PathVariable("companyId") String companyId)
    {
        startPage();
        List<FinanceAcctcodeGroup> list = financeAcctcodeGroupService.selectCodeGroupList(acctCode,companyId);
        return getDataTable(list);
    }

    /**
     * 新增会计科目-公司级
     */
    @RequiresPermissions("finance:acctcodeCorp:add")
    @Log(title = "会计科目-公司级", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult addBatch(@RequestBody Map request)
    {
        return toAjax(financeAaAcctcodeCorpService.insertBatchFinanceAcctcodeCorp(request));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:acctcodeCorp:edit")
    @Log(title = "启用禁用-会计科目-集团级", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        return toAjax(financeAaAcctcodeCorpService.updateFinanceAaAcctcodeCorp(financeAaAcctcodeCorp));
    }

    /**
     * 查询会计科目-公司级列表
     */
    @RequiresPermissions("finance:acctcodeCorp:list")
    @GetMapping("/listPop")
    public TableDataInfo listPop(FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        startPage();
        List<FinanceAaAcctcodeCorp> list = financeAaAcctcodeCorpService.selectFinanceAaAcctcodeCorpListPop(financeAaAcctcodeCorp);
        return getDataTable(list);
    }
}
