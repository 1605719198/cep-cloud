package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourcePositionsClassDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourcePositionsClass;
import com.jlkj.human.hm.service.IHumanresourcePositionsClassService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/humanresource/positions/class")
public class HumanresourcePositionsClassController {
    @Autowired
    private IHumanresourcePositionsClassService humanresourcePositionsClassService;

    /**
     * 新增职级数据
     */
    @Log(title = "新增职级数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增职级数据")
    @PostMapping("/addPositionsClassData")
    public AjaxResult addPositionsClassData(@RequestBody HumanresourcePositionsClassDTO humanresourcePositionsClassDTO, @RequestHeader("token") String token){
        HumanresourcePositionsClass humanresourcePositionsClass = new HumanresourcePositionsClass();
        BeanUtils.copyProperties(humanresourcePositionsClassDTO,humanresourcePositionsClass);
        boolean save = humanresourcePositionsClassService.save(humanresourcePositionsClass);
        return AjaxResult.success(save);
    }
    /**
     * 修改职级数据
     */
    @Log(title = "修改职级数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改职级数据")
    @PutMapping("/updatePositionsClassData")
    public AjaxResult updatePositionsClassData(@RequestBody HumanresourcePositionsClassDTO humanresourcePositionsClassDTO,@RequestHeader("token") String token){
        HumanresourcePositionsClass humanresourcePositionsClass = new HumanresourcePositionsClass();
        BeanUtils.copyProperties(humanresourcePositionsClassDTO,humanresourcePositionsClass);
        HumanresourcePositionsClass positionsEntity = humanresourcePositionsClassService.getById(humanresourcePositionsClass.getId());
        if (StringUtils.isNotNull(positionsEntity)) {
            boolean update = humanresourcePositionsClassService.updateById(humanresourcePositionsClass);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePositionsClassService.save(humanresourcePositionsClass);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除职级数据
     */
    @Log(title = "删除职级数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除职级数据")
    @DeleteMapping("/deletePositionsClassData")
    public AjaxResult deletePositionsClassData(@RequestBody HumanresourcePositionsClassDTO humanresourcePositionsClassDTO,@RequestHeader("token") String token){
        HumanresourcePositionsClass humanresourcePositionsClass = new HumanresourcePositionsClass();
        BeanUtils.copyProperties(humanresourcePositionsClassDTO,humanresourcePositionsClass);
        boolean delete = humanresourcePositionsClassService.removeById(humanresourcePositionsClass);
        return AjaxResult.success(delete);
    }

}
