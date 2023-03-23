package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobFire;
import com.jlkj.safety.si.mapper.SafeJobFireMapper;
import com.jlkj.safety.si.service.SafeJobFireService;
import com.jlkj.safety.si.utils.JobUtil;
import com.jlkj.safety.si.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.RabbitConstant.*;
import static com.jlkj.safety.si.constants.Constant.*;

/**
 * <p>
 * 1.动火安全作业证 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
@Slf4j
public class SafeJobFireServiceImpl extends ServiceImpl<SafeJobFireMapper, SafeSiJobFire> implements SafeJobFireService {

    @Resource
    private SafeJobFireMapper safeJobFireMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

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
        Date date=new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("DH").append(sdf.format(date)).append("-");
        params.put("codeHeader", sb.toString());
        Map<String, Object> jobData = safeJobFireMapper.getMaxJobCode(params);
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
    public Object getSafeJobFireInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobFireMapper.getSafeJobFireInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "动火数据不存在");
        }
        else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFirePersonsList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobFireMapper.getSafeJobFirePersonsList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireAnalysisList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobFireMapper.getSafeJobFireAnalysisList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireSafetyMeasuresList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobFireMapper.getSafeJobFireSafetyMeasuresList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireApprovalList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobFireMapper.getSafeJobFireApprovalList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFirePageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobFireMapper.getSafeJobFirePageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobFire(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("job_code", params.get("job_code").toString());
        if (listByMap(query).size() == 0) {
            SafeSiJobFire safeSiJobFire = new SafeSiJobFire();
            safeSiJobFire.setId(IdUtil.randomUUID());
            safeSiJobFire.setApplyDepartId(params.get("apply_depart_id").toString());
            safeSiJobFire.setApplyDepartName(params.get("apply_depart_name").toString());
            safeSiJobFire.setApplyPersonId(params.get("apply_person_id").toString());
            safeSiJobFire.setApplyPersonName(params.get("apply_person_name").toString());
            safeSiJobFire.setJobCode(params.get("job_code").toString());
            safeSiJobFire.setJobLevel(params.get("job_level").toString());
            safeSiJobFire.setSafeAreaId(params.get("safe_area_id").toString());
            safeSiJobFire.setSafeAreaName(params.get("safe_area_name").toString());
            safeSiJobFire.setPlace(params.get("place").toString());
            safeSiJobFire.setMode(params.get("mode").toString());
            safeSiJobFire.setJobResponsiblePersonName(params.get("job_responsible_person_name").toString());
            safeSiJobFire.setJobResponsiblePersonId(params.get("job_responsible_person_id").toString());
            safeSiJobFire.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeSiJobFire.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeSiJobFire.setOtherJob(params.get("other_job").toString());
            safeSiJobFire.setSafeEducationPersonName(params.get("safe_education_person_name").toString());
            safeSiJobFire.setHarmIdentification(params.get("harm_identification").toString());
            safeSiJobFire.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeSiJobFire.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeSiJobFire.setWorkTypeName(params.get("work_type_name").toString());
            safeSiJobFire.setProductionResponsiblePersonName(params.get("production_responsible_person_name").toString());
            safeSiJobFire.setRelationGuardianPersonId(params.get("relation_guardian_person_id").toString());
            safeSiJobFire.setRelationGuardianPersonName(params.get("relation_guardian_person_name").toString());
            safeSiJobFire.setRelationGuardianPersonWorkTypeName(params.get("relation_guardian_person_work_type_name").toString());
            safeSiJobFire.setEquipmentUnitId(params.get("equipment_unit_id").toString());
            safeSiJobFire.setEquipmentUnitName(params.get("equipment_unit_name").toString());
            safeSiJobFire.setSpecialJobNo(params.get("special_job_no").toString());
            safeSiJobFire.setJobContents(params.get("job_contents").toString());
            safeSiJobFire.setJobResponsiblePersonPhone(params.get("job_responsible_person_phone").toString());
            safeSiJobFire.setIsGasArea(Integer.parseInt(params.get("is_gas_area").toString()));
            safeSiJobFire.setStatus(0);
            safeSiJobFire.setApplyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiJobFire.setFinishTime(null);
            safeSiJobFire.setSpecialName(params.get("special_name").toString());
            if (save(safeSiJobFire)) {
                return ResponseUtil.toResult(params, "动火作业证保存成功", safeSiJobFire);
            } else {
                return ResponseUtil.toError(params, "动火作业证保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "作业证编号已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobFire(Map<String, Object> params) {
        SafeSiJobFire safeSiJobFire = getById(params.get("id").toString());
        if (null != safeSiJobFire) {
            safeSiJobFire.setApplyDepartId(params.get("apply_depart_id").toString());
            safeSiJobFire.setApplyDepartName(params.get("apply_depart_name").toString());
            safeSiJobFire.setApplyPersonId(params.get("apply_person_id").toString());
            safeSiJobFire.setApplyPersonName(params.get("apply_person_name").toString());
            safeSiJobFire.setJobCode(params.get("job_code").toString());
            safeSiJobFire.setJobLevel(params.get("job_level").toString());
            safeSiJobFire.setSafeAreaId(params.get("safe_area_id").toString());
            safeSiJobFire.setSafeAreaName(params.get("safe_area_name").toString());
            safeSiJobFire.setPlace(params.get("place").toString());
            safeSiJobFire.setMode(params.get("mode").toString());
            safeSiJobFire.setJobResponsiblePersonName(params.get("job_responsible_person_name").toString());
            safeSiJobFire.setJobResponsiblePersonId(params.get("job_responsible_person_id").toString());
            safeSiJobFire.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeSiJobFire.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeSiJobFire.setOtherJob(params.get("other_job").toString());
            safeSiJobFire.setSafeEducationPersonName(params.get("safe_education_person_name").toString());
            safeSiJobFire.setHarmIdentification(params.get("harm_identification").toString());
            safeSiJobFire.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeSiJobFire.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeSiJobFire.setWorkTypeName(params.get("work_type_name").toString());
            safeSiJobFire.setProductionResponsiblePersonName(params.get("production_responsible_person_name").toString());
            safeSiJobFire.setRelationGuardianPersonId(params.get("relation_guardian_person_id").toString());
            safeSiJobFire.setRelationGuardianPersonName(params.get("relation_guardian_person_name").toString());
            safeSiJobFire.setRelationGuardianPersonWorkTypeName(params.get("relation_guardian_person_work_type_name").toString());
            safeSiJobFire.setEquipmentUnitId(params.get("equipment_unit_id").toString());
            safeSiJobFire.setEquipmentUnitName(params.get("equipment_unit_name").toString());
            safeSiJobFire.setSpecialJobNo(params.get("special_job_no").toString());
            safeSiJobFire.setJobContents(params.get("job_contents").toString());
            safeSiJobFire.setJobResponsiblePersonPhone(params.get("job_responsible_person_phone").toString());
            safeSiJobFire.setIsGasArea(Integer.parseInt(params.get("is_gas_area").toString()));
            safeSiJobFire.setSpecialName(params.get("special_name").toString());
            if (updateById(safeSiJobFire)) {
                return ResponseUtil.toResult(params, "动火作业证保存成功");
            } else {
                return ResponseUtil.toError(params, "动火作业证保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "动火作业证记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobFire(Map<String, Object> params) {
        SafeSiJobFire safeSiJobFire = getById(params.get("id").toString());
        if (safeSiJobFire.getStatus() != 0) {
            return ResponseUtil.toError(params, "对不起，您只能删除未审批的作业证");
        }
        if (removeById(params.get(ID).toString())) {
            safeJobFireMapper.deleteSafeJobFirePersons(params);
            safeJobFireMapper.deleteSafeJobFireAnalysis(params);
            safeJobFireMapper.deleteSafeJobFireSafetyMeasures(params);
            safeJobFireMapper.deleteSafeJobFireApproval(params);
            safeJobFireMapper.deleteSafeJobFireFiles(params);
            return ResponseUtil.toResult(params, "动火作业证删除成功");
        }
        else {
            return ResponseUtil.toError(params, "动火作业证删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobFireSafetyMeasures(Map<String, Object> params) {
        return (safeJobFireMapper.insertSafeJobFireSafetyMeasures(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobFireApproval(Map<String, Object> params) {
        return (safeJobFireMapper.insertSafeJobFireApproval(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobFirePersons(Map<String, Object> params) {
        safeJobFireMapper.deleteSafeJobFirePersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobFireAnalysis(Map<String, Object> params) {
        safeJobFireMapper.deleteSafeJobFireAnalysis(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeJobFireApprovalStatus(Map<String, Object> params) {
        SafeSiJobFire safeSiJobFire = getById(params.get("id").toString());
        if (safeSiJobFire.getStatus() != Integer.parseInt(params.get(STATUS).toString())) {
            safeSiJobFire.setStatus(Integer.parseInt(params.get("status").toString()));
            if (Integer.parseInt(params.get(STATUS).toString()) == STATUS_WORK) {
                safeSiJobFire.setFinishTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            return updateById(safeSiJobFire);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireApprovalTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobFireMapper.getSafeJobFireApprovalTemplateList(params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobFireFiles(Map<String, Object> params) {
        safeJobFireMapper.deleteSafeJobFireFiles(params);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobFireMapper.getSafeJobFireFileList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireJobLevelTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobFireMapper.getSafeJobFireJobLevelTemplateList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getErpDataSafeJobFireInfo(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireInfo(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireSafetyMeasuresList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireSafetyMeasuresList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireJobLevelList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireJobLevelList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireApprovalList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireApprovalList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireConfirmList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireConfirmList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireAnalysisList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireAnalysisList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFirePersonList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFirePersonList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireFileList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireFileList(params);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public void sendSafeJobFireMessageQueue(Map<String, Object> params) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("thread sleep failure");
        }
        Map<String, Object> outMap = new HashMap<>(1);
        outMap.put("class", "com.icsc.si.logic.sijcnbApprLogic");
        outMap.put("message_id", "si34");
        outMap.put("action_code", "N");
        outMap.put("data_source", "cep");
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("si34vo", getErpDataSafeJobFireInfo(params));
        dataMap.put("si34list2", getErpDataSafeJobFireJobLevelList(params));
        dataMap.put("si34list3", getErpDataSafeJobFireAnalysisList(params));
        dataMap.put("si34list4", getErpDataSafeJobFirePersonList(params));
        dataMap.put("si34list5", getErpDataSafeJobFireSafetyMeasuresList(params));
        List<Map<String, Object>> approvalList = getErpDataSafeJobFireApprovalList(params);
        for (int i = 0; i < approvalList.size(); i++) {
            Map<String, Object> approval = approvalList.get(i);
            approval.put("id", JobUtil.generateAprovalFlowId());
            if (i > 0) {
                Map<String, Object> lastApproval = approvalList.get(i - 1);
                approval.put("receiveDate", lastApproval.get("dealDate"));
                approval.put("receiveTime", lastApproval.get("dealTime"));
            }
        }
        dataMap.put("si34Appr", approvalList);
        List<Map<String, Object>> confirmList = getErpDataSafeJobFireConfirmList(params);
        dataMap.put("si34confirm", confirmList);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", SAFETY_EXCHANGE, SAFETY_FIRE_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(SAFETY_EXCHANGE, SAFETY_FIRE_ROUTE_KEY, JSONObject.toJSONString(outMap));
        String uuid = confirmList.get(0).get("uuid").toString();
        String userId = confirmList.get(0).get("updateNo").toString();
        List<Map<String, Object>> fileList = getErpDataSafeJobFireFileList(params);
        for (Map<String, Object> file: fileList) {
            String tmpFile = downloadDir + IdUtil.randomUUID() + '.' + file.get("suffix").toString();
            HttpUtil.downloadFile(downloadUrl + file.get("appendix_id").toString(), tmpFile);
            HashMap<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("sijcAppr", "SI");
            paramMap.put("uuid", uuid);
            paramMap.put("userId", userId);
            paramMap.put("imgName", file.get("file_name").toString().substring(0, file.get("file_name").toString().indexOf(".") - 1));
            paramMap.put("imgformat", file.get("suffix").toString());
            paramMap.put("file", FileUtil.file(tmpFile));
            HttpUtil.post(uploadUrl, paramMap);
            FileUtil.del(tmpFile);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobFireJobCode(Map<String, Object> params) {
        SafeSiJobFire safeSiJobFire = getById(params.get("uuid").toString());
        if (null != safeSiJobFire) {
            safeSiJobFire.setJobCodeErp(params.get("dangerNo").toString());
            if (updateById(safeSiJobFire)) {
                return ResponseUtil.toResult(params, "动火作业证编号保存成功");
            } else {
                return ResponseUtil.toError(params, "动火作业证编号保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "动火作业证记录不存在");
        }
    }

}
