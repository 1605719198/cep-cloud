package com.jlkj.flow.workflow.controller;


import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.flow.workflow.domain.bo.WfTaskBo;
import com.jlkj.flow.workflow.service.IWfInstanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 工作流流程实例管理
 *
 * @author xin
 * @createTime 2022/3/10 00:12
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/instance")
public class WfInstanceController {

    @Autowired
    private final IWfInstanceService instanceService;

    /**
     * 激活或挂起流程实例
     *
     * @param state 1:激活,2:挂起
     * @param instanceId 流程实例ID
     */
    @PostMapping(value = "/updateState")
    public R updateState(@RequestParam Integer state, @RequestParam String instanceId) {
        instanceService.updateState(state, instanceId);
        return R.ok();
    }

    /**
     * 结束流程实例
     *
     * @param bo 流程任务业务对象
     */
    @PostMapping(value = "/stopProcessInstance")
    public R stopProcessInstance(@RequestBody WfTaskBo bo) {
        instanceService.stopProcessInstance(bo);
        return R.ok();
    }

    /**
     * 删除流程实例
     *
     * @param instanceId 流程实例ID
     * @param deleteReason 删除原因
     */
    @DeleteMapping(value = "/delete")
    public R delete(@RequestParam String instanceId, String deleteReason) {
        instanceService.delete(instanceId, deleteReason);
        return R.ok();
    }

    /**
     * 查询流程实例详情信息
     *
     * @param procInsId 流程实例ID
     * @param deployId 流程部署ID
     */
    @GetMapping("/detail")
    public R detail(String procInsId, String deployId) {
        return R.ok(instanceService.queryDetailProcess(procInsId, deployId));
    }

    /**
     * 根据流程实例id查询流程状态
     * @param procInsId
     * @return
     */
    @GetMapping("/getHisByProcInsId/{procInsId}")
    public AjaxResult getHisByProcInsId(@PathVariable String procInsId) {
        return AjaxResult.success("查询成功",instanceService.getHisByProcInsId(procInsId));
    }

}
