package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldInsertDTO;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldQueryDTO;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldUpdateDTO;
import com.jlkj.product.oi.service.ProductionCokeItemYieldService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 焦炭分项产量
*@Author: 265823
*@date: 2023/7/10 11:35
*/
@Tag(name = "焦炭分项产量")
@RestController
@RequestMapping("/performance/cokeitemyield")
@Slf4j
public class ProductionCokeItemYieldController {

    @Autowired
    private HttpServletRequest httpServletRequest ;

    @Autowired
    private ProductionCokeItemYieldService service ;

    /**
     * 焦炭分项产量-查询
     * @param dto
     * @return
     */
    @Log(title = "焦炭分项产量-查询",businessType = BusinessType.OTHER)
    @GetMapping("/getCokeItemYieldList")
    public Object getCokeItemYieldList(@Valid @ParamModel CokeItemYieldQueryDTO dto){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(service.getCokeItemYieldList(dto));
    }

    /**
     * 焦炭分项产量-查询2
     * @param dto
     * @return
     */
    @Log(title = "焦炭分项产量-查询2",businessType = BusinessType.OTHER)
    @GetMapping("/getCokeItemYieldList2")
    public AjaxResult getCokeItemYieldList2(@Valid @ParamModel CokeItemYieldQueryDTO dto){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(service.getCokeItemYieldList2(dto));
    }

    /**
     * 焦炭分项产量-新增
     * @param dto
     * @return
     */
    @Log(title = "焦炭分项产量-新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertCokeItemYieldList" , method = RequestMethod.POST, produces = "application/json")
    public void insertCokeItemYield(@Valid @RequestBody CokeItemYieldInsertDTO dto){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        service.insertCokeItemYield(dto);
    }

    /**
     * 焦炭分项产量-修改
     * @param dto
     * @return
     */
    @Log(title = "焦炭分项产量-修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateCokeItemYieldList" , method = RequestMethod.POST, produces = "application/json")
    public void updateCokeItemYield(@Valid @RequestBody CokeItemYieldUpdateDTO dto){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        service.updateCokeItemYield(dto);
    }

    /**
     * 焦炭分项产量-删除
     * @param id
     * @return
     */
    @Log(title = "焦炭分项产量-删除",businessType = BusinessType.DELETE)
    @GetMapping("/deleteCokeItemYieldList")
    public void deleteCokeItemYield(@Valid @PathParam("id") String id){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, id);
        service.deleteCokeItemYield(id);
    }
}
