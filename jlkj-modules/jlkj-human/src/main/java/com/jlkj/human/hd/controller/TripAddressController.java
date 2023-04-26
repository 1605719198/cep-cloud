package com.jlkj.human.hd.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.human.hd.dto.TripAddressDTO;
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
import com.jlkj.human.hd.domain.TripAddress;
import com.jlkj.human.hd.service.ITripAddressService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 出差地点记录Controller
 *
 * @author 266861
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/tripAddressRule")
public class TripAddressController extends BaseController
{
    @Autowired
    private ITripAddressService tripAddressService;

    /**
     * 查询出差地点记录列表
     */
    @RequiresPermissions("human:tripAddressRule:list")
    @GetMapping("/list")
    public TableDataInfo list(TripAddress tripAddress)
    {
        startPage();
        List<TripAddress> list = tripAddressService.selectTripAddressList(tripAddress);
        return getDataTable(list);
    }

    /**
     * 查询出差地点
     */
    @GetMapping("/listTripAddress")
    public TableDataInfo listTripAddress(TripAddressDTO tripAddressDTO)
    {
        List<TripAddressDTO> list = tripAddressService.selectTripAddress(tripAddressDTO);
        return getDataTable(list);
    }

    /**
     * 导出出差地点记录列表
     */
    @RequiresPermissions("human:tripAddressRule:export")
    @Log(title = "出差地点记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TripAddress tripAddress)
    {
        List<TripAddress> list = tripAddressService.selectTripAddressList(tripAddress);
        ExcelUtil<TripAddress> util = new ExcelUtil<TripAddress>(TripAddress.class);
        util.exportExcel(response, list, "出差地点记录数据");
    }

    /**
     * 获取出差地点记录详细信息
     */
    @RequiresPermissions("human:tripAddressRule:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(tripAddressService.selectTripAddressById(id));
    }

    /**
     * 新增出差地点记录
     */
    @RequiresPermissions("human:tripAddressRule:add")
    @Log(title = "出差地点记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TripAddress tripAddress)
    {
        return toAjax(tripAddressService.insertTripAddress(tripAddress));
    }

    /**
     * 修改出差地点记录
     */
    @RequiresPermissions("human:tripAddressRule:edit")
    @Log(title = "出差地点记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TripAddress tripAddress)
    {
        return toAjax(tripAddressService.updateTripAddress(tripAddress));
    }

    /**
     * 删除出差地点记录
     */
    @RequiresPermissions("human:tripAddressRule:remove")
    @Log(title = "出差地点记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tripAddressService.deleteTripAddressByIds(ids));
    }
}
