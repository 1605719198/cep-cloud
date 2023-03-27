package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobSoil;
import com.jlkj.safety.si.mapper.SafeJobSoilMapper;
import com.jlkj.safety.si.service.SafeJobSoilService;
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
 * 7.动土安全作业证 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobSoilServiceImpl extends ServiceImpl<SafeJobSoilMapper, SafeJobSoil> implements SafeJobSoilService {
    @Resource
    private SafeJobSoilMapper safeJobSoilMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getjobCode(Map<String, Object> params) {
        Map<String, Object> data = new HashMap<>(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("DT").append(sdf.format(date)).append("-");
        params.put("codeHeader", sb.toString());
        Map<String, Object> jobData = safeJobSoilMapper.getMaxJobCode(params);
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
    public Object getSafeJobSoilInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobSoilMapper.getSafeJobSoilInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "动土安全数据不存在");
        }
        else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSoilPersonsList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSoilMapper.getSafeJobSoilPersonsList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSoilSafetyMeasuresList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSoilMapper.getSafeJobSoilSafetyMeasuresList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSoilApprovalList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSoilMapper.getSafeJobSoilApprovalList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSoilPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSoilMapper.getSafeJobSoilPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobSoil(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("job_code", params.get("job_code").toString());
        if (listByMap(query).size() == 0) {
            SafeJobSoil safeJobSoil = new SafeJobSoil();
            safeJobSoil.setId(IdUtil.randomUUID());
            safeJobSoil.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobSoil.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobSoil.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobSoil.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobSoil.setJobCode(params.get("job_code").toString());
            safeJobSoil.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobSoil.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobSoil.setPlace(params.get("place").toString());
            safeJobSoil.setJobContent(params.get("job_content").toString());
            safeJobSoil.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeJobSoil.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeJobSoil.setOtherJob(params.get("other_job").toString());
            safeJobSoil.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeJobSoil.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeJobSoil.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeJobSoil.setHarmIdentification(params.get("harm_identification").toString());
            safeJobSoil.setStatus(0);
            safeJobSoil.setApplyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeJobSoil.setFinishTime(null);
            safeJobSoil.setJobName(params.get("job_name").toString());
            if (save(safeJobSoil)) {
                return ResponseUtil.toResult(params, "动土安全作业证保存成功", safeJobSoil);
            } else {
                return ResponseUtil.toError(params, "动土安全作业证保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "作业证编号已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobSoil(Map<String, Object> params) {
        SafeJobSoil safeJobSoil = getById(params.get("id").toString());
        if (null != safeJobSoil) {
            safeJobSoil.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobSoil.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobSoil.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobSoil.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobSoil.setJobCode(params.get("job_code").toString());
            safeJobSoil.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobSoil.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobSoil.setPlace(params.get("place").toString());
            safeJobSoil.setJobContent(params.get("job_content").toString());
            safeJobSoil.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeJobSoil.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeJobSoil.setOtherJob(params.get("other_job").toString());
            safeJobSoil.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeJobSoil.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeJobSoil.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeJobSoil.setHarmIdentification(params.get("harm_identification").toString());
            safeJobSoil.setJobName(params.get("job_name").toString());
            if (updateById(safeJobSoil)) {
                return ResponseUtil.toResult(params, "动土安全作业证保存成功");
            } else {
                return ResponseUtil.toError(params, "动土安全作业证保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "动土安全作业证记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobSoil(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            safeJobSoilMapper.deleteSafeJobSoilPersons(params);
            safeJobSoilMapper.deleteSafeJobSoilSafetyMeasures(params);
            safeJobSoilMapper.deleteSafeJobSoilApproval(params);
            return ResponseUtil.toResult(params, "动土安全作业证删除成功");
        }
        else {
            return ResponseUtil.toError(params, "动土安全作业证删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSoilSafetyMeasures(Map<String, Object> params) {
        return (safeJobSoilMapper.insertSafeJobSoilSafetyMeasures(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSoilApproval(Map<String, Object> params) {
        return (safeJobSoilMapper.insertSafeJobSoilApproval(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobSoilPersons(Map<String, Object> params) {
        safeJobSoilMapper.deleteSafeJobSoilPersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeJobSoilApprovalStatus(Map<String, Object> params) {
        SafeJobSoil safeJobSoil = getById(params.get("id").toString());
        if (safeJobSoil.getStatus() != Integer.parseInt(params.get(STATUS).toString())) {
            safeJobSoil.setStatus(Integer.parseInt(params.get("status").toString()));
            if (Integer.parseInt(params.get(STATUS).toString()) == STATUS_WORK) {
                safeJobSoil.setFinishTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            return updateById(safeJobSoil);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSoilApprovalTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSoilMapper.getSafeJobSoilApprovalTemplateList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSoilFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSoilMapper.getSafeJobSoilFileList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public void getSafeJobSoilFilePdf(HttpServletResponse response, Map<String, Object> params) {
        //读取作业证详情
        Map<String, Object> safeJobSoil = safeJobSoilMapper.getSafeJobSoilById(params);
        //读取审批信息
        List<Map<String, Object>> approvals = safeJobSoilMapper.getSafeJobSoilApprovalById(params);
        for (Map<String, Object> approval : approvals) {
            if ("现场实施确认".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("xc_content", approval.get("content"));
                safeJobSoil.put("xc_signature", IMG_URL + approval.get("signature"));
            } else if ("动土作业主管分厂工程师".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("zgfcgcs_content", approval.get("content"));
                safeJobSoil.put("zgfcgcs_signature", IMG_URL + approval.get("signature"));
            } else if ("分厂主管领导".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("fczgld_content", approval.get("content"));
                safeJobSoil.put("fczgld_signature", IMG_URL + approval.get("signature"));
            } else if ("动土作业区域所属厂(处)".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("zyqy_content", approval.get("content"));
                safeJobSoil.put("zyqy_signature", IMG_URL + approval.get("signature"));
            } else if ("能源处".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("nyc_content", approval.get("content"));
                safeJobSoil.put("nyc_signature", IMG_URL + approval.get("signature"));
            } else if ("生产技术处".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("scjsc_content", approval.get("content"));
                safeJobSoil.put("scjsc_signature", IMG_URL + approval.get("signature"));
            } else if ("工程设备处".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("gcsbc_content", approval.get("content"));
                safeJobSoil.put("gcsbc_signature", IMG_URL + approval.get("signature"));
            } else if ("涉及的相关单位".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("xgdw_content", approval.get("content"));
                safeJobSoil.put("xgdw_signature", IMG_URL + approval.get("signature"));
            } else if ("项目总监".equals(approval.get("approval_name").toString())) {
                safeJobSoil.put("xmzj_content", approval.get("content"));
                safeJobSoil.put("xmzj_signature", IMG_URL + approval.get("signature"));
            }
        }
///        PdfUtil.exportDownloadPdf(response, "dt", safeJobSoil,"动土作业证许可证-" + safeJobSoil.get("job_code"));
        PdfUtil.exportStreamPdf(response, "dt", safeJobSoil);
    }
}
