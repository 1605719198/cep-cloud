package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.entity.SafeSiJobCutRoad;
import com.jlkj.safety.si.entity.SafeJobCutRoadApproval;
import com.jlkj.safety.si.mapper.SafeJobCutRoadApprovalMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import com.jlkj.safety.si.service.SafeJobCutRoadApprovalService;
import com.jlkj.safety.si.service.SafeJobCutRoadService;
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
 * 断路安全作业证-审批记录 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobCutRoadApprovalServiceImpl extends ServiceImpl<SafeJobCutRoadApprovalMapper, SafeJobCutRoadApproval> implements SafeJobCutRoadApprovalService {

    @Resource
    SafeJobCutRoadService safeJobCutRoadService;
    @Resource
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobCutRoadApproval(Map<String, Object> params) {
        SafeJobCutRoadApproval safeJobCutRoadApproval = getById(params.get("id").toString());
        if (null != safeJobCutRoadApproval) {
            safeJobCutRoadApproval.setApprovalPersonId(params.get("approval_person_id").toString());
            safeJobCutRoadApproval.setApprovalPersonName(params.get("approval_person_name").toString());
            safeJobCutRoadApproval.setPickPersonId(params.get("pick_person_id").toString());
            safeJobCutRoadApproval.setPickPersonName(params.get("pick_person_name").toString());
            safeJobCutRoadApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeJobCutRoadApproval)) {
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
    public Object confirmSafeJobCutRoadApproval(Map<String, Object> params) {
        SafeJobCutRoadApproval safeJobCutRoadApproval = getById(params.get("id").toString());
        if (null != safeJobCutRoadApproval) {
            if (!"".equals(params.get(SIGNATURE).toString())) {
                QueryWrapper<SafeJobCutRoadApproval> queryWrapperNoApproval = new QueryWrapper<>();
                queryWrapperNoApproval
                        .eq("job_id", safeJobCutRoadApproval.getJobId())
                        .eq("is_approval", 0)
                        .lt("sort", safeJobCutRoadApproval.getSort())
                        .orderByDesc("sort");
                if (listMaps(queryWrapperNoApproval).size() > 0) {
                    return ResponseUtil.toError(params, "上步审批未完成");
                }
                safeJobCutRoadApproval.setContent(params.get("content").toString());
                safeJobCutRoadApproval.setIsApproval(1);
                safeJobCutRoadApproval.setApprovalTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                safeJobCutRoadApproval.setSignature(params.get("signature").toString());
                if (updateById(safeJobCutRoadApproval)) {
                    Map<String, Object> infoParams = new HashMap<>(1);
                    infoParams.put("id", safeJobCutRoadApproval.getJobId());
                    Map<String, Object> infoData = (Map<String, Object>) safeJobCutRoadService.getSafeJobCutRoadInfo(infoParams);
                    if (infoData.containsKey(DATA)) {
                        Map<String, Object> infoCutRoadData = (Map<String, Object>) infoData.get("data");
                        if (Integer.parseInt(infoCutRoadData.get(STATUS).toString()) != STATUS_FINISH) {
                            int status = 1;
                            if (FINISHED.equals(safeJobCutRoadApproval.getApprovalName())) {
                                status = 3;
                            } else {
                                QueryWrapper<SafeJobCutRoadApproval> queryWrapper = new QueryWrapper<>();
                                queryWrapper
                                        .eq("job_id", safeJobCutRoadApproval.getJobId())
                                        .eq("is_approval", 0)
                                        .ne("approval_name", "完工验收");
                                if (listMaps(queryWrapper).size() == 0) {
                                    status = 2;
                                }
                            }
                            Map<String, Object> updateParams = new HashMap<>(1);
                            updateParams.put("id", safeJobCutRoadApproval.getJobId());
                            updateParams.put("status", status);
                            if (!safeJobCutRoadService.updateSafeJobCutRoadApprovalStatus(updateParams)) {
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
    public Object confirmSafeJobCutRoadApprovalTime(Map<String, Object> params) {
        SafeJobCutRoadApproval safeJobCutRoadApproval = getById(params.get("id").toString());
        if (null != safeJobCutRoadApproval) {
            safeJobCutRoadApproval.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeJobCutRoadApproval)) {
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
    public Object confirmSafeJobCutRoadApprovalReturn(Map<String, Object> params) {
        SafeJobCutRoadApproval safeJobCutRoadApproval = getById(params.get("id").toString());
        if (null != safeJobCutRoadApproval) {
            SafeSiJobCutRoad safeSiJobCutRoad = safeJobCutRoadService.getById(safeJobCutRoadApproval.getJobId());
            if (null != safeSiJobCutRoad) {
                //获取已审批内容
                QueryWrapper<SafeJobCutRoadApproval> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SafeJobCutRoadApproval::getJobId, safeJobCutRoadApproval.getJobId())
                        .eq(SafeJobCutRoadApproval::getIsApproval, 1)
                        .orderByAsc(SafeJobCutRoadApproval::getSort);
                List<SafeJobCutRoadApproval> list = list(queryWrapper);
                //重置审批状态
                UpdateWrapper<SafeJobCutRoadApproval> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().
                        set(SafeJobCutRoadApproval::getContent, null).
                        set(SafeJobCutRoadApproval::getConfirmTime, null).
                        set(SafeJobCutRoadApproval::getApprovalTime, null).
                        set(SafeJobCutRoadApproval::getIsApproval, 0).
                        set(SafeJobCutRoadApproval::getSignature, null);
                updateWrapper.eq("job_id", safeJobCutRoadApproval.getJobId());
                update(null,updateWrapper);
                safeSiJobCutRoad.setStatus(0);
                safeSiJobCutRoad.setFinishTime(null);
                safeJobCutRoadService.updateById(safeSiJobCutRoad);
                //生成回退日志

                SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
                safeJobApprovalRecord.setId(IdUtil.randomUUID());
                safeJobApprovalRecord.setJobId(safeJobCutRoadApproval.getJobId());
                safeJobApprovalRecord.setJobType(7);
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
        SafeJobCutRoadApproval approval = getById(params.get("id").toString());
        SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
        safeJobApprovalRecord.setId(IdUtil.randomUUID());
        safeJobApprovalRecord.setJobId(approval.getJobId());
        safeJobApprovalRecord.setJobType(7);
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
