package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobTemporaryElectricity;
import com.jlkj.safety.si.mapper.SafeJobTemporaryElectricityMapper;
import com.jlkj.safety.si.service.SafeJobTemporaryElectricityService;
import com.jlkj.safety.si.utils.PdfUtil;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jlkj.safety.si.constants.Constant.*;

/**
 * <p>
 * 5.临时用电安全作业证 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobTemporaryElectricityServiceImpl extends ServiceImpl<SafeJobTemporaryElectricityMapper, SafeJobTemporaryElectricity> implements SafeJobTemporaryElectricityService {
    @Resource
    private SafeJobTemporaryElectricityMapper safeJobTemporaryElectricityMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getjobCode(Map<String, Object> params) {
        Map<String, Object> data = new HashMap<>(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("LD").append(sdf.format(date)).append("-");
        params.put("codeHeader", sb.toString());
        Map<String, Object> jobData = safeJobTemporaryElectricityMapper.getMaxJobCode(params);
        if (null != jobData) {
            String jobCode = jobData.get("job_code").toString();
            int value = Integer.parseInt(jobCode.replace(sb.toString(), ""));
            value = value + 1;
            if (value < TEN) {
                data.put("job_code", sb.append("0").append(value).toString());
            } else {
                data.put("job_code", sb.append(value).toString());
            }
        } else {
            data.put("job_code", sb.append("01").toString());
        }
        return ResponseUtil.toResult(params, data);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobTemporaryElectricityInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricityInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "临时用电安全数据不存在");
        } else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobTemporaryElectricityPersonsList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricityPersonsList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobTemporaryElectricitySafetyMeasuresList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricitySafetyMeasuresList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobTemporaryElectricityApprovalList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricityApprovalList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobTemporaryElectricityPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricityPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobTemporaryElectricity(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("job_code", params.get("job_code").toString());
        if (listByMap(query).size() == 0) {
            SafeJobTemporaryElectricity safeJobTemporaryElectricity = new SafeJobTemporaryElectricity();
            safeJobTemporaryElectricity.setId(IdUtil.randomUUID());
            safeJobTemporaryElectricity.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobTemporaryElectricity.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobTemporaryElectricity.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobTemporaryElectricity.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobTemporaryElectricity.setJobCode(params.get("job_code").toString());
            safeJobTemporaryElectricity.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobTemporaryElectricity.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobTemporaryElectricity.setPlace(params.get("place").toString());
            safeJobTemporaryElectricity.setPowerAccessPoint(params.get("power_access_point").toString());
            safeJobTemporaryElectricity.setWorkVoltage(params.get("work_voltage").toString());
            safeJobTemporaryElectricity.setElectricalEquipment(params.get("electrical_equipment").toString());
            safeJobTemporaryElectricity.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeJobTemporaryElectricity.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeJobTemporaryElectricity.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeJobTemporaryElectricity.setHarmIdentification(params.get("harm_identification").toString());
            safeJobTemporaryElectricity.setJobName(params.get("job_name").toString());
            safeJobTemporaryElectricity.setStatus(0);
            safeJobTemporaryElectricity.setApplyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeJobTemporaryElectricity.setFinishTime(null);
            if (save(safeJobTemporaryElectricity)) {
                return ResponseUtil.toResult(params, "临时用电安全作业证保存成功", safeJobTemporaryElectricity);
            } else {
                return ResponseUtil.toError(params, "临时用电安全作业证保存失败");
            }
        } else {
            return ResponseUtil.toError(params, "作业证编号已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobTemporaryElectricity(Map<String, Object> params) {
        SafeJobTemporaryElectricity safeJobTemporaryElectricity = getById(params.get("id").toString());
        if (null != safeJobTemporaryElectricity) {
            safeJobTemporaryElectricity.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobTemporaryElectricity.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobTemporaryElectricity.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobTemporaryElectricity.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobTemporaryElectricity.setJobCode(params.get("job_code").toString());
            safeJobTemporaryElectricity.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobTemporaryElectricity.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobTemporaryElectricity.setPlace(params.get("place").toString());
            safeJobTemporaryElectricity.setPowerAccessPoint(params.get("power_access_point").toString());
            safeJobTemporaryElectricity.setWorkVoltage(params.get("work_voltage").toString());
            safeJobTemporaryElectricity.setElectricalEquipment(params.get("electrical_equipment").toString());
            safeJobTemporaryElectricity.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeJobTemporaryElectricity.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeJobTemporaryElectricity.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeJobTemporaryElectricity.setHarmIdentification(params.get("harm_identification").toString());
            safeJobTemporaryElectricity.setJobName(params.get("job_name").toString());
            if (updateById(safeJobTemporaryElectricity)) {
                return ResponseUtil.toResult(params, "临时用电安全作业证保存成功");
            } else {
                return ResponseUtil.toError(params, "临时用电安全作业证保存失败");
            }
        } else {
            return ResponseUtil.toError(params, "临时用电安全作业证记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobTemporaryElectricity(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            safeJobTemporaryElectricityMapper.deleteSafeJobTemporaryElectricityPersons(params);
            safeJobTemporaryElectricityMapper.deleteSafeJobTemporaryElectricitySafetyMeasures(params);
            safeJobTemporaryElectricityMapper.deleteSafeJobTemporaryElectricityApproval(params);
            return ResponseUtil.toResult(params, "临时用电安全作业证删除成功");
        } else {
            return ResponseUtil.toError(params, "临时用电安全作业证删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobTemporaryElectricitySafetyMeasures(Map<String, Object> params) {
        return (safeJobTemporaryElectricityMapper.insertSafeJobTemporaryElectricitySafetyMeasures(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobTemporaryElectricityApproval(Map<String, Object> params) {
        return (safeJobTemporaryElectricityMapper.insertSafeJobTemporaryElectricityApproval(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobTemporaryElectricityPersons(Map<String, Object> params) {
        safeJobTemporaryElectricityMapper.deleteSafeJobTemporaryElectricityPersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeJobTemporaryElectricityApprovalStatus(Map<String, Object> params) {
        SafeJobTemporaryElectricity safeJobTemporaryElectricity = getById(params.get("id").toString());
        if (safeJobTemporaryElectricity.getStatus() != Integer.parseInt(params.get(STATUS).toString())) {
            safeJobTemporaryElectricity.setStatus(Integer.parseInt(params.get("status").toString()));
            if (Integer.parseInt(params.get(STATUS).toString()) == STATUS_WORK) {
                safeJobTemporaryElectricity.setFinishTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            return updateById(safeJobTemporaryElectricity);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobTemporaryElectricityApprovalTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricityApprovalTemplateList(params));
    }


    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHoistingFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobTemporaryElectricityMapper.getSafeJobHoistingFileList(params));
    }


    @Override
    @Transactional(readOnly = true)
    public void getSafeJobHoistingFilePdf(HttpServletResponse response, Map<String, Object> params) {
        //读取作业证详情
        Map<String, Object> safeJobHoisting = safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricityById(params);
        //读取审批信息
        List<Map<String, Object>> approvals = safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricityApprovalById(params);
        for (Map<String, Object> approval : approvals) {
            if ("审核".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("examine_name", approval.get("approval_person_name"));
                safeJobHoisting.put("examine_time", approval.get("approval_time"));
                safeJobHoisting.put("examine_content", approval.get("content"));
                safeJobHoisting.put("examine_signature", IMG_URL + approval.get("signature"));
            } else if ("批准".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("approval_name", approval.get("approval_person_name"));
                safeJobHoisting.put("approval_time", approval.get("approval_time"));
                safeJobHoisting.put("approval_content", approval.get("content"));
                safeJobHoisting.put("approval_signature", IMG_URL + approval.get("signature"));
            } else if ("完工验收".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("completion_name", approval.get("approval_person_name"));
                safeJobHoisting.put("completion_time", approval.get("approval_time"));
                safeJobHoisting.put("completion_content", approval.get("content"));
                safeJobHoisting.put("completion_signature", IMG_URL + approval.get("signature"));
            }
        }
        //读取安全措施
        List<Map<String, Object>> safetyMeasures = safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricitySafetyMeasuresById(params);
        safeJobHoisting.put("safetyMeasures", safetyMeasures);
        //读取作业人
        List<Map<String, Object>> persons = safeJobTemporaryElectricityMapper.getSafeJobTemporaryElectricityPersonsById(params);

        StringBuilder electrician = new StringBuilder();
        String name = "";
        String code = "";
        for (Map<String, Object> person : persons) {
            if (electrician.length() == 0) {
                name = person.get("person_name").toString();
                code = person.get("work_card_code").toString();
                electrician.append(person.get("person_name"));
            }else{
                electrician.append("/").append(person.get("person_name"));
            }
        }
        safeJobHoisting.put("electrician", electrician);
        safeJobHoisting.put("name", name);
        safeJobHoisting.put("code", code);
///        PdfUtil.exportDownloadPdf(response, "lsyd", safeJobHoisting,"临时用电作业许可证-" + safeJobHoisting.get("job_code"));
        PdfUtil.exportStreamPdf(response, "lsyd", safeJobHoisting);
    }
}
