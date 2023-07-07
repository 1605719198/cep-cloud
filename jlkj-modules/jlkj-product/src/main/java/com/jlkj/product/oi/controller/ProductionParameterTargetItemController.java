package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.MaterialsCode;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.dto.productionparametertargetitem.*;
import com.jlkj.product.oi.service.*;
import com.jlkj.product.oi.service.impl.MaterialsCodeServiceImpl;
import com.jlkj.product.oi.service.impl.ProductionParameterTargetItemServiceImpl;
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
import java.util.List;
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-04-19 8:45
 */
@Tag(name = "参数维护-指标项")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionParameterTargetItemController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionParameterTargetItemService productionParameterTargetItemService;

    @Autowired
    MaterialsCodeServiceImpl materialsCodeService;


    @Operation(summary = "获取指标项列表",
            parameters = {
                    @Parameter(name = "targetItemTypeId", description = "指标类型id  0:全部；1:焦化；2：发电"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "获取指标项列表",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionTargetItem", method = RequestMethod.GET)
    public Object getSysRoleList(@Valid GetProductionParameterTargetItemDTO productionParameterTargetItemDTO) {
        log.info("params => " + productionParameterTargetItemDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, productionParameterTargetItemDTO);
        List<ProductionParameterTargetItem> list =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(productionParameterTargetItemDTO.getTargetItemTypeId() != 0, ProductionParameterTargetItem::getTargetItemTypeId, productionParameterTargetItemDTO.getTargetItemTypeId()));
        return AjaxResult.success(list);
    }

    @Operation(summary = "查询指标项",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段"),
                    @Parameter(name = "target_item_type_id", description = "指标类型id(0全部,1焦化,2发电)")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识"),
                                    @ExampleObject(name = "target_item_type_id", description = "指标类型id"),
                                    @ExampleObject(name = "target_item_type_name", description = "指标类型名称"),
                                    @ExampleObject(name = "target_item_name", description = "指标名称"),
                                    @ExampleObject(name = "calc_unit_id", description = "计算单位id"),
                                    @ExampleObject(name = "calc_unit_name", description = "计算单位名称"),
                                    @ExampleObject(name = "measuring_unit", description = "计量单位"),
                                    @ExampleObject(name = "create_user_id", description = "创建人id"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                                    @ExampleObject(name = "material_id", description = "物料代码id"),
                                    @ExampleObject(name = "material_name", description = "物料名称"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询指标项",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listProductionTargetItem", method = RequestMethod.GET)
    public Object listProductionTargetItem(@Validated @ParamModel PageProductionParameterTargetItemDTO pageProductionParameterTargetItemDTO) {
        log.info("params => " + pageProductionParameterTargetItemDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionParameterTargetItemDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionParameterTargetItemDTO);
        IPage<Map<String, String>> list = productionParameterTargetItemService.getListPage(pageProductionParameterTargetItemDTO);
        return AjaxResult.success(list);
    }

    @Operation(summary = "新增指标项",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "target_item_type_id", description = "指标类型id"),
                            @ExampleObject(name = "target_item_name", description = "指标名称"),
                            @ExampleObject(name = "calc_unit_id", description = "计算单位id"),
                            @ExampleObject(name = "measuring_unit", description = "计量单位"),
                            @ExampleObject(name = "material_id", description = "物料代码id"),
                            @ExampleObject(name = "material_name", description = "物料名称"),
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
    @Log(title = "新增指标项",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/addProductionTargetItem", method = RequestMethod.POST, produces = "application/json")
    public Object addProductionTargetItem(@Valid @RequestBody AddProductionParameterTargetItemDTO addProductionParameterTargetItemDTO) {
        log.info("params => " + addProductionParameterTargetItemDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addProductionParameterTargetItemDTO);
        return productionParameterTargetItemService.addProductionTargetItem(addProductionParameterTargetItemDTO);
    }

    @Operation(summary = "修改指标项",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "target_item_type_id", description = "指标类型id"),
                            @ExampleObject(name = "target_item_name", description = "指标名称"),
                            @ExampleObject(name = "calc_unit_id", description = "计算单位id"),
                            @ExampleObject(name = "measuring_unit", description = "计量单位"),
                            @ExampleObject(name = "material_id", description = "物料代码id"),
                            @ExampleObject(name = "material_name", description = "物料名称"),
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
    @Log(title = "修改指标项",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/editProductionTargetItem", method = RequestMethod.POST, produces = "application/json")
    public Object editProductionTargetItem(@Valid @RequestBody UpdateProductionParameterTargetItemDTO updateProductionParameterTargetItemDTO) {
        log.info("params => " + updateProductionParameterTargetItemDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionParameterTargetItemDTO);
        return productionParameterTargetItemService.editProductionTargetItem(updateProductionParameterTargetItemDTO);
    }

    @Operation(summary = "删除指标项",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
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
    @Log(title = "删除指标项",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delProductionTargetItem", method = RequestMethod.POST, produces = "application/json")
    public Object delProductionTargetItem(@Valid @RequestBody DeleteProductionParameterTargetItemDTO deleteProductionParameterTargetItemDTO) {
        log.info("params => " + deleteProductionParameterTargetItemDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionParameterTargetItemDTO);
        return productionParameterTargetItemService.delProductionTargetItem(deleteProductionParameterTargetItemDTO);
    }


    @Operation(summary = "查询物料代码",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识"),
                                    @ExampleObject(name = "category_id", description = "类别编号"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "materials_code", description = "物料代码"),
                                    @ExampleObject(name = "measurement_unit", description = "计量单位"),
                                    @ExampleObject(name = "delete_flag", description = "删除标识"),
                                    @ExampleObject(name = "create_user_id", description = "创建人id"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询物料代码",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialCode", method = RequestMethod.GET)
    public Object listProductionTargetItem() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        List<Map<String, Object>> list = materialsCodeService.listMaps(new LambdaQueryWrapper<MaterialsCode>()
                .eq(MaterialsCode::getDeleteFlag, 0));
        return AjaxResult.success(list);
    }


}
