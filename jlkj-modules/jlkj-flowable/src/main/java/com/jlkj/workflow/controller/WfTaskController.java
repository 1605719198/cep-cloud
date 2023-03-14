package com.jlkj.workflow.controller;

import cn.hutool.core.util.ObjectUtil;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.workflow.domain.bo.WfTaskBo;
import com.jlkj.workflow.service.IWfTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 工作流任务管理
 *
 * @author xin
 * @createTime 2022/3/10 00:12
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/task")
public class WfTaskController {
    @Autowired
    private final IWfTaskService flowTaskService;

    /**
     * 取消申请
     */
    @PostMapping(value = "/stopProcess")
    @RequiresPermissions("workflow:process:cancel")
    public AjaxResult stopProcess(@RequestBody WfTaskBo bo) {
        flowTaskService.stopProcess(bo);
        return AjaxResult.success("取消成功！");
    }

    /**
     * 撤回流程
     */
    @PostMapping(value = "/revokeProcess")
    @RequiresPermissions("workflow:process:revoke")
    public AjaxResult revokeProcess(@RequestBody WfTaskBo bo) {
        flowTaskService.revokeProcess(bo);
        return AjaxResult.success("撤回成功！");
    }

    /**
     * 获取流程变量
     * @param taskId 流程任务Id
     */
    @GetMapping(value = "/processVariables/{taskId}")
    @RequiresPermissions("workflow:process:query")
    public AjaxResult processVariables(@PathVariable(value = "taskId") String taskId) {
        return AjaxResult.success(flowTaskService.getProcessVariables(taskId));
    }

    /**
     * 审批任务
     */
    @PostMapping(value = "/complete")
    @RequiresPermissions("workflow:process:approval")
    public AjaxResult complete(@RequestBody WfTaskBo bo) {
        flowTaskService.complete(bo);
        return AjaxResult.success("审批成功！");
    }

    /**
     * 拒绝任务
     */
    @PostMapping(value = "/reject")
    @RequiresPermissions("workflow:process:approval")
    public AjaxResult taskReject(@RequestBody WfTaskBo taskBo) {
        flowTaskService.taskReject(taskBo);
        return AjaxResult.success("任务已拒绝！");
    }

    /**
     * 退回任务
     */
    @PostMapping(value = "/return")
    @RequiresPermissions("workflow:process:approval")
    public AjaxResult taskReturn(@RequestBody WfTaskBo bo) {
        flowTaskService.taskReturn(bo);
        return AjaxResult.success("任务已退回！");
    }

    /**
     * 获取所有可回退的节点
     */
    @PostMapping(value = "/returnList")
    @RequiresPermissions("workflow:process:query")
    public R findReturnTaskList(@RequestBody WfTaskBo bo) {
        return R.ok(flowTaskService.findReturnTaskList(bo));
    }

    /**
     * 删除任务
     */
    @DeleteMapping(value = "/delete")
    @RequiresPermissions("workflow:process:approval")
    public AjaxResult delete(@RequestBody WfTaskBo bo) {
        flowTaskService.deleteTask(bo);
        return AjaxResult.success("删除成功！");
    }

    /**
     * 认领/签收任务
     */
    @PostMapping(value = "/claim")
    @RequiresPermissions("workflow:process:claim")
    public AjaxResult claim(@RequestBody WfTaskBo bo) {
        flowTaskService.claim(bo);
        return AjaxResult.success("签收成功！");
    }

    /**
     * 取消认领/签收任务
     */
    @PostMapping(value = "/unClaim")
    @RequiresPermissions("workflow:process:claim")
    public AjaxResult unClaim(@RequestBody WfTaskBo bo) {
        flowTaskService.unClaim(bo);
        return AjaxResult.success("取消签收成功！");
    }

    /**
     * 委派任务
     */
    @PostMapping(value = "/delegate")
    @RequiresPermissions("workflow:process:approval")
    public AjaxResult delegate(@RequestBody WfTaskBo bo) {
        if (ObjectUtil.hasNull(bo.getTaskId(), bo.getUserId())) {
            return AjaxResult.error("委派失败！");
        }
        flowTaskService.delegateTask(bo);
        return AjaxResult.success("委派成功！");
    }

    /**
     * 转办任务
     */
    @PostMapping(value = "/transfer")
    @RequiresPermissions("workflow:process:approval")
    public AjaxResult transfer(@RequestBody WfTaskBo bo) {
        if (ObjectUtil.hasNull(bo.getTaskId(), bo.getUserId())) {
            return AjaxResult.error("参数错误！");
        }
        flowTaskService.transferTask(bo);
        return AjaxResult.success("转办成功！");
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) {
        InputStream inputStream = flowTaskService.diagram(processId);
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
