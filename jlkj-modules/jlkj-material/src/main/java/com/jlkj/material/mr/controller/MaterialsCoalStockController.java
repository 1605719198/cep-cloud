package com.jlkj.material.mr.controller;

import cn.hutool.core.util.IdUtil;
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
import com.jlkj.material.mr.dto.materialscoalstock.AddMaterialsCoalStockDTO;
import com.jlkj.material.mr.dto.materialscoalstock.AdjustMaterialsCoalStockDTO;
import com.jlkj.material.mr.dto.materialscoalstock.GetCoalStockDTO;
import com.jlkj.material.mr.dto.materialscoalstock.PageMaterialsCoalStockDTO;
import com.jlkj.material.mr.domain.*;
import com.jlkj.material.mr.service.ChangeLogService;
import com.jlkj.material.mr.service.MaterialsCoalDayStockService;
import com.jlkj.material.mr.service.MaterialsCoalStockHistoryService;
import com.jlkj.material.mr.service.MaterialsCoalStockService;
import com.jlkj.material.mr.vo.materialscoalstock.ListHomeMaterialAnalysisListVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * @Description 物料煤场库存
 * @create 2022年6月10日 10:12:26
 */
@Tag(name = "物料煤场库存")
@RestController
@RequestMapping("/stock/materialsCoalStock")
@Slf4j
public class MaterialsCoalStockController {
    @Resource
    HttpServletRequest httpServletRequest;
    @Resource
    MaterialsCoalStockService materialsCoalStockService;
    @Resource
    private MaterialsCoalDayStockService materialsCoalDayStockService;
    @Resource
    MaterialsCoalStockHistoryService materialsCoalStockHistoryService;
    @Resource
    private ChangeLogService changeLogService;

