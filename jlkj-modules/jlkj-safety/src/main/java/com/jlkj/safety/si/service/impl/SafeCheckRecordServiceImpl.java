package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiCheckRecord;
import com.jlkj.safety.si.mapper.SafeCheckRecordMapper;
import com.jlkj.safety.si.service.SafeCheckRecordService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 安全巡检 服务实现类
 * </p>
 *
 * @author cy
 * @since 2021-04-08
 */
@Service
public class SafeCheckRecordServiceImpl extends ServiceImpl<SafeCheckRecordMapper, SafeSiCheckRecord> implements SafeCheckRecordService {

	@Resource
	private SafeCheckRecordMapper safeCheckRecordMapper;

	@Override
	@Transactional(readOnly = true)
	public Object getSafeCheckRecordPageList(Map<String, Object> params) {
		return ResponseUtil.toResult(params, safeCheckRecordMapper.getSafeCheckRecordPageList(ResponseUtil.initPage(params), params));
	}

	@Override
	@Transactional(readOnly = true)
	public Object getSafeCheckRecordById(Map<String, Object> params) {
		Map<String, Object> data = safeCheckRecordMapper.getSafeCheckRecordById(params);
		if (null == data) {
			return ResponseUtil.toError(params, "安全巡检记录不存在");
		}
		else {
			return ResponseUtil.toResult(params, data);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object insertSafeCheckRecord(Map<String, Object> params) {
		SafeSiCheckRecord safeSiCheckRecord = new SafeSiCheckRecord();
		safeSiCheckRecord.setId(IdUtil.randomUUID());
		safeSiCheckRecord.setCheckDepartId(params.get("check_depart_id").toString());
		safeSiCheckRecord.setCheckDepartName(params.get("check_depart_name").toString());
		safeSiCheckRecord.setCheckPersonId(params.get("check_person_id").toString());
		safeSiCheckRecord.setCheckPersonName(params.get("check_person_name").toString());
		safeSiCheckRecord.setSafeAreaId(params.get("safe_area_id").toString());
		safeSiCheckRecord.setSafeAreaName(params.get("safe_area_name").toString());
		safeSiCheckRecord.setCheckTypeId(params.get("check_type_id").toString());
		safeSiCheckRecord.setCheckTypeName(params.get("check_type_name").toString());
		safeSiCheckRecord.setCheckContent(params.get("check_content").toString());
		safeSiCheckRecord.setCheckStatus(Integer.parseInt(params.get("check_status").toString()));
		safeSiCheckRecord.setCheckImage1(params.get("check_image1").toString());
		safeSiCheckRecord.setCheckImage2(params.get("check_image2").toString());
		safeSiCheckRecord.setCheckImage3(params.get("check_image3").toString());
		safeSiCheckRecord.setCheckImage4(params.get("check_image4").toString());
		safeSiCheckRecord.setCheckTime(DateUtil.parseLocalDateTime(DateUtil.now()));
		safeSiCheckRecord.setCheckDisposed(0);
		safeSiCheckRecord.setDisposeContent("");
		safeSiCheckRecord.setDisposerId("");
		safeSiCheckRecord.setDisposerName("");
		safeSiCheckRecord.setDisposerImage1("");
		safeSiCheckRecord.setDisposerImage2("");
		safeSiCheckRecord.setDisposerImage3("");
		if (save(safeSiCheckRecord)) {
			Map<String, Object> out = new HashMap<>(1);
			out.put("safe_check_id", safeSiCheckRecord.getId());
			return ResponseUtil.toResult(params, "安全巡检记录保存成功", out);
		} else {
			return ResponseUtil.toError(params, "安全巡检数记录存失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object updateSafeCheckRecord(Map<String, Object> params) {
		SafeSiCheckRecord safeSiCheckRecord = getById(params.get("safe_check_id").toString());
		if (null != safeSiCheckRecord) {
			safeSiCheckRecord.setCheckDepartId(params.get("check_depart_id").toString());
			safeSiCheckRecord.setCheckDepartName(params.get("check_depart_name").toString());
			safeSiCheckRecord.setCheckPersonId(params.get("check_person_id").toString());
			safeSiCheckRecord.setCheckPersonName(params.get("check_person_name").toString());
			safeSiCheckRecord.setSafeAreaId(params.get("safe_area_id").toString());
			safeSiCheckRecord.setSafeAreaName(params.get("safe_area_name").toString());
			safeSiCheckRecord.setCheckTypeId(params.get("check_type_id").toString());
			safeSiCheckRecord.setCheckTypeName(params.get("check_type_name").toString());
			safeSiCheckRecord.setCheckContent(params.get("check_content").toString());
			safeSiCheckRecord.setCheckStatus(Integer.parseInt(params.get("check_status").toString()));
			safeSiCheckRecord.setCheckImage1(params.get("check_image1").toString());
			safeSiCheckRecord.setCheckImage2(params.get("check_image2").toString());
			safeSiCheckRecord.setCheckImage3(params.get("check_image3").toString());
			safeSiCheckRecord.setCheckImage4(params.get("check_image4").toString());
			safeSiCheckRecord.setCheckTime(DateUtil.parseLocalDateTime(DateUtil.now()));
			if (updateById(safeSiCheckRecord)) {
				return ResponseUtil.toResult(params, "安全巡检记录保存成功");
			} else {
				return ResponseUtil.toError(params, "安全巡检记录保存失败");
			}
		}
		else {
			return ResponseUtil.toError(params, "安全巡检记录不存在");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object deleteSafeCheckRecord(Map<String, Object> params) {
		SafeSiCheckRecord safeSiCheckRecord = getById(params.get("safe_check_id").toString());
		if (null == safeSiCheckRecord) {
			return ResponseUtil.toError(params, "安全巡检记录不存在");
		}
		if (1 == safeSiCheckRecord.getCheckDisposed()) {
			return ResponseUtil.toError(params, "安全巡检记录已处理，不能删除");
		}
		if (removeById(safeSiCheckRecord.getId())) {
			return ResponseUtil.toResult(params, "安全巡检记录删除成功");
		}
		else {
			return ResponseUtil.toError(params, "安全巡检记录删除失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object setSafeCheckDisposed(Map<String, Object> params) {
		SafeSiCheckRecord safeSiCheckRecord = getById(params.get("safe_check_id").toString());
		if (null != safeSiCheckRecord) {
			if (1 == safeSiCheckRecord.getCheckDisposed()) {
				return ResponseUtil.toError(params, "安全巡检记录已处理，不能重复处理");
			}
			safeSiCheckRecord.setCheckDisposed(1);
			safeSiCheckRecord.setDisposeTime(DateUtil.parseLocalDateTime(DateUtil.now()));
			safeSiCheckRecord.setDisposerId(params.get("disposer_id").toString());
			safeSiCheckRecord.setDisposerName(params.get("disposer_name").toString());
			safeSiCheckRecord.setDisposeContent(params.get("dispose_content").toString());
			safeSiCheckRecord.setDisposerImage1(params.get("disposer_image1").toString());
			safeSiCheckRecord.setDisposerImage2(params.get("disposer_image2").toString());
			safeSiCheckRecord.setDisposerImage3(params.get("disposer_image3").toString());
			if (updateById(safeSiCheckRecord)) {
				return ResponseUtil.toResult(params, "安全巡检记录处理成功");
			} else {
				return ResponseUtil.toError(params, "安全巡检记录处理失败");
			}
		}
		else {
			return ResponseUtil.toError(params, "安全巡检记录不存在");
		}
	}
}
