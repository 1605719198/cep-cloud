package com.jlkj.safety.si.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.service.CommonListService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 公共-数据列表
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/common/list")
@Slf4j
@Api(tags = "1.2 公共-数据列表")
public class CommonListController {

    @Resource
    CommonListService commonListService;

    @ApiOperation(value = "施工单位-施工人员列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id_list", value = "记录ID列表", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "params", value = "{\"id_list\":\"1,2\"}", dataTypeClass = Object.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "construction_unit_id:所属单位ID\n" +
                                    "construction_unit_name:所属单位名称\n" +
                                    "type:所属单位类型(1本厂,2外委)\n" +
                                    "person_id:人员ID\n" +
                                    "person_name:人员姓名\n" +
                                    "work_type_name:工种\n" +
                                    "work_card_code:作业证编号"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/constructionunit/person/list", method = RequestMethod.GET)
    public Object getSafeConstructionUnitPersonList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(commonListService.getSafeConstructionUnitPersonList(params));
    }



}
