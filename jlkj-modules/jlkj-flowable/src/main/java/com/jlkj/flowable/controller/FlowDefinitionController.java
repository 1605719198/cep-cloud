package com.jlkj.flowable.controller;


import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.flowable.domain.SysExpression;
import com.jlkj.flowable.domain.dto.FlowProcDefDto;
import com.jlkj.flowable.domain.dto.FlowSaveXmlVo;
import com.jlkj.flowable.service.IFlowDefinitionService;
import com.jlkj.flowable.service.ISysExpressionService;
import com.jlkj.system.api.RemoteRoleService;
import com.jlkj.system.api.RemoteUserService;
import com.jlkj.system.api.domain.SysRole;
import com.jlkj.system.api.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工作流程定义
 * </p>
 *
 * @author xin
 * @date 2021-04-03
 */
@Slf4j
@Api(tags = "流程定义")
@RestController
@RequestMapping("/flowable/definition")
public class FlowDefinitionController extends BaseController {

    @Autowired
    private IFlowDefinitionService flowDefinitionService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RemoteRoleService remoteRoleService;
    @Autowired
    private ISysExpressionService sysExpressionService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "流程定义列表", response = FlowProcDefDto.class)
    public AjaxResult list(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
                           @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize,
                           @ApiParam(value = "流程名称", required = false) @RequestParam(required = false) String name) {
        return AjaxResult.success(flowDefinitionService.list(name, pageNum, pageSize));
    }


    @ApiOperation(value = "导入流程文件", notes = "上传bpmn20的xml文件")
    @PostMapping("/import")
    public AjaxResult importFile(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String category,
                                 MultipartFile file) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            flowDefinitionService.importFile(name, category, in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return AjaxResult.success(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流出错", e);
            }
        }

        return AjaxResult.success("导入成功");
    }


    @ApiOperation(value = "读取xml文件")
    @GetMapping("/readXml/{deployId}")
    public AjaxResult readXml(@ApiParam(value = "流程定义id") @PathVariable(value = "deployId") String deployId) {
        try {
            return flowDefinitionService.readXml(deployId);
        } catch (Exception e) {
            return AjaxResult.error("加载xml文件异常");
        }

    }

    @ApiOperation(value = "读取图片文件")
    @GetMapping("/readImage/{deployId}")
    public void readImage(@ApiParam(value = "流程定义id") @PathVariable(value = "deployId") String deployId, HttpServletResponse response) {
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(flowDefinitionService.readImage(deployId));
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


    @ApiOperation(value = "保存流程设计器内的xml文件")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody FlowSaveXmlVo vo) {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(vo.getXml().getBytes(StandardCharsets.UTF_8));
            flowDefinitionService.importFile(vo.getName(), vo.getCategory(), in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return AjaxResult.success(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流出错", e);
            }
        }

        return AjaxResult.success("导入成功");
    }


    @ApiOperation(value = "发起流程")
    @PostMapping("/start/{procDefId}")
    public AjaxResult start(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                            @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        return flowDefinitionService.startProcessInstanceById(procDefId, variables);

    }

    @ApiOperation(value = "激活或挂起流程定义")
    @PutMapping(value = "/updateState")
    public AjaxResult updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                                  @ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.updateState(state, deployId);
        return AjaxResult.success();
    }

    @ApiOperation(value = "删除流程")
    @DeleteMapping(value = "/{deployIds}")
    public AjaxResult delete(@PathVariable String[] deployIds) {
        for (String deployId : deployIds) {
            flowDefinitionService.delete(deployId);
        }
        return AjaxResult.success();
    }

    @ApiOperation(value = "指定流程办理人员列表")
    @GetMapping("/userList")
    public AjaxResult userList(SysUser user) {
        R<List<SysUser>> data = remoteUserService.selectUserList(user, SecurityConstants.INNER);
        if (R.FAIL == data.getCode()) {
            throw new ServiceException(data.getMsg());
        }
        List<SysUser> list = data.getData();
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "指定流程办理组列表")
    @GetMapping("/roleList")
    public AjaxResult roleList(SysRole role) {
        R<List<SysRole>> data = remoteRoleService.selectRoleList(role, SecurityConstants.INNER);
        if (R.FAIL == data.getCode()) {
            throw new ServiceException(data.getMsg());
        }
        List<SysRole> list = data.getData();
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "指定流程达式列表")
    @GetMapping("/expList")
    public AjaxResult expList(SysExpression sysExpression) {
        List<SysExpression> list = sysExpressionService.selectSysExpressionList(sysExpression);
        return AjaxResult.success(list);
    }

}
