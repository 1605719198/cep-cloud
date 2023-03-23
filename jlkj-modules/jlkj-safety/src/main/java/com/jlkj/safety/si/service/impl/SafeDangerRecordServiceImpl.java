package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.jlkj.safety.si.entity.SafeSiDangerRecord;
import com.jlkj.safety.si.mapper.SafeDangerRecordMapper;
import com.jlkj.safety.si.service.SafeDangerRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 隐患处理 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-06-22
 */
@Service
public class SafeDangerRecordServiceImpl extends ServiceImpl<SafeDangerRecordMapper, SafeSiDangerRecord> implements SafeDangerRecordService {

    @Resource
    SafeDangerRecordMapper safeDangerRecordMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeDangerRecordPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeDangerRecordMapper.getSafeDangerRecordPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeDangerRecordById(Map<String, Object> params) {
        Map<String, Object> data = safeDangerRecordMapper.getSafeDangerRecordById(params);
        if (null == data) {
            return ResponseUtil.toError(params, "安全隐患数据不存在");
        }
        else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addSafeDangerRecord(Map<String, Object> params) {
        SafeSiDangerRecord safeSiDangerRecord = new SafeSiDangerRecord();
        safeSiDangerRecord.setId(IdUtil.randomUUID());
        safeSiDangerRecord.setSafeAreaId(params.get("safe_area_id").toString());
        safeSiDangerRecord.setSafeAreaName(params.get("safe_area_name").toString());
        safeSiDangerRecord.setDangerTypeId(params.get("danger_type_id").toString());
        safeSiDangerRecord.setDangerTypeName(params.get("danger_type_name").toString());
        safeSiDangerRecord.setDangerContent(params.get("danger_content").toString());
        safeSiDangerRecord.setDisposeMeasure(params.get("dispose_measure").toString());
        safeSiDangerRecord.setDangerDeadlineTime(DateUtil.parseLocalDateTime(params.get("danger_deadline_time").toString()));
        safeSiDangerRecord.setDutyDepartId(params.get("duty_depart_id").toString());
        safeSiDangerRecord.setDutyDepartName(params.get("duty_depart_name").toString());
        safeSiDangerRecord.setDutyPersonId(params.get("duty_person_id").toString());
        safeSiDangerRecord.setDutyPersonName(params.get("duty_person_name").toString());
        safeSiDangerRecord.setSubmitDepartId(params.get("submit_depart_id").toString());
        safeSiDangerRecord.setSubmitDepartName(params.get("submit_depart_name").toString());
        safeSiDangerRecord.setSubmitPersonId(params.get("submit_person_id").toString());
        safeSiDangerRecord.setSubmitPersonName(params.get("submit_person_name").toString());
        safeSiDangerRecord.setAcceptancePersonId(params.get("acceptance_person_id").toString());
        safeSiDangerRecord.setAcceptancePersonName(params.get("acceptance_person_name").toString());
        safeSiDangerRecord.setDangerStatus(0);
        safeSiDangerRecord.setDangerTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        safeSiDangerRecord.setSubmitImage1(params.get("submit_image1").toString());
        safeSiDangerRecord.setSubmitImage2(params.get("submit_image2").toString());
        safeSiDangerRecord.setSubmitImage3(params.get("submit_image3").toString());
        safeSiDangerRecord.setSubmitImage4(params.get("submit_image4").toString());
        safeSiDangerRecord.setSubmitSignImage(params.get("submit_sign_image").toString());
        if (save(safeSiDangerRecord)) {
            return ResponseUtil.toResult(params, "安全隐患保存成功", safeSiDangerRecord);
        } else {
            return ResponseUtil.toError(params, "安全隐患保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object disposeSafeDanger(Map<String, Object> params) {
        SafeSiDangerRecord safeSiDangerRecord = getById(params.get("danger_id").toString());
        if (null != safeSiDangerRecord) {
            safeSiDangerRecord.setDisposeContent(params.get("dispose_content").toString());
            safeSiDangerRecord.setDisposerId(params.get("disposer_id").toString());
            safeSiDangerRecord.setDisposerName(params.get("disposer_name").toString());
            safeSiDangerRecord.setAcceptancePersonId(params.get("acceptance_person_id").toString());
            safeSiDangerRecord.setAcceptancePersonName(params.get("acceptance_person_name").toString());
            safeSiDangerRecord.setDangerStatus(1);
            safeSiDangerRecord.setDisposeTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiDangerRecord)) {
                return ResponseUtil.toResult(params, "安全隐患整改成功");
            } else {
                return ResponseUtil.toError(params, "安全隐患整改失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全隐患数据不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object acceptanceSafeDanger(Map<String, Object> params) {
        SafeSiDangerRecord safeSiDangerRecord = getById(params.get("danger_id").toString());
        if (null != safeSiDangerRecord) {
            safeSiDangerRecord.setAcceptanceDepartId(params.get("acceptance_depart_id").toString());
            safeSiDangerRecord.setAcceptanceDepartName(params.get("acceptance_depart_name").toString());
            safeSiDangerRecord.setAcceptancePersonId(params.get("acceptance_person_id").toString());
            safeSiDangerRecord.setAcceptancePersonName(params.get("acceptance_person_name").toString());
            safeSiDangerRecord.setAcceptanceAppraise(params.get("acceptance_appraise").toString());
            safeSiDangerRecord.setDangerStatus(2);
            safeSiDangerRecord.setAcceptanceTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiDangerRecord.setAcceptanceImage1(params.get("acceptance_image1").toString());
            safeSiDangerRecord.setAcceptanceImage2(params.get("acceptance_image2").toString());
            safeSiDangerRecord.setAcceptanceImage3(params.get("acceptance_image3").toString());
            safeSiDangerRecord.setAcceptanceImage4(params.get("acceptance_image4").toString());
            safeSiDangerRecord.setAcceptanceSignImage(params.get("acceptance_sign_image").toString());
            if (updateById(safeSiDangerRecord)) {
                return ResponseUtil.toResult(params, "安全隐患验收成功");
            } else {
                return ResponseUtil.toError(params, "安全隐患验收失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全隐患数据不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeDanger(Map<String, Object> params) {
        SafeSiDangerRecord safeSiDangerRecord = getById(params.get("danger_id").toString());
        if (null != safeSiDangerRecord) {
            if(safeSiDangerRecord.getDangerStatus() != 0) {
                return ResponseUtil.toError(params, "只能删除待整改的安全隐患数据");
            }
            if (removeById(safeSiDangerRecord.getId())) {
                return ResponseUtil.toResult(params, "安全隐患删除成功");
            } else {
                return ResponseUtil.toError(params, "安全隐患删除失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全隐患数据不存在");
        }
    }
}
