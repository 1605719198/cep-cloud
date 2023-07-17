package com.jlkj.energy.ee.controller;

import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.dto.energyinoutlimit.DeleteEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.InsertEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.PageEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.UpdateEnergyInOutLimitDTO;
import com.jlkj.energy.ee.service.EnergyInOutLimitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 控制器-能源信息配置
 *
 * @author 265800
 * @since 2023-02-13 14:47:44
 */
@Tag(name = "能源信息配置")
@RestController
@RequestMapping("/energyinformationconfiguration")
@Slf4j
public class EnergyInOutLimitController {

    @Resource
    private EnergyInOutLimitService energyInformationConfigurationService;

    /**
     * 能源信息配置查询
     * @author 265800
     * @date 2023/7/12 11:04
     * @param pageEnergyInOutLimitDTO PageEnergyInOutLimitDTO
     * @return java.lang.Object
     */
    @Log(title = "能源信息配置查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getEnergyInformationConfigurationPage", method = RequestMethod.GET)
    public Object getEnergyInformationConfigurationPageData(@Validated @ParamModel PageEnergyInOutLimitDTO pageEnergyInOutLimitDTO) {
        log.info("params => " + pageEnergyInOutLimitDTO);
        String errorMsg = ValidUtil.checkValid(pageEnergyInOutLimitDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        return AjaxResult.success(energyInformationConfigurationService.getEnergyInformationConfigurationPageData(pageEnergyInOutLimitDTO));
    }

    /**
     * 能源信息配置新增
     * @author 265800
     * @date 2023/7/12 11:05
     * @param insertEnergyInOutLimitDTO InsertEnergyInOutLimitDTO
     * @return java.lang.Object
     */
    @Log(title = "能源信息配置新增", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertEnergyInformationConfiguration", method = RequestMethod.POST, produces = "application/json")
    public Object insertEnergyInformationConfigurationData(@Valid @RequestBody InsertEnergyInOutLimitDTO insertEnergyInOutLimitDTO) {
        log.info("params => " + insertEnergyInOutLimitDTO);
        return energyInformationConfigurationService.insertEnergyInformationConfigurationData(insertEnergyInOutLimitDTO);
    }

    /**
     * 能源信息配置更新
     * @author 265800
     * @date 2023/7/12 11:05
     * @param updateEnergyInOutLimitDTO UpdateEnergyInOutLimitDTO
     * @return java.lang.Object
     */
    @Log(title = "能源信息配置更新", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateEnergyInformationConfiguration", method = RequestMethod.PUT, produces = "application/json")
    public Object updateEnergyInformationConfigurationData(@Valid @RequestBody UpdateEnergyInOutLimitDTO updateEnergyInOutLimitDTO) {
        log.info("params => " + updateEnergyInOutLimitDTO);
        return energyInformationConfigurationService.updateEnergyInformationConfigurationData(updateEnergyInOutLimitDTO);
    }

    /**
     * 能源信息配置删除
     * @author 265800
     * @date 2023/7/12 11:06
     * @param deleteEnergyInOutLimitDTO DeleteEnergyInOutLimitDTO
     * @return java.lang.Object
     */
    @Log(title = "能源信息配置删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteEnergyInformationConfiguration", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteEnergyInformationConfigurationData(@Valid @RequestBody DeleteEnergyInOutLimitDTO deleteEnergyInOutLimitDTO) {
        log.info("params => " + deleteEnergyInOutLimitDTO);
        return energyInformationConfigurationService.deleteEnergyInformationConfigurationData(deleteEnergyInOutLimitDTO);
    }
}

