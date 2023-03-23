package com.jlkj.safety.si.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.datascope.annotation.ParamModel;

import com.jlkj.common.log.annotation.Log;

import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.safety.si.dto.environmentalcollectionpoints.AddEnvironmentalCollectionPointsDTO;
import com.jlkj.safety.si.dto.environmentalcollectionpoints.DeleteEnvironmentalCollectionPointsDTO;
import com.jlkj.safety.si.dto.environmentalcollectionpoints.PageEnvironmentalCollectionPointsDTO;
import com.jlkj.safety.si.dto.environmentalcollectionpoints.UpdateEnvironmentalCollectionPointsDTO;
import com.jlkj.safety.si.entity.EnvironmentalCollectionPoints;
import com.jlkj.safety.si.service.EnvironmentalCollectionPointsService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
 * @author yzl
 * @Description 环保采集点位表
 * @create 2022-12-14 09:04:30.932275
 */
@Tag(name = "环保采集点位表")
@RestController
@RequestMapping("/environmentalCollectionPoints")
@Slf4j
public class EnvironmentalCollectionPointsController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    EnvironmentalCollectionPointsService environmentalCollectionPointsService;

    @Operation(summary = "查询环保采集点位表",
            parameters = {
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "device_name", description = "名称"),
                                    @ExampleObject(name = "pollutant_name", description = "污染物名称"),
                                    @ExampleObject(name = "collection_point", description = "采集点位"),
                                    @ExampleObject(name = "emission_limit", description = "排放限值"),
                                    @ExampleObject(name = "sort", description = "序号"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getEnvironmentalCollectionPoints", method = RequestMethod.GET)
    public Object getEnvironmentalCollectionPoints(@RequestParam String sort) {
        List<Map<String, String>> list = environmentalCollectionPointsService.getEnvironmentalCollectionPoints(sort);
        return AjaxResult.success(list);
    }

    @Operation(summary = "查询环保采集点位",
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
                                    @ExampleObject(name = "device_name", description = "名称"),
                                    @ExampleObject(name = "pollutant_name", description = "污染物名称"),
                                    @ExampleObject(name = "collection_point", description = "采集点位"),
                                    @ExampleObject(name = "emission_limit", description = "排放限值"),
                                    @ExampleObject(name = "sort", description = "序号"),

                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageEnvironmentalCollectionPointsDTO pageEnvironmentalCollectionPointsDTO) {
        log.info("params => " + pageEnvironmentalCollectionPointsDTO);
        String errorMsg = ValidUtil.checkValid(pageEnvironmentalCollectionPointsDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageEnvironmentalCollectionPointsDTO);
        IPage<Map<String, String>> list = environmentalCollectionPointsService.getPageData(pageEnvironmentalCollectionPointsDTO);
        return AjaxResult.success(list);
    }

    @Operation(summary = "新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "device_name", description = "名称"),
                            @ExampleObject(name = "pollutant_name", description = "污染物名称"),
                            @ExampleObject(name = "collection_point", description = "采集点位"),
                            @ExampleObject(name = "emission_limit", description = "排放限值"),
                            @ExampleObject(name = "sort", description = "序号"),

                            @ExampleObject(name = "create_user_id", description = "创建人id"),
                            @ExampleObject(name = "create_user_name", description = "创建人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "业务操作-新增", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddEnvironmentalCollectionPointsDTO addEnvironmentalCollectionPointsDTO) {
        log.info("params => " + addEnvironmentalCollectionPointsDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addEnvironmentalCollectionPointsDTO);
        EnvironmentalCollectionPoints environmentalCollectionPoints = new EnvironmentalCollectionPoints();
        environmentalCollectionPoints.setId(IdUtil.randomUUID());
        environmentalCollectionPoints.setDeviceName(addEnvironmentalCollectionPointsDTO.getDeviceName());
        environmentalCollectionPoints.setPollutantName(addEnvironmentalCollectionPointsDTO.getPollutantName());
        environmentalCollectionPoints.setCollectionPoint(addEnvironmentalCollectionPointsDTO.getCollectionPoint());
        environmentalCollectionPoints.setEmissionLimit(addEnvironmentalCollectionPointsDTO.getEmissionLimit());
        environmentalCollectionPoints.setSort(addEnvironmentalCollectionPointsDTO.getSort());
        environmentalCollectionPoints.setUnit(addEnvironmentalCollectionPointsDTO.getUnit());
        environmentalCollectionPointsService.save(environmentalCollectionPoints);
        return AjaxResult.success(ResponseUtil.toResult("环保采集点位增加成功"));
    }


    @Operation(summary = "修改",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "device_name", description = "名称"),
                            @ExampleObject(name = "pollutant_name", description = "污染物名称"),
                            @ExampleObject(name = "collection_point", description = "采集点位"),
                            @ExampleObject(name = "emission_limit", description = "排放限值"),
                            @ExampleObject(name = "sort", description = "序号"),

                            @ExampleObject(name = "modify_user_id", description = "修改人id"),
                            @ExampleObject(name = "modify_user_name", description = "修改人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "业务操作-修改", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateEnvironmentalCollectionPointsDTO updateEnvironmentalCollectionPointsDTO) {
        log.info("params => " + updateEnvironmentalCollectionPointsDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateEnvironmentalCollectionPointsDTO);
        EnvironmentalCollectionPoints environmentalCollectionPoints = environmentalCollectionPointsService.getById(updateEnvironmentalCollectionPointsDTO.getId());
        if (null != environmentalCollectionPoints) {
            environmentalCollectionPoints.setDeviceName(updateEnvironmentalCollectionPointsDTO.getDeviceName());
            environmentalCollectionPoints.setPollutantName(updateEnvironmentalCollectionPointsDTO.getPollutantName());
            environmentalCollectionPoints.setCollectionPoint(updateEnvironmentalCollectionPointsDTO.getCollectionPoint());
            environmentalCollectionPoints.setEmissionLimit(updateEnvironmentalCollectionPointsDTO.getEmissionLimit());
            environmentalCollectionPoints.setSort(updateEnvironmentalCollectionPointsDTO.getSort());
            environmentalCollectionPoints.setUnit(updateEnvironmentalCollectionPointsDTO.getUnit());
            environmentalCollectionPointsService.updateById(environmentalCollectionPoints);
            return AjaxResult.success(ResponseUtil.toResult("环保采集点位修改成功"));
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "环保采集点位不存在");
        }
    }

    @Operation(summary = "删除环保采集点位",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "业务操作-删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public Object delete(@Valid @RequestBody DeleteEnvironmentalCollectionPointsDTO deleteEnvironmentalCollectionPointsDTO) {
        log.info("params => " + deleteEnvironmentalCollectionPointsDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteEnvironmentalCollectionPointsDTO);
        EnvironmentalCollectionPoints environmentalCollectionPoints = environmentalCollectionPointsService.getById(deleteEnvironmentalCollectionPointsDTO.getId());
        if (null != environmentalCollectionPoints) {
            environmentalCollectionPointsService.removeById(environmentalCollectionPoints);
            return AjaxResult.success(ResponseUtil.toResult("环保采集点位删除成功"));
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "环保采集点位不存在或已被删除");
        }
    }

}



