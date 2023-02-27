package com.jlkj.human.hm.controller;

import com.jlkj.common.dto.human.hm.HumanresourcePersonnelDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourcePersonnel;
import com.jlkj.human.hm.service.IHumanresourcePersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 人员数据 前端控制器
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/humanresource/personnel/base")
public class HumanresourcePersonnelController {
    @Autowired
    private IHumanresourcePersonnelService humanresourcePersonnelService;
    /**
     * 新增人员资料
     */
    @Log(title = "新增人员资料",businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员资料")
    @PostMapping("/addPersonnelData")
    public void addPersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO, @RequestHeader("token") String token){
        humanresourcePersonnelService.saveData(humanresourcePersonnelDTO);
    }
    /**
     * 修改人员资料
     */
    @Log(title = "修改人员资料",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改人员资料")
    @PutMapping("/updatePersonnelData")
    public void updatePersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO,@RequestHeader("token") String token){
        humanresourcePersonnelService.updateData(humanresourcePersonnelDTO);
    }
    /**
     * 删除人员资料
     */
    @Log(title = "删除人员资料",businessType = BusinessType.DELETE)
    @Operation(summary = "删除人员资料")
    @DeleteMapping("/deletePersonnelData")
    public void deletePersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO,@RequestHeader("token") String token){
        HumanresourcePersonnel humanresourcePersonnel = new HumanresourcePersonnel();
        BeanUtils.copyProperties(humanresourcePersonnelDTO,humanresourcePersonnel);
        humanresourcePersonnelService.removeData(humanresourcePersonnelDTO);
    }

}
