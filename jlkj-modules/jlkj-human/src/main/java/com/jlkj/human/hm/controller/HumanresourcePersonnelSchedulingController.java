package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourcePersonnelSchedulingDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourcePersonnelScheduling;
import com.jlkj.human.hm.service.IHumanresourcePersonnelSchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 人员排班数据 前端控制器
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/humanresource/personnel/scheduling")
public class HumanresourcePersonnelSchedulingController {

    private static final String CLASS_TEXT_A = "甲";
    private static final String CLASS_TEXT_B = "乙";
    private static final String CLASS_TEXT_C = "丙";
    private static final String CLASS_TEXT_D = "丁";
    private static final String CLASS_TYPE_A = "A";
    private static final String CLASS_TYPE_B = "B";
    private static final String CLASS_TYPE_C = "C";
    private static final String CLASS_TYPE_D = "D";
    @Autowired
    private IHumanresourcePersonnelSchedulingService humanresourcePersonnelSchedulingService;
    /**
     * 新增人员排班资料
     */
    @Log(title = "新增人员排班资料",businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员排班资料")
    @PostMapping("/addPersonnelSchedulingData")
    public AjaxResult addPersonnelSchedulingData(@RequestBody HumanresourcePersonnelSchedulingDTO humanresourcePersonnelSchedulingDTO, @RequestHeader("token") String token){
        HumanresourcePersonnelScheduling humanresourcePersonnelScheduling = new HumanresourcePersonnelScheduling();
        BeanUtils.copyProperties(humanresourcePersonnelSchedulingDTO,humanresourcePersonnelScheduling);
        switch (humanresourcePersonnelSchedulingDTO.getClassType().trim()) {
            case CLASS_TEXT_A:
                humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_A);
                break;
            case CLASS_TEXT_B:
                humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_B);
                break;
            case CLASS_TEXT_C:
                humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_C);
                break;
            case CLASS_TEXT_D:
                humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_D);
                break;
            default:
                break;
        }
        boolean save = humanresourcePersonnelSchedulingService.save(humanresourcePersonnelScheduling);
        return AjaxResult.success(save);
    }
    /**
     * 修改人员排班资料
     */
    @Log(title = "修改人员排班资料",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改人员排班资料")
    @PutMapping("/updatePersonnelSchedulingData")
    public AjaxResult updatePersonnelSchedulingData(@RequestBody HumanresourcePersonnelSchedulingDTO humanresourcePersonnelSchedulingDTO,@RequestHeader("token") String token){
        HumanresourcePersonnelScheduling humanresourcePersonnelScheduling = new HumanresourcePersonnelScheduling();
        BeanUtils.copyProperties(humanresourcePersonnelSchedulingDTO,humanresourcePersonnelScheduling);
        HumanresourcePersonnelScheduling personnelScheduling = humanresourcePersonnelSchedulingService.getById(humanresourcePersonnelScheduling.getId());
        if (StringUtils.isNotNull(personnelScheduling)) {
            switch (humanresourcePersonnelSchedulingDTO.getClassType().trim()) {
                case CLASS_TEXT_A:
                    humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_A);
                    break;
                case CLASS_TEXT_B:
                    humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_B);
                    break;
                case CLASS_TEXT_C:
                    humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_C);
                    break;
                case CLASS_TEXT_D:
                    humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_D);
                    break;
                default:
                    break;
            }
            boolean update = humanresourcePersonnelSchedulingService.updateById(humanresourcePersonnelScheduling);
            return AjaxResult.success(update);
        } else {
            switch (humanresourcePersonnelSchedulingDTO.getClassType().trim()) {
                case CLASS_TEXT_A:
                    humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_A);
                    break;
                case CLASS_TEXT_B:
                    humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_B);
                    break;
                case CLASS_TEXT_C:
                    humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_C);
                    break;
                case CLASS_TEXT_D:
                    humanresourcePersonnelScheduling.setClassTypeNo(CLASS_TYPE_D);
                    break;
                default:
                    break;
            }
            boolean save = humanresourcePersonnelSchedulingService.save(humanresourcePersonnelScheduling);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除人员排班资料
     */
    @Log(title = "删除人员排班资料",businessType = BusinessType.DELETE)
    @Operation(summary = "删除人员排班资料")
    @DeleteMapping("/deletePersonnelSchedulingData")
    public AjaxResult deletePersonnelSchedulingData(@RequestBody HumanresourcePersonnelSchedulingDTO humanresourcePersonnelSchedulingDTO,@RequestHeader("token") String token){
        HumanresourcePersonnelScheduling humanresourcePersonnelScheduling = new HumanresourcePersonnelScheduling();
        BeanUtils.copyProperties(humanresourcePersonnelSchedulingDTO,humanresourcePersonnelScheduling);
        boolean delete = humanresourcePersonnelSchedulingService.removeById(humanresourcePersonnelScheduling);
        return AjaxResult.success(delete);
    }
}
