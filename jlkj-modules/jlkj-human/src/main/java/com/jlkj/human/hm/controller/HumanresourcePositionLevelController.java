package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourcePositionLevelDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourcePositionLevel;
import com.jlkj.human.hm.service.IHumanresourcePositionLevelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 职位等级数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/humanresource/position/level")
public class HumanresourcePositionLevelController {

    @Autowired
    private IHumanresourcePositionLevelService humanresourcePositionLevelService;
    /**
     * 新增职位等级
     */
    @Log(title = "新增职位等级",businessType = BusinessType.INSERT)
    @Operation(summary = "新增职位等级")
    @PostMapping("/addPositionLevelData")
    public AjaxResult addPositionLevelData(@RequestBody HumanresourcePositionLevelDTO humanresourcePositionLevelDTO, @RequestHeader("token") String token){
        HumanresourcePositionLevel humanresourcePositionLevel = new HumanresourcePositionLevel();
        BeanUtils.copyProperties(humanresourcePositionLevelDTO,humanresourcePositionLevel);
        boolean save = humanresourcePositionLevelService.save(humanresourcePositionLevel);
        return AjaxResult.success(save);
    }
    /**
     * 修改职位等级
     */
    @Log(title = "修改职位等级",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改职位等级")
    @PutMapping("/updatePositionLevelData")
    public AjaxResult updatePositionLevelData(@RequestBody HumanresourcePositionLevelDTO humanresourcePositionLevelDTO,@RequestHeader("token") String token){
        HumanresourcePositionLevel humanresourcePositionLevel = new HumanresourcePositionLevel();
        BeanUtils.copyProperties(humanresourcePositionLevelDTO,humanresourcePositionLevel);
        HumanresourcePositionLevel levelEntity = humanresourcePositionLevelService.getById(humanresourcePositionLevel.getId());
        if (StringUtils.isNotNull(levelEntity)) {
            boolean update = humanresourcePositionLevelService.updateById(humanresourcePositionLevel);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePositionLevelService.save(humanresourcePositionLevel);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除职位等级
     */
    @Log(title = "删除职位等级",businessType = BusinessType.DELETE)
    @Operation(summary = "删除职位等级")
    @DeleteMapping("/deletePositionLevelData")
    public AjaxResult deletePositionLevelData(@RequestBody HumanresourcePositionLevelDTO humanresourcePositionLevelDTO,@RequestHeader("token") String token){
        HumanresourcePositionLevel humanresourcePositionLevel = new HumanresourcePositionLevel();
        BeanUtils.copyProperties(humanresourcePositionLevelDTO,humanresourcePositionLevel);
        boolean delete = humanresourcePositionLevelService.removeById(humanresourcePositionLevel);
        return AjaxResult.success(delete);
    }
}
