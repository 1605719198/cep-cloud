package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.Comptime;
import com.jlkj.human.hd.dto.ComptimeDTO;
import com.jlkj.human.hd.service.IComptimeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-04-13 15:39
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comptime")
public class ComptimeController extends BaseController {

    private final IComptimeService iComptimeService;

    /**
     * 查询补休申请作业列表
     */
    @RequiresPermissions("human:comptime:list")
    @Log(title = "查询补休申请作业列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询补休申请作业列表")
    @GetMapping("/list")
    public Object listComptime(ComptimeDTO comptimeDTO) {
        startPage();
        Comptime comptime = new Comptime();
        BeanUtils.copyProperties(comptimeDTO, comptime);
        List<Comptime> list = iComptimeService.lambdaQuery()
                .eq(StringUtils.isNotBlank(comptime.getCompId()), Comptime::getCompId, comptime.getCompId())
                .eq(Comptime::getEmpNo, comptime.getEmpNo())
                .apply("date_format (start_date,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", comptimeDTO.getStartTime())
                .apply("date_format (end_date,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", comptimeDTO.getEndTime())
                .list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 根据uuid查询补休申请详细信息
     */
    @RequiresPermissions("human:comptime:query")
    @Operation(summary = "根据uuid查询补休申请详细信息")
    @GetMapping("/{uuid}")
    @Log(title = "根据uuid查询劳动合同详细信息", businessType = BusinessType.OTHER)
    public Object getComptime(@PathVariable String uuid) {
        Comptime comptime = iComptimeService.lambdaQuery().eq(Comptime::getId, uuid).one();
        ComptimeDTO comptimeDTO = new ComptimeDTO();
        BeanUtils.copyProperties(comptime, comptimeDTO);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> list = new ArrayList<>();
        list.add(simpleDateFormat.format(comptime.getStartDate()));
        list.add(simpleDateFormat.format(comptime.getEndDate()));
        comptimeDTO.setWorkOvertimeDate(list);
        return AjaxResult.success("查询成功", comptimeDTO);
    }

    /**
     * 添加补休申请信息
     */
    @RequiresPermissions("human:comptime:add")
    @Operation(summary = "添加补休申请信息")
    @PostMapping
    @Log(title = "添加补休申请信息", businessType = BusinessType.INSERT)
    public Object addComptime(@RequestBody ComptimeDTO comptimeDTO) throws ParseException {
        Comptime comptime = new Comptime();
        BeanUtils.copyProperties(comptimeDTO, comptime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        comptime.setStartDate(simpleDateFormat.parse(comptimeDTO.getStartTime()));
        comptime.setEndDate(simpleDateFormat.parse(comptimeDTO.getEndTime()));
        comptime.setCreator(SecurityUtils.getNickName());
        comptime.setStatus("未送出");
        String strStartTime = "8:00";
        String strEndTime = "17:00";
        Date startTime = sdf.parse(strStartTime);
        Date endTime  = sdf.parse(strEndTime);
        Date nowTime = sdf.parse(comptimeDTO.getStartTime().substring(10, 16));
        boolean effectiveDate = DateUtils.isEffectiveDate(nowTime, startTime, endTime);
        if (effectiveDate) {
            return AjaxResult.error("正常上班时间段内不能有补休");
        }
        List<Comptime> list = iComptimeService.lambdaQuery()
                .eq(Comptime::getEmpNo, comptime.getEmpNo())
                .orderBy(true, false, Comptime::getEndDate)
                .last("limit 1")
                .list();
        if (list.isEmpty()) {
            iComptimeService.save(comptime);
            return AjaxResult.success("新增成功");
        } else {
            if (comptime.getStartDate().before(list.get(0).getEndDate())) {
                return AjaxResult.error("补休开始时间早于上次补休的结束时间");
            } else {
                iComptimeService.save(comptime);
                return AjaxResult.success("新增成功");
            }
        }
    }

    /**
     * 修改补休申请信息
     */
    @RequiresPermissions("human:comptime:edit")
    @Operation(summary = "修改补休申请信息")
    @PutMapping
    @Log(title = "修改补休申请信息", businessType = BusinessType.UPDATE)
    public Object updateComptime(@RequestBody ComptimeDTO comptimeDTO) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Comptime comptime = new Comptime();
        BeanUtils.copyProperties(comptimeDTO, comptime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        comptime.setStartDate(simpleDateFormat.parse(comptimeDTO.getWorkOvertimeDate().get(0)));
        comptime.setEndDate(simpleDateFormat.parse(comptimeDTO.getWorkOvertimeDate().get(1)));
        String nickName = SecurityUtils.getNickName();
        if (nickName.equals(comptime.getCreator())) {
            String strStartTime = "8:00";
            String strEndTime = "17:00";
            Date startTime = sdf.parse(strStartTime);
            Date endTime  = sdf.parse(strEndTime);
            Date nowTime = sdf.parse(comptimeDTO.getStartTime().substring(10, 16));
            boolean effectiveDate = DateUtils.isEffectiveDate(nowTime, startTime, endTime);
            if (effectiveDate) {
                return AjaxResult.error("正常上班时间段内不能有补休");
            }
            List<Comptime> list = iComptimeService.lambdaQuery()
                    .eq(Comptime::getEmpNo, comptime.getEmpNo())
                    .orderBy(true, false, Comptime::getEndDate)
                    .last("limit 1")
                    .list();
            if (list.isEmpty()) {
                iComptimeService.updateById(comptime);
                return AjaxResult.success("修改成功");
            } else {
                if (comptime.getStartDate().before(list.get(0).getEndDate())) {
                    return AjaxResult.error("补休开始时间早于上次补休的结束时间");
                } else {
                    iComptimeService.updateById(comptime);
                    return AjaxResult.success("修改成功");
                }
            }
        } else {
            return AjaxResult.error("登录人不是申请人或输入人不能修改！");
        }
    }

    /**
     * 删除补休申请信息
     */
    @RequiresPermissions("human:comptime:remove")
    @Operation(summary = "删除补休申请信息")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除补休申请信息", businessType = BusinessType.DELETE)
    public Object delComptime(@PathVariable String uuid) {
        String nickName = SecurityUtils.getNickName();
        Comptime comptime = iComptimeService.lambdaQuery().eq(Comptime::getId, uuid).one();
        if (nickName.equals(comptime.getCreator())) {
            iComptimeService.lambdaUpdate().eq(Comptime::getId, uuid).remove();
            return AjaxResult.success("删除成功");
        } else {
            return AjaxResult.error("登录人不为申请人或输入人,不能删除");
        }
    }

    @Log(title = "补休资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:comptime:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Comptime> util = new ExcelUtil<Comptime>(Comptime.class);
        List<Comptime> comptimeList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = iComptimeService.importUser(comptimeList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入补休数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<Comptime> util = new ExcelUtil<Comptime>(Comptime.class);
        util.importTemplateExcel(response, "补休数据");
    }
}
