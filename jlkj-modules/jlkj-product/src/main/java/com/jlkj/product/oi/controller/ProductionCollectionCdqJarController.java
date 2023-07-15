package com.jlkj.product.oi.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.ProductionCollectionCdqJar;
import com.jlkj.product.oi.dto.productioncollectioncdqjar.GetDTO;
import com.jlkj.product.oi.dto.productioncollectioncdqjar.PageProductionCollectionCdqJarDTO;
import com.jlkj.product.oi.service.ProductionCollectionCdqJarService;
import com.jlkj.product.oi.utils.ExportExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
*@description: 生产管理-数据采集-干熄焦-接焦信息
*@Author: 265823
*@date: 2023/7/13 7:59
*/
@Tag(name = "生产管理-数据采集-干熄焦-接焦信息")
@RestController
@RequestMapping("/productionCollectionCdqJar")
@Slf4j
public class ProductionCollectionCdqJarController {

    @Resource
    HttpServletRequest httpServletRequest;

    @Resource
    ProductionCollectionCdqJarService productionCollectionCdqJarService;

    /**
     * 查询生产管理-数据采集-干熄焦-接焦信息
     * @param dto
     * @return
     */
    @Operation(summary = "查询生产管理-数据采集-干熄焦-接焦信息",
            parameters = {
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "collection_time", description = "采集时间"),
                                    @ExampleObject(name = "process_weight", description = "过程值重量"),
                                    @ExampleObject(name = "tank_weight", description = "罐车重量"),
                                    @ExampleObject(name = "coke_weight", description = "焦重量"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦棚产量维护-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxResult list(@Validated @ParamModel PageProductionCollectionCdqJarDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionCollectionCdqJarService.getListPage(dto));
    }

    /**
     * 干熄焦实绩导出
     * @param response
     * @param dto
     */
    @Log(title = "干熄焦实绩导出",businessType = BusinessType.OTHER)
    @PostMapping("/export")
    public void export(HttpServletResponse response, @Validated @ParamModel GetDTO dto) {
        LambdaQueryWrapper<ProductionCollectionCdqJar> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(StrUtil.isNotEmpty(dto.getStartTime()), ProductionCollectionCdqJar::getCollectionTime, dto.getStartTime());
        wrapper.le(StrUtil.isNotEmpty(dto.getEndTime()), ProductionCollectionCdqJar::getCollectionTime, dto.getEndTime());
        wrapper.orderByAsc(ProductionCollectionCdqJar::getCollectionTime);
        List<ProductionCollectionCdqJar> list = productionCollectionCdqJarService.list(wrapper);
//        ExportExcelUtil.exportExcel(list, ProductionCollectionCdqJar.class, "干熄焦实绩", "干熄焦实绩", "干熄焦实绩", response);
        ExcelUtil<ProductionCollectionCdqJar> util = new ExcelUtil<ProductionCollectionCdqJar>(ProductionCollectionCdqJar.class);
        util.exportExcel(response, list, "干熄焦实绩");
    }

}

