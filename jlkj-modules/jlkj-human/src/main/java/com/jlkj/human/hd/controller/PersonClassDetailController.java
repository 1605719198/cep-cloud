package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hd.domain.PersonClassDetail;
import com.jlkj.human.hd.dto.ChangeClassDetailDTO;
import com.jlkj.human.hd.service.IPersonClassDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 人员排班明细Controller
 *
 * @author 266861
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/personClassDetail")
public class PersonClassDetailController extends BaseController
{
    @Autowired
    private IPersonClassDetailService personClassDetailService;

    /**
     * 查询人员排班明细列表
     */
    @GetMapping("/list")
    public TableDataInfo list(PersonClassDetail personClassDetail)
    {
        List<PersonClassDetail> list = personClassDetailService.selectPersonClassDetailList(personClassDetail);
        return getDataTable(list);
    }

    /**
     * 导出人员排班明细列表
     */
    @Log(title = "人员排班明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonClassDetail personClassDetail)
    {
        List<PersonClassDetail> list = personClassDetailService.selectPersonClassDetailList(personClassDetail);
        ExcelUtil<PersonClassDetail> util = new ExcelUtil<PersonClassDetail>(PersonClassDetail.class);
        util.exportExcel(response, list, "人员排班明细数据");
    }

    /**
     * 获取人员排班明细详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personClassDetailService.selectPersonClassDetailById(id));
    }


    /**
     * 修改人员排班明细
     */
    @Log(title = "人员排班明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonClassDetail personClassDetail)
    {
        return toAjax(personClassDetailService.updatePersonClassDetail(personClassDetail));
    }

    /**
     * 删除人员排班明细
     */
    @Log(title = "人员排班明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personClassDetailService.deletePersonClassDetailByIds(ids));
    }

    /**
     * 人员排班操作
     */
    @Log(title = "人员排班", businessType = BusinessType.INSERT)
    @PostMapping("/setDetail")
    public AjaxResult setDetail(PersonClassDetail personClassDetail){
        personClassDetailService.setPersonClassDetail(personClassDetail);
        return toAjax(personClassDetailService.setPersonClassDetail(personClassDetail));
    }

    /**
     * 人员调班操作
     */
    @Log(title = "人员调班", businessType = BusinessType.INSERT)
    @PostMapping("/changeDetail")
    public AjaxResult changeDetail(@RequestBody ChangeClassDetailDTO detail){
        return toAjax(personClassDetailService.changeDetail(detail.getShiftChangeList()));
    }

    /**
     * 新增人员排班明细
     */
    @Log(title = "人员排班明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonClassDetail personClassDetail)
    {
        return toAjax(personClassDetailService.insertPersonClassDetail(personClassDetail));
    }
}