package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PositionLevelDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.PositionLevel;
import com.jlkj.human.hm.service.IPositionLevelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequiredArgsConstructor
@RequestMapping("/humanresource/position/level")
public class PositionLevelController {

    private final IPositionLevelService humanresourcePositionLevelService;
    /**
     * 新增职位等级
     */
    @Log(title = "新增职位等级",businessType = BusinessType.INSERT)
    @Operation(summary = "新增职位等级")
    @PostMapping("/addPositionLevelData")
    public AjaxResult addPositionLevelData(@RequestBody PositionLevelDTO positionLevelDTO, @RequestHeader("token") String token){
        PositionLevel positionLevel = new PositionLevel();
        BeanUtils.copyProperties(positionLevelDTO, positionLevel);
        boolean save = humanresourcePositionLevelService.save(positionLevel);
        return AjaxResult.success(save);
    }
    /**
     * 修改职位等级
     */
    @Log(title = "修改职位等级",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改职位等级")
    @PutMapping("/updatePositionLevelData")
    public AjaxResult updatePositionLevelData(@RequestBody PositionLevelDTO positionLevelDTO, @RequestHeader("token") String token){
        PositionLevel positionLevel = new PositionLevel();
        BeanUtils.copyProperties(positionLevelDTO, positionLevel);
        PositionLevel levelEntity = humanresourcePositionLevelService.getById(positionLevel.getId());
        if (StringUtils.isNotNull(levelEntity)) {
            boolean update = humanresourcePositionLevelService.updateById(positionLevel);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePositionLevelService.save(positionLevel);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除职位等级
     */
    @Log(title = "删除职位等级",businessType = BusinessType.DELETE)
    @Operation(summary = "删除职位等级")
    @DeleteMapping("/deletePositionLevelData")
    public AjaxResult deletePositionLevelData(@RequestBody PositionLevelDTO positionLevelDTO, @RequestHeader("token") String token){
        PositionLevel positionLevel = new PositionLevel();
        BeanUtils.copyProperties(positionLevelDTO, positionLevel);
        boolean delete = humanresourcePositionLevelService.removeById(positionLevel);
        return AjaxResult.success(delete);
    }
}
