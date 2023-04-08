package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.*;
import com.jlkj.safety.si.mapper.*;
import com.jlkj.safety.si.service.*;
import com.jlkj.safety.si.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jlkj.common.core.constant.RabbitConstant.*;
import static com.jlkj.safety.si.constants.Constant.*;

/**
 * <p>
 * 2.受限空间安全作业证 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
@Slf4j
public class SafeJobSpaceServiceImpl extends ServiceImpl<SafeJobSpaceMapper, SafeJobSpace> implements SafeJobSpaceService {

    @Resource
    private SafeJobSpaceMapper safeJobSpaceMapper;

    @Resource
    SafeJobSpacePersonsService safeJobSpacePersonsService;

    @Resource
    SafeJobSpaceApprovalMapper safeJobSpaceApprovalMapper;

    @Resource
    SafeJobSpaceAppendixMapper safeJobSpaceAppendixMapper;

    @Resource
    SysFileresourceMapper fileresourceMapper;

    @Resource
    SafeJobSpaceAnalysisService safeJobSpaceAnalysisService;

    @Resource
    SafeJobSpaceSafetyMeasuresService jobSpaceSafetyMeasuresService;

    @Resource
    HumanresourcePersonnelServiceImpl humanresourcePersonnelService;
    @Resource
    HumanresourceOrganizationMapper humanresourceOrganizationMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${erp-file-upload.download-dir}")
    private String downloadDir;

    @Value("${erp-file-upload.download-url}")
    private String downloadUrl;

    @Value("${erp-file-upload.upload-url}")
    private String uploadUrl;

    @Override
    @Transactional(readOnly = true)
    public Object getjobCode(Map<String, Object> params) {
        Map<String, Object> data = new HashMap<>(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("SX").append(sdf.format(date)).append("-");
        params.put("codeHeader", sb.toString());
        Map<String, Object> jobData = safeJobSpaceMapper.getMaxJobCode(params);
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
    public Object getSafeJobSpaceInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobSpaceMapper.getSafeJobSpaceInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "受限空间数据不存在");
        } else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSpacePersonsList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSpaceMapper.getSafeJobSpacePersonsList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSpaceAnalysisList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSpaceMapper.getSafeJobSpaceAnalysisList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSpaceSafetyMeasuresList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSpaceMapper.getSafeJobSpaceSafetyMeasuresList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSpaceApprovalList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSpaceMapper.getSafeJobSpaceApprovalList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSpacePageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSpaceMapper.getSafeJobSpacePageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobSpace(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("job_code", params.get("job_code").toString());
        if (listByMap(query).size() == 0) {
            SafeJobSpace safeJobSpace = new SafeJobSpace();
            safeJobSpace.setId(IdUtil.randomUUID());
            safeJobSpace.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobSpace.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobSpace.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobSpace.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobSpace.setJobCode(params.get("job_code").toString());
            safeJobSpace.setJobLevel(params.get("job_level").toString());
            safeJobSpace.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobSpace.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobSpace.setPlace(params.get("place").toString());
            safeJobSpace.setOriginalMediumName(params.get("original_medium_name").toString());
            safeJobSpace.setJobContent(params.get("job_content").toString());
            safeJobSpace.setWorkEmpId(params.get("work_emp_id").toString());
            safeJobSpace.setWorkEmpName(params.get("work_emp_name").toString());
            safeJobSpace.setRescueEquipment(params.get("rescue_equipment").toString());
            safeJobSpace.setJobResponsiblePersonId(params.get("job_responsible_person_id").toString());
            safeJobSpace.setJobResponsiblePersonName(params.get("job_responsible_person_name").toString());
            safeJobSpace.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeJobSpace.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeJobSpace.setWorkTypeName(params.get("work_type_name").toString());
            safeJobSpace.setOtherJob(params.get("other_job").toString());
            safeJobSpace.setSafeEducationPersonName(params.get("safe_education_person_name").toString());
            safeJobSpace.setHarmIdentification(params.get("harm_identification").toString());
            safeJobSpace.setEquipDepId(params.get("equip_dep_id").toString());
            safeJobSpace.setEquipDepName(params.get("equip_dep_name").toString());
            safeJobSpace.setStatus(0);
            safeJobSpace.setApplyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeJobSpace.setFinishTime(null);
            if (save(safeJobSpace)) {
                return ResponseUtil.toResult(params, "受限空间作业证保存成功", safeJobSpace);
            } else {
                return ResponseUtil.toError(params, "受限空间作业证保存失败");
            }
        } else {
            return ResponseUtil.toError(params, "作业证编号已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobSpace(Map<String, Object> params) {
        SafeJobSpace safeJobSpace = getById(params.get("id").toString());
        if (null != safeJobSpace) {
            safeJobSpace.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobSpace.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobSpace.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobSpace.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobSpace.setJobCode(params.get("job_code").toString());
            safeJobSpace.setJobLevel(params.get("job_level").toString());
            safeJobSpace.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobSpace.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobSpace.setPlace(params.get("place").toString());
            safeJobSpace.setOriginalMediumName(params.get("original_medium_name").toString());
            safeJobSpace.setJobContent(params.get("job_content").toString());
            safeJobSpace.setWorkEmpId(params.get("work_emp_id").toString());
            safeJobSpace.setWorkEmpName(params.get("work_emp_name").toString());
            safeJobSpace.setRescueEquipment(params.get("rescue_equipment").toString());
            safeJobSpace.setJobResponsiblePersonId(params.get("job_responsible_person_id").toString());
            safeJobSpace.setJobResponsiblePersonName(params.get("job_responsible_person_name").toString());
            safeJobSpace.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeJobSpace.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeJobSpace.setWorkTypeName(params.get("work_type_name").toString());
            safeJobSpace.setOtherJob(params.get("other_job").toString());
            safeJobSpace.setSafeEducationPersonName(params.get("safe_education_person_name").toString());
            safeJobSpace.setHarmIdentification(params.get("harm_identification").toString());
            safeJobSpace.setEquipDepId(params.get("equip_dep_id").toString());
            safeJobSpace.setEquipDepName(params.get("equip_dep_name").toString());
            if (updateById(safeJobSpace)) {
                return ResponseUtil.toResult(params, "受限空间作业证保存成功");
            } else {
                return ResponseUtil.toError(params, "受限空间作业证保存失败");
            }
        } else {
            return ResponseUtil.toError(params, "受限空间作业证记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobSpace(Map<String, Object> params) {
        SafeJobSpace safeJobSpace = getById(params.get("id").toString());
        if (safeJobSpace.getStatus() != 0) {
            return ResponseUtil.toError(params, "对不起，您只能删除未审批的作业证");
        }
        if (removeById(params.get(ID).toString())) {
            safeJobSpaceMapper.deleteSafeJobSpacePersons(params);
            safeJobSpaceMapper.deleteSafeJobSpaceAnalysis(params);
            safeJobSpaceMapper.deleteSafeJobSpaceSafetyMeasures(params);
            safeJobSpaceMapper.deleteSafeJobSpaceApproval(params);
            safeJobSpaceMapper.deleteSafeJobSpaceFiles(params);
            return ResponseUtil.toResult(params, "受限空间作业证删除成功");
        } else {
            return ResponseUtil.toError(params, "受限空间作业证删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSpaceSafetyMeasures(Map<String, Object> params) {
        return (safeJobSpaceMapper.insertSafeJobSpaceSafetyMeasures(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSpaceApproval(Map<String, Object> params) {
        return (safeJobSpaceMapper.insertSafeJobSpaceApproval(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSpacePersons(Map<String, Object> params) {
        safeJobSpaceMapper.insertSafeJobSpacePersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobSpacePersons(Map<String, Object> params) {
        safeJobSpaceMapper.deleteSafeJobSpacePersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobSpaceAnalysis(Map<String, Object> params) {
        safeJobSpaceMapper.deleteSafeJobSpaceAnalysis(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeJobSpaceApprovalStatus(Map<String, Object> params) {
        SafeJobSpace safeJobSpace = getById(params.get("id").toString());
        if (safeJobSpace.getStatus() != Integer.parseInt(params.get(STATUS).toString())) {
            safeJobSpace.setStatus(Integer.parseInt(params.get("status").toString()));
            if (Integer.parseInt(params.get(STATUS).toString()) == STATUS_WORK) {
                safeJobSpace.setFinishTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            return updateById(safeJobSpace);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSpaceApprovalTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSpaceMapper.getSafeJobSpaceApprovalTemplateList(params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobSpaceFiles(Map<String, Object> params) {
        safeJobSpaceMapper.deleteSafeJobSpaceFiles(params);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobSpaceFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobSpaceMapper.getSafeJobSpaceFileList(params));
    }

    private void createBaseData(JSONObject jobObj, SafeJobSpace jobSpace, List<SafeSiJobSpaceApproval> approvalList) {
        jobObj.put("uuid", jobSpace.getId());
        jobObj.put("work_no", "");
        jobObj.put("worke_dep_no", "WX00");
        jobObj.put("worke_dep_no_ch", "");
        HumanresourceOrganization humanresourceOrganization = humanresourceOrganizationMapper.selectOne(new LambdaQueryWrapper<HumanresourceOrganization>()
                .eq(HumanresourceOrganization::getId, jobSpace.getApplyDepartId()));
        jobObj.put("worke_dep_no", humanresourceOrganization.getOrganizationCode());
        jobObj.put("worke_dep_no_ch", humanresourceOrganization.getOrganizationName());
        humanresourceOrganization = humanresourceOrganizationMapper.selectOne(new LambdaQueryWrapper<HumanresourceOrganization>()
                .eq(HumanresourceOrganization::getId, jobSpace.getEquipDepId()));
        jobObj.put("equip_dep_no", humanresourceOrganization.getOrganizationCode());
        jobObj.put("equip_dep_no_ch", humanresourceOrganization.getOrganizationName());
        jobObj.put("work_dep01_no", "");
        jobObj.put("work_dep01_no_ch", "");
        HumanresourcePersonnel responsibleUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(HumanresourcePersonnel::getId, jobSpace.getJobResponsiblePersonId()));
        jobObj.put("work_respon_no", responsibleUser.getJobNumber());
        jobObj.put("work_respon_name", jobSpace.getJobResponsiblePersonName());
        jobObj.put("work_respon_phone", "");
        HumanresourcePersonnel workEmpUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(HumanresourcePersonnel::getId, jobSpace.getWorkEmpId()));
        jobObj.put("work_emp_no", workEmpUser.getJobNumber());
        jobObj.put("work_emp_name", jobSpace.getWorkEmpName());
        HumanresourcePersonnel guardianPerson = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(HumanresourcePersonnel::getId, jobSpace.getGuardianPersonId()));
        jobObj.put("custodian_no", guardianPerson.getJobNumber());
        jobObj.put("custodian_name", jobSpace.getGuardianPersonName());
        List<SafeSiJobSpacePersons> personList = safeJobSpacePersonsService.list(new LambdaQueryWrapper<SafeSiJobSpacePersons>()
                .eq(SafeSiJobSpacePersons::getJobId, jobSpace.getId()));
        String workers = "";
        StringBuilder sb = new StringBuilder();
        for (SafeSiJobSpacePersons person : personList) {
            sb.append(person.getPersonName()).append(",");
        }
        if (sb.length() > 0) {
            workers = sb.substring(0, sb.length() - 1);
        }
        jobObj.put("work_user_name", workers);
        jobObj.put("work_date_start", "");
        jobObj.put("work_time_start", "");
        jobObj.put("work_date_end", "");
        jobObj.put("work_time_end", "");
        jobObj.put("work_danger", comvertHarm(jobSpace.getHarmIdentification()));
        jobObj.put("handover_name", jobSpace.getSafeEducationPersonName());
        jobObj.put("seq_no", 0);
        jobObj.put("safe_emp_no", "");
        jobObj.put("safe_emp_name", "");

        try {
            Optional<SafeSiJobSpaceApproval> approvalsafeEngineerOptional = approvalList.stream().filter(approval -> approval.getApprovalName().contains("安全工程师")).findFirst();
            if (approvalsafeEngineerOptional.isPresent()) {
                HumanresourcePersonnel safeEngineer = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                        .eq(HumanresourcePersonnel::getId, approvalsafeEngineerOptional.get().getApprovalPersonId()));
                jobObj.put("safe_emp_no", safeEngineer.getJobNumber());
                jobObj.put("safe_emp_name", safeEngineer.getFullName());
            }
        } catch (Exception e) {
            log.error("获取安全工程师信息失败");
        }
        jobObj.put("security_emp_no", "");
        jobObj.put("security_emp_name", "");
        jobObj.put("status", "");
        jobObj.put("create_no", "");
        jobObj.put("create_date", DateUtil.format(jobSpace.getApplyTime(), "yyyyMMdd"));
        jobObj.put("create_time", DateUtil.format(jobSpace.getApplyTime(), "HHmmss"));
        jobObj.put("update_no", "");
        jobObj.put("update_date", "");
        jobObj.put("update_time", "");
        jobObj.put("fire_memo", jobSpace.getJobContent());
        jobObj.put("w_do_date_start", "");
        jobObj.put("w_do_time_start", "");
    }

    private void createSi40list3Data(JSONObject data, SafeJobSpace jobSpace) {
        JSONArray analysisArr = new JSONArray();
        List<SafeJobSpaceAnalysis> analysisList = safeJobSpaceAnalysisService.list(new LambdaQueryWrapper<SafeJobSpaceAnalysis>()
                .eq(SafeJobSpaceAnalysis::getJobId, jobSpace.getId()));
        int cur = 0;
        for (SafeJobSpaceAnalysis jobSpaceAnalysis : analysisList) {
            cur++;
            JSONObject obj = new JSONObject();
            obj.put("uuid", jobSpaceAnalysis.getId());
            obj.put("parent_id", jobSpace.getId());
            obj.put("seq_no", cur);
            obj.put("work_date", DateUtil.format(jobSpaceAnalysis.getAnalysisTime(), "yyyyMMdd"));
            obj.put("work_time", DateUtil.format(jobSpaceAnalysis.getAnalysisTime(), "HHmmss"));
            obj.put("work_place", "");
            obj.put("fire_density", jobSpaceAnalysis.getCombustibleGas());
            obj.put("o2_density", jobSpaceAnalysis.getOxygenContent());
            obj.put("othre_density", jobSpaceAnalysis.getToxicMedium());
            obj.put("work_no", "");
            obj.put("work_name", jobSpaceAnalysis.getPersonName());
            HumanresourcePersonnel analysisPerson = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                    .eq(HumanresourcePersonnel::getId, jobSpaceAnalysis.getPersonId()));
            if (analysisPerson != null) {
                obj.put("work_no", analysisPerson.getJobNumber());
                obj.put("work_name", analysisPerson.getFullName());
            } else {
                log.error("获取分析人信息失败");
            }
            obj.put("create_no", "");
            obj.put("create_date", DateUtil.format(jobSpaceAnalysis.getAnalysisTime(), "yyyyMMdd"));
            obj.put("create_time", DateUtil.format(jobSpaceAnalysis.getAnalysisTime(), "HHmmss"));
            obj.put("update_no", "");
            obj.put("update_date", "");
            obj.put("update_time", "");
            analysisArr.add(obj);
        }
        data.put("si40list3", analysisArr);
    }

    private void createSi40list5Data(JSONObject data, SafeJobSpace jobSpace) {
        JSONArray measureArr = new JSONArray();
        List<SafeSiJobSpaceSafetyMeasures> measureList = jobSpaceSafetyMeasuresService.list(new LambdaQueryWrapper<SafeSiJobSpaceSafetyMeasures>()
                .eq(SafeSiJobSpaceSafetyMeasures::getJobId, jobSpace.getId()));
        for (SafeSiJobSpaceSafetyMeasures spaceSafetyMeasures : measureList) {
            JSONObject obj = new JSONObject();
            obj.put("uuid", spaceSafetyMeasures.getId());
            obj.put("parent_id", jobSpace.getId());
            obj.put("seq_no", spaceSafetyMeasures.getSort());
            obj.put("safe_method", spaceSafetyMeasures.getSafetyMeasures());
            obj.put("is_use", "N");
            obj.put("confirm_no", "");
            obj.put("confirm_name", spaceSafetyMeasures.getConfirmPersonName());
            HumanresourcePersonnel measurePerson = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                    .eq(HumanresourcePersonnel::getId, spaceSafetyMeasures.getConfirmPersonId()));
            if (measurePerson != null) {
                obj.put("confirm_no", measurePerson.getJobNumber());
                obj.put("confirm_name", measurePerson.getFullName());
                obj.put("is_use", "Y");
            } else {
                log.error("获取安全措施确认人信息失败");
            }
            obj.put("type", "");
            obj.put("create_no", "");
            obj.put("create_date", "");
            obj.put("create_time", "");
            obj.put("update_no", "");
            obj.put("update_date", "");
            obj.put("update_time", "");
            obj.put("is_grade", "");
            measureArr.add(obj);
        }
        data.put("si40list5", measureArr);
    }

    private void createSi40ApprData(JSONObject data, SafeJobSpace jobSpace, List<SafeSiJobSpaceApproval> approvalList, List<HumanresourcePersonnel> approvalPersonList) {
        JSONArray workFlow = new JSONArray();
        JSONObject firstStep = new JSONObject();
        Random rand = new Random();
        String firstid = System.currentTimeMillis() + "" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
        firstStep.put("id", firstid);
        firstStep.put("actionCode", "N");
        firstStep.put("chkNo", "00");
        firstStep.put("content", "送审");
        firstStep.put("status", "00");
        firstStep.put("receiveDate", DateUtil.format(jobSpace.getApplyTime(), "yyyyMMdd"));
        firstStep.put("receiveTime", DateUtil.format(jobSpace.getApplyTime(), "HHmmss"));
        firstStep.put("dealDate", DateUtil.format(jobSpace.getApplyTime(), "yyyyMMdd"));
        firstStep.put("dealTime", DateUtil.format(jobSpace.getApplyTime(), "HHmmss"));
        Optional<HumanresourcePersonnel> fitstPersonOptional =
                approvalPersonList.stream().filter(approvalPerson -> approvalPerson.getId().equals(jobSpace.getApplyPersonId())).findFirst();
        fitstPersonOptional.ifPresent(tHumanresourcePersonnel -> firstStep.put("dealEmpno", tHumanresourcePersonnel.getJobNumber()));
        workFlow.add(firstStep);
        for (int i = 0; i < approvalList.size(); i++) {
            JSONObject workFlowStep = new JSONObject();
            Random r = new Random();
            String id = System.currentTimeMillis() + "" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10);
            workFlowStep.put("id", id);
            workFlowStep.put("actionCode", "N");
            workFlowStep.put("chkNo", approvalList.get(i).getSort() < 10 ? "0" + String.valueOf(approvalList.get(i).getSort()) : String.valueOf(approvalList.get(i).getSort()));
            workFlowStep.put("content", ObjectUtil.isEmpty(approvalList.get(i).getContent()) ? "同意" : approvalList.get(i).getContent());
            workFlowStep.put("status", "02");
            if (i == approvalList.size() - 1) {
                workFlowStep.put("status", "03");
            }
            workFlowStep.put("receiveDate", DateUtil.format(approvalList.get(i).getApprovalTime(), "yyyyMMdd"));
            workFlowStep.put("receiveTime", DateUtil.format(approvalList.get(i).getApprovalTime(), "HHmmss"));
            workFlowStep.put("dealDate", DateUtil.format(approvalList.get(i).getApprovalTime(), "yyyyMMdd"));
            workFlowStep.put("dealTime", DateUtil.format(approvalList.get(i).getApprovalTime(), "HHmmss"));
            String approvalPersonId = approvalList.get(i).getApprovalPersonId();
            Optional<HumanresourcePersonnel> approvalPersonOptional =
                    approvalPersonList.stream().filter(approvalPerson -> approvalPerson.getId().equals(approvalPersonId)).findFirst();
            approvalPersonOptional.ifPresent(tHumanresourcePersonnel -> workFlowStep.put("dealEmpno", tHumanresourcePersonnel.getJobNumber()));
            workFlow.add(workFlowStep);
        }
        data.put("si40Appr", workFlow);
    }

    private void createSi40confirmData(JSONObject data, SafeJobSpace jobSpace, List<SafeSiJobSpaceApproval> approvalList, List<HumanresourcePersonnel> approvalPersonList) {
        JSONArray confirmFlow = new JSONArray();
        for (int i = 0; i < approvalList.size(); i++) {
            JSONObject confirmFlowStep = new JSONObject();
            confirmFlowStep.put("uuid", approvalList.get(i).getId());
            confirmFlowStep.put("actionCode", "N");
            confirmFlowStep.put("seqNo", approvalList.get(i).getSort() < 10 ? "0" + String.valueOf(approvalList.get(i).getSort()) : String.valueOf(approvalList.get(i).getSort()));
            confirmFlowStep.put("workMemo", "");
            confirmFlowStep.put("status", "01");
            confirmFlowStep.put("updateDate", DateUtil.format(approvalList.get(i).getApprovalTime(), "yyyyMMdd"));
            confirmFlowStep.put("updateTime", DateUtil.format(approvalList.get(i).getApprovalTime(), "HHmmss"));
            HumanresourcePersonnel responsibleUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                    .eq(HumanresourcePersonnel::getId, jobSpace.getJobResponsiblePersonId()));
            confirmFlowStep.put("responNo", responsibleUser.getJobNumber());
            confirmFlowStep.put("responName", responsibleUser.getFullName());
            String approvalPersonId = approvalList.get(i).getApprovalPersonId();
            Optional<HumanresourcePersonnel> approvalPersonOptional =
                    approvalPersonList.stream().filter(approvalPerson -> approvalPerson.getId().equals(approvalPersonId)).findFirst();
            approvalPersonOptional.ifPresent(tHumanresourcePersonnel -> confirmFlowStep.put("updateNo", tHumanresourcePersonnel.getJobNumber()));
            confirmFlow.add(confirmFlowStep);
        }
        data.put("si40confirm", confirmFlow);
    }

    @Override
    @Async
    public void sendSafeJobSpaceMessageQueue(SafeJobSpace jobSpace) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("thread sleep failure");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("class", "com.icsc.si.logic.sijcnbApprLogic");
        jsonObject.put("message_id", "SI40");
        jsonObject.put("action_code", "N");
        jsonObject.put("data_source", "cep");
        JSONObject data = new JSONObject();
        JSONObject jobObj = new JSONObject();
        List<SafeSiJobSpaceApproval> approvalList = safeJobSpaceApprovalMapper.selectList(new LambdaQueryWrapper<SafeSiJobSpaceApproval>()
                .eq(SafeSiJobSpaceApproval::getJobId, jobSpace.getId())
                .orderByAsc(SafeSiJobSpaceApproval::getSort));
        List<String> approvalPersonIds = new ArrayList<String>();
        approvalPersonIds.add("");
        approvalPersonIds.add(jobSpace.getApplyPersonId());
        for (SafeSiJobSpaceApproval person : approvalList) {
            approvalPersonIds.add(person.getApprovalPersonId());
        }
        List<HumanresourcePersonnel> approvalPersonList = humanresourcePersonnelService.list(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .in(HumanresourcePersonnel::getId, approvalPersonIds));
        createBaseData(jobObj, jobSpace, approvalList);
        data.put("si40vo", jobObj);
        createSi40list3Data(data, jobSpace);
        createSi40list5Data(data, jobSpace);
        createSi40ApprData(data, jobSpace, approvalList, approvalPersonList);
        createSi40confirmData(data, jobSpace, approvalList, approvalPersonList);
        jsonObject.put("data", data);
        log.info("send mq message:{}:{} => {}", SAFETY_EXCHANGE, SAFETY_SPACE_ROUTE_KEY, jsonObject.toJSONString());
        rabbitTemplate.convertAndSend(SAFETY_EXCHANGE, SAFETY_SPACE_ROUTE_KEY, jsonObject.toJSONString());
        List<SafeSiJobSpaceAppendix> appList = safeJobSpaceAppendixMapper.selectList(new LambdaQueryWrapper<SafeSiJobSpaceAppendix>()
                .eq(SafeSiJobSpaceAppendix::getJobId, jobSpace.getId()));
        if (appList.size() > 0) {
            appList.forEach(safeSiJobSpaceAppendix -> {
                HumanresourcePersonnel appUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                        .eq(HumanresourcePersonnel::getId, jobSpace.getApplyPersonId()));
                SysFileresource file = fileresourceMapper.selectOne(new LambdaQueryWrapper<SysFileresource>()
                        .eq(SysFileresource::getId, safeSiJobSpaceAppendix.getAppendixId()));
                String tmpFile = downloadDir + IdUtil.randomUUID() + '.' + file.getSuffix();
                HttpUtil.downloadFile(downloadUrl + safeSiJobSpaceAppendix.getAppendixId(), tmpFile);
                HashMap<String, Object> paramMap = new HashMap<>(1);
                paramMap.put("sijcAppr", "SI");
                paramMap.put("uuid", approvalList.get(0).getId());
                paramMap.put("userId", appUser.getJobNumber());
                paramMap.put("imgName", file.getFileName().substring(0, file.getFileName().indexOf(".") - 1));
                paramMap.put("imgformat", file.getSuffix());
                paramMap.put("file", FileUtil.file(tmpFile));
                HttpUtil.post(uploadUrl, paramMap);
                FileUtil.del(tmpFile);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobSpaceJobCode(Map<String, Object> params) {
        SafeJobSpace safeJobSpace = getById(params.get("uuid").toString());
        if (null != safeJobSpace) {
            safeJobSpace.setJobCodeErp(params.get("dangerNo").toString());
            if (updateById(safeJobSpace)) {
                return ResponseUtil.toResult(params, "受限空间作业证编号保存成功");
            } else {
                return ResponseUtil.toError(params, "受限空间作业证编号保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "受限空间作业证记录不存在");
        }
    }

    /**
     * 格式化危害辨识
     * @param harm 名称
     * @return code
     */
    private String comvertHarm(String harm) {
        StringBuilder res = new StringBuilder();
        String[] harms = harm.split("\\|");
        for (String s : harms) {
            switch (s) {
                case "爆炸性粉尘":
                    res.append("01|");
                    break;
                case "易燃性物质":
                    res.append("02|");
                    break;
                case "腐蚀性液体":
                    res.append("03|");
                    break;
                case "挥发性物质":
                    res.append("04|");
                    break;
                case "高压气体/液体":
                    res.append("05|");
                    break;
                case "高温/低温":
                    res.append("06|");
                    break;
                case "触电":
                    res.append("07|");
                    break;
                case "惰性气体、有毒气体":
                    res.append("08|");
                    break;
                case "噪音":
                    res.append("09|");
                    break;
                case "产生火花／静电":
                    res.append("10|");
                    break;
                case "旋转设备":
                    res.append("11|");
                    break;
                case "蒸汽":
                    res.append("12|");
                    break;
                case "淹没/埋没":
                    res.append("13|");
                    break;
                case "坠落":
                    res.append("14|");
                    break;
                case "空间内活动受限":
                    res.append("15|");
                    break;
                case "有毒有害化学品":
                    res.append("16|");
                    break;
                default:
                    break;
            }
        }
        return res.toString();
    }
}
