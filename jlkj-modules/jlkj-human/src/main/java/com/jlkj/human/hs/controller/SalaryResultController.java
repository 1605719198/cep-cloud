package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.dto.SalaryResultDTO;
import com.jlkj.human.hs.service.ISalaryResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 薪资计算结果Controller
 *
 * @author 266861
 * @date 2023-06-20
 */
@RestController
@RequestMapping("/salaryResult")
public class SalaryResultController extends BaseController
{
    @Autowired
    private ISalaryResultService salaryResultService;

    /**
    * @Description 用于薪资计算结果页面列表展示
    * @param salaryResultDTO 薪资结果DTO
    * @return 返回薪资结果列表
    * @author 266861
    * @date 2023/6/21 14:45
    **/
    @RequiresPermissions("human:salaryResult:list")
    @GetMapping("/list")
    public TableDataInfo list(SalaryResultDTO salaryResultDTO)
    {
        startPage();
        List<SalaryResultDTO> list = salaryResultService.selectSalaryResultList(salaryResultDTO);
        return getDataTable(list);
    }


    /**
    * @Description 薪资计算结果详情
    * @param id 薪资计算结果ID（员工id+'-'+年月）
    * @return 薪资计算结果详情
    * @author 266861
    * @date 2023/6/21 14:47
    **/
    @RequiresPermissions("human:salaryResult:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(salaryResultService.selectSalaryResultById(id));
    }

}
