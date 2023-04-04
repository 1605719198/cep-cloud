package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldInsertDTO;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldQueryDTO;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldUpdateDTO;
import com.jlkj.product.oi.service.impl.ProductionCokeItemYieldServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
* @author zzh
* @datetime 2022/9/9 14:44
* @description 焦炭分项产量 controller类
*/

@Tag(name = "焦炭分项产量")
@RestController
@RequestMapping("/performance/cokeitemyield")
@Slf4j
public class ProductionCokeItemYieldController {

    @Autowired
    private HttpServletRequest httpServletRequest ;

    @Autowired
    private ProductionCokeItemYieldServiceImpl service ;

    @Log(title = "焦炭分项产量-查询",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @GetMapping("/getCokeItemYieldList")
    public Object getCokeItemYieldList(@Valid @ParamModel CokeItemYieldQueryDTO dto){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, Object>> list = service.getCokeItemYieldList(dto);
        return AjaxResult.success(list);
    }

    @Log(title = "焦炭分项产量-查询2",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @GetMapping("/getCokeItemYieldList2")
    public Object getCokeItemYieldList2(@Valid @ParamModel CokeItemYieldQueryDTO dto){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, Object>> list = service.getCokeItemYieldList2(dto);
        return AjaxResult.success(list);
    }

    @Log(title = "焦炭分项产量-新增",businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/insertCokeItemYieldList" , method = RequestMethod.POST, produces = "application/json")
    public Object insertCokeItemYield(@Valid @RequestBody CokeItemYieldInsertDTO dto){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return service.insertCokeItemYield(dto);
    }

    @Log(title = "焦炭分项产量-新增",businessType = BusinessType.UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/updateCokeItemYieldList" , method = RequestMethod.POST, produces = "application/json")
    public Object updateCokeItemYield(@Valid @RequestBody CokeItemYieldUpdateDTO dto){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return service.updateCokeItemYield(dto);
    }

    @Log(title = "焦炭分项产量-删除",businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/deleteCokeItemYieldList")
    public Object deleteCokeItemYield(@Valid @PathParam("id") String id){
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, id);
        return service.deleteCokeItemYield(id);
    }
}
