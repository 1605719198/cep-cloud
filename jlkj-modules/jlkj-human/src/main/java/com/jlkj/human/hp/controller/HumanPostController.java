package com.jlkj.human.hp.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hp.domain.HumanPost;
import com.jlkj.human.hp.domain.HumanPostVersion;
import com.jlkj.human.hp.service.IHumanPostService;
import com.jlkj.human.hp.service.IHumanPostVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位信息数据维护Controller
 *
 * @author 266861
 * @date 2023-03-15
 */
@RestController
@RequestMapping("/postMaintenance")
public class HumanPostController extends BaseController
{
    @Autowired
    private IHumanPostService sysPostService;
    @Autowired
    private IHumanPostVersionService sysPostVersionService;

    /**
     * 查询岗位信息数据维护列表
     */
    @RequiresPermissions("human:postMaintenance:list")
    @GetMapping("/list")
    public TableDataInfo list(HumanPost humanPost)
    {
        startPage();
        List<HumanPost> list = sysPostService.selectSysPostList(humanPost);
        return getDataTable(list);
    }

    /**
     * 查询部门资料变更版本列表
     */
    @GetMapping("/getHistory")
    public TableDataInfo list(HumanPostVersion humanPostVersion)
    {
        List<HumanPostVersion> list = sysPostVersionService.selectSysPostVersionList(humanPostVersion);
        return getDataTable(list);
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
    public AjaxResult add(@RequestBody HumanPost humanPost)throws Exception
    {
        return toAjax(sysPostService.insertSysPost(humanPost));
    }

    /**
     * 修改岗位信息数据维护
     */
    @RequiresPermissions("human:postMaintenance:edit")
    @Log(title = "岗位信息数据维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HumanPost humanPost)
    {
        return toAjax(sysPostService.updateSysPost(humanPost));
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
