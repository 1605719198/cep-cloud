package com.jlkj.human.hd.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.Attendencebasis;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hd.dto.OptionTypeDTO;
import com.jlkj.human.hd.service.IAttendencebasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工出勤基本资料维护Controller
 *
 * @author 266861
 * @date 2023-03-20
 */
@RestController
@RequestMapping("/attendenceBasis")
public class AttendencebasisController extends BaseController {
    @Autowired
    private IAttendencebasisService attendencebasisService;

    /**
     * 查询员工出勤基本资料维护列表
     */
    @RequiresPermissions("human:attendenceBasis:list")
    @GetMapping("/list")
    public TableDataInfo list(Attendencebasis attendencebasis) {
        startPage();
        List<Attendencebasis> list = attendencebasisService.selectAttendencebasisList(attendencebasis);
        return getDataTable(list);
    }


    /**
     * 获取员工出勤基本资料维护详细信息
     */
    @RequiresPermissions("human:attendenceBasis:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(attendencebasisService.selectAttendencebasisById(id));
    }

    /**
     * 新增员工出勤基本资料维护
     */
    @RequiresPermissions("human:attendenceBasis:add")
    @Log(title = "员工出勤基本资料维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Attendencebasis attendencebasis) throws Exception {
        return toAjax(attendencebasisService.insertAttendencebasis(attendencebasis));
    }

    /**
     * 修改员工出勤基本资料维护
     */
    @RequiresPermissions("human:attendenceBasis:edit")
    @Log(title = "员工出勤基本资料维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Attendencebasis attendencebasis) throws Exception {
        return toAjax(attendencebasisService.updateAttendencebasis(attendencebasis));
    }

    /**
     * 删除员工出勤基本资料维护
     */
    @RequiresPermissions("human:attendenceBasis:remove")
    @Log(title = "员工出勤基本资料维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) throws Exception {
        return toAjax(attendencebasisService.deleteAttendencebasisById(id));
    }

    /**
     * 获取出勤设定下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(Attendencebasis attendencebasis) {
        List<Attendencebasis> attendencebasiss = attendencebasisService.selectAttendencebasisList(attendencebasis);
        return AjaxResult.success(attendencebasisService.buildAttendenceTreeSelect(attendencebasiss));
    }


    /**
     * 获取出勤作业下拉选单列表
     */
    @GetMapping(value = "/getBasisOptions")
    public Object getBasisOptions(OptionTypeDTO optionType) {

        return AjaxResult.success(attendencebasisService.getBasisOptions(optionType));
    }

    /**
     * 获取出勤作业下拉选单细项
     */
    @GetMapping(value = "/getDeepOptions")
    public AjaxResult getDeepOptions(OptionTypeDTO optionType) {
        List<BasisOptionsDTO> options = attendencebasisService.getDeepOptions(optionType);
        return AjaxResult.success(options);
    }
}
