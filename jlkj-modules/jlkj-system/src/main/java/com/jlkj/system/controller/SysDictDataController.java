package com.jlkj.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.system.api.domain.SysDictData;
import com.jlkj.system.service.ISysDictDataService;
import com.jlkj.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典信息
 *
 * @author jlkj
 */
@RestController
@RequestMapping("/dict/data")
public class SysDictDataController extends BaseController
{
    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:list")
    @GetMapping("/list")
    public TableDataInfo list(SysDictData dictData)
    {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictData dictData)
    {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @RequiresPermissions("system:dict:query")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode)
    {
        return success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType)
    {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        return success(data);
    }

    /**
     * 新增字典类型
     */
    @RequiresPermissions("system:dict:add")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictData dict)
    {
        dict.setCreateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @RequiresPermissions("system:dict:edit")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictData dict)
    {
        dict.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes)
    {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }

    /**
     * 根据字典类型、字典键值查询数据===财务模块用
     * @param dictValue
     * @param dictType
     * @return
     */
    @GetMapping("/getLabelByDictValue")
    public Map<String, Object> getLabelByDictValue(@RequestParam(defaultValue = "")String dictType,
                                                   @RequestParam(defaultValue = "")String dictValue) {
        QueryWrapper<SysDictData> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type",dictType);
        String sysDictDataAccount = "";
        if(StringUtils.isNoneBlank(dictValue)){
            wrapper.eq("dict_value",dictValue);
            SysDictData sysDictData = dictDataService.getOne(wrapper);
            String sysDictDataValue = sysDictData.getDictValue() ;
            String sysDictDataLabel = sysDictData.getDictLabel();

            sysDictDataAccount = sysDictDataValue+"_"+sysDictDataLabel;
        }else {
            sysDictDataAccount = "";
        }

        Map<String,Object> returnMap = new HashMap<>(32);
        returnMap.put("sysDictDataAccount",sysDictDataAccount);

        return returnMap;
    }
    /**
     * 根据字典类型、字典键值查询数据===财务模块用
     * @param dictValue
     * @param dictType
     * @return
     */
    @GetMapping("/getMainAreaIdValue")
    public AjaxResult getMainAreaIdValue(@RequestParam(defaultValue = "")String dictType,
                                     @RequestParam(defaultValue = "")String dictValue) {
        QueryWrapper<SysDictData> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type",dictType);
        String field1 = "";
        if(StringUtils.isNoneBlank(dictValue)){
            wrapper.eq("dict_value",dictValue);
            SysDictData sysDictData = dictDataService.getOne(wrapper);
//            field1 = sysDictData.getField1();
        }
        return AjaxResult.success(field1);
    }
    /**
     * 根据字典类型查询数据===财务模块调用公司别
     * @param dictType
     * @return
     */
    @GetMapping("/getMainCompIdValue")
    public List<Map<String,Object>> getMainCompIdValue(@RequestParam(defaultValue = "")String dictType) {
        QueryWrapper<SysDictData> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type",dictType);
        List<Map<String,Object>> returnList = new ArrayList<>();
        List<SysDictData> list = dictDataService.list(wrapper);
        list.forEach(item -> {
            Map<String,Object> map = new HashMap<>(32);
            map.put("value",item.getDictLabel());
            returnList.add(map);
        });
        return returnList;

    }
}
