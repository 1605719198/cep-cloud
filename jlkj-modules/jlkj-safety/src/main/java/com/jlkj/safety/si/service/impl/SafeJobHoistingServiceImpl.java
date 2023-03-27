package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobHoisting;
import com.jlkj.safety.si.mapper.SafeJobHoistingMapper;
import com.jlkj.safety.si.service.SafeJobHoistingService;
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
 * 4.吊装安全作业证 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
@Slf4j
public class SafeJobHoistingServiceImpl extends ServiceImpl<SafeJobHoistingMapper, SafeSiJobHoisting> implements SafeJobHoistingService {
    @Resource
    private SafeJobHoistingMapper safeJobHoistingMapper;

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
        sb.append("DZ").append(sdf.format(date)).append("-");
        params.put("codeHeader", sb.toString());
        Map<String, Object> jobData = safeJobHoistingMapper.getMaxJobCode(params);
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
    public Object getSafeJobHoistingInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobHoistingMapper.getSafeJobHoistingInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "吊装安全数据不存在");
        }
        else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHoistingPersonsList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHoistingMapper.getSafeJobHoistingPersonsList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHoistingSafetyMeasuresList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHoistingMapper.getSafeJobHoistingSafetyMeasuresList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHoistingApprovalList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHoistingMapper.getSafeJobHoistingApprovalList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHoistingPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHoistingMapper.getSafeJobHoistingPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobHoisting(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("job_code", params.get("job_code").toString());
        if (listByMap(query).size() == 0) {
            SafeSiJobHoisting safeSiJobHoisting = new SafeSiJobHoisting();
            safeSiJobHoisting.setId(IdUtil.randomUUID());
            safeSiJobHoisting.setApplyDepartId(params.get("apply_depart_id").toString());
            safeSiJobHoisting.setApplyDepartName(params.get("apply_depart_name").toString());
            safeSiJobHoisting.setApplyPersonId(params.get("apply_person_id").toString());
            safeSiJobHoisting.setApplyPersonName(params.get("apply_person_name").toString());
            safeSiJobHoisting.setJobCode(params.get("job_code").toString());
            safeSiJobHoisting.setSafeAreaId(params.get("safe_area_id").toString());
            safeSiJobHoisting.setSafeAreaName(params.get("safe_area_name").toString());
            safeSiJobHoisting.setPlace(params.get("place").toString());
            safeSiJobHoisting.setHoistingTools(params.get("hoisting_tools").toString());
            safeSiJobHoisting.setJobContent(params.get("job_content").toString());
            safeSiJobHoisting.setWeight(params.get("weight").toString());
            safeSiJobHoisting.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeSiJobHoisting.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeSiJobHoisting.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeSiJobHoisting.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeSiJobHoisting.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeSiJobHoisting.setSafeEducationPersonsId(params.get("safe_education_persons_id").toString());
            safeSiJobHoisting.setHarmIdentification(params.get("harm_identification").toString());
            safeSiJobHoisting.setStatus(0);
            safeSiJobHoisting.setApplyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiJobHoisting.setFinishTime(null);
            safeSiJobHoisting.setHoistName(params.get("hoist_name").toString());
            safeSiJobHoisting.setJobLevel(params.get("job_level").toString());
            safeSiJobHoisting.setHoistChargeId(params.get("hoist_charge_id").toString());
            safeSiJobHoisting.setHoistChargeName(params.get("hoist_charge_name").toString());
            safeSiJobHoisting.setSpecialName(params.get("special_name").toString());
            if (save(safeSiJobHoisting)) {
                return ResponseUtil.toResult(params, "吊装安全作业证保存成功", safeSiJobHoisting);
            } else {
                return ResponseUtil.toError(params, "吊装安全作业证保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "作业证编号已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobHoisting(Map<String, Object> params) {
        SafeSiJobHoisting safeSiJobHoisting = getById(params.get("id").toString());
        if (null != safeSiJobHoisting) {
            safeSiJobHoisting.setApplyDepartId(params.get("apply_depart_id").toString());
            safeSiJobHoisting.setApplyDepartName(params.get("apply_depart_name").toString());
            safeSiJobHoisting.setApplyPersonId(params.get("apply_person_id").toString());
            safeSiJobHoisting.setApplyPersonName(params.get("apply_person_name").toString());
            safeSiJobHoisting.setJobCode(params.get("job_code").toString());
            safeSiJobHoisting.setSafeAreaId(params.get("safe_area_id").toString());
            safeSiJobHoisting.setSafeAreaName(params.get("safe_area_name").toString());
            safeSiJobHoisting.setPlace(params.get("place").toString());
            safeSiJobHoisting.setHoistingTools(params.get("hoisting_tools").toString());
            safeSiJobHoisting.setJobContent(params.get("job_content").toString());
            safeSiJobHoisting.setWeight(params.get("weight").toString());
            safeSiJobHoisting.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeSiJobHoisting.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeSiJobHoisting.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeSiJobHoisting.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeSiJobHoisting.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeSiJobHoisting.setSafeEducationPersonsId(params.get("safe_education_persons_id").toString());
            safeSiJobHoisting.setHarmIdentification(params.get("harm_identification").toString());
            safeSiJobHoisting.setHoistName(params.get("hoist_name").toString());
            safeSiJobHoisting.setJobLevel(params.get("job_level").toString());
            safeSiJobHoisting.setHoistChargeId(params.get("hoist_charge_id").toString());
            safeSiJobHoisting.setHoistChargeName(params.get("hoist_charge_name").toString());
            safeSiJobHoisting.setSpecialName(params.get("special_name").toString());
            if (updateById(safeSiJobHoisting)) {
                return ResponseUtil.toResult(params, "吊装安全作业证保存成功");
            } else {
                return ResponseUtil.toError(params, "吊装安全作业证保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "吊装安全作业证记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobHoisting(Map<String, Object> params) {
        SafeSiJobHoisting safeSiJobHoisting = getById(params.get("id").toString());
        if (safeSiJobHoisting.getStatus() != 0) {
            return ResponseUtil.toError(params, "对不起，您只能删除未审批的作业证");
        }
        if (removeById(params.get(ID).toString())) {
            safeJobHoistingMapper.deleteSafeJobHoistingPersons(params);
            safeJobHoistingMapper.deleteSafeJobHoistingSafetyMeasures(params);
            safeJobHoistingMapper.deleteSafeJobHoistingApproval(params);
            safeJobHoistingMapper.deleteSafeJobHoistingFiles(params);
            return ResponseUtil.toResult(params, "吊装安全作业证删除成功");
        }
        else {
            return ResponseUtil.toError(params, "吊装安全作业证删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHoistingSafetyMeasures(Map<String, Object> params) {
        return (safeJobHoistingMapper.insertSafeJobHoistingSafetyMeasures(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHoistingApproval(Map<String, Object> params) {
        return (safeJobHoistingMapper.insertSafeJobHoistingApproval(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobHoistingPersons(Map<String, Object> params) {
        safeJobHoistingMapper.deleteSafeJobHoistingPersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeJobHoistingApprovalStatus(Map<String, Object> params) {
        SafeSiJobHoisting safeSiJobHoisting = getById(params.get("id").toString());
        if (safeSiJobHoisting.getStatus() != Integer.parseInt(params.get(STATUS).toString())) {
            safeSiJobHoisting.setStatus(Integer.parseInt(params.get("status").toString()));
            if (Integer.parseInt(params.get(STATUS).toString()) == STATUS_WORK) {
                safeSiJobHoisting.setFinishTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            return updateById(safeSiJobHoisting);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHoistingApprovalTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHoistingMapper.getSafeJobHoistingApprovalTemplateList(params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobHoistingFiles(Map<String, Object> params) {
        safeJobHoistingMapper.deleteSafeJobHoistingFiles(params);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHoistingFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHoistingMapper.getSafeJobHoistingFileList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getErpDataSafeJobHoistingInfo(Map<String, Object> params) {
        return safeJobHoistingMapper.getErpDataSafeJobHoistingInfo(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobHoistingSafetyMeasuresList(Map<String, Object> params) {
        return safeJobHoistingMapper.getErpDataSafeJobHoistingSafetyMeasuresList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobHoistingJobLevelList(Map<String, Object> params) {
        return safeJobHoistingMapper.getErpDataSafeJobHoistingJobLevelList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobHoistingApprovalList(Map<String, Object> params) {
        return safeJobHoistingMapper.getErpDataSafeJobHoistingApprovalList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobHoistingConfirmList(Map<String, Object> params) {
        return safeJobHoistingMapper.getErpDataSafeJobHoistingConfirmList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobHoistingFileList(Map<String, Object> params) {
        return safeJobHoistingMapper.getErpDataSafeJobHoistingFileList(params);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public void sendSafeJobHoistingMessageQueue(Map<String, Object> params) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("thread sleep failure");
        }
        Map<String, Object> outMap = new HashMap<>(1);
        outMap.put("class", "com.icsc.si.logic.sijcnbApprLogic");
        outMap.put("message_id", "si61");
        outMap.put("action_code", "N");
        outMap.put("data_source", "cep");
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("si61vo", getErpDataSafeJobHoistingInfo(params));
        dataMap.put("si61list2", getErpDataSafeJobHoistingJobLevelList(params));
        dataMap.put("si61list5", getErpDataSafeJobHoistingSafetyMeasuresList(params));
        List<Map<String, Object>> approvalList = getErpDataSafeJobHoistingApprovalList(params);
        for (int i = 0; i < approvalList.size(); i++) {
            Map<String, Object> approval = approvalList.get(i);
            approval.put("id", JobUtil.generateAprovalFlowId());
            if (i > 0) {
                Map<String, Object> lastApproval = approvalList.get(i - 1);
                approval.put("receiveDate", lastApproval.get("dealDate"));
                approval.put("receiveTime", lastApproval.get("dealTime"));
            }
        }
        dataMap.put("si61Appr", approvalList);
        List<Map<String, Object>> confirmList = getErpDataSafeJobHoistingConfirmList(params);
        dataMap.put("si61confirm", confirmList);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", SAFETY_EXCHANGE, SAFETY_HOISTING_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(SAFETY_EXCHANGE, SAFETY_HOISTING_ROUTE_KEY, JSONObject.toJSONString(outMap));
        String uuid = confirmList.get(0).get("uuid").toString();
        String userId = confirmList.get(0).get("updateNo").toString();
        List<Map<String, Object>> fileList = getErpDataSafeJobHoistingFileList(params);
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
    public Object updateSafeJobHoistingJobCode(Map<String, Object> params) {
        SafeSiJobHoisting safeSiJobHoisting = getById(params.get("uuid").toString());
        if (null != safeSiJobHoisting) {
            safeSiJobHoisting.setJobCodeErp(params.get("dangerNo").toString());
            if (updateById(safeSiJobHoisting)) {
                return ResponseUtil.toResult(params, "吊装作业证编号保存成功");
            } else {
                return ResponseUtil.toError(params, "吊装作业证编号保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "吊装作业证记录不存在");
        }
    }
}
