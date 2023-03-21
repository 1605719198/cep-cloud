package com.jlkj.human.hp.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hp.domain.SysPost;
import com.jlkj.human.hp.domain.SysPostVersion;
import com.jlkj.human.hp.service.ISysPostService;
import com.jlkj.human.hp.service.ISysPostVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岗位信息数据维护Controller
 *
 * @author 266861
 * @date 2023-03-15
 */
@RestController
@RequestMapping("/postMaintenance")
public class SysPostController extends BaseController
{
    @Autowired
    private ISysPostService sysPostService;
    @Autowired
    private ISysPostVersionService SysPostVersionService;

    /**
     * 查询岗位信息数据维护列表
     */
    @RequiresPermissions("human:postMaintenance:list")
    @GetMapping("/list")
    public TableDataInfo list(SysPost sysPost)
    {
        startPage();
        List<SysPost> list = sysPostService.selectSysPostList(sysPost);
        return getDataTable(list);
    }

    /**
     * 查询部门资料变更版本列表
     */
    @GetMapping("/getHistory")
    public TableDataInfo list(SysPostVersion SysPostVersion)
    {
        startPage();
        List<SysPostVersion> list = SysPostVersionService.selectSysPostVersionList(SysPostVersion);
        return getDataTable(list);
    }

    /**
     * 导出岗位信息数据维护列表
     */
    @RequiresPermissions("human:postMaintenance:export")
    @Log(title = "岗位信息数据维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost sysPost)
    {
        List<SysPost> list = sysPostService.selectSysPostList(sysPost);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "岗位信息数据维护数据");
    }

    /**
     * 获取岗位信息数据维护详细信息
     */
    @RequiresPermissions("human:postMaintenance:query")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable("postId") Long postId)
    {
        return success(sysPostService.selectSysPostByPostId(postId));
    }

    /**
     * 新增岗位信息数据维护
     */
    @RequiresPermissions("human:postMaintenance:add")
    @Log(title = "岗位信息数据维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPost sysPost)throws Exception
    {
        return toAjax(sysPostService.insertSysPost(sysPost));
    }

    /**
     * 修改岗位信息数据维护
     */
    @RequiresPermissions("human:postMaintenance:edit")
    @Log(title = "岗位信息数据维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPost sysPost)
    {
        return toAjax(sysPostService.updateSysPost(sysPost));
    }

    /**
     * 删除岗位信息数据维护
     */
    @RequiresPermissions("human:postMaintenance:remove")
    @Log(title = "岗位信息数据维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds)
    {
        return toAjax(sysPostService.deleteSysPostByPostIds(postIds));
    }
}
