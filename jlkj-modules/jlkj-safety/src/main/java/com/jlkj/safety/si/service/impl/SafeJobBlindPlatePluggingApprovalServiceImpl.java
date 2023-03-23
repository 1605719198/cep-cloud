package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.entity.SafeJobBlindPlatePlugging;
import com.jlkj.safety.si.entity.SafeJobBlindPlatePluggingApproval;
import com.jlkj.safety.si.entity.SafeJobCutRoadApproval;
import com.jlkj.safety.si.mapper.SafeJobBlindPlatePluggingApprovalMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingApprovalService;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingService;
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
 * 盲板抽堵安全作业证-审批记录 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobBlindPlatePluggingApprovalServiceImpl extends ServiceImpl<SafeJobBlindPlatePluggingApprovalMapper, SafeJobBlindPlatePluggingApproval> implements SafeJobBlindPlatePluggingApprovalService {

    @Resource
    SafeJobBlindPlatePluggingService safeJobBlindPlatePluggingService;
    @Resource
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobBlindPlatePluggingApproval(Map<String, Object> params) {
        SafeJobBlindPlatePluggingApproval safeJobBlindPlatePluggingApproval = getById(params.get("id").toString());
        if (null != safeJobBlindPlatePluggingApproval) {
            safeJobBlindPlatePluggingApproval.setApprovalPersonId(params.get("approval_person_id").toString());
            safeJobBlindPlatePluggingApproval.setApprovalPersonName(params.get("approval_person_name").toString());
            safeJobBlindPlatePluggingApproval.setPickPersonId(params.get("pick_person_id").toString());
            safeJobBlindPlatePluggingApproval.setPickPersonName(params.get("pick_person_name").toString());
            safeJobBlindPlatePluggingApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeJobBlindPlatePluggingApproval)) {
                return ResponseUtil.toResult(params, "设置审批人成功");
            } else {
                return ResponseUtil.toError(params, "设置审批人失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "审批记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobBlindPlatePluggingApproval(Map<String, Object> params) {
        SafeJobBlindPlatePluggingApproval safeJobBlindPlatePluggingApproval = getById(params.get("id").toString());
        if (null != safeJobBlindPlatePluggingApproval) {
            if (!"".equals(params.get(SIGNATURE).toString())) {
                QueryWrapper<SafeJobBlindPlatePluggingApproval> queryWrapperNoApproval = new QueryWrapper<>();
                queryWrapperNoApproval
                        .eq("job_id", safeJobBlindPlatePluggingApproval.getJobId())
                        .eq("is_approval", 0)
                        .lt("sort", safeJobBlindPlatePluggingApproval.getSort())
                        .orderByDesc("sort");
                if (listMaps(queryWrapperNoApproval).size() > 0) {
                    return ResponseUtil.toError(params, "上步审批未完成");
                }
                safeJobBlindPlatePluggingApproval.setContent(params.get("content").toString());
                safeJobBlindPlatePluggingApproval.setIsApproval(1);
                safeJobBlindPlatePluggingApproval.setApprovalTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                safeJobBlindPlatePluggingApproval.setSignature(params.get("signature").toString());
                if (updateById(safeJobBlindPlatePluggingApproval)) {
                    Map<String, Object> infoParams = new HashMap<>(1);
                    infoParams.put("id", safeJobBlindPlatePluggingApproval.getJobId());
                    Map<String, Object> infoData = (Map<String, Object>) safeJobBlindPlatePluggingService.getSafeJobBlindPlatePluggingInfo(infoParams);
                    if (infoData.containsKey(DATA)) {
                        Map<String, Object> infoBlindPlatePluggingData = (Map<String, Object>) infoData.get("data");
                        if (Integer.parseInt(infoBlindPlatePluggingData.get(STATUS).toString()) != STATUS_FINISH) {
                            int status = 1;
                            if (FINISHED.equals(safeJobBlindPlatePluggingApproval.getApprovalName())) {
                                status = 3;
                            } else {
                                QueryWrapper<SafeJobBlindPlatePluggingApproval> queryWrapper = new QueryWrapper<>();
                                queryWrapper
                                        .eq("job_id", safeJobBlindPlatePluggingApproval.getJobId())
                                        .eq("is_approval", 0)
                                        .ne("approval_name", "完工验收");
                                if (listMaps(queryWrapper).size() == 0) {
                                    status = 2;
                                }
                            }
                            Map<String, Object> updateParams = new HashMap<>(1);
                            updateParams.put("id", safeJobBlindPlatePluggingApproval.getJobId());
                            updateParams.put("status", status);
                            if (!safeJobBlindPlatePluggingService.updateSafeJobBlindPlatePluggingApprovalStatus(updateParams)) {
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
            }
            else {
                return ResponseUtil.toError(params, "电子签名不存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "审批记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobBlindPlatePluggingApprovalTime(Map<String, Object> params) {
        SafeJobBlindPlatePluggingApproval safeJobBlindPlatePluggingApproval = getById(params.get("id").toString());
        if (null != safeJobBlindPlatePluggingApproval) {
            safeJobBlindPlatePluggingApproval.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeJobBlindPlatePluggingApproval)) {
                return ResponseUtil.toResult(params, "确认成功");
            } else {
                return ResponseUtil.toError(params, "确认失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "审批记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobBlindPlatePluggingApprovalReturn(Map<String, Object> params) {
        SafeJobBlindPlatePluggingApproval safeJobBlindPlatePluggingApproval = getById(params.get("id").toString());
        if (null != safeJobBlindPlatePluggingApproval) {
            SafeJobBlindPlatePlugging safeJobBlindPlatePlugging = safeJobBlindPlatePluggingService.getById(safeJobBlindPlatePluggingApproval.getJobId());
            if (null != safeJobBlindPlatePlugging) {
                //获取已审批内容
                QueryWrapper<SafeJobBlindPlatePluggingApproval> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SafeJobBlindPlatePluggingApproval::getJobId, safeJobBlindPlatePluggingApproval.getJobId())
                        .eq(SafeJobBlindPlatePluggingApproval::getIsApproval, 1)
                        .orderByAsc(SafeJobBlindPlatePluggingApproval::getSort);
                List<SafeJobBlindPlatePluggingApproval> list = list(queryWrapper);
                //重置审批状态
                UpdateWrapper<SafeJobBlindPlatePluggingApproval> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().
                        set(SafeJobBlindPlatePluggingApproval::getContent, null).
                        set(SafeJobBlindPlatePluggingApproval::getConfirmTime, null).
                        set(SafeJobBlindPlatePluggingApproval::getApprovalTime, null).
                        set(SafeJobBlindPlatePluggingApproval::getIsApproval, 0).
                        set(SafeJobBlindPlatePluggingApproval::getSignature, null);
                updateWrapper.eq("job_id", safeJobBlindPlatePluggingApproval.getJobId());
                update(null,updateWrapper);
                safeJobBlindPlatePlugging.setStatus(0);
                safeJobBlindPlatePlugging.setFinishTime(null);
                safeJobBlindPlatePluggingService.updateById(safeJobBlindPlatePlugging);
                //生成回退日志

                SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
                safeJobApprovalRecord.setId(IdUtil.randomUUID());
                safeJobApprovalRecord.setJobId(safeJobBlindPlatePluggingApproval.getJobId());
                safeJobApprovalRecord.setJobType(6);
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
        SafeJobBlindPlatePluggingApproval approval = getById(params.get("id").toString());
        SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
        safeJobApprovalRecord.setId(IdUtil.randomUUID());
        safeJobApprovalRecord.setJobId(approval.getJobId());
        safeJobApprovalRecord.setJobType(6);
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
