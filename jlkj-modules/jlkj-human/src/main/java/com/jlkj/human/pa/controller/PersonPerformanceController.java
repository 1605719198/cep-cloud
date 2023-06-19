package com.jlkj.human.pa.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.human.pa.service.IPersonPerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangBing
 * @Description
 * @create 2023-06-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/personPerformance")
public class PersonPerformanceController extends BaseController {

    private final IPersonPerformanceService iPersonPerformanceService;

}
