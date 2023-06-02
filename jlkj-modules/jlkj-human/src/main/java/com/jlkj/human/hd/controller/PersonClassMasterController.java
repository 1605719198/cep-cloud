package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.PersonClassMaster;
import com.jlkj.human.hd.service.IPersonClassMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 出勤身份Controller
 *
 * @author 266861
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/personClassMaster")
public class PersonClassMasterController extends BaseController
{
    @Autowired
    private IPersonClassMasterService personClassMasterService;

    /**
     * 查询出勤身份列表
     */
    @RequiresPermissions("human:personClassMaster:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonClassMaster personClassMaster)
    {
        startPage();
        List<PersonClassMaster> list = personClassMasterService.selectPersonClassMasterList(personClassMaster);
        return getDataTable(list);
    }


    /**
     * 获取出勤身份详细信息
     */
    @RequiresPermissions("human:personClassMaster:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personClassMasterService.selectPersonClassMasterById(id));
    }

    /**
     * 获取某员工出勤身份轮班方式
     */
    @GetMapping(value = "/queryPerson")
    public AjaxResult getClassMaster( PersonClassMaster personClassMaster)
    {
        return success(personClassMasterService.selectClassMasterByPerson(personClassMaster));
    }

    /**
     * 新增出勤身份
     */
    @RequiresPermissions("human:personClassMaster:add")
    @Log(title = "出勤身份", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonClassMaster personClassMaster)
    {
        return toAjax(personClassMasterService.insertPersonClassMaster(personClassMaster));
    }

    /**
     * 新增批量出勤身份
     */
    @RequiresPermissions("human:personClassMaster:add")
    @Log(title = "出勤身份", businessType = BusinessType.INSERT)
    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@RequestBody List<PersonClassMaster> personClassMasterList)
    {
        return toAjax(personClassMasterService.insertBatchPersonClassMaster(personClassMasterList));
    }

    /**
     * 修改出勤身份
     */
    @RequiresPermissions("human:personClassMaster:edit")
    @Log(title = "出勤身份", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonClassMaster personClassMaster)
    {
        return toAjax(personClassMasterService.updatePersonClassMaster(personClassMaster));
    }

    /**
     * 删除出勤身份
     */
    @RequiresPermissions("human:personClassMaster:remove")
    @Log(title = "出勤身份", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personClassMasterService.deletePersonClassMasterByIds(ids));
    }

    /**
     * 导入批量排班数据
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "批量排班资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:personClassMaster:export")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<PersonClassMaster> util = new ExcelUtil<PersonClassMaster>(PersonClassMaster.class);
        List<PersonClassMaster> personClassMasterList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = personClassMasterService.importUser(personClassMasterList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入批量排班资料数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<PersonClassMaster> util = new ExcelUtil<PersonClassMaster>(PersonClassMaster.class);
        util.importTemplateExcel(response, "批量排班数据");
    }
}
