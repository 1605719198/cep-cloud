package com.jlkj.product.oi.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.product.oi.domain.ProductionLogsName;
import com.jlkj.product.oi.dto.productionlogsname.AddProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.DeleteProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.PageProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.UpdateProductionLogsNameDTO;
import com.jlkj.product.oi.service.ProductionLogsNameService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
*@description: 日志项名称
*@Author: 265823
*@date: 2023/7/10 16:04
*/
@Tag(name = "日志项名称")
@RestController
@RequestMapping("/productionLogsName")
@Slf4j
public class ProductionLogsNameController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    ProductionLogsNameService productionLogsNameService;

    /**
     * 查询日志项名称
     * @param pageProductionLogsNameDTO
     * @return
     */
    @Operation(summary = "查询日志项名称",
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
                                    @ExampleObject(name = "logs_name", description = "日志项名称"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询日志项名称",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxResult list(@Validated @ParamModel PageProductionLogsNameDTO pageProductionLogsNameDTO) {
        log.info("params => " + pageProductionLogsNameDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionLogsNameDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionLogsNameDTO);
        return AjaxResult.success(productionLogsNameService.getListPage(pageProductionLogsNameDTO));
    }

    /**
     * 新增
     * @param addProductionLogsNameDTO
     */
    @Operation(summary = "新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "logs_name", description = "日志项名称"),
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
    @Log(title = "新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    public void save(@Valid @RequestBody AddProductionLogsNameDTO addProductionLogsNameDTO) {
        log.info("params => " + addProductionLogsNameDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addProductionLogsNameDTO);
        productionLogsNameService.saveCustom(addProductionLogsNameDTO);
    }


    /**
     * 修改
     * @param updateProductionLogsNameDTO
     */
    @Operation(summary = "修改",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "logs_name", description = "日志项名称"),
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
    @Log(title = "修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public void update(@Valid @RequestBody UpdateProductionLogsNameDTO updateProductionLogsNameDTO) {
        log.info("params => " + updateProductionLogsNameDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionLogsNameDTO);
        productionLogsNameService.updateCustom(updateProductionLogsNameDTO);
    }

    /**
     * 删除日志项名称
     * @param deleteProductionLogsNameDTO
     */
    @Operation(summary = "删除日志项名称",
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
    @Log(title = "删除日志项名称",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public void delete(@Valid @RequestBody DeleteProductionLogsNameDTO deleteProductionLogsNameDTO) {
        log.info("params => " + deleteProductionLogsNameDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionLogsNameDTO);
        productionLogsNameService.delete(deleteProductionLogsNameDTO);
    }

    /**
     * 查询日志项名称
     * @return
     */
    @Operation(summary = "查询日志项名称",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "name", description = "日志项名称"),
                            })
                    ),
            }
    )
    @Log(title = "查询日志项名称",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public AjaxResult selectAll() {
        QueryWrapper<ProductionLogsName> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id, logs_name name");
        List<Map<String, Object>> list = productionLogsNameService.listMaps(queryWrapper);
        return AjaxResult.success(list);
    }
}

