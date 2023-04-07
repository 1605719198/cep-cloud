package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PositionsClassDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.PositionsClass;
import com.jlkj.human.hm.service.IPositionsClassService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 职级数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/humanresource/positions/class")
public class PositionsClassController {
    private final IPositionsClassService humanresourcePositionsClassService;

    /**
     * 新增职级数据
     */
    @Log(title = "新增职级数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增职级数据")
    @PostMapping("/addPositionsClassData")
    public AjaxResult addPositionsClassData(@RequestBody PositionsClassDTO positionsClassDTO, @RequestHeader("token") String token){
        PositionsClass positionsClass = new PositionsClass();
        BeanUtils.copyProperties(positionsClassDTO, positionsClass);
        boolean save = humanresourcePositionsClassService.save(positionsClass);
        return AjaxResult.success(save);
    }
    /**
     * 修改职级数据
     */
    @Log(title = "修改职级数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改职级数据")
    @PutMapping("/updatePositionsClassData")
    public AjaxResult updatePositionsClassData(@RequestBody PositionsClassDTO positionsClassDTO, @RequestHeader("token") String token){
        PositionsClass positionsClass = new PositionsClass();
        BeanUtils.copyProperties(positionsClassDTO, positionsClass);
        PositionsClass positionsEntity = humanresourcePositionsClassService.getById(positionsClass.getId());
        if (StringUtils.isNotNull(positionsEntity)) {
            boolean update = humanresourcePositionsClassService.updateById(positionsClass);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePositionsClassService.save(positionsClass);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除职级数据
     */
    @Log(title = "删除职级数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除职级数据")
    @DeleteMapping("/deletePositionsClassData")
    public AjaxResult deletePositionsClassData(@RequestBody PositionsClassDTO positionsClassDTO, @RequestHeader("token") String token){
        PositionsClass positionsClass = new PositionsClass();
        BeanUtils.copyProperties(positionsClassDTO, positionsClass);
        boolean delete = humanresourcePositionsClassService.removeById(positionsClass);
        return AjaxResult.success(delete);
    }

}
