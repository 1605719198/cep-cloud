package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.entity.SafeJobTemporaryElectricity;
import com.jlkj.safety.si.entity.SafeJobTemporaryElectricityApproval;
import com.jlkj.safety.si.mapper.SafeJobTemporaryElectricityApprovalMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import com.jlkj.safety.si.service.SafeJobTemporaryElectricityApprovalService;
import com.jlkj.safety.si.service.SafeJobTemporaryElectricityService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.*;

/**
 * <p>
 * 临时用电安全作业证-审批记录 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobTemporaryElectricityApprovalServiceImpl extends ServiceImpl<SafeJobTemporaryElectricityApprovalMapper, SafeJobTemporaryElectricityApproval> implements SafeJobTemporaryElectricityApprovalService {
    @Resource
    SafeJobTemporaryElectricityService safeJobTemporaryElectricityService;
    @Resource
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobTemporaryElectricityApproval(Map<String, Object> params) {
        SafeJobTemporaryElectricityApproval safeJobTemporaryElectricityApproval = getById(params.get("id").toString());
        if (null != safeJobTemporaryElectricityApproval) {
            safeJobTemporaryElectricityApproval.setApprovalPersonId(params.get("approval_person_id").toString());
            safeJobTemporaryElectricityApproval.setApprovalPersonName(params.get("approval_person_name").toString());
            safeJobTemporaryElectricityApproval.setPickPersonId(params.get("pick_person_id").toString());
            safeJobTemporaryElectricityApproval.setPickPersonName(params.get("pick_person_name").toString());
            safeJobTemporaryElectricityApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeJobTemporaryElectricityApproval)) {
                return ResponseUtil.toResult(params, "设置审批人成功");
            } else {
                return ResponseUtil.toError(params, "设置审批人失败");
            }
        } else {
            return ResponseUtil.toError(params, "审批记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobTemporaryElectricityApproval(Map<String, Object> params) {
        SafeJobTemporaryElectricityApproval safeJobTemporaryElectricityApproval = getById(params.get("id").toString());
        if (null != safeJobTemporaryElectricityApproval) {
            if (!"".equals(params.get(SIGNATURE).toString())) {
                QueryWrapper<SafeJobTemporaryElectricityApproval> queryWrapperNoApproval = new QueryWrapper<>();
                queryWrapperNoApproval
                        .eq("job_id", safeJobTemporaryElectricityApproval.getJobId())
                        .eq("is_approval", 0)
                        .lt("sort", safeJobTemporaryElectricityApproval.getSort())
                        .orderByDesc("sort");
                if (listMaps(queryWrapperNoApproval).size() > 0) {
                    return ResponseUtil.toError(params, "上步审批未完成");
                }
                safeJobTemporaryElectricityApproval.setContent(params.get("content").toString());
                safeJobTemporaryElectricityApproval.setIsApproval(1);
                safeJobTemporaryElectricityApproval.setApprovalTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                safeJobTemporaryElectricityApproval.setSignature(params.get("signature").toString());
                if (updateById(safeJobTemporaryElectricityApproval)) {
                    Map<String, Object> infoParams = new HashMap<>(1);
                    infoParams.put("id", safeJobTemporaryElectricityApproval.getJobId());
                    Map<String, Object> infoData = (Map<String, Object>) safeJobTemporaryElectricityService.getSafeJobTemporaryElectricityInfo(infoParams);
                    if (infoData.containsKey(DATA)) {
                        Map<String, Object> infoTemporaryElectricityData = (Map<String, Object>) infoData.get("data");
                        if (Integer.parseInt(infoTemporaryElectricityData.get(STATUS).toString()) != STATUS_FINISH) {
                            int status = 1;
                            if (FINISHED.equals(safeJobTemporaryElectricityApproval.getApprovalName())) {
                                status = 3;
                            } else {
                                QueryWrapper<SafeJobTemporaryElectricityApproval> queryWrapper = new QueryWrapper<>();
                                queryWrapper
                                        .eq("job_id", safeJobTemporaryElectricityApproval.getJobId())
                                        .eq("is_approval", 0)
                                        .ne("approval_name", "完工验收");
                                if (listMaps(queryWrapper).size() == 0) {
                                    status = 2;
                                }
                            }
                            Map<String, Object> updateParams = new HashMap<>(1);
                            updateParams.put("id", safeJobTemporaryElectricityApproval.getJobId());
                            updateParams.put("status", status);
                            if (!safeJobTemporaryElectricityService.updateSafeJobTemporaryElectricityApprovalStatus(updateParams)) {
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return ResponseUtil.toError(params, "更新作业证状态失败");
                            } else {
                                this.confirmReturnAddOne(params);
                                return ResponseUtil.toResult(params, "签字确认成功");
                            }
                        } else {
                            this.confirmReturnAddOne(params);
                            return ResponseUtil.toResult(params, "签字确认成功");
                        }
                    } else {
                        this.confirmReturnAddOne(params);
                        return ResponseUtil.toResult(params, "签字确认成功");
                    }
                } else {
                    return ResponseUtil.toError(params, "签字确认失败");
                }
            } else {
                return ResponseUtil.toError(params, "电子签名不存在");
            }
        } else {
            return ResponseUtil.toError(params, "审批记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobTemporaryElectricityApprovalTime(Map<String, Object> params) {
        SafeJobTemporaryElectricityApproval safeJobTemporaryElectricityApproval = getById(params.get("id").toString());
        if (null != safeJobTemporaryElectricityApproval) {
            safeJobTemporaryElectricityApproval.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeJobTemporaryElectricityApproval)) {
                return ResponseUtil.toResult(params, "确认成功");
            } else {
                return ResponseUtil.toError(params, "确认失败");
            }
        } else {
            return ResponseUtil.toError(params, "审批记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobTemporaryElectricityApprovalReturn(Map<String, Object> params) {
        SafeJobTemporaryElectricityApproval safeJobTemporaryElectricityApproval = getById(params.get("id").toString());
        if (null != safeJobTemporaryElectricityApproval) {
            SafeJobTemporaryElectricity safeJobTemporaryElectricity = safeJobTemporaryElectricityService.getById(safeJobTemporaryElectricityApproval.getJobId());
            if (null != safeJobTemporaryElectricity) {
                //获取已审批内容
                QueryWrapper<SafeJobTemporaryElectricityApproval> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SafeJobTemporaryElectricityApproval::getJobId, safeJobTemporaryElectricityApproval.getJobId())
                        .eq(SafeJobTemporaryElectricityApproval::getIsApproval, 1)
                        .orderByAsc(SafeJobTemporaryElectricityApproval::getSort);
                List<SafeJobTemporaryElectricityApproval> list = list(queryWrapper);
                //重置审批状态
                UpdateWrapper<SafeJobTemporaryElectricityApproval> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().
                        set(SafeJobTemporaryElectricityApproval::getContent, null).
                        set(SafeJobTemporaryElectricityApproval::getConfirmTime, null).
                        set(SafeJobTemporaryElectricityApproval::getApprovalTime, null).
                        set(SafeJobTemporaryElectricityApproval::getIsApproval, 0).
                        set(SafeJobTemporaryElectricityApproval::getSignature, null);
                updateWrapper.eq("job_id", safeJobTemporaryElectricityApproval.getJobId());
                update(null, updateWrapper);
                safeJobTemporaryElectricity.setStatus(0);
                safeJobTemporaryElectricity.setFinishTime(null);
                safeJobTemporaryElectricityService.updateById(safeJobTemporaryElectricity);
                //生成回退日志

                SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
                safeJobApprovalRecord.setId(IdUtil.randomUUID());
                safeJobApprovalRecord.setJobId(safeJobTemporaryElectricityApproval.getJobId());
                safeJobApprovalRecord.setJobType(8);
                safeJobApprovalRecord.setSort((Integer) params.get("sort"));
                safeJobApprovalRecord.setApprovalName(params.get("approval_name").toString());
                safeJobApprovalRecord.setApprovalPersonId(params.get("approval_person_id").toString());
                safeJobApprovalRecord.setApprovalPersonName(params.get("approval_person_name").toString());
                safeJobApprovalRecord.setApprovalTime(LocalDateTime.now());
                safeJobApprovalRecord.setIsPass(0);
                safeJobApprovalRecord.setContent(params.get("content").toString());
                safeJobApprovalRecordService.save(safeJobApprovalRecord);
                return ResponseUtil.toResult(params, "审批退回成功");
            } else {
                return ResponseUtil.toError(params, "作业票不存在");
            }
        } else {
            return ResponseUtil.toError(params, "审批记录不存在");
        }
    }

    /**
     * 审批历史记录添加
     * @param params
     */
    private void confirmReturnAddOne(Map<String, Object> params) {
        SafeJobTemporaryElectricityApproval approval = getById(params.get("id").toString());
        SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
        safeJobApprovalRecord.setId(IdUtil.randomUUID());
        safeJobApprovalRecord.setJobId(approval.getJobId());
        safeJobApprovalRecord.setJobType(8);
        safeJobApprovalRecord.setSort(approval.getSort());
        safeJobApprovalRecord.setApprovalName(approval.getApprovalName());
        safeJobApprovalRecord.setApprovalPersonId(approval.getApprovalPersonId());
        safeJobApprovalRecord.setApprovalPersonName(approval.getApprovalPersonName());
        safeJobApprovalRecord.setApprovalTime(approval.getApprovalTime());
        safeJobApprovalRecord.setIsPass(1);
        safeJobApprovalRecord.setContent(approval.getContent());
        safeJobApprovalRecordService.save(safeJobApprovalRecord);
    }
}
