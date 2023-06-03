package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.Director;
import com.jlkj.human.hs.dto.DirectorDTO;
import com.jlkj.human.hs.service.IDirectorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuangBing
 * @Description
 * @create 2023-05-11 11:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/director")
public class DirectorController extends BaseController {

    private final IDirectorService iDirectorService;

    /**
     * 查询各公司人事业务负责人设定列表
     */
    @RequiresPermissions("human:director:list")
    @Log(title = "查询各公司人事业务负责人设定列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询各公司人事业务负责人设定列表")
    @GetMapping("/list")
    public Object listDirector(Director director) {
        startPage();
        List<Director> list = iDirectorService.lambdaQuery()
                .eq(Director::getCompId, director.getCompId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存各公司人事业务负责人设定列表
     */
    @RequiresPermissions("human:director:add")
    @Log(title = "保存各公司人事业务负责人设定列表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存各公司人事业务负责人设定列表")
    @PostMapping
    public Object addDirector(@RequestBody DirectorDTO directorDTO) {
        for (Director item : directorDTO.getDirectorList()) {
            item.setCreator(SecurityUtils.getNickName());
            item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        }
        return iDirectorService.saveOrUpdateBatch(directorDTO.getDirectorList().stream().filter(item -> null != item.getDutyCode() && null != item.getPostId()).filter(item -> !item.getDutyCode().isEmpty() && !item.getPostId().isEmpty()).collect(Collectors.toList()));
    }


    /**
     * 删除各公司人事业务负责人设定信息
     */
    @RequiresPermissions("human:director:remove")
    @Operation(summary = "删除各公司人事业务负责人设定信息")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除各公司人事业务负责人设定信息", businessType = BusinessType.DELETE)
    public Object delDirector(@PathVariable List<String> uuid) {
        for (String item : uuid) {
            iDirectorService.lambdaUpdate().eq(Director::getUuid, item).remove();
        }
        return AjaxResult.success("删除成功");
    }
}
