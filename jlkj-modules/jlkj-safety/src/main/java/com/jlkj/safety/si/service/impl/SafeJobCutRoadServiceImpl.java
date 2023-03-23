package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobCutRoad;
import com.jlkj.safety.si.mapper.SafeJobCutRoadMapper;
import com.jlkj.safety.si.service.SafeJobCutRoadService;
import com.jlkj.safety.si.utils.PdfUtil;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.*;

/**
 * <p>
 * 8.断路安全作业证 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobCutRoadServiceImpl extends ServiceImpl<SafeJobCutRoadMapper, SafeSiJobCutRoad> implements SafeJobCutRoadService {
    @Resource
    private SafeJobCutRoadMapper safeJobCutRoadMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getjobCode(Map<String, Object> params) {
        Map<String, Object> data = new HashMap<>(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("DL").append(sdf.format(date)).append("-");
        params.put("codeHeader", sb.toString());
        Map<String, Object> jobData = safeJobCutRoadMapper.getMaxJobCode(params);
        if (null != jobData) {
            String jobCode = jobData.get("job_code").toString();
            int value = Integer.parseInt(jobCode.replace(sb.toString(), ""));
            value = value + 1;
            if (value < TEN) {
                data.put("job_code", sb.append("0").append(value).toString());
            }
            else {
                data.put("job_code", sb.append(value).toString());
            }
        }
        else {
            data.put("job_code", sb.append("01").toString());
        }
        return ResponseUtil.toResult(params, data);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobCutRoadInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobCutRoadMapper.getSafeJobCutRoadInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "断路安全数据不存在");
        }
        else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobCutRoadPersonsList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobCutRoadMapper.getSafeJobCutRoadPersonsList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobCutRoadSafetyMeasuresList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobCutRoadMapper.getSafeJobCutRoadSafetyMeasuresList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobCutRoadApprovalList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobCutRoadMapper.getSafeJobCutRoadApprovalList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobCutRoadPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobCutRoadMapper.getSafeJobCutRoadPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobCutRoad(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("job_code", params.get("job_code").toString());
        if (listByMap(query).size() == 0) {
            SafeSiJobCutRoad safeSiJobCutRoad = new SafeSiJobCutRoad();
            safeSiJobCutRoad.setId(IdUtil.randomUUID());
            safeSiJobCutRoad.setApplyDepartId(params.get("apply_depart_id").toString());
            safeSiJobCutRoad.setApplyDepartName(params.get("apply_depart_name").toString());
            safeSiJobCutRoad.setApplyPersonId(params.get("apply_person_id").toString());
            safeSiJobCutRoad.setApplyPersonName(params.get("apply_person_name").toString());
            safeSiJobCutRoad.setJobCode(params.get("job_code").toString());
            safeSiJobCutRoad.setSafeAreaId(params.get("safe_area_id").toString());
            safeSiJobCutRoad.setSafeAreaName(params.get("safe_area_name").toString());
            safeSiJobCutRoad.setPlace(params.get("place").toString());
            safeSiJobCutRoad.setJobContent(params.get("job_content").toString());
            safeSiJobCutRoad.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeSiJobCutRoad.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeSiJobCutRoad.setCause(params.get("cause").toString());
            safeSiJobCutRoad.setOtherUnits(params.get("other_units").toString());
            safeSiJobCutRoad.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeSiJobCutRoad.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeSiJobCutRoad.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeSiJobCutRoad.setHarmIdentification(params.get("harm_identification").toString());
            safeSiJobCutRoad.setStatus(0);
            safeSiJobCutRoad.setApplyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiJobCutRoad.setFinishTime(null);

            safeSiJobCutRoad.setJobUnitId(params.get("job_unit_id").toString());
            safeSiJobCutRoad.setJobUnitName(params.get("job_unit_name").toString());
            safeSiJobCutRoad.setJobResponsiblePersonId (params.get("job_responsible_person_id").toString());
            safeSiJobCutRoad.setJobResponsiblePersonName(params.get("job_responsible_person_name").toString());
            safeSiJobCutRoad.setUrl(params.get("url").toString());
            safeSiJobCutRoad.setContent(params.get("content").toString());

            if (save(safeSiJobCutRoad)) {
                return ResponseUtil.toResult(params, "断路安全作业证保存成功", safeSiJobCutRoad);
            } else {
                return ResponseUtil.toError(params, "断路安全作业证保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "作业证编号已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobCutRoad(Map<String, Object> params) {
        SafeSiJobCutRoad safeSiJobCutRoad = getById(params.get("id").toString());
        if (null != safeSiJobCutRoad) {
            safeSiJobCutRoad.setApplyDepartId(params.get("apply_depart_id").toString());
            safeSiJobCutRoad.setApplyDepartName(params.get("apply_depart_name").toString());
            safeSiJobCutRoad.setApplyPersonId(params.get("apply_person_id").toString());
            safeSiJobCutRoad.setApplyPersonName(params.get("apply_person_name").toString());
            safeSiJobCutRoad.setJobCode(params.get("job_code").toString());
            safeSiJobCutRoad.setSafeAreaId(params.get("safe_area_id").toString());
            safeSiJobCutRoad.setSafeAreaName(params.get("safe_area_name").toString());
            safeSiJobCutRoad.setPlace(params.get("place").toString());
            safeSiJobCutRoad.setJobContent(params.get("job_content").toString());
            safeSiJobCutRoad.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeSiJobCutRoad.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeSiJobCutRoad.setCause(params.get("cause").toString());
            safeSiJobCutRoad.setOtherUnits(params.get("other_units").toString());
            safeSiJobCutRoad.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeSiJobCutRoad.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeSiJobCutRoad.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeSiJobCutRoad.setHarmIdentification(params.get("harm_identification").toString());

            safeSiJobCutRoad.setJobUnitId(params.get("job_unit_id").toString());
            safeSiJobCutRoad.setJobUnitName(params.get("job_unit_name").toString());
            safeSiJobCutRoad.setJobResponsiblePersonId (params.get("job_responsible_person_id").toString());
            safeSiJobCutRoad.setJobResponsiblePersonName(params.get("job_responsible_person_name").toString());
            safeSiJobCutRoad.setUrl(params.get("url").toString());
            safeSiJobCutRoad.setContent(params.get("content").toString());
            if (updateById(safeSiJobCutRoad)) {
                return ResponseUtil.toResult(params, "断路安全作业证保存成功");
            } else {
                return ResponseUtil.toError(params, "断路安全作业证保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "断路安全作业证记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobCutRoad(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            safeJobCutRoadMapper.deleteSafeJobCutRoadPersons(params);
            safeJobCutRoadMapper.deleteSafeJobCutRoadSafetyMeasures(params);
            safeJobCutRoadMapper.deleteSafeJobCutRoadApproval(params);
            return ResponseUtil.toResult(params, "断路安全作业证删除成功");
        }
        else {
            return ResponseUtil.toError(params, "断路安全作业证删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobCutRoadSafetyMeasures(Map<String, Object> params) {
        return (safeJobCutRoadMapper.insertSafeJobCutRoadSafetyMeasures(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobCutRoadApproval(Map<String, Object> params) {
        return (safeJobCutRoadMapper.insertSafeJobCutRoadApproval(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobCutRoadPersons(Map<String, Object> params) {
        safeJobCutRoadMapper.deleteSafeJobCutRoadPersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeJobCutRoadApprovalStatus(Map<String, Object> params) {
        SafeSiJobCutRoad safeSiJobCutRoad = getById(params.get("id").toString());
        if (safeSiJobCutRoad.getStatus() != Integer.parseInt(params.get(STATUS).toString())) {
            safeSiJobCutRoad.setStatus(Integer.parseInt(params.get("status").toString()));
            if (Integer.parseInt(params.get(STATUS).toString()) == STATUS_WORK) {
                safeSiJobCutRoad.setFinishTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            return updateById(safeSiJobCutRoad);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobCutRoadApprovalTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobCutRoadMapper.getSafeJobCutRoadApprovalTemplateList(params));
    }



    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobCutRoadFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobCutRoadMapper.getSafeJobCutRoadFileList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public void getSafeJobHoistingFilePdf(HttpServletResponse response, Map<String, Object> params) {
        //读取作业证详情
        Map<String, Object> safeJobHoisting = safeJobCutRoadMapper.getSafeJobHoistingById(params);
        safeJobHoisting.put("url", IMG_URL + safeJobHoisting.get("url"));
        //读取审批信息
        List<Map<String, Object>> approvals = safeJobCutRoadMapper.getSafeJobHoistingApprovalById(params);
        for (Map<String, Object> approval : approvals) {
            if ("相关单位意见".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("xgdw_name", approval.get("approval_person_name"));
                safeJobHoisting.put("xgdw_time", approval.get("approval_time"));
                safeJobHoisting.put("xgdw_content", approval.get("content"));
                safeJobHoisting.put("xgdw_signature", IMG_URL + approval.get("signature"));
            } else if ("安保处意见".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("anbao_name", approval.get("approval_person_name"));
                safeJobHoisting.put("anbao_time", approval.get("approval_time"));
                safeJobHoisting.put("anbao_content", approval.get("content"));
                safeJobHoisting.put("anbao_signature", IMG_URL + approval.get("signature"));
            } else if ("安全措施落实情况".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("anquan_name", approval.get("approval_person_name"));
                safeJobHoisting.put("anquan_time", approval.get("approval_time"));
                safeJobHoisting.put("anquan_content", approval.get("content"));
                safeJobHoisting.put("anquan_signature", IMG_URL + approval.get("signature"));
            } else if ("完工验收".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("wangong_name", approval.get("approval_person_name"));
                safeJobHoisting.put("wangong_time", approval.get("approval_time"));
                safeJobHoisting.put("wangong_content", approval.get("content"));
                safeJobHoisting.put("wangong_signature", approval.get("signature"));
                safeJobHoisting.put("wangong_department_name", IMG_URL + approval.get("department_name"));
            }
        }
        //读取安全措施
        List<Map<String, String>> safetyMeasures = safeJobCutRoadMapper.getSafeJobHoistingSafetyMeasuresById(params);
        StringBuilder measures = new StringBuilder();
        for (int i = 0; i < safetyMeasures.size(); i++) {
            Map<String, String> map = safetyMeasures.get(i);
            measures.append(i+1);
            measures.append("、");
            measures.append(map.get("safety_measures"));
            measures.append("\r\n");
        }
        safeJobHoisting.put("safetyMeasures", measures);
//        PdfUtil.exportDownloadPdf(response, "dl", safeJobHoisting,"断路作业证许可证-" + safeJobHoisting.get("job_code"));
        PdfUtil.exportStreamPdf(response, "dl", safeJobHoisting);
    }
}
