package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.NightDuty;
import com.jlkj.human.hd.dto.NightDutyDTO;
import com.jlkj.human.hd.service.INightDutyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-30 9:40
 */
@RestController
@RequestMapping("/nightDuty")
public class NightDutyController extends BaseController {

    @Autowired
    INightDutyService iNightDutyService;

    /**
     * 夜班查询列表
     */
    @RequiresPermissions("human:nightDuty:list")
    @Log(title = "夜班查询列表",businessType = BusinessType.OTHER)
    @Operation(summary = "夜班查询列表")
    @GetMapping("/list")
    public Object listNightDuty(NightDutyDTO nightDutyDTO) {
        startPage();
        NightDuty nightDuty = new NightDuty();
        BeanUtils.copyProperties(nightDutyDTO, nightDuty);
        List<NightDuty> list = iNightDutyService.lambdaQuery()
                .eq(StringUtils.isNotBlank(nightDuty.getCompId()), NightDuty::getCompId, nightDuty.getCompId())
                .eq(NightDuty::getEmpNo, nightDuty.getEmpNo())
                .between(NightDuty::getShiftDate, nightDutyDTO.getStartTime(), nightDutyDTO.getEndTime()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    @Log(title = "夜班资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:nightDuty:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<NightDuty> util = new ExcelUtil<NightDuty>(NightDuty.class);
        List<NightDuty> nightDutyList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = iNightDutyService.importUser(nightDutyList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入夜班资料数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<NightDuty> util = new ExcelUtil<NightDuty>(NightDuty.class);
        util.importTemplateExcel(response, "夜班资料数据");
    }
}
