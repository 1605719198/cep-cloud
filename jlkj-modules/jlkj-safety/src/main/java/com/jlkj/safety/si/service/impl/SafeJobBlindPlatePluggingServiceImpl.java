package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobBlindPlatePlugging;
import com.jlkj.safety.si.mapper.SafeJobBlindPlatePluggingAppendixMapper;
import com.jlkj.safety.si.mapper.SafeJobBlindPlatePluggingMapper;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingService;
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
 * 6.盲板抽堵安全作业证 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobBlindPlatePluggingServiceImpl extends ServiceImpl<SafeJobBlindPlatePluggingMapper, SafeJobBlindPlatePlugging> implements SafeJobBlindPlatePluggingService {
    @Resource
    private SafeJobBlindPlatePluggingMapper safeJobBlindPlatePluggingMapper;

    @Resource
    SafeJobBlindPlatePluggingAppendixMapper appendixMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object getjobCode(Map<String, Object> params) {
        Map<String, Object> data = new HashMap<>(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("MD").append(sdf.format(date)).append("-");
        params.put("codeHeader", sb.toString());
        Map<String, Object> jobData = safeJobBlindPlatePluggingMapper.getMaxJobCode(params);
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
    @Transactional(rollbackFor = Exception.class)
    public Object getSafeJobBlindPlatePluggingInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobBlindPlatePluggingMapper.getSafeJobBlindPlatePluggingInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "盲板抽堵安全数据不存在");
        } else {
            List<Map<String, Object>> fileList = appendixMapper.getFiles(params);
            data.put("file_list", fileList);
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object getSafeJobBlindPlatePluggingPersonsList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobBlindPlatePluggingMapper.getSafeJobBlindPlatePluggingPersonsList(params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object getSafeJobBlindPlatePluggingSafetyMeasuresList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobBlindPlatePluggingMapper.getSafeJobBlindPlatePluggingSafetyMeasuresList(params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object getSafeJobBlindPlatePluggingApprovalList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobBlindPlatePluggingMapper.getSafeJobBlindPlatePluggingApprovalList(params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object getSafeJobBlindPlatePluggingPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobBlindPlatePluggingMapper.getSafeJobBlindPlatePluggingPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobBlindPlatePlugging(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("job_code", params.get("job_code").toString());
        if (listByMap(query).size() == 0) {
            SafeJobBlindPlatePlugging safeJobBlindPlatePlugging = new SafeJobBlindPlatePlugging();
            safeJobBlindPlatePlugging.setId(IdUtil.randomUUID());
            safeJobBlindPlatePlugging.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobBlindPlatePlugging.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobBlindPlatePlugging.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobBlindPlatePlugging.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobBlindPlatePlugging.setJobCode(params.get("job_code").toString());
            safeJobBlindPlatePlugging.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobBlindPlatePlugging.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobBlindPlatePlugging.setPlace(params.get("place").toString());
            safeJobBlindPlatePlugging.setEquipmentPipingName(params.get("equipment_piping_name").toString());
            safeJobBlindPlatePlugging.setMedium(params.get("medium").toString());
            safeJobBlindPlatePlugging.setTemperature(params.get("temperature").toString());
            safeJobBlindPlatePlugging.setPressure(params.get("pressure").toString());
            safeJobBlindPlatePlugging.setBlindPlateMaterialQuality(params.get("blind_plate_material_quality").toString());
            safeJobBlindPlatePlugging.setBlindPlateSpecifications(params.get("blind_plate_specifications").toString());
            safeJobBlindPlatePlugging.setBlindPlateCode(params.get("blind_plate_code").toString());
            safeJobBlindPlatePlugging.setPositonContent(params.get("positon_content").toString());
            safeJobBlindPlatePlugging.setProductionUnitJobDirectorPersonName(params.get("production_unit_job_director_person_name").toString());
            safeJobBlindPlatePlugging.setJobUnitId(params.get("job_unit_id").toString());
            safeJobBlindPlatePlugging.setJobUnitName(params.get("job_unit_name").toString());
            safeJobBlindPlatePlugging.setJobUnitResponsiblePersonId(params.get("job_unit_responsible_person_id").toString());
            safeJobBlindPlatePlugging.setJobUnitResponsiblePersonName(params.get("job_unit_responsible_person_name").toString());
            safeJobBlindPlatePlugging.setOtherJob(params.get("other_job").toString());
            safeJobBlindPlatePlugging.setHazardIdentification(params.get("hazard_identification").toString());
            safeJobBlindPlatePlugging.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeJobBlindPlatePlugging.setStatus(0);
            safeJobBlindPlatePlugging.setApplyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeJobBlindPlatePlugging.setFinishTime(null);

            safeJobBlindPlatePlugging.setStartTime("".equals(params.get("start_time").toString()) ? null : DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeJobBlindPlatePlugging.setEndTime("".equals(params.get("end_time").toString()) ? null : DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeJobBlindPlatePlugging.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeJobBlindPlatePlugging.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeJobBlindPlatePlugging.setPumpingBlocking((int) params.get("pumping_blocking"));
            if (save(safeJobBlindPlatePlugging)) {
                return ResponseUtil.toResult(params, "盲板抽堵安全作业证保存成功", safeJobBlindPlatePlugging);
            } else {
                return ResponseUtil.toError(params, "盲板抽堵安全作业证保存失败");
            }
        } else {
            return ResponseUtil.toError(params, "作业证编号已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobBlindPlatePlugging(Map<String, Object> params) {
        SafeJobBlindPlatePlugging safeJobBlindPlatePlugging = getById(params.get("id").toString());
        if (null != safeJobBlindPlatePlugging) {
            safeJobBlindPlatePlugging.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobBlindPlatePlugging.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobBlindPlatePlugging.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobBlindPlatePlugging.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobBlindPlatePlugging.setJobCode(params.get("job_code").toString());
            safeJobBlindPlatePlugging.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobBlindPlatePlugging.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobBlindPlatePlugging.setPlace(params.get("place").toString());
            safeJobBlindPlatePlugging.setEquipmentPipingName(params.get("equipment_piping_name").toString());
            safeJobBlindPlatePlugging.setMedium(params.get("medium").toString());
            safeJobBlindPlatePlugging.setTemperature(params.get("temperature").toString());
            safeJobBlindPlatePlugging.setPressure(params.get("pressure").toString());
            safeJobBlindPlatePlugging.setBlindPlateMaterialQuality(params.get("blind_plate_material_quality").toString());
            safeJobBlindPlatePlugging.setBlindPlateSpecifications(params.get("blind_plate_specifications").toString());
            safeJobBlindPlatePlugging.setBlindPlateCode(params.get("blind_plate_code").toString());
            safeJobBlindPlatePlugging.setPositonContent(params.get("positon_content").toString());
            safeJobBlindPlatePlugging.setProductionUnitJobDirectorPersonName(params.get("production_unit_job_director_person_name").toString());
            safeJobBlindPlatePlugging.setJobUnitId(params.get("job_unit_id").toString());
            safeJobBlindPlatePlugging.setJobUnitName(params.get("job_unit_name").toString());
            safeJobBlindPlatePlugging.setJobUnitResponsiblePersonId(params.get("job_unit_responsible_person_id").toString());
            safeJobBlindPlatePlugging.setJobUnitResponsiblePersonName(params.get("job_unit_responsible_person_name").toString());
            safeJobBlindPlatePlugging.setOtherJob(params.get("other_job").toString());
            safeJobBlindPlatePlugging.setHazardIdentification(params.get("hazard_identification").toString());
            safeJobBlindPlatePlugging.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeJobBlindPlatePlugging.setStartTime("".equals(params.get("start_time").toString()) ? null : DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeJobBlindPlatePlugging.setEndTime("".equals(params.get("end_time").toString()) ? null : DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeJobBlindPlatePlugging.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeJobBlindPlatePlugging.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeJobBlindPlatePlugging.setPumpingBlocking((int) params.get("pumping_blocking"));
            if (updateById(safeJobBlindPlatePlugging)) {
                return ResponseUtil.toResult(params, "盲板抽堵安全作业证保存成功");
            } else {
                return ResponseUtil.toError(params, "盲板抽堵安全作业证保存失败");
            }
        } else {
            return ResponseUtil.toError(params, "盲板抽堵安全作业证记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobBlindPlatePlugging(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            safeJobBlindPlatePluggingMapper.deleteSafeJobBlindPlatePluggingPersons(params);
            safeJobBlindPlatePluggingMapper.deleteSafeJobBlindPlatePluggingSafetyMeasures(params);
            safeJobBlindPlatePluggingMapper.deleteSafeJobBlindPlatePluggingApproval(params);
            return ResponseUtil.toResult(params, "盲板抽堵安全作业证删除成功");
        } else {
            return ResponseUtil.toError(params, "盲板抽堵安全作业证删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobBlindPlatePluggingSafetyMeasures(Map<String, Object> params) {
        return (safeJobBlindPlatePluggingMapper.insertSafeJobBlindPlatePluggingSafetyMeasures(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobBlindPlatePluggingApproval(Map<String, Object> params) {
        return (safeJobBlindPlatePluggingMapper.insertSafeJobBlindPlatePluggingApproval(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobBlindPlatePluggingPersons(Map<String, Object> params) {
        safeJobBlindPlatePluggingMapper.deleteSafeJobBlindPlatePluggingPersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeJobBlindPlatePluggingApprovalStatus(Map<String, Object> params) {
        SafeJobBlindPlatePlugging safeJobBlindPlatePlugging = getById(params.get("id").toString());
        if (safeJobBlindPlatePlugging.getStatus() != Integer.parseInt(params.get(STATUS).toString())) {
            safeJobBlindPlatePlugging.setStatus(Integer.parseInt(params.get("status").toString()));
            if (Integer.parseInt(params.get(STATUS).toString()) == STATUS_WORK) {
                safeJobBlindPlatePlugging.setFinishTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            return updateById(safeJobBlindPlatePlugging);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobBlendingApprovalTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobBlindPlatePluggingMapper.getSafeJobBlendingApprovalTemplateList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobBlendingFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobBlindPlatePluggingMapper.getSafeJobBlendingFileList(params));
    }


    @Override
    @Transactional(readOnly = true)
    public void getSafeJobBlendingFilePdf(HttpServletResponse response, Map<String, Object> params) {

        //读取作业证详情
        Map<String, Object> safeJobHoisting = safeJobBlindPlatePluggingMapper.getSafeJobBlendingById(params);
        //读取审批信息
        List<Map<String, Object>> approvals = safeJobBlindPlatePluggingMapper.getSafeJobBlendingApprovalById(params);
        for (Map<String, Object> approval : approvals) {
            if ("作业负责人意见：".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("zyfzr_name", approval.get("approval_person_name"));
                safeJobHoisting.put("zyfzr_time", approval.get("approval_time"));
                safeJobHoisting.put("zyfzr_content", approval.get("content"));
                safeJobHoisting.put("zyfzr_signature", IMG_URL + approval.get("signature"));
            } else if ("设备单位安全工程师意见：".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("gcs_name", approval.get("approval_person_name"));
                safeJobHoisting.put("gcs_time", approval.get("approval_time"));
                safeJobHoisting.put("gcs_content", approval.get("content"));
                safeJobHoisting.put("gcs_signature", IMG_URL + approval.get("signature"));
            } else if ("设备单位一级主管意见：".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("zg_name", approval.get("approval_person_name"));
                safeJobHoisting.put("zg_time", approval.get("approval_time"));
                safeJobHoisting.put("zg_content", approval.get("content"));
                safeJobHoisting.put("zg_signature", IMG_URL + approval.get("signature"));
            } else if ("安保处专工意见：".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("anbao_name", approval.get("approval_person_name"));
                safeJobHoisting.put("anbao_time", approval.get("approval_time"));
                safeJobHoisting.put("anbao_content", approval.get("content"));
                safeJobHoisting.put("anbao_signature", IMG_URL + approval.get("signature"));
            } else if ("完工验收".equals(approval.get("approval_name").toString())) {
                safeJobHoisting.put("wangong_name", approval.get("approval_person_name"));
                safeJobHoisting.put("wangong_time", approval.get("approval_time"));
                safeJobHoisting.put("wangong_content", approval.get("content"));
                safeJobHoisting.put("wangong_signature", approval.get("signature"));
                safeJobHoisting.put("wangong_department_name", IMG_URL + approval.get("department_name"));
            }
        }
        //读取安全措施
        List<Map<String, Object>> safetyMeasures = safeJobBlindPlatePluggingMapper.getSafeJobBlendingSafetyMeasuresById(params);
        safeJobHoisting.put("safetyMeasures", safetyMeasures);

        //读取附件图片
        List<Map<String, Object>> images = safeJobBlindPlatePluggingMapper.getSafeJobBlendingImageById(params);
        safeJobHoisting.put("image1", "");
        safeJobHoisting.put("image2", "");
        safeJobHoisting.put("image3", "");

        for (int i = 0; i < images.size(); i++) {
            safeJobHoisting.put("image" + i, IMG_URL + images.get(i).get("url"));
        }

//        PdfUtil.exportDownloadPdf(response, "cdmb", safeJobHoisting,"盲板抽堵安全作业证-" + safeJobHoisting.get("job_code"));
        PdfUtil.exportStreamPdf(response, "cdmb", safeJobHoisting);
    }
}
