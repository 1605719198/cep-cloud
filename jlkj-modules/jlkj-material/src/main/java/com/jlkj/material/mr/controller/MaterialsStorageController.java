package com.jlkj.material.mr.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialsstorage.AddMaterialsStorageDTO;
import com.jlkj.material.mr.dto.materialsstorage.GetMaterialsStorageDTO;
import com.jlkj.material.mr.dto.materialsweighingplan.DelMaterialsWeighingPlanDTO;
import com.jlkj.material.mr.domain.MaterialsStorage;
import com.jlkj.material.mr.service.impl.MaterialsStorageServiceImpl;
import com.jlkj.material.mr.service.impl.MaterialsWeighingPlanServiceImpl;
import com.jlkj.material.mr.swaggerdto.MaterialsStorageSwagger;
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
 * @create 2022-05-17 9:36
 */
@Tag(name = "原料入储")
@RestController
@RequestMapping("/")
@Slf4j
public class MaterialsStorageController {


    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    MaterialsStorageServiceImpl materialsStorageService;

    @Autowired
    MaterialsWeighingPlanServiceImpl weighingPlanService;

    @Operation(summary = "入储查询",
            parameters = {
                    @Parameter(name = "createDateStart", description = "开始时间"),
                    @Parameter(name = "createDateEnd", description = "结束时间"),
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
                                    @ExampleObject(name = "stg_no", description = "储位代码"),
                                    @ExampleObject(name = "stg_name", description = "储位说明"),
                                    @ExampleObject(name = "chk_no", description = "组批号"),
                                    @ExampleObject(name = "trans_type", description = "传送类型"),
                                    @ExampleObject(name = "carrier_type", description = "运输方式"),
                                    @ExampleObject(name = "recv_date", description = "到货日期"),
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
                                    @ExampleObject(name = "net_wgt", description = "净重"),
                                    @ExampleObject(name = "load_date_time", description = "检斤实绩时间"),
                                    @ExampleObject(name = "create_time", description = "创建时间")
                            }, schema = @Schema(implementation = MaterialsStorageSwagger.class))
                    )
            }
    )
    @Log(title = "入储查询", businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listMaterialsStorage", method = RequestMethod.GET)
    public Object get(@Valid GetMaterialsStorageDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);

        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getCreateDateStart()) ? "1790-01-01" : dto.getCreateDateStart() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getCreateDateEnd()) ? "1790-01-01" : dto.getCreateDateEnd() + " 23:59:59");
        QueryWrapper<MaterialsStorage> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getCreateDateStart()), MaterialsStorage::getRecvDate, start)
                .le(!StrUtil.isEmpty(dto.getCreateDateEnd()), MaterialsStorage::getRecvDate, end);
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = materialsStorageService.pageMaps(page, queryWrapper);
        return AjaxResult.success(list);
    }

    @Operation(summary = "入储新增",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planListNo", description = "计划单号"),
                            @ExampleObject(name = "lotNo", description = "编组号"),
                            @ExampleObject(name = "carrierNo", description = "车号"),
                            @ExampleObject(name = "seqNo", description = "储位代码"),
                            @ExampleObject(name = "stgName", description = "储位说明"),
                            @ExampleObject(name = "mtrlNo", description = "料号"),
                            @ExampleObject(name = "mtrlName", description = "料号说明"),
                            @ExampleObject(name = "toCode", description = "收货单位代码"),
                            @ExampleObject(name = "toDesc", description = "收货单位代码"),
                            @ExampleObject(name = "grossWgt", description = "毛重"),
                            @ExampleObject(name = "createDate", description = "创建日期")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = ""),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "入储新增", businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/addMaterialsStorage", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddMaterialsStorageDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        MaterialsStorage materialsStorage = new MaterialsStorage();
        materialsStorage.setId(UUID.randomUUID().toString());
        materialsStorage.setPlanListNo(dto.getPlanListNo());
        materialsStorage.setStgNo(dto.getStgNo());
        materialsStorage.setStgName(dto.getStgName());
        materialsStorage.setChkNo(dto.getChkNo());
        materialsStorage.setTransType(dto.getTransType());
        materialsStorage.setCarrierType(dto.getCarrierType());
        materialsStorage.setRecvDate(DateUtil.parseDate(DateUtil.formatDate(DateUtil.parse(dto.getRecvDate()))));
        materialsStorage.setLotNo(dto.getLotNo());
        materialsStorage.setCarrierNo(dto.getCarrierNo());
        materialsStorage.setSeqNo(dto.getSeqNo());
        materialsStorage.setMtrlNo(dto.getMtrlNo());
        materialsStorage.setMtrlName(dto.getMtrlName());
        materialsStorage.setFromCode(dto.getFromCode());
        materialsStorage.setFromDesc(dto.getFromDesc());
        materialsStorage.setToCode(dto.getToCode());
        materialsStorage.setToDesc(dto.getToDesc());
        materialsStorage.setPchrNo(dto.getPchrNo());
        materialsStorage.setIsLoad(dto.getIsLoad());
        materialsStorage.setLoadStation(dto.getLoadStation());
        materialsStorage.setIssueDept(dto.getIssueDept());
        materialsStorage.setIssueEmpNo(dto.getIssueEmpNo());
        materialsStorage.setIssueDate(DateUtil.parse(dto.getIssueDate()));
        materialsStorage.setIsBack(dto.getIsBack());
        materialsStorage.setWgtListNo(dto.getWgtListNo());
        materialsStorage.setGrossWgt(dto.getGrossWgt());
        materialsStorage.setTartWgt(dto.getTartWgt());
        materialsStorage.setNetWgt(dto.getNetWgt());
        materialsStorage.setLoadDateTime(DateUtil.parse(dto.getLoadDateTime()));
        materialsStorage.setCreateTime(DateUtil.date());
        materialsStorageService.save(materialsStorage);
        return AjaxResult.success();
    }

    @Operation(summary = "入储删除",
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
    @Log(title = "入储删除", businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delMaterialsStorage", method = RequestMethod.POST, produces = "application/json")
    public Object delete(@Valid @RequestBody DelMaterialsWeighingPlanDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        MaterialsStorage materialsStorage = materialsStorageService.getById(dto.getId());
        if (materialsStorage == null) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "当前记录不存在或已删除");
        }
        materialsStorageService.removeById(materialsStorage);
        return AjaxResult.success();
    }
}
