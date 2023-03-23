package com.jlkj.safety.si.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.service.CommonTreeService;
import com.jlkj.safety.si.utils.ResponseUtil;
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
 * @Description 公共-树型列表
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/common/tree")
@Slf4j
@Api(tags = "1.3 公共-树型列表")
public class CommonTreeController {

    @Resource
    CommonTreeService commonTreeService;

    @ApiOperation(value = "施工人员树", notes = "")
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id: '树id'\n"+
                                    "pid: '上级树id'\n"+
                                    "label: '标题'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/constructionunit/person", method = RequestMethod.GET)
    public Object getSafeConstructionUnitPersonList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(commonTreeService.getSafeConstructionUnitPersonTree(params));
    }

    @ApiOperation(value = "施工单位树", notes = "")
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id: '树id'\n"+
                                    "pid: '上级树id'\n"+
                                    "label: '标题'\n" +
                                    "type:所属单位类型(1本厂,2外委)\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/constructionunit", method = RequestMethod.GET)
    public Object getSafeConstructionUnitList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(ResponseUtil.toTree(commonTreeService.getSafeConstructionUnitTree(params)));
    }

    @ApiOperation(value = "知识类别树", notes = "")
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id: '树id'\n"+
                                            "pid: '上级树id'\n"+
                                            "label: '标题'\n"+
                                            "isOpen: '是否展开(true-展开,false-关闭)'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/knowledgecategory", method = RequestMethod.GET)
    public Object getSafeKnowledgeCategoryTree(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(ResponseUtil.toTree(commonTreeService.getSafeKnowledgeCategoryTree(params)));
    }
}
