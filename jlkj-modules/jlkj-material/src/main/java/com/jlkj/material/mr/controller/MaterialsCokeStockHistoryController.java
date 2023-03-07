package com.jlkj.material.mr.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.materialscokestockhistory.PageMaterialsCokeStockHistoryDTO;
import com.jlkj.material.mr.domain.MaterialsCokeStockHistory;
import com.jlkj.material.mr.service.MaterialsCokeStockHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
 * @author yzl
 * @Description 物料焦场库存调整记录
 * @create 2022年6月10日 10:12:26
 */
@Tag(name = "物料焦场库存调整记录")
@RestController
@RequestMapping("stock/materialsCokeStock")
@Slf4j
public class MaterialsCokeStockHistoryController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    MaterialsCokeStockHistoryService materialsCokeStockHistoryService;

    @Operation(summary = "查询物料焦场库存调整记录",
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
                                    @ExampleObject(name = "before_inventory", description = "调整前库存量"),
                                    @ExampleObject(name = "after_inventory", description = "调整后库存量"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                                    @ExampleObject(name = "remark", description = "备注"),

                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询物料焦场库存调整记录", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/materialsCokeStockHistoryList", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageMaterialsCokeStockHistoryDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<MaterialsCokeStockHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder()).lambda().eq(MaterialsCokeStockHistory::getCokeStockId, dto.getId());
        Page<MaterialsCokeStockHistory> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<MaterialsCokeStockHistory> list = materialsCokeStockHistoryService.page(page, queryWrapper);
        return AjaxResult.success(list);
    }

}

