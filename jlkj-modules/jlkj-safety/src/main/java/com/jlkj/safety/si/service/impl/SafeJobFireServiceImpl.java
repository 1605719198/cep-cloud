package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.entity.SafeSiJobFire;
import com.jlkj.safety.si.entity.SafeSiJobFireApproval;
import com.jlkj.safety.si.mapper.SafeJobFireMapper;
import com.jlkj.safety.si.service.*;
import com.jlkj.safety.si.utils.JobUtil;
import com.jlkj.safety.si.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import org.springframework.context.annotation.Lazy;
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
    SafeJobFirePersonsService safeJobFirePersonsService;

    @Resource
    SafeJobFireAnalysisService safeJobFireAnalysisService;

    @Lazy
    @Resource
    SafeJobFireApprovalService safeJobFireApprovalService;

    @Resource
    SafeJobFireAppendixService safeJobFireAppendixService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${erp-file-upload.download-dir}")
    private String downloadDir;

    @Value("${erp-file-upload.download-url}")
    private String downloadUrl;

    @Value("${erp-file-upload.upload-url}")
    private String uploadUrl;

    /**
     * 动火安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
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
        return AjaxResult.success(data);
    }

    /**
     * 动火安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobFireMapper.getSafeJobFireInfo(params);
        if (null == data) {
            return AjaxResult.error("动火数据不存在", params);
        }
        else {
            return AjaxResult.success(data);
        }
    }

    /**
     * 动火安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFirePersonsList(Map<String, Object> params) {
        return AjaxResult.success(safeJobFireMapper.getSafeJobFirePersonsList(params));
    }

    /**
     * 动火安全作业证-作业分析列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireAnalysisList(Map<String, Object> params) {
        return AjaxResult.success(safeJobFireMapper.getSafeJobFireAnalysisList(params));
    }

    /**
     * 动火安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireSafetyMeasuresList(Map<String, Object> params) {
        return AjaxResult.success(safeJobFireMapper.getSafeJobFireSafetyMeasuresList(params));
    }

    /**
     * 动火安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireApprovalList(Map<String, Object> params) {
        return AjaxResult.success(safeJobFireMapper.getSafeJobFireApprovalList(params));
    }

    /**
     * 动火安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFirePageList(Map<String, Object> params) {
        return AjaxResult.success(safeJobFireMapper.getSafeJobFirePageList(ResponseUtil.initPage(params), params));
    }

    /**
     * 动火安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
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
                return AjaxResult.success("动火作业证保存成功", safeSiJobFire);
            } else {
                return AjaxResult.error("动火作业证保存失败", params);
            }
        }
        else {
            return AjaxResult.error("作业证编号已存在", params);
        }
    }

    /**
     * 动火安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
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
                return AjaxResult.success("动火作业证保存成功");
            } else {
                return AjaxResult.error("动火作业证保存失败", params);
            }
        }
        else {
            return AjaxResult.error("动火作业证记录不存在", params);
        }
    }

    /**
     * 动火安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobFire(Map<String, Object> params) {
        SafeSiJobFire safeSiJobFire = getById(params.get("id").toString());
        if (safeSiJobFire.getStatus() != 0) {
            return AjaxResult.error("对不起，您只能删除未审批的作业证", params);
        }
        if (removeById(params.get(ID).toString())) {
            safeJobFireMapper.deleteSafeJobFirePersons(params);
            safeJobFireMapper.deleteSafeJobFireAnalysis(params);
            safeJobFireMapper.deleteSafeJobFireSafetyMeasures(params);
            safeJobFireMapper.deleteSafeJobFireApproval(params);
            safeJobFireMapper.deleteSafeJobFireFiles(params);
            return AjaxResult.success("动火作业证删除成功");
        }
        else {
            return AjaxResult.error("动火作业证删除失败", params);
        }
    }

    /**
     * 动火安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobFireSafetyMeasures(Map<String, Object> params) {
        return (safeJobFireMapper.insertSafeJobFireSafetyMeasures(params) > 0);
    }

    /**
     * 动火安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobFireApproval(Map<String, Object> params) {
        return (safeJobFireMapper.insertSafeJobFireApproval(params) > 0);
    }

    /**
     * 动火安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobFirePersons(Map<String, Object> params) {
        safeJobFireMapper.deleteSafeJobFirePersons(params);
        return true;
    }

    /**
     * 动火安全作业证-删除作业分析
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobFireAnalysis(Map<String, Object> params) {
        safeJobFireMapper.deleteSafeJobFireAnalysis(params);
        return true;
    }

    /**
     * 动火安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
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

    /**
     * 动火安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireApprovalTemplateList(Map<String, Object> params) {
        return AjaxResult.success(safeJobFireMapper.getSafeJobFireApprovalTemplateList(params));
    }

    /**
     * 动火安全作业证-删除附件
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobFireFiles(Map<String, Object> params) {
        safeJobFireMapper.deleteSafeJobFireFiles(params);
        return true;
    }

    /**
     * 动火安全作业证-附件列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireFileList(Map<String, Object> params) {
        return AjaxResult.success(safeJobFireMapper.getSafeJobFireFileList(params));
    }

    /**
     * 动火安全作业证-作业级别模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobFireJobLevelTemplateList(Map<String, Object> params) {
        return AjaxResult.success(safeJobFireMapper.getSafeJobFireJobLevelTemplateList(params));
    }

    /**
     * 动火安全作业证-报文-信息
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(readOnly = true)
    public Object getErpDataSafeJobFireInfo(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireInfo(params);
    }

    /**
     * 动火安全作业证-报文-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireSafetyMeasuresList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireSafetyMeasuresList(params);
    }

    /**
     * 动火安全作业证-报文-作业级别列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireJobLevelList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireJobLevelList(params);
    }

    /**
     * 动火安全作业证-报文-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireApprovalList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireApprovalList(params);
    }

    /**
     * 动火安全作业证-报文-确认列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireConfirmList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireConfirmList(params);
    }

    /**
     * 动火安全作业证-报文-分析列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireAnalysisList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireAnalysisList(params);
    }

    /**
     * 动火安全作业证-报文-动火人列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFirePersonList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFirePersonList(params);
    }

    /**
     * 动火安全作业证-报文-附件列表
     * @param params 请求参数
     * @return 结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getErpDataSafeJobFireFileList(Map<String, Object> params) {
        return safeJobFireMapper.getErpDataSafeJobFireFileList(params);
    }

    /**
     * 动火安全作业证-报文-发送
     * @param params 请求参数
     */
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

    /**
     * 动火安全作业证-更新作业证编号
     * @param params 请求参数
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobFireJobCode(Map<String, Object> params) {
        SafeSiJobFire safeSiJobFire = getById(params.get("uuid").toString());
        if (null != safeSiJobFire) {
            safeSiJobFire.setJobCodeErp(params.get("dangerNo").toString());
            if (updateById(safeSiJobFire)) {
                return AjaxResult.success("动火作业证编号保存成功");
            } else {
                return AjaxResult.error("动火作业证编号保存失败", params);
            }
        }
        else {
            return AjaxResult.error("动火作业证记录不存在", params);
        }
    }

    /**
     * 动火作业证-新增
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    @Override
    public Object insertSafeJobFireData(Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "动火作业证保存失败";
        try {
            SafeSiJobFire safeSiJobFire = null;
            Map<String, Object> objectMap = (Map<String, Object>) insertSafeJobFire(params);
            int code = Integer.parseInt(objectMap.get("code").toString());
            if (code == 200) {
                safeSiJobFire = (SafeSiJobFire) objectMap.get("data");
                msg = insertSafeJobFireCore(safeSiJobFire, params);
            } else {
                msg = objectMap.get("msg").toString();
            }
            if ("".equals(msg)) {
                Map<String, Object> outData = new HashMap<>(1);
                outData.put("id", safeSiJobFire.getId());
                return AjaxResult.success("动火作业证保存成功", outData);
            } else {
                return AjaxResult.error(msg, params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(msg, params);
        }
    }

    /**
     * 动火作业证-修改
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    @Override
    public Object updateSafeJobFireData(Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "动火作业证保存失败";
        try {
            Map<String, Object> objectMap = (Map<String, Object>) updateSafeJobFire(params);
            int code = Integer.parseInt(objectMap.get("code").toString());
            if (code == 200) {
                msg = updateSafeJobFireCore(params);
            } else {
                msg = objectMap.get("msg").toString();
            }
            if ("".equals(msg)) {
                return AjaxResult.success("动火作业证保存成功");
            } else {
                return AjaxResult.error(msg, params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(msg, params);
        }
    }

    /**
     * 动火作业证-批量保存动火人
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    @Override
    public Object batchSaveSafeJobPerson(Map<String, Object> params) {
        String msg = "动火人保存失败";
        try {
            boolean succ = true;
            Map<String, Object> deleteParam = new HashMap<>(1);
            deleteParam.put("id", params.get("id").toString());
            if (!deleteSafeJobFirePersons(deleteParam)) {
                succ = false;
                msg = "动火人删除失败";
            }
            if (succ) {
                if (!"".equals(params.get(PERSON_LIST).toString())) {
                    List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get(PERSON_LIST)).toString(), Map.class);
                    for (int i = 0; i < listPerson.size(); i++) {
                        Map<String, Object> param = listPerson.get(i);
                        param.put("job_id", params.get("id").toString());
                        if (!safeJobFirePersonsService.insertSafeJobFirePersons(param)) {
                            succ = false;
                            msg = "动火人保存失败";
                            break;
                        }
                    }
                }
            }
            if (succ) {
                return AjaxResult.success("动火人保存成功");
            } else {
                return AjaxResult.error(msg, params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(msg, params);
        }
    }

    /**
     * 动火作业证-批量保存动火分析
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    @Override
    public Object batchSaveSafeJobAnalysis(Map<String, Object> params) {
        String msg = "动火分析保存失败";
        try {
            boolean succ = true;
            Map<String, Object> deleteParam = new HashMap<>(1);
            deleteParam.put("id", params.get("id").toString());
            if (!deleteSafeJobFireAnalysis(deleteParam)) {
                succ = false;
                msg = "动火分析数据删除失败";
            }
            if (succ) {
                if (!"".equals(params.get(ANALYSIS_LIST).toString())) {
                    List<Map> listAnalysis = JSONObject.parseArray(JSONObject.toJSON(params.get(ANALYSIS_LIST)).toString(), Map.class);
                    for (int i = 0; i < listAnalysis.size(); i++) {
                        Map<String, Object> param = listAnalysis.get(i);
                        param.put("job_id", params.get("id").toString());
                        if (!safeJobFireAnalysisService.insertSafeJobFireAnalysis(param)) {
                            succ = false;
                            msg = "动火分析保存失败";
                            break;
                        }
                    }
                }
            }
            if (succ) {
                return AjaxResult.success("动火分析保存成功");
            } else {
                return AjaxResult.error(msg, params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(msg, params);
        }
    }

    /**
     * 动火作业证-修改
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    private String updateSafeJobFireCore(Map<String, Object> params) {
        boolean succ = true;
        String msg = "";
        Map<String, Object> deleteParam = new HashMap<>(1);
        deleteParam.put("id", params.get("id").toString());
        if (!(safeJobFireMapper.deleteSafeJobFirePersons(deleteParam)>0)) {
            succ = false;
            msg = "动火人删除失败";
        }
        if (succ) {
            SafeSiJobFire safeSiJobFire = getBaseMapper().selectById(params.get("id").toString());
            safeJobFireApprovalService.remove(new QueryWrapper<SafeSiJobFireApproval>().lambda()
                    .eq(SafeSiJobFireApproval::getJobId, safeSiJobFire.getId())
            );
            succ = insertSafeJobFireApprovals(safeSiJobFire, params);
            if (!succ) {
                msg = "审批人员添加失败";
            }
        }
        if (succ) {
            if (!(safeJobFireMapper.deleteSafeJobFireAnalysis(deleteParam)>0)) {
                succ = false;
                msg = "动火分析数据删除失败";
            }
        }
        if (succ) {
            if (!"".equals(params.get(PERSON_LIST).toString())) {
                List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get(PERSON_LIST)).toString(), Map.class);
                for (int i = 0; i < listPerson.size(); i++) {
                    Map<String, Object> param = listPerson.get(i);
                    param.put("job_id", params.get("id").toString());
                    if (!safeJobFirePersonsService.insertSafeJobFirePersons(param)) {
                        succ = false;
                        msg = "动火人保存失败";
                        break;
                    }
                }
            }
        }
        if (succ) {
            if (!"".equals(params.get(ANALYSIS_LIST).toString())) {
                List<Map> listAnalysis = JSONObject.parseArray(JSONObject.toJSON(params.get(ANALYSIS_LIST)).toString(), Map.class);
                for (int i = 0; i < listAnalysis.size(); i++) {
                    Map<String, Object> param = listAnalysis.get(i);
                    param.put("job_id", params.get("id").toString());
                    if (!safeJobFireAnalysisService.insertSafeJobFireAnalysis(param)) {
                        succ = false;
                        msg = "动火分析保存失败";
                        break;
                    }
                }
            }
        }
        if (succ) {
            if (safeJobFireMapper.deleteSafeJobFireFiles(deleteParam)>0) {
                succ = false;
                msg = "附件删除失败";
            }
        }
        if (succ) {
            if (!"".equals(params.get(FILE_LIST).toString())) {
                List<Map> listFile = JSONObject.parseArray(JSONObject.toJSON(params.get(FILE_LIST)).toString(), Map.class);
                for (int i = 0; i < listFile.size(); i++) {
                    Map<String, Object> param = listFile.get(i);
                    param.put("job_id", params.get("id").toString());
                    if (!safeJobFireAppendixService.insertSafeJobFireFile(param)) {
                        succ = false;
                        msg = "附件添加失败";
                        break;
                    }
                }
            }
        }
        return msg;
    }

    /**
     * 动火作业证-新增
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    private String insertSafeJobFireCore(SafeSiJobFire safeSiJobFire, Map<String, Object> params) {
        boolean succ;
        String msg = "";
        succ = insertSafeJobFirePersons(safeSiJobFire, params);
        if (!succ) {
            msg = "动火人添加失败";
        }
        if (succ) {
            succ = insertSafeJobFireAnalysis(safeSiJobFire, params);
            if (!succ) {
                msg = "动火分析添加失败";
            }
        }
        if (succ) {
            succ = insertSafeJobFireApprovals(safeSiJobFire, params);
            if (!succ) {
                msg = "审批人员添加失败";
            }
        }
        if (succ) {
            succ = insertSafeJobFireFile(safeSiJobFire, params);
            if (!succ) {
                msg = "附件添加失败";
            }
        }
        if (succ) {
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", safeSiJobFire.getId());
            if (!insertSafeJobFireSafetyMeasures(param)) {
                succ = false;
                msg = "安全措施初始化失败";
            }
        }
        return msg;
    }

    /**
     * 动火作业证-新增动火人
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return boolean
     */
    private boolean insertSafeJobFirePersons(SafeSiJobFire safeSiJobFire, Map<String, Object> params) {
        boolean succ = true;
        if (!"".equals(params.get(PERSON_LIST).toString())) {
            List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get(PERSON_LIST)).toString(), Map.class);
            for (int i = 0; i < listPerson.size(); i++) {
                Map<String, Object> param = listPerson.get(i);
                param.put("job_id", safeSiJobFire.getId());
                if (!safeJobFirePersonsService.insertSafeJobFirePersons(param)) {
                    succ = false;
                    break;
                }
            }
        }
        return succ;
    }

    /**
     * 动火作业证-新增动火分析
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return boolean
     */
    private boolean insertSafeJobFireAnalysis(SafeSiJobFire safeSiJobFire, Map<String, Object> params) {
        boolean succ = true;
        if (!"".equals(params.get(ANALYSIS_LIST).toString())) {
            List<Map> listAnalysis =  JSONObject.parseArray(JSONObject.toJSON(params.get(ANALYSIS_LIST)).toString(), Map.class);
            for (int i = 0; i < listAnalysis.size(); i++) {
                Map<String, Object> param = listAnalysis.get(i);
                param.put("job_id", safeSiJobFire.getId());
                if (!safeJobFireAnalysisService.insertSafeJobFireAnalysis(param)) {
                    succ = false;
                    break;
                }
            }
        }
        return succ;
    }

    /**
     * 动火作业证-新增动火审批人
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return boolean
     */
    private boolean insertSafeJobFireApprovals(SafeSiJobFire safeSiJobFire, Map<String, Object> params) {
        boolean succ = true;
        if (!"".equals(params.get(APPROVAL_LIST).toString())) {
            List<Map> listApproval = JSONObject.parseArray(JSONObject.toJSON(params.get(APPROVAL_LIST)).toString(), Map.class);
            for (int i = 0; i < listApproval.size(); i++) {
                Map<String, Object> param = listApproval.get(i);
                param.put("job_id", safeSiJobFire.getId());
                if (!safeJobFireApprovalService.insertSafeJobFireApprovals(param)) {
                    succ = false;
                    break;
                }
            }
        }
        return succ;
    }

    /**
     * 动火作业证-新增动火附件
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return boolean
     */
    private boolean insertSafeJobFireFile(SafeSiJobFire safeSiJobFire, Map<String, Object> params) {
        boolean succ = true;
        if (!"".equals(params.get(FILE_LIST).toString())) {
            List<Map> listFile = JSONObject.parseArray(JSONObject.toJSON(params.get(FILE_LIST)).toString(), Map.class);
            for (int i = 0; i < listFile.size(); i++) {
                Map<String, Object> param = listFile.get(i);
                param.put("job_id", safeSiJobFire.getId());
                if (!safeJobFireAppendixService.insertSafeJobFireFile(param)) {
                    succ = false;
                    break;
                }
            }
        }
        return succ;
    }
}
