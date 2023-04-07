package com.jlkj.material.mr.controller;

import com.alibaba.fastjson.JSONObject;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.materialscoalstockhistory.PageMaterialsCoalStockHistoryDTO;
import com.jlkj.material.mr.dto.materialscoalstockhistory.SendDTO;
import com.jlkj.material.mr.domain.HumanresourceOrganization;
import com.jlkj.material.mr.domain.MaterialsCoalStockHistory;
import com.jlkj.material.mr.feignclients.LogisticsFeignService;
import com.jlkj.material.mr.mapper.MaterialsCoalStockHistoryMapper;
import com.jlkj.material.mr.service.MaterialsCoalStockHistoryService;
import com.jlkj.material.mr.service.impl.HumanresourceOrganizationServiceImpl;
import com.jlkj.material.mr.vo.water.CoalWaterRateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.math.BigDecimal;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;
import static com.jlkj.common.core.constant.RabbitConstant.MATERIAL_CONSUMPTION_ROUTE_KEY;
import static com.jlkj.common.core.constant.RabbitConstant.MATERIAL_EXCHANGE;


/**
 * @author yzl
 * @Description 物料煤场库存调整记录
 * @create 2022年6月10日 10:12:26
 */
@Tag(name = "物料煤场库存调整记录")
@RestController
@RequestMapping("stock/materialsCoalStock")
@Slf4j
public class MaterialsCoalStockHistoryController {
    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    MaterialsCoalStockHistoryService materialsCoalStockHistoryService;

    @Autowired
    HumanresourceOrganizationServiceImpl organizationService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MaterialsCoalStockHistoryMapper materialsCoalStockHistoryMapper;

    @Resource
    private LogisticsFeignService logisticsFeignService;


    @Operation(summary = "查询物料煤场库存调整记录",
            parameters = {
                    @Parameter(name = "id", description = "煤场库存id"),
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
                                    @ExampleObject(name = "coal_stock_id", description = "煤场库存id"),
                                    @ExampleObject(name = "before_inventory", description = "调整前库存量"),
                                    @ExampleObject(name = "after_inventory", description = "调整后库存量"),
                                    @ExampleObject(name = "modify_user_id", description = "调整人编号"),
                                    @ExampleObject(name = "modify_user_name", description = "调整人姓名"),
                                    @ExampleObject(name = "modify_time", description = "调整时间"),
                                    @ExampleObject(name = "remark", description = "备注"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询物料煤场库存调整记录", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/materialsCoalStockHistoryList", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageMaterialsCoalStockHistoryDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<MaterialsCoalStockHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder()).lambda().eq(MaterialsCoalStockHistory::getCoalStockId, dto.getId());
        Page<MaterialsCoalStockHistory> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<MaterialsCoalStockHistory> list = materialsCoalStockHistoryService.page(page, queryWrapper);
        return AjaxResult.success(list);
    }

    @Operation(summary = "上煤手动报文",
            parameters = {
                    @Parameter(name = "organization_code", description = "部门编号"),
                    @Parameter(name = "matrlnoA", description = "料号"),
                    @Parameter(name = "stgNoA", description = "储位"),
                    @Parameter(name = "qty", description = "重量（保留整数，单位kg）"),
                    @Parameter(name = "waterRate", description = "水分（保留两位小数 \n" +
                            "单位%，-1表示没有水分）")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json")
    public Object send(@Valid @RequestBody SendDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        try {
            String waterRate = "-1";
            CoalWaterRateVO coalWaterRateVO = materialsCoalStockHistoryMapper.getCoalWaterRateData();
            if(null != coalWaterRateVO) {
                waterRate = coalWaterRateVO.getMt();
            }
            JSONObject object = new JSONObject();
            object.put("class", "com.icsc.mr.api.mrjc150Api");
            object.put("messageId", "MR58");
            object.put("actionCode", "N");
            object.put("dataSource", "cep");
            object.put("data", logisticsFeignService.getLogisticsMrPlan(
                    DateUtil.format(DateUtil.date(), "yyyyMMdd"),
                    new BigDecimal(dto.getQty()),
                    "41",
                    DateUtil.format(DateUtil.date(), "yyyyMMddHHmm") + "00001",
                    new BigDecimal(waterRate),
                    dto.getMatrlnoA(),
                    "carrierType_C_" + dto.getMatrlnoA(),
                    "clent-test"
            ));
            log.info("send mq message:{}:{} => {}", MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(object));
            rabbitTemplate.convertAndSend(MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(object));
        } catch (Exception e) {
            log.info(e.toString());
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    private String getSeqNo(Integer no) {
        // 0 代表前面补充0
        // 5 代表长度为5
        // d 代表参数为正数型
        return String.format("%05d", no);
    }
}

