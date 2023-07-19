package com.jlkj.human.hr.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hr.domain.RecruitingNeeds;
import com.jlkj.human.hr.service.IRecruitingNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 招聘需求Controller
 * @Description: 招聘需求作业控制层
 * @author 266861
 * @date 2023-07-18
 * @version:V1.0
 */
@RestController
@RequestMapping("/recruitingNeeds")
public class RecruitingNeedsController extends BaseController
{
    @Autowired
    private IRecruitingNeedsService recruitingNeedsService;

    /**
     * 查询招聘需求列表
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 查询参数
     * @return 招聘需求资料列表
     */
    @RequiresPermissions("human:recruitingNeeds:list")
    @GetMapping("/list")
    public TableDataInfo list(RecruitingNeeds recruitingNeeds)
    {
        startPage();
        List<RecruitingNeeds> list = recruitingNeedsService.selectRecruitingNeedsList(recruitingNeeds);
        return getDataTable(list);
    }

    /**
     * 导出招聘需求列表
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求资料列表
     * @return 招聘需求excel
     */
    @RequiresPermissions("human:recruitingNeeds:export")
    @Log(title = "招聘需求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RecruitingNeeds recruitingNeeds)
    {
        List<RecruitingNeeds> list = recruitingNeedsService.selectRecruitingNeedsList(recruitingNeeds);
        ExcelUtil<RecruitingNeeds> util = new ExcelUtil<RecruitingNeeds>(RecruitingNeeds.class);
        util.exportExcel(response, list, "招聘需求数据");
    }

    /**
     * 获取招聘需求详细信息
     * @author 266861
     * @date 2023-07-18
     * @param uuid 招聘需求主键
     * @return 招聘需求信息
     */
    @RequiresPermissions("human:recruitingNeeds:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(recruitingNeedsService.selectRecruitingNeedsByUuid(uuid));
    }

    /**
     * 新增招聘需求
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求资料
     * @return 新增结果
     */
    @RequiresPermissions("human:recruitingNeeds:add")
    @Log(title = "招聘需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RecruitingNeeds recruitingNeeds)
    {
        return toAjax(recruitingNeedsService.insertRecruitingNeeds(recruitingNeeds));
    }

    /**
     * 修改招聘需求
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求资料
     * @return 修改结果
     */
    @RequiresPermissions("human:recruitingNeeds:edit")
    @Log(title = "招聘需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RecruitingNeeds recruitingNeeds)
    {
        return toAjax(recruitingNeedsService.updateRecruitingNeeds(recruitingNeeds));
    }

    /**
     * 删除招聘需求
     * @author 266861
     * @date 2023-07-18
     * @param uuids 招聘需求主键数组
     * @return 删除结果
     */
    @RequiresPermissions("human:recruitingNeeds:remove")
    @Log(title = "招聘需求", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(recruitingNeedsService.deleteRecruitingNeedsByUuids(uuids));
    }
}
