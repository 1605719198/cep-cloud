package com.jlkj.energy.ee.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.DictData;
import com.jlkj.energy.ee.service.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 参数配置
 * </p>
 *
 * @author jlkj
 * @since 2022-11-16
 */
@Slf4j
@RestController
@RequestMapping("/energy/dict/data")
public class DictDataController {
    @Autowired
    private DictDataService dictDataService;
    /**
     * 分页查询、根据条件查询信息
     */
    @Operation(summary = "分页查询、根据条件查询字典信息，名称" )
    @GetMapping("/queryDataByParams")
    @Log(title = "物流参数分页查询", businessType = BusinessType.OTHER)
    public Object queryDictByParams(@RequestParam Long current,
                                    @RequestParam Long limit,
                                    @RequestParam(defaultValue = "") String dictType,
                                    @RequestParam(defaultValue = "") String dictLabel) {
        if (StringUtils.isNull(current) || StringUtils.isNull(limit)) {
            current = 1L;
            limit = 10L;
        }
        //创建page对象
        Page<DictData> pageData = new Page<>(current, limit);
        //构造条件
        QueryWrapper<DictData> wrapper = new QueryWrapper<>();
        //条件组合查询
        wrapper.eq(StringUtils.isNotEmpty(dictType),"dict_type",dictType)
                .like(StringUtils.isNotEmpty(dictLabel),"dict_label",dictLabel);
        //实现分页查询
        dictDataService.page(pageData,wrapper);
        //总记录数
        long total = pageData.getTotal();
        //数据list集合
        List<DictData> records = pageData.getRecords();
        Map<String,Object> dataMap = new HashMap<>(16);
        dataMap.put("total",total);
        dataMap.put("list",records);
        return AjaxResult.success(dataMap);
    }

    /**
     * 根据id查询要修改的数据
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询数据" )
    @GetMapping("/getDataById/{id}")
    @Log(title = "根据id查询数据", businessType = BusinessType.OTHER)
    public AjaxResult getDataById(@PathVariable Long id) {
        DictData data = dictDataService.getById(id);
        return AjaxResult.success(data);
    }

    /**
     * 新增物流参数
     * @param data
     * @return
     */
    @Operation(summary = "新增物流参数" )
    @PostMapping("/insert")
    @Log(title = "新增物流参数", businessType = BusinessType.INSERT)
    public AjaxResult insertData(@RequestBody DictData data) {
        return AjaxResult.success(dictDataService.save(data));
    }

    /**
     * 修改物流参数
     * @param data
     * @return
     */
    @Operation(summary = "修改物流参数" )
    @PutMapping("/update")
    @Log(title = "修改物流参数", businessType = BusinessType.UPDATE)
    public AjaxResult updateData(@RequestBody DictData data) {
        return AjaxResult.success(dictDataService.updateById(data));
    }

    /**
     * 删除物流参数
     * @param ids
     * @return
     */
    @Operation(summary = "删除物流参数" )
    @DeleteMapping("/delete/{ids}")
    @Log(title = "删除物流参数", businessType = BusinessType.DELETE)
    public AjaxResult delData(@PathVariable Long[] ids) {
        return AjaxResult.success(dictDataService.removeBatchByIds(Arrays.asList(ids)));
    }
    /**
     * 查询字典类型列表
     */
    @Operation(summary = "查询字典类型列表" )
    @GetMapping("/getDictOptionselect")
    @Log(title = "物流查询字典类型列表", businessType = BusinessType.OTHER)
    public Object queryDictByParams() {
        QueryWrapper<DictData> wrapper = new QueryWrapper<>();
        wrapper.select(" DISTINCT dict_type ");
        List<DictData> list = dictDataService.list(wrapper);
        List<JSONObject> resultList = new ArrayList<>();
        list.forEach(item -> {
            JSONObject json = new JSONObject();
            json.put("value",item.getDictType());
            json.put("label",item.getDictType());
            resultList.add(json);
        });
        return AjaxResult.success(resultList);
    }
}
