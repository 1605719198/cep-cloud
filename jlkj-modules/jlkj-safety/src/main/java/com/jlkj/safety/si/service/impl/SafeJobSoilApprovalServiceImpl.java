package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.entity.SafeJobSoil;
import com.jlkj.safety.si.entity.SafeJobSoilApproval;
import com.jlkj.safety.si.mapper.SafeJobSoilApprovalMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import com.jlkj.safety.si.service.SafeJobSoilApprovalService;
import com.jlkj.safety.si.service.SafeJobSoilService;
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
 * 动土安全作业证-审批记录 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobSoilApprovalServiceImpl extends ServiceImpl<SafeJobSoilApprovalMapper, SafeJobSoilApproval> implements SafeJobSoilApprovalService {
    @Resource
    SafeJobSoilService safeJobSoilService;
    @Resource
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobSoilApproval(Map<String, Object> params) {
        SafeJobSoilApproval safeJobSoilApproval = getById(params.get("id").toString());
        if (null != safeJobSoilApproval) {
            safeJobSoilApproval.setApprovalPersonId(params.get("approval_person_id").toString());
            safeJobSoilApproval.setApprovalPersonName(params.get("approval_person_name").toString());
            safeJobSoilApproval.setPickPersonId(params.get("pick_person_id").toString());
            safeJobSoilApproval.setPickPersonName(params.get("pick_person_name").toString());
            safeJobSoilApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeJobSoilApproval)) {
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
    public Object confirmSafeJobSoilApproval(Map<String, Object> params) {
        SafeJobSoilApproval safeJobSoilApproval = getById(params.get("id").toString());
        if (null != safeJobSoilApproval) {
            if (!"".equals(params.get(SIGNATURE).toString())) {
                QueryWrapper<SafeJobSoilApproval> queryWrapperNoApproval = new QueryWrapper<>();
                queryWrapperNoApproval
                        .eq("job_id", safeJobSoilApproval.getJobId())
                        .eq("is_approval", 0)
                        .lt("sort", safeJobSoilApproval.getSort())
                        .orderByDesc("sort");
                if (listMaps(queryWrapperNoApproval).size() > 0) {
                    return ResponseUtil.toError(params, "上步审批未完成");
                }
                safeJobSoilApproval.setContent(params.get("content").toString());
                safeJobSoilApproval.setIsApproval(1);
                safeJobSoilApproval.setApprovalTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                safeJobSoilApproval.setSignature(params.get("signature").toString());
                if (updateById(safeJobSoilApproval)) {
                    Map<String, Object> infoParams = new HashMap<>(1);
                    infoParams.put("id", safeJobSoilApproval.getJobId());
                    Map<String, Object> infoData = (Map<String, Object>) safeJobSoilService.getSafeJobSoilInfo(infoParams);
                    if (infoData.containsKey(DATA)) {
                        Map<String, Object> infoSoilData = (Map<String, Object>) infoData.get("data");
                        if (Integer.parseInt(infoSoilData.get(STATUS).toString()) != STATUS_FINISH) {
                            int status = 1;
                            String approval = "项目总监";
                            if (approval.equals(safeJobSoilApproval.getApprovalName())) {
                                status = 3;
                            } else {
                                QueryWrapper<SafeJobSoilApproval> queryWrapper = new QueryWrapper<>();
                                queryWrapper
                                        .eq("job_id", safeJobSoilApproval.getJobId())
                                        .eq("is_approval", 0)
                                        .ne("approval_name", "项目总监");
                                if (listMaps(queryWrapper).size() == 0) {
                                    status = 2;
                                }
                            }
                            Map<String, Object> updateParams = new HashMap<>(1);
                            updateParams.put("id", safeJobSoilApproval.getJobId());
                            updateParams.put("status", status);
                            if (!safeJobSoilService.updateSafeJobSoilApprovalStatus(updateParams)) {
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
    public Object confirmSafeJobSoilApprovalTime(Map<String, Object> params) {
        SafeJobSoilApproval safeJobSoilApproval = getById(params.get("id").toString());
        if (null != safeJobSoilApproval) {
            safeJobSoilApproval.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeJobSoilApproval)) {
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
    public Object confirmSafeJobSoilApprovalReturn(Map<String, Object> params) {
        SafeJobSoilApproval safeJobSoilApproval = getById(params.get("id").toString());
        if (null != safeJobSoilApproval) {
            SafeJobSoil safeJobSoil = safeJobSoilService.getById(safeJobSoilApproval.getJobId());
            if (null != safeJobSoil) {
                //获取已审批内容
                QueryWrapper<SafeJobSoilApproval> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SafeJobSoilApproval::getJobId, safeJobSoilApproval.getJobId())
                        .eq(SafeJobSoilApproval::getIsApproval, 1)
                        .orderByAsc(SafeJobSoilApproval::getSort);
                List<SafeJobSoilApproval> list = list(queryWrapper);
                //重置审批状态
                UpdateWrapper<SafeJobSoilApproval> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().
                        set(SafeJobSoilApproval::getContent, null).
                        set(SafeJobSoilApproval::getConfirmTime, null).
                        set(SafeJobSoilApproval::getApprovalTime, null).
                        set(SafeJobSoilApproval::getIsApproval, 0).
                        set(SafeJobSoilApproval::getSignature, null);
                updateWrapper.eq("job_id", safeJobSoilApproval.getJobId());
                update(null,updateWrapper);
                safeJobSoil.setStatus(0);
                safeJobSoil.setFinishTime(null);
                safeJobSoilService.updateById(safeJobSoil);

                //生成回退日志

                SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
                safeJobApprovalRecord.setId(IdUtil.randomUUID());
                safeJobApprovalRecord.setJobId(safeJobSoilApproval.getJobId());
                safeJobApprovalRecord.setJobType(5);
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
        SafeJobSoilApproval approval = getById(params.get("id").toString());
        SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
        safeJobApprovalRecord.setId(IdUtil.randomUUID());
        safeJobApprovalRecord.setJobId(approval.getJobId());
        safeJobApprovalRecord.setJobType(5);
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
