package com.jlkj.material.mr.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.datascope.annotation.ParamModel;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.constant.CommonConstant;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.changelog.InsertChangeLogDTO;
import com.jlkj.material.mr.dto.materialscokestock.*;
import com.jlkj.material.mr.domain.MaterialsCokeDayStock;
import com.jlkj.material.mr.domain.MaterialsCokeStock;
import com.jlkj.material.mr.domain.MaterialsCokeStockHistory;
import com.jlkj.material.mr.service.ChangeLogService;
import com.jlkj.material.mr.service.MaterialsCokeDayStockService;
import com.jlkj.material.mr.service.MaterialsCokeStockHistoryService;
import com.jlkj.material.mr.service.MaterialsCokeStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
 * @author yzl
 * @Description 物料焦场库存
 * @create 2022年6月10日 10:12:26
 */
@Tag(name = "物料焦场库存")
@RestController
@RequestMapping("/stock/materialsCokeStock")
@Slf4j
public class MaterialsCokeStockController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    MaterialsCokeStockService materialsCokeStockService;
    @Resource
    private MaterialsCokeDayStockService materialsCokeDayStockService;
    @Autowired
    MaterialsCokeStockHistoryService materialsCokeStockHistoryService;
    @Resource
    private ChangeLogService changeLogService;

    @Operation(summary = "查询物料焦场库存",
            parameters = {
                    @Parameter(name = "categoryId", description = "物料类别"),
                    @Parameter(name = "materialsId", description = "物料名称"),
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
                                    @ExampleObject(name = "category_id", description = "物料类别id"),
                                    @ExampleObject(name = "category_name", description = "物资类别名称"),
                                    @ExampleObject(name = "materials_id", description = "物料id"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "inventory", description = "库存量"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "clear_name", description = "清零时间"),

                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询物料焦场库存", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageMaterialsCokeStockDTO pageMaterialsCokeStockDTO) {
        log.info("params => " + pageMaterialsCokeStockDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsCokeStockDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsCokeStockDTO);
        IPage<Map<String, String>> list = materialsCokeStockService.getListPage(pageMaterialsCokeStockDTO);
        return AjaxResult.success(list);
    }

    @Operation(summary = "调整库存",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "inventory", description = "库存量"),
                            @ExampleObject(name = "modifyUserId", description = "调整人编号"),
                            @ExampleObject(name = "modifyUserName", description = "调整人姓名"),
                            @ExampleObject(name = "remark", description = "备注"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "调整库存", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/adjust", method = RequestMethod.POST, produces = "application/json")
    public Object adjust(@Valid @RequestBody AdjustMaterialsCokeStockDTO adjustMaterialsCokeStockDTO) {
        log.info("params => " + adjustMaterialsCokeStockDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, adjustMaterialsCokeStockDTO);
        MaterialsCokeStock materialsCokeStock = materialsCokeStockService.getById(adjustMaterialsCokeStockDTO.getId());
        if (null != materialsCokeStock) {

            BigDecimal inventory =adjustMaterialsCokeStockDTO.getInventory().subtract(materialsCokeStock.getInventory());

            MaterialsCokeStockHistory materialsCokeStockHistory = new MaterialsCokeStockHistory();
            materialsCokeStockHistory.setId(UUID.randomUUID().toString());
            materialsCokeStockHistory.setCokeStockId(adjustMaterialsCokeStockDTO.getId());
            materialsCokeStockHistory.setBeforeInventory(materialsCokeStock.getInventory());
            materialsCokeStockHistory.setAfterInventory(adjustMaterialsCokeStockDTO.getInventory());
            materialsCokeStockHistory.setModifyUserId(adjustMaterialsCokeStockDTO.getModifyUserId());
            materialsCokeStockHistory.setModifyUserName(adjustMaterialsCokeStockDTO.getModifyUserName());
            materialsCokeStockHistory.setModifyTime(new Date());
            materialsCokeStockHistory.setRemark(adjustMaterialsCokeStockDTO.getRemark());
            materialsCokeStockHistoryService.save(materialsCokeStockHistory);

            StringBuilder content = new StringBuilder();
            if (materialsCokeStock.getInventory().compareTo(adjustMaterialsCokeStockDTO.getInventory()) != 0) {
                content.append("[库存量：").append(materialsCokeStock.getInventory().stripTrailingZeros().toPlainString()).append("->").append(adjustMaterialsCokeStockDTO.getInventory().stripTrailingZeros().toPlainString()).append("]");
            }
            if (content.length() > 0) {
                InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                insertChangeLogDTO.setModuleName("物料管理");
                insertChangeLogDTO.setFunctionName("焦炭库存管理");
                insertChangeLogDTO.setContent("修改：" + content);
                insertChangeLogDTO.setCreateUserId(adjustMaterialsCokeStockDTO.getModifyUserId());
                insertChangeLogDTO.setCreateUserName(adjustMaterialsCokeStockDTO.getModifyUserName());
                changeLogService.insertChangeLogData(insertChangeLogDTO);
            }
            materialsCokeStock.setInventory(adjustMaterialsCokeStockDTO.getInventory());
            materialsCokeStockService.updateById(materialsCokeStock);


            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            LambdaQueryWrapper<MaterialsCokeDayStock> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MaterialsCokeDayStock::getMaterialsId, materialsCokeStock.getMaterialsId());
            queryWrapper.eq(MaterialsCokeDayStock::getStatDate,formatter.format(date));
            queryWrapper.last(CommonConstant.LIMIT_ONE_ROW);
            MaterialsCokeDayStock coke = materialsCokeDayStockService.getOne(queryWrapper);
            if(null != coke){
                coke.setInventory(coke.getInventory().add(inventory));
                materialsCokeDayStockService.updateById(coke);
            }else{
                coke = new MaterialsCokeDayStock();
                coke.setId(UUID.randomUUID().toString());
                coke.setCategoryId(materialsCokeStock.getCategoryId());
                coke.setCategoryName(materialsCokeStock.getCategoryName());
                coke.setMaterialsId(materialsCokeStock.getMaterialsId());
                coke.setMaterialsName(materialsCokeStock.getMaterialsName());
                coke.setInventory(adjustMaterialsCokeStockDTO.getInventory());
                coke.setCreateTime(new Date());
                coke.setStatDate(date);
                materialsCokeDayStockService.save(coke);
            }
            return AjaxResult.success("物料焦场库存调整成功");
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "物料焦场库存不存在");
        }
    }


    @Operation(summary = "数据看板-焦炭库存-分类汇总",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "materials_id", description = "物料id"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "inventory", description = "产量"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "数据看板-焦炭库存-分类汇总", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeStock", method = RequestMethod.GET)
    public Object getCokeStock(@Validated @ParamModel GetCokeStockDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(materialsCokeStockService.getCokeStock(dto));
    }

    @Operation(summary = "焦场库存新增",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "category_id", description = "物料类别id"),
                            @ExampleObject(name = "category_name", description = "物资类别名称"),
                            @ExampleObject(name = "materials_id", description = "物料id"),
                            @ExampleObject(name = "materials_name", description = "物料名称"),
                            @ExampleObject(name = "inventory", description = "库存量"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = ""),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦场库存新增", businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/addMaterialsCokeStock", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddMaterialsCokeStockDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<MaterialsCokeStock> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MaterialsCokeStock::getMaterialsId, dto.getMaterialId());
        List<Map<String, Object>> list = materialsCokeStockService.listMaps(queryWrapper);
        if (list.size() > 0) {
            return AjaxResult.error("物料代码不可重复，请重新输入！");
        } else {
            String content = "新增期初：" +
                    "[物料类别：" + dto.getCategoryName() + "]" +
                    "[物料名称：" + dto.getMaterialName() + "]" +
                    "[库存量：" + dto.getInventory().stripTrailingZeros().toPlainString() + "]";
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("物料管理");
            insertChangeLogDTO.setFunctionName("焦炭库存管理");
            insertChangeLogDTO.setContent(content);
            insertChangeLogDTO.setCreateUserId(dto.getCreateUserId());
            insertChangeLogDTO.setCreateUserName(dto.getCreateUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);

            MaterialsCokeStock materialsCokeStock = new MaterialsCokeStock();
            materialsCokeStock.setId(UUID.randomUUID().toString());
            materialsCokeStock.setCategoryId(dto.getCategoryId());
            materialsCokeStock.setCategoryName(dto.getCategoryName());
            materialsCokeStock.setMaterialsId(dto.getMaterialId());
            materialsCokeStock.setMaterialsName(dto.getMaterialName());
            materialsCokeStock.setInventory(dto.getInventory());
            materialsCokeStock.setCreateTime(new Date());
            materialsCokeStockService.save(materialsCokeStock);
            return AjaxResult.success();
        }
    }


    @Operation(summary = "焦炭库存-焦炭入库",
            parameters = {
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦炭库存-焦炭入库", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokePutStorageList", method = RequestMethod.GET)
    public Object getCokePutStorageList(@Validated @ParamModel PageMaterialsCokeStockDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, String>> list = materialsCokeStockService.getCokePutStorageList(dto);
        return AjaxResult.success(list);
    }

    @Operation(summary = "焦炭库存-焦炭出库",
            parameters = {
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦炭库存-焦炭出库", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeOutStorageList", method = RequestMethod.GET)
    public Object getCokeOutStorageList(@Validated @ParamModel PageMaterialsCokeStockDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, String>> list = materialsCokeStockService.getCokeOutStorageList(dto);
        return AjaxResult.success(list);
    }


    @Operation(summary = "调整库存 - 增加焦炭库存量",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "materials_code", description = "物料代码"),
                            @ExampleObject(name = "inventory", description = "库存量"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "调整库存 - 增加焦炭库存量", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/increase", method = RequestMethod.POST, produces = "application/json")
    public Object increase(@Valid @RequestBody AdjustInventoryDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<MaterialsCokeStock> qw = new QueryWrapper<>();
        qw.lambda().eq(MaterialsCokeStock::getMaterialsId, dto.getMaterialCode()).last("limit 1");
        List<MaterialsCokeStock> list = materialsCokeStockService.list(qw);
        if (list.size() > 0) {
            MaterialsCokeStock materialsCokeStock = list.get(0);
            materialsCokeStock.setInventory(dto.getInventory());
            materialsCokeStockService.updateById(materialsCokeStock);
            return AjaxResult.success();
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "物料焦场库存不存在");
        }
    }

    @Operation(summary = "调整库存 - 减少焦炭库存量",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "materials_code", description = "物料代码"),
                            @ExampleObject(name = "inventory", description = "库存量"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "调整库存 - 减少焦炭库存量", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/reduce", method = RequestMethod.POST, produces = "application/json")
    public Object reduce(@Valid @RequestBody AdjustInventoryDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<MaterialsCokeStock> qw = new QueryWrapper<>();
        qw.lambda().eq(MaterialsCokeStock::getMaterialsId, dto.getMaterialCode()).last("limit 1");
        List<MaterialsCokeStock> list = materialsCokeStockService.list(qw);
        if (list.size() > 0) {
            MaterialsCokeStock materialsCokeStock = list.get(0);
            materialsCokeStock.setInventory(dto.getInventory());
            materialsCokeStockService.updateById(materialsCokeStock);
            return AjaxResult.success();
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "物料焦场库存不存在");
        }
    }


}

