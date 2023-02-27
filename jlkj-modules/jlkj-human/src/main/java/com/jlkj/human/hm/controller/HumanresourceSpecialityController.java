package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourceSpecialityDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourceSpeciality;
import com.jlkj.human.hm.service.IHumanresourceSpecialityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 专长数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-05
 */
@RestController
@RequestMapping("/humanresource/speciality/base")
public class HumanresourceSpecialityController {
    @Autowired
    private IHumanresourceSpecialityService humanresourceSpecialityService;
    /**
     * 新增专长数据资料
     */
    @Log(title = "新增专长数据资料",businessType = BusinessType.INSERT)
    @Operation(summary = "新增专长数据资料")
    @PostMapping("/addSpecialityData")
    public AjaxResult addSpecialityData(@RequestBody HumanresourceSpecialityDTO humanresourceSpecialityDTO, @RequestHeader("token") String token){
        HumanresourceSpeciality humanresourceSpeciality = new HumanresourceSpeciality();
        BeanUtils.copyProperties(humanresourceSpecialityDTO,humanresourceSpeciality);
        boolean save = humanresourceSpecialityService.save(humanresourceSpeciality);
        return AjaxResult.success(save);
    }
    /**
     * 修改专长数据资料
     */
    @Log(title = "修改专长数据资料",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改专长数据资料")
    @PutMapping("/updateSpecialityData")
    public AjaxResult updateSpecialityData(@RequestBody HumanresourceSpecialityDTO humanresourceSpecialityDTO,@RequestHeader("token") String token){
        HumanresourceSpeciality humanresourceSpeciality = new HumanresourceSpeciality();
        BeanUtils.copyProperties(humanresourceSpecialityDTO,humanresourceSpeciality);
        HumanresourceSpeciality speciality = humanresourceSpecialityService.getById(humanresourceSpeciality.getId());
        if (StringUtils.isNotNull(speciality)) {
            boolean update = humanresourceSpecialityService.updateById(humanresourceSpeciality);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceSpecialityService.save(humanresourceSpeciality);
            return AjaxResult.success(save);
        }
    }
    /**
     * 删除专长数据资料
     */
    @Log(title = "删除专长数据资料",businessType = BusinessType.DELETE)
    @Operation(summary = "删除专长数据资料")
    @DeleteMapping("/deleteSpecialityData")
    public AjaxResult deleteSpecialityData(@RequestBody HumanresourceSpecialityDTO humanresourceSpecialityDTO,@RequestHeader("token") String token){
        HumanresourceSpeciality humanresourceSpeciality = new HumanresourceSpeciality();
        BeanUtils.copyProperties(humanresourceSpecialityDTO,humanresourceSpeciality);
        boolean delete = humanresourceSpecialityService.removeById(humanresourceSpeciality);
        return AjaxResult.success(delete);
    }
}
