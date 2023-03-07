package com.jlkj.material.mr.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialsweighingperformance.AddMaterialsWeighingPerformanceDTO;
import com.jlkj.material.mr.dto.materialsweighingperformance.GetMaterialsWeighingPerformanceDTO;
import com.jlkj.material.mr.domain.MaterialsWeighingPerformance;
import com.jlkj.material.mr.service.impl.MaterialsWeighingPerformanceServiceImpl;
import com.jlkj.material.mr.swaggerdto.MaterialsWeighingPerformanceSwagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-05-23 14:51
 */
@Tag(name = "原料检斤实绩")
@RestController
@RequestMapping("/")
@Slf4j
public class MaterialsWeighingPerformanceController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    MaterialsWeighingPerformanceServiceImpl weighingPerformanceService;

    @Operation(summary = "原料检斤实绩查询",
            parameters = {
                    @Parameter(name = "issueDateStart", description = "申请日期开始时间"),
                    @Parameter(name = "issueDateEnd", description = "申请日期结束时间"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "plan_list_no", description = "计划单号"),
                                    @ExampleObject(name = "trans_type", description = "传送类型"),
                                    @ExampleObject(name = "carrier_type", description = "运输方式"),
                                    @ExampleObject(name = "lot_no", description = "编组号"),
                                    @ExampleObject(name = "carrier_no", description = "车号"),
                                    @ExampleObject(name = "seq_no", description = "序号"),
                                    @ExampleObject(name = "mtrl_no", description = "料号"),
                                    @ExampleObject(name = "mtrl_name", description = "料号说明"),
                                    @ExampleObject(name = "from_code", description = "来源代码"),
                                    @ExampleObject(name = "from_desc", description = "来源说明"),
                                    @ExampleObject(name = "to_code", description = "去向代码"),
                                    @ExampleObject(name = "to_desc", description = "去向说明"),
                                    @ExampleObject(name = "pchr_no", description = "采购合同号"),
                                    @ExampleObject(name = "is_load", description = "是否过磅"),
                                    @ExampleObject(name = "load_station", description = "过磅点"),
                                    @ExampleObject(name = "issue_dept", description = "申请单位"),
                                    @ExampleObject(name = "issue_emp_no", description = "申请人"),
                                    @ExampleObject(name = "issue_date", description = "申请日期"),
                                    @ExampleObject(name = "is_back", description = "是否回皮"),
                                    @ExampleObject(name = "wgt_list_no", description = "磅单号"),
                                    @ExampleObject(name = "gross_wgt", description = "毛重"),
                                    @ExampleObject(name = "tart_wgt", description = "皮重"),
                                    @ExampleObject(name = "net_wgt", description = "净重")
                            }, schema = @Schema(implementation = MaterialsWeighingPerformanceSwagger.class))
                    )
            }
    )
    @Log(title = "原料检斤实绩查询", businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listMaterialsWeighingPerformance", method = RequestMethod.GET)
    public Object get(@Valid GetMaterialsWeighingPerformanceDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);

        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getIssueDateStart()) ? "1790-01-01" : dto.getIssueDateStart() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getIssueDateEnd()) ? "1790-01-01" : dto.getIssueDateEnd() + " 23:59:59");
        QueryWrapper<MaterialsWeighingPerformance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getIssueDateStart()), MaterialsWeighingPerformance::getIssueDate, start)
                .le(!StrUtil.isEmpty(dto.getIssueDateEnd()), MaterialsWeighingPerformance::getIssueDate, end);
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = weighingPerformanceService.pageMaps(page, queryWrapper);
        return AjaxResult.success(list);
    }

    @Operation(summary = "原料检斤实绩新增",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planListNo", description = "计划单号"),
                            @ExampleObject(name = "transType", description = "传送类型"),
                            @ExampleObject(name = "carrierType", description = "运输方式"),
                            @ExampleObject(name = "lotNo", description = "编组号"),
                            @ExampleObject(name = "carrierNo", description = "车号"),
                            @ExampleObject(name = "seqNo", description = "序号"),
                            @ExampleObject(name = "mtrlNo", description = "料号"),
                            @ExampleObject(name = "mtrlName", description = "料号说明"),
                            @ExampleObject(name = "fromCode", description = "来源代码"),
                            @ExampleObject(name = "fromDesc", description = "来源说明"),
                            @ExampleObject(name = "toCode", description = "去向代码"),
                            @ExampleObject(name = "toDesc", description = "去向说明"),
                            @ExampleObject(name = "pchrNo", description = "采购合同号"),
                            @ExampleObject(name = "isLoad", description = "是否过磅"),
                            @ExampleObject(name = "loadStation", description = "过磅点"),
                            @ExampleObject(name = "issueDept", description = "申请单位"),
                            @ExampleObject(name = "issueEmpNo", description = "申请人"),
                            @ExampleObject(name = "issueDate", description = "申请日期"),
                            @ExampleObject(name = "isBack", description = "是否回皮"),
                            @ExampleObject(name = "wgtListNo", description = "磅单号"),
                            @ExampleObject(name = "grossWgt", description = "毛重"),
                            @ExampleObject(name = "tartWgt", description = "皮重"),
                            @ExampleObject(name = "netWgt", description = "净重")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = ""),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "原料检斤实绩新增", businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/addMaterialsWeighingPerformance", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddMaterialsWeighingPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        MaterialsWeighingPerformance weighingPerformance = new MaterialsWeighingPerformance();
        weighingPerformance.setId(UUID.randomUUID().toString());
        weighingPerformance.setPlanListNo(dto.getPlanListNo());
        weighingPerformance.setTransType(dto.getTransType());
        weighingPerformance.setCarrierNo(dto.getCarrierNo());
        weighingPerformance.setLotNo(dto.getLotNo());
        weighingPerformance.setCarrierType(dto.getCarrierType());
        weighingPerformance.setSeqNo(dto.getSeqNo());
        weighingPerformance.setMtrlNo(dto.getMtrlNo());
        weighingPerformance.setMtrlName(dto.getMtrlName());
        weighingPerformance.setFromCode(dto.getFromCode());
        weighingPerformance.setFromDesc(dto.getFromDesc());
        weighingPerformance.setPchrNo(dto.getPchrNo());
        weighingPerformance.setIsLoad(dto.getIsLoad());
        weighingPerformance.setLoadStation(dto.getLoadStation());
        weighingPerformance.setIssueDept(dto.getIssueDept());
        weighingPerformance.setIssueEmpNo(dto.getIssueEmpNo());
        weighingPerformance.setIssueDate(DateUtil.parse(dto.getIssueDate()));
        weighingPerformance.setIsBack(dto.getIsBack());
        weighingPerformance.setWgtListNo(dto.getWgtListNo());
        weighingPerformance.setGrossWgt(dto.getGrossWgt());
        weighingPerformance.setTartWgt(dto.getTartWgt());
        weighingPerformance.setNetWgt(dto.getNetWgt());
        weighingPerformanceService.save(weighingPerformance);
        return AjaxResult.success();
    }


    @Log(title = "", businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delMaterialsWeighingPerformance", method = RequestMethod.POST, produces = "application/json")
    public Object del(@RequestBody AddMaterialsWeighingPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        MaterialsWeighingPerformance weighingPerformance = weighingPerformanceService.getOne(new LambdaQueryWrapper<MaterialsWeighingPerformance>()
                .eq(MaterialsWeighingPerformance::getPlanListNo, dto.getPlanListNo()));
        if (ObjectUtil.isNotNull(weighingPerformance)) {
            weighingPerformanceService.removeById(weighingPerformance);
        }
        return AjaxResult.success();
    }
}
