package com.jlkj.safety.si.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.datascope.annotation.ParamModel;

import com.jlkj.common.log.annotation.Log;

import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.safety.si.dto.safejobapprovalrecord.PageSafeJobApprovalRecordDTO;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
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
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
 * @author yzl
 * @Description 作业票回退记录
 * @create 2023-02-01 13:41:04.067743
 */
@Tag(name = "作业票回退记录")
@RestController
@RequestMapping("/safe/job/safeJobApprovalRecord")
@Slf4j
public class SafeJobApprovalRecordController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    @Operation(summary = "查询作业票回退记录",
            parameters = {
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "id"),
                                    @ExampleObject(name = "job_id", description = "作业证id"),
                                    @ExampleObject(name = "job_type", description = "作业票类型(1:吊装;2:动火;3:高处;4:有限空间;5:动土;6:抽堵;7:断路;8:临时)"),
                                    @ExampleObject(name = "sort", description = "序号"),
                                    @ExampleObject(name = "approval_name", description = "审批环节"),
                                    @ExampleObject(name = "approval_person_id", description = "审批人ID"),
                                    @ExampleObject(name = "approval_person_name", description = "审批人姓名"),
                                    @ExampleObject(name = "approval_time", description = "审批时间"),
                                    @ExampleObject(name = "is_pass", description = "是否通过(0:否;1:是)"),
                                    @ExampleObject(name = "content", description = "意见内容"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageSafeJobApprovalRecordDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<SafeJobApprovalRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SafeJobApprovalRecord::getJobId, dto.getJobId())
                .eq(SafeJobApprovalRecord::getJobType, dto.getJobType())
                .orderByDesc(SafeJobApprovalRecord::getApprovalTime);
        List<Map<String, Object>> list = safeJobApprovalRecordService.listMaps(queryWrapper);
        return AjaxResult.success(list);
    }


}

