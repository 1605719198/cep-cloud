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
import com.jlkj.human.hd.domain.OvertimeRecord;
import com.jlkj.human.hd.dto.OvertimeRecordDTO;
import com.jlkj.human.hd.service.IOvertimeRecordService;
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
 * @create 2023-04-11 13:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/overtimeRecord")
public class OvertimeRecordController extends BaseController {

    private final IOvertimeRecordService iOvertimeRecordService;

    /**
     * 查询加班申请作业列表
     */
    @RequiresPermissions("human:overtimeRecord:list")
    @Log(title = "查询加班申请作业列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询加班申请作业列表")
    @GetMapping("/list")
    public Object listOvertimeRecord(OvertimeRecordDTO overtimeRecordDTO) {
        startPage();
        OvertimeRecord overtimeRecord = new OvertimeRecord();
        BeanUtils.copyProperties(overtimeRecordDTO, overtimeRecord);
        List<OvertimeRecord> list = iOvertimeRecordService.lambdaQuery()
                .eq(StringUtils.isNotBlank(overtimeRecord.getCompId()), OvertimeRecord::getCompId, overtimeRecord.getCompId())
                .eq(OvertimeRecord::getEmpNo, overtimeRecord.getEmpNo())
                .apply("date_format (start_date,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", overtimeRecordDTO.getStartTime())
                .apply("date_format (end_date,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", overtimeRecordDTO.getEndTime())
                .list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 根据uuid查询加班申请详细信息
     */
    @RequiresPermissions("human:overtimeRecord:query")
    @Operation(summary = "根据uuid查询加班申请详细信息")
    @GetMapping("/{uuid}")
    @Log(title = "根据uuid查询劳动合同详细信息", businessType = BusinessType.OTHER)
    public Object getOvertimeRecord(@PathVariable String uuid) {
        OvertimeRecord overtimeRecord = iOvertimeRecordService.lambdaQuery().eq(OvertimeRecord::getId, uuid).one();
        OvertimeRecordDTO overtimeRecordDTO = new OvertimeRecordDTO();
        BeanUtils.copyProperties(overtimeRecord, overtimeRecordDTO);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> resultList = new ArrayList<>();
        resultList.add(simpleDateFormat.format(overtimeRecord.getStartDate()));
        resultList.add(simpleDateFormat.format(overtimeRecord.getEndDate()));
        overtimeRecordDTO.setWorkOvertimeDate(resultList);
        return AjaxResult.success("查询成功", overtimeRecordDTO);
    }

    /**
     * 添加加班申请信息
     */
    @RequiresPermissions("human:overtimeRecord:add")
    @Operation(summary = "添加加班申请信息")
    @PostMapping
    @Log(title = "添加加班申请信息", businessType = BusinessType.INSERT)
    public Object addOvertimeRecord(@RequestBody OvertimeRecordDTO overtimeRecordDTO) throws ParseException {
        OvertimeRecord overtimeRecord = new OvertimeRecord();
        BeanUtils.copyProperties(overtimeRecordDTO, overtimeRecord);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        overtimeRecord.setStartDate(simpleDateFormat.parse(overtimeRecordDTO.getStartTime()));
        overtimeRecord.setEndDate(simpleDateFormat.parse(overtimeRecordDTO.getEndTime()));
        overtimeRecord.setCreator(SecurityUtils.getNickName());
        overtimeRecord.setStatus("未送出");
        String strStartTime = "8:00";
        String strEndTime = "17:00";
        Date startTime = sdf.parse(strStartTime);
        Date endTime  = sdf.parse(strEndTime);
        Date nowTime = sdf.parse(overtimeRecordDTO.getStartTime().substring(10, 16));
        boolean effectiveDate = DateUtils.isEffectiveDate(nowTime, startTime, endTime);
        if (effectiveDate) {
            return AjaxResult.error("正常上班时间段内不能有加班");
        }
        List<OvertimeRecord> list = iOvertimeRecordService.lambdaQuery()
                .eq(OvertimeRecord::getEmpNo, overtimeRecord.getEmpNo())
                .orderBy(true, false, OvertimeRecord::getEndDate)
                .last("limit 1")
                .list();
        if (list.isEmpty()) {
            iOvertimeRecordService.save(overtimeRecord);
            return AjaxResult.success("新增成功");
        } else {
            if (overtimeRecord.getStartDate().before(list.get(0).getEndDate())) {
                return AjaxResult.error("加班开始时间早于上次加班的结束时间");
            } else {
                iOvertimeRecordService.save(overtimeRecord);
                return AjaxResult.success("新增成功");
            }
        }
    }

    /**
     * 修改加班申请信息
     */
    @RequiresPermissions("human:overtimeRecord:edit")
    @Operation(summary = "修改加班申请信息")
    @PutMapping
    @Log(title = "修改加班申请信息", businessType = BusinessType.UPDATE)
    public Object updateOvertimeRecord(@RequestBody OvertimeRecordDTO overtimeRecordDTO) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        OvertimeRecord overtimeRecord = new OvertimeRecord();
        BeanUtils.copyProperties(overtimeRecordDTO, overtimeRecord);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        overtimeRecord.setStartDate(simpleDateFormat.parse(overtimeRecordDTO.getWorkOvertimeDate().get(0)));
        overtimeRecord.setEndDate(simpleDateFormat.parse(overtimeRecordDTO.getWorkOvertimeDate().get(1)));
        String nickName = SecurityUtils.getNickName();
        if (nickName.equals(overtimeRecord.getCreator())) {
            String strStartTime = "8:00";
            String strEndTime = "17:00";
            Date startTime = sdf.parse(strStartTime);
            Date endTime  = sdf.parse(strEndTime);
            Date nowTime = sdf.parse(overtimeRecordDTO.getStartTime().substring(10, 16));
            boolean effectiveDate = DateUtils.isEffectiveDate(nowTime, startTime, endTime);
            if (effectiveDate) {
                return AjaxResult.error("正常上班时间段内不能有加班");
            }
            List<OvertimeRecord> list = iOvertimeRecordService.lambdaQuery()
                    .eq(OvertimeRecord::getEmpNo, overtimeRecord.getEmpNo())
                    .orderBy(true, false, OvertimeRecord::getEndDate)
                    .last("limit 1")
                    .list();
            if (list.isEmpty()) {
                iOvertimeRecordService.updateById(overtimeRecord);
                return AjaxResult.success("修改成功");
            } else {
                if (overtimeRecord.getStartDate().before(list.get(0).getEndDate())) {
                    return AjaxResult.error("加班开始时间早于上次加班的结束时间");
                } else {
                    iOvertimeRecordService.updateById(overtimeRecord);
                    return AjaxResult.success("修改成功");
                }
            }
        } else {
            return AjaxResult.error("登录人不是申请人或输入人不能修改！");
        }
    }

    /**
     * 删除加班申请信息
     */
    @RequiresPermissions("human:overtimeRecord:remove")
    @Operation(summary = "删除加班申请信息")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除加班申请信息", businessType = BusinessType.DELETE)
    public Object delOvertimeRecord(@PathVariable String uuid) {
        String nickName = SecurityUtils.getNickName();
        OvertimeRecord overtimeRecord = iOvertimeRecordService.lambdaQuery().eq(OvertimeRecord::getId, uuid).one();
        if (nickName.equals(overtimeRecord.getCreator())) {
            iOvertimeRecordService.lambdaUpdate().eq(OvertimeRecord::getId, uuid).remove();
            return AjaxResult.success("删除成功");
        } else {
            return AjaxResult.error("登录人不为申请人或输入人,不能删除");
        }
    }

    @Log(title = "加班资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:overtimeRecord:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<OvertimeRecord> util = new ExcelUtil<OvertimeRecord>(OvertimeRecord.class);
        List<OvertimeRecord> overtimeRecordList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = iOvertimeRecordService.importUser(overtimeRecordList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入加班数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OvertimeRecord> util = new ExcelUtil<OvertimeRecord>(OvertimeRecord.class);
        util.importTemplateExcel(response, "加班数据");
    }
}
