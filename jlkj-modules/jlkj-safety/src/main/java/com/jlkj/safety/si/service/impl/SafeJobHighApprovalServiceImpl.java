package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.entity.SafeJobHigh;
import com.jlkj.safety.si.entity.SafeSiJobHighApproval;
import com.jlkj.safety.si.mapper.SafeJobHighApprovalMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import com.jlkj.safety.si.service.SafeJobHighApprovalService;
import com.jlkj.safety.si.service.SafeJobHighService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.*;

/**
 * <p>
 * 高处安全作业证-审批记录 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobHighApprovalServiceImpl extends ServiceImpl<SafeJobHighApprovalMapper, SafeSiJobHighApproval> implements SafeJobHighApprovalService {
    @Resource
    SafeJobHighService safeJobHighService;
    @Resource
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobHighApproval(Map<String, Object> params) {
        SafeSiJobHighApproval safeSiJobHighApproval = getById(params.get("id").toString());
        if (null != safeSiJobHighApproval) {
            safeSiJobHighApproval.setApprovalPersonId(params.get("approval_person_id").toString());
            safeSiJobHighApproval.setApprovalPersonName(params.get("approval_person_name").toString());
            safeSiJobHighApproval.setPickPersonId(params.get("pick_person_id").toString());
            safeSiJobHighApproval.setPickPersonName(params.get("pick_person_name").toString());
            safeSiJobHighApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiJobHighApproval)) {
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
    public Object confirmSafeJobHighApproval(Map<String, Object> params) {
        SafeSiJobHighApproval safeSiJobHighApproval = getById(params.get("id").toString());
        if (null != safeSiJobHighApproval) {
            if (safeSiJobHighApproval.getConfirmTime() == null) {
                return ResponseUtil.toError(params, "该审批记录未确认，请先确认");
            }
            Map<String, Object> lastApproval = null;
            Map<String, Object> lastApproval2 = null;
            Map<String, Object> firstApproval = null;
            QueryWrapper<SafeSiJobHighApproval> queryWrapperNoApproval = new QueryWrapper<>();
            queryWrapperNoApproval
                    .eq("job_id", safeSiJobHighApproval.getJobId())
                    .eq("is_approval", 0)
                    .lt("sort", safeSiJobHighApproval.getSort())
                    .orderByDesc("sort");
            if (listMaps(queryWrapperNoApproval).size() > 0) {
                return ResponseUtil.toError(params, "上步审批未完成");
            }
            QueryWrapper<SafeSiJobHighApproval> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("job_id", safeSiJobHighApproval.getJobId())
                    .orderByDesc("sort");
            List<Map<String, Object>> list = listMaps(queryWrapper);
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    lastApproval = list.get(i);
                }
                else if (i == 1) {
                    lastApproval2 = list.get(i);
                }
                else if (i == (list.size() - 1)) {
                    firstApproval = list.get(i);
                }
            }
            if ((lastApproval == null) || (lastApproval2 == null) || (firstApproval == null)) {
                return ResponseUtil.toError(params, "审批列表数据不完整");
            }
            SafeJobHigh safeJobHigh = safeJobHighService.getById(safeSiJobHighApproval.getJobId());
            if (safeSiJobHighApproval.getId().equals(lastApproval.get(ID).toString()) && safeJobHigh.getStatus() < STATUS_FINISH) {
                safeJobHigh.setStatus(3);
            }
            else if (safeSiJobHighApproval.getId().equals(lastApproval2.get(ID).toString()) && safeJobHigh.getStatus() < STATUS_WORK) {
                safeJobHigh.setStatus(2);
            }
            else if (safeSiJobHighApproval.getId().equals(firstApproval.get(ID).toString()) && safeJobHigh.getStatus() < 1) {
                safeJobHigh.setStatus(1);
            }
            safeJobHighService.updateById(safeJobHigh);
            safeSiJobHighApproval.setContent(params.get("content").toString());
            safeSiJobHighApproval.setIsApproval(1);
            safeSiJobHighApproval.setApprovalTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiJobHighApproval.setSignature(params.get("signature").toString());
            if (updateById(safeSiJobHighApproval)) {
                this.confirmReturnAddOne(params);
                if (safeJobHigh.getStatus() == STATUS_FINISH) {
                    safeJobHighService.sendSafeJobHighMessageQueue(safeJobHigh);
                }
                return ResponseUtil.toResult(params, "审批成功");
            } else {
                return ResponseUtil.toError(params, "审批失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "审批记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobHighApprovalTime(Map<String, Object> params) {
        SafeSiJobHighApproval safeSiJobHighApproval = getById(params.get("id").toString());
        if (null != safeSiJobHighApproval) {
            safeSiJobHighApproval.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiJobHighApproval)) {
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
    public boolean insertSafeJobHighApprovals(Map<String, Object> params) {
        SafeSiJobHighApproval safeSiJobHighApproval = new SafeSiJobHighApproval();
        safeSiJobHighApproval.setId(IdUtil.randomUUID());
        safeSiJobHighApproval.setJobId(params.get("job_id").toString());
        safeSiJobHighApproval.setSort(Integer.parseInt(params.get("sort").toString()));
        safeSiJobHighApproval.setApprovalName(params.get("approval_name").toString());
        safeSiJobHighApproval.setApprovalPersonId(params.get("approval_person_id").toString());
        safeSiJobHighApproval.setApprovalPersonName(params.get("approval_person_name").toString());
        safeSiJobHighApproval.setPickPersonId(params.get("pick_person_id").toString());
        safeSiJobHighApproval.setPickPersonName(params.get("pick_person_name").toString());
        safeSiJobHighApproval.setIsApproval(0);
        safeSiJobHighApproval.setIsRequired(1);
        safeSiJobHighApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        return save(safeSiJobHighApproval);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobHighApprovalReturn(Map<String, Object> params) {
        SafeSiJobHighApproval safeSiJobHighApproval = getById(params.get("id").toString());
        if (null != safeSiJobHighApproval) {
            SafeJobHigh safeJobHigh = safeJobHighService.getById(safeSiJobHighApproval.getJobId());
            if (null != safeJobHigh) {
                //获取已审批内容
                QueryWrapper<SafeSiJobHighApproval> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SafeSiJobHighApproval::getJobId, safeSiJobHighApproval.getJobId())
                        .eq(SafeSiJobHighApproval::getIsApproval, 1)
                        .orderByAsc(SafeSiJobHighApproval::getSort);
                List<SafeSiJobHighApproval> list = list(queryWrapper);
                //重置审批状态
                UpdateWrapper<SafeSiJobHighApproval> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().
                        set(SafeSiJobHighApproval::getContent, null).
                        set(SafeSiJobHighApproval::getConfirmTime, null).
                        set(SafeSiJobHighApproval::getApprovalTime, null).
                        set(SafeSiJobHighApproval::getIsApproval, 0).
                        set(SafeSiJobHighApproval::getSignature, null);
                updateWrapper.eq("job_id", safeSiJobHighApproval.getJobId());
                update(null,updateWrapper);
                safeJobHigh.setStatus(0);
                safeJobHigh.setFinishTime(null);
                safeJobHighService.updateById(safeJobHigh);
                //生成回退日志

                SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
                safeJobApprovalRecord.setId(IdUtil.randomUUID());
                safeJobApprovalRecord.setJobId(safeSiJobHighApproval.getJobId());
                safeJobApprovalRecord.setJobType(3);
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
        SafeSiJobHighApproval approval = getById(params.get("id").toString());
        SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
        safeJobApprovalRecord.setId(IdUtil.randomUUID());
        safeJobApprovalRecord.setJobId(approval.getJobId());
        safeJobApprovalRecord.setJobType(3);
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
