package com.jlkj.material.mr.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialsweighingplan.AddMaterialsWeighingPlanDTO;
import com.jlkj.material.mr.dto.materialsweighingplan.DelMaterialsWeighingPlanDTO;
import com.jlkj.material.mr.dto.materialsweighingplan.GetMaterialsWeighingPlanDTO;
import com.jlkj.material.mr.domain.MaterialsWeighingPlan;
import com.jlkj.material.mr.service.impl.MaterialsWeighingPlanServiceImpl;
import com.jlkj.material.mr.swaggerdto.MaterialsWeighingPlanSwagger;
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
 * @create 2022-05-16 15:40
 */
@Tag(name = "原料检斤计划")
@RestController
@RequestMapping("/")
@Slf4j
public class MaterialsWeighingPlanController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    MaterialsWeighingPlanServiceImpl weighingPlanService;

    @Operation(summary = "原料检斤计划查询",
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
                                    @ExampleObject(name = "is_back", description = "是否回皮")
                            }, schema = @Schema(implementation = MaterialsWeighingPlanSwagger.class))
                    )
            }
    )
    @Log(title = "原料检斤计划查询", businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listMaterialsWeighingPlan", method = RequestMethod.GET)
    public Object get(@Valid GetMaterialsWeighingPlanDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);

        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getIssueDateStart()) ? "1790-01-01" : dto.getIssueDateStart() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getIssueDateEnd()) ? "1790-01-01" : dto.getIssueDateEnd() + " 23:59:59");
        QueryWrapper<MaterialsWeighingPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getIssueDateStart()), MaterialsWeighingPlan::getIssueDate, start)
                .le(!StrUtil.isEmpty(dto.getIssueDateEnd()), MaterialsWeighingPlan::getIssueDate, end);
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = weighingPlanService.pageMaps(page, queryWrapper);
        return AjaxResult.success(list);
    }

    @Operation(summary = "原料检斤计划新增",
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
                            @ExampleObject(name = "isBack", description = "是否回皮")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = ""),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "原料检斤计划新增", businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/addMaterialsWeighingPlan", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddMaterialsWeighingPlanDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        MaterialsWeighingPlan materialsWeighingPlan = new MaterialsWeighingPlan();
        materialsWeighingPlan.setId(UUID.randomUUID().toString());
        materialsWeighingPlan.setPlanListNo(dto.getPlanListNo());
        materialsWeighingPlan.setTransType(dto.getTransType());
        materialsWeighingPlan.setCarrierNo(dto.getCarrierNo());
        materialsWeighingPlan.setLotNo(dto.getLotNo());
        materialsWeighingPlan.setCarrierType(dto.getCarrierType());
        materialsWeighingPlan.setSeqNo(dto.getSeqNo());
        materialsWeighingPlan.setMtrlNo(dto.getMtrlNo());
        materialsWeighingPlan.setMtrlName(dto.getMtrlName());
        materialsWeighingPlan.setFromCode(dto.getFromCode());
        materialsWeighingPlan.setFromDesc(dto.getFromDesc());
        materialsWeighingPlan.setPchrNo(dto.getPchrNo());
        materialsWeighingPlan.setIsLoad(dto.getIsLoad());
        materialsWeighingPlan.setLoadStation(dto.getLoadStation());
        materialsWeighingPlan.setIssueDept(dto.getIssueDept());
        materialsWeighingPlan.setIssueEmpNo(dto.getIssueEmpNo());
        materialsWeighingPlan.setIssueDate(DateUtil.parse(dto.getIssueDate()));
        materialsWeighingPlan.setIsBack(dto.getIsBack());
        weighingPlanService.save(materialsWeighingPlan);
        return AjaxResult.success();
    }

    @Operation(summary = "原料检斤计划删除",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "已存在或不存在消息"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "原料检斤计划删除", businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delMaterialsWeighingPlan", method = RequestMethod.POST, produces = "application/json")
    public Object delete(@Valid @RequestBody DelMaterialsWeighingPlanDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        MaterialsWeighingPlan weighingPlan = weighingPlanService.getById(dto.getId());
        if (weighingPlan == null) {
            return AjaxResult.error(HttpStatus.NO_CONTENT, "当前记录不存在或已删除");
        }
        weighingPlanService.removeById(weighingPlan);
        return AjaxResult.success();
    }

    @Log(title = "原料检斤计划删除", businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delMaterialsWeighingPlanByNo", method = RequestMethod.POST, produces = "application/json")
    public Object delByNo(@Valid @RequestBody AddMaterialsWeighingPlanDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        MaterialsWeighingPlan weighingPlan = weighingPlanService.getOne(new LambdaQueryWrapper<MaterialsWeighingPlan>()
                .eq(MaterialsWeighingPlan::getPlanListNo, dto.getPlanListNo()));
        if (weighingPlan != null) {
            weighingPlanService.removeById(weighingPlan);
        }
        return AjaxResult.success();
    }
}