    @Operation(summary = "查询物料煤场库存",
            parameters = {
                    @Parameter(name = "categoryId", description = "物料类别"),
                    @Parameter(name = "materialsId", description = "物料名称"),
                    @Parameter(name = "storageSpacesId", description = "储位编号"),
                    @Parameter(name = "createStartTime", description = "创建时间[开始]"),
                    @Parameter(name = "createEndTime", description = "创建时间[结束]"),
                    @Parameter(name = "clearStartTime", description = "清零时间[开始]"),
                    @Parameter(name = "clearEndTime", description = "清零时间[结束]"),
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
                                    @ExampleObject(name = "storage_spaces_id", description = "储位编号"),
                                    @ExampleObject(name = "storage_spaces_name", description = "储位名称"),
                                    @ExampleObject(name = "inventory", description = "库存量"),
                                    @ExampleObject(name = "today_unloaded_weight", description = "今日卸煤净重"),
                                    @ExampleObject(name = "today_loading_weight", description = "今日煤仓上煤"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "clear_name", description = "清零时间"),

                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询物料煤场库存", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO) {
        log.info("params => " + pageMaterialsCoalStockDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsCoalStockDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsCoalStockDTO);
        IPage<Map<String, String>> list = materialsCoalStockService.getListPage(pageMaterialsCoalStockDTO);
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
    public Object adjust(@Valid @RequestBody AdjustMaterialsCoalStockDTO adjustMaterialsCoalStockDTO) {
        log.info("params => " + adjustMaterialsCoalStockDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, adjustMaterialsCoalStockDTO);
        MaterialsCoalStock materialsCoalStock = materialsCoalStockService.getById(adjustMaterialsCoalStockDTO.getId());
        if (null != materialsCoalStock) {

            BigDecimal inventory =adjustMaterialsCoalStockDTO.getInventory().subtract(materialsCoalStock.getInventory());

            MaterialsCoalStockHistory materialsCoalStockHistory = new MaterialsCoalStockHistory();
            materialsCoalStockHistory.setId(IdUtil.randomUUID());
            materialsCoalStockHistory.setCoalStockId(adjustMaterialsCoalStockDTO.getId());
            materialsCoalStockHistory.setBeforeInventory(materialsCoalStock.getInventory());
            materialsCoalStockHistory.setAfterInventory(adjustMaterialsCoalStockDTO.getInventory());
            materialsCoalStockHistory.setModifyUserId(adjustMaterialsCoalStockDTO.getModifyUserId());
            materialsCoalStockHistory.setModifyUserName(adjustMaterialsCoalStockDTO.getModifyUserName());
            materialsCoalStockHistory.setModifyTime(new Date());
            materialsCoalStockHistory.setRemark(adjustMaterialsCoalStockDTO.getRemark());
            materialsCoalStockHistoryService.save(materialsCoalStockHistory);

            StringBuilder content = new StringBuilder();
            if (materialsCoalStock.getInventory().compareTo(adjustMaterialsCoalStockDTO.getInventory()) != 0) {
                content.append("[库存量：").append(materialsCoalStock.getInventory().stripTrailingZeros().toPlainString()).append("->").append(adjustMaterialsCoalStockDTO.getInventory().stripTrailingZeros().toPlainString()).append("]");
            }
            if (content.length() > 0) {
                InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                insertChangeLogDTO.setModuleName("物料管理");
                insertChangeLogDTO.setFunctionName("煤场库存管理");
                insertChangeLogDTO.setContent("修改：" + content);
                insertChangeLogDTO.setCreateUserId(adjustMaterialsCoalStockDTO.getModifyUserId());
                insertChangeLogDTO.setCreateUserName(adjustMaterialsCoalStockDTO.getModifyUserName());
                changeLogService.insertChangeLogData(insertChangeLogDTO);
            }
            materialsCoalStock.setInventory(adjustMaterialsCoalStockDTO.getInventory());
            materialsCoalStockService.updateById(materialsCoalStock);

            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());

            LambdaQueryWrapper<MaterialsCoalDayStock> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MaterialsCoalDayStock::getMaterialsId, materialsCoalStock.getMaterialsId());
            queryWrapper.eq(MaterialsCoalDayStock::getStatDate,formatter.format(date));
            queryWrapper.last(CommonConstant.LIMIT_ONE_ROW);
            MaterialsCoalDayStock coal = materialsCoalDayStockService.getOne(queryWrapper);
            if(null != coal){
                coal.setInventory(coal.getInventory().add(inventory));
                materialsCoalDayStockService.updateById(coal);
            }else{
                coal = new MaterialsCoalDayStock();
                coal.setId(IdUtil.randomUUID());
                coal.setCategoryId(materialsCoalStock.getCategoryId());
                coal.setCategoryName(materialsCoalStock.getCategoryName());
                coal.setMaterialsId(materialsCoalStock.getMaterialsId());
                coal.setMaterialsName(materialsCoalStock.getMaterialsName());
                coal.setStorageSpacesId(materialsCoalStock.getStorageSpacesId());
                coal.setStorageSpacesName(materialsCoalStock.getStorageSpacesName());
                coal.setInventory(adjustMaterialsCoalStockDTO.getInventory());
                coal.setCreateTime(new Date());
                coal.setStatDate(date);
                materialsCoalDayStockService.save(coal);
            }

            return AjaxResult.success("物料煤场库存调整成功");
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "物料煤场库存不存在");
        }
    }

    @Operation(summary = "生产看板-原煤库存-分类汇总",
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
    @Log(title = "查询生产看板-原煤库存-分类汇总", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCoalStock", method = RequestMethod.GET)
    public Object getCoalStock(@Valid @ParamModel GetCoalStockDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(materialsCoalStockService.getCoalStock(dto));
    }

    @Operation(summary = "首页-物料分析",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListHomeMaterialAnalysisListVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询首页-物料分析", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getHomeMaterialAnalysisList", method = RequestMethod.GET)
    public Object getHomeMaterialAnalysisList() {
        return materialsCoalStockService.getHomeMaterialAnalysisList();
    }


    @Operation(summary = "煤场库存-上煤记录",
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
                                    @ExampleObject(description = "主键标识;UUID", name = "id"),
                                    @ExampleObject(description = "班次", name = "shift_name"),
                                    @ExampleObject(description = "班别", name = "class_name"),
                                    @ExampleObject(description = "上煤仓号", name = "conveying_coal_warehouse_number"),
                                    @ExampleObject(description = "堆取料机号", name = "stacker_reclaimer_number"),
                                    @ExampleObject(description = "料仓编号", name = "silo_number"),
                                    @ExampleObject(description = "料仓名称", name = "silo_name"),
                                    @ExampleObject(description = "储位编号", name = "slot_number"),
                                    @ExampleObject(description = "储位名称", name = "slot_name"),
                                    @ExampleObject(description = "上煤开始", name = "start_time"),
                                    @ExampleObject(description = "上煤结束", name = "end_time"),
                                    @ExampleObject(description = "上煤时长", name = "duration"),
                                    @ExampleObject(description = "上煤重量", name = "material_weight"),
                                    @ExampleObject(description = "物料编号", name = "material_number"),
                                    @ExampleObject(description = "物料名称", name = "material_name"),
                                    @ExampleObject(description = "创建时间", name = "create_time"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询煤场库存-上煤记录", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getConveyingCoalList", method = RequestMethod.GET)
    public Object getConveyingCoalList(@Validated @ParamModel PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO) {
        log.info("params => " + pageMaterialsCoalStockDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsCoalStockDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsCoalStockDTO);
        IPage<Map<String, String>> list = materialsCoalStockService.getConveyingCoalList(pageMaterialsCoalStockDTO);
        return AjaxResult.success(list);
    }

    @Operation(summary = "煤场库存-卸煤记录",
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
                                    @ExampleObject(name = "id", description = "主键标识UUID"),
                                    @ExampleObject(name = "plan_id", description = "检斤计划编号"),
                                    @ExampleObject(name = "plan_date", description = "检斤计划日期"),
                                    @ExampleObject(name = "supplier_number", description = "供货单位编号"),
                                    @ExampleObject(name = "supplier_name", description = "供货单位名称"),
                                    @ExampleObject(name = "shipping_method", description = "运输方式"),
                                    @ExampleObject(name = "vehicle_number", description = "车号"),
                                    @ExampleObject(name = "storage_spaces_id", description = "erp储位编号"),
                                    @ExampleObject(name = "material_number", description = "物料编号"),
                                    @ExampleObject(name = "material_name", description = "物料名称"),
                                    @ExampleObject(name = "material_code", description = "物料代码"),
                                    @ExampleObject(name = "material_weight", description = "物料重量"),
                                    @ExampleObject(name = "shift_name", description = "班次"),
                                    @ExampleObject(name = "class_name", description = "班别"),
                                    @ExampleObject(name = "unloading_time", description = "卸车时间"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                                    @ExampleObject(name = "storage_maintain_id", description = "平台储位编号"),
                                    @ExampleObject(name = "storage_maintain_space_name", description = "平台储位名称"),
                                    @ExampleObject(name = "storage_spaces_name", description = "erp储位说明"),
                                    @ExampleObject(name = "chk_no", description = "组批号"),
                                    @ExampleObject(name = "recv_date", description = "到货日期"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询煤场库存-卸煤记录", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getHCoalUnloadingList", method = RequestMethod.GET)
    public Object getCoalUnloadingList(@Validated @ParamModel PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO) {
        log.info("params => " + pageMaterialsCoalStockDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsCoalStockDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsCoalStockDTO);
        IPage<Map<String, String>> list = materialsCoalStockService.getCoalUnloadingList(pageMaterialsCoalStockDTO);
        return AjaxResult.success(list);
    }

    @Operation(summary = "煤场库存新增",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "category_id", description = "物料类别id"),
                            @ExampleObject(name = "category_name", description = "物资类别名称"),
                            @ExampleObject(name = "materials_id", description = "物料id"),
                            @ExampleObject(name = "materials_name", description = "物料名称"),
                            @ExampleObject(name = "storage_spaces_id", description = "储位编号"),
                            @ExampleObject(name = "storage_spaces_name", description = "储位名称"),
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
    @Log(title = "煤场库存新增", businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/addMaterialsCoalStock", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddMaterialsCoalStockDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);

        QueryWrapper<MaterialsCoalStock> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MaterialsCoalStock::getMaterialsSmallCode, dto.getMaterialsSmallCode());
        List<Map<String, Object>> list = materialsCoalStockService.listMaps(queryWrapper);
        if (list.size() > 0) {
            return AjaxResult.error("小煤种不可重复，请重新输入！");
        } else {
            String content = "新增期初：" +
                    "[物料类别：" + dto.getCategoryName() + "]" +
                    "[物料代码：" + dto.getMaterialName() + "]" +
                    "[小煤种：" + dto.getMaterialsSmallName() + "]" +
                    "[储位名称：" + dto.getStorageSpaceName() + "]" +
                    "[库存量：" + dto.getInventory().stripTrailingZeros().toPlainString() + "]";
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("物料管理");
            insertChangeLogDTO.setFunctionName("煤场库存管理");
            insertChangeLogDTO.setContent(content);
            insertChangeLogDTO.setCreateUserId(dto.getModifyUserId());
            insertChangeLogDTO.setCreateUserName(dto.getModifyUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);

            MaterialsCoalStock materialsCoalStock = new MaterialsCoalStock();
            materialsCoalStock.setId(IdUtil.randomUUID());
            materialsCoalStock.setCategoryId(dto.getCategoryId());
            materialsCoalStock.setCategoryName(dto.getCategoryName());
            materialsCoalStock.setMaterialsId(dto.getMaterialId());
            materialsCoalStock.setMaterialsName(dto.getMaterialName());
            materialsCoalStock.setMaterialsSmallCode(dto.getMaterialsSmallCode());
            materialsCoalStock.setMaterialsSmallName(dto.getMaterialsSmallName());
            materialsCoalStock.setStorageSpacesId(dto.getStorageSpaceId());
            materialsCoalStock.setStorageSpacesName(dto.getStorageSpaceName());
            materialsCoalStock.setInventory(dto.getInventory());
            materialsCoalStock.setCreateTime(new Date());
            materialsCoalStockService.save(materialsCoalStock);
            return AjaxResult.success();
        }
    }
}

