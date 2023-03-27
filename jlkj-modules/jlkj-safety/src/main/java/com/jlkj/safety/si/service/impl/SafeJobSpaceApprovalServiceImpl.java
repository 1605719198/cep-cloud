package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.entity.SafeJobSpace;
import com.jlkj.safety.si.entity.SafeSiJobSpaceApproval;
import com.jlkj.safety.si.mapper.SafeJobSpaceApprovalMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import com.jlkj.safety.si.service.SafeJobSpaceApprovalService;
import com.jlkj.safety.si.service.SafeJobSpaceService;
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
 * 受限空间安全作业证-审批记录 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobSpaceApprovalServiceImpl extends ServiceImpl<SafeJobSpaceApprovalMapper, SafeSiJobSpaceApproval> implements SafeJobSpaceApprovalService {

    @Resource
    SafeJobSpaceService safeJobSpaceService;
    @Resource
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobSpaceApproval(Map<String, Object> params) {
        SafeSiJobSpaceApproval safeSiJobSpaceApproval = getById(params.get("id").toString());
        if (null != safeSiJobSpaceApproval) {
            safeSiJobSpaceApproval.setApprovalPersonId(params.get("approval_person_id").toString());
            safeSiJobSpaceApproval.setApprovalPersonName(params.get("approval_person_name").toString());
            safeSiJobSpaceApproval.setPickPersonId(params.get("pick_person_id").toString());
            safeSiJobSpaceApproval.setPickPersonName(params.get("pick_person_name").toString());
            safeSiJobSpaceApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiJobSpaceApproval)) {
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
    public Object confirmSafeJobSpaceApproval(Map<String, Object> params) {
        SafeSiJobSpaceApproval safeSiJobSpaceApproval = getById(params.get("id").toString());
        if (null != safeSiJobSpaceApproval) {
            if (safeSiJobSpaceApproval.getConfirmTime() == null) {
                return ResponseUtil.toError(params, "该审批记录未确认，请先确认");
            }
            Map<String, Object> lastApproval = null;
            Map<String, Object> lastApproval2 = null;
            Map<String, Object> firstApproval = null;
            QueryWrapper<SafeSiJobSpaceApproval> queryWrapperNoApproval = new QueryWrapper<>();
            queryWrapperNoApproval
                    .eq("job_id", safeSiJobSpaceApproval.getJobId())
                    .eq("is_approval", 0)
                    .lt("sort", safeSiJobSpaceApproval.getSort())
                    .orderByDesc("sort");
            if (listMaps(queryWrapperNoApproval).size() > 0) {
                return ResponseUtil.toError(params, "上步审批未完成");
            }
            QueryWrapper<SafeSiJobSpaceApproval> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("job_id", safeSiJobSpaceApproval.getJobId())
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
            SafeJobSpace safeJobSpace = safeJobSpaceService.getById(safeSiJobSpaceApproval.getJobId());
            if (safeSiJobSpaceApproval.getId().equals(lastApproval.get(ID).toString()) && safeJobSpace.getStatus() < STATUS_FINISH) {
                safeJobSpace.setStatus(3);
                safeJobSpace.setEndTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            else if (safeSiJobSpaceApproval.getId().equals(lastApproval2.get(ID).toString()) && safeJobSpace.getStatus() < STATUS_WORK) {
                safeJobSpace.setStatus(2);
            }
            else if (safeSiJobSpaceApproval.getId().equals(firstApproval.get(ID).toString()) && safeJobSpace.getStatus() < 1) {
                safeJobSpace.setStatus(1);
            }
            safeJobSpaceService.updateById(safeJobSpace);
            safeSiJobSpaceApproval.setContent(params.get("content").toString());
            safeSiJobSpaceApproval.setIsApproval(1);
            safeSiJobSpaceApproval.setApprovalTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiJobSpaceApproval.setSignature(params.get("signature").toString());
            if (updateById(safeSiJobSpaceApproval)) {
                this.confirmReturnAddOne(params);
                if (safeJobSpace.getStatus() == STATUS_FINISH) {
                    safeJobSpaceService.sendSafeJobSpaceMessageQueue(safeJobSpace);
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
    public Object confirmSafeJobSpaceApprovalTime(Map<String, Object> params) {
        SafeSiJobSpaceApproval safeSiJobSpaceApproval = getById(params.get("id").toString());
        if (null != safeSiJobSpaceApproval) {
            safeSiJobSpaceApproval.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiJobSpaceApproval)) {
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
    public boolean insertSafeJobSpaceApprovals(Map<String, Object> params) {
        SafeSiJobSpaceApproval safeSiJobSpaceApproval = new SafeSiJobSpaceApproval();
        safeSiJobSpaceApproval.setId(IdUtil.randomUUID());
        safeSiJobSpaceApproval.setJobId(params.get("job_id").toString());
        safeSiJobSpaceApproval.setSort(Integer.parseInt(params.get("sort").toString()));
        safeSiJobSpaceApproval.setApprovalName(params.get("approval_name").toString());
        safeSiJobSpaceApproval.setApprovalPersonId(params.get("approval_person_id").toString());
        safeSiJobSpaceApproval.setApprovalPersonName(params.get("approval_person_name").toString());
        safeSiJobSpaceApproval.setPickPersonId(params.get("pick_person_id").toString());
        safeSiJobSpaceApproval.setPickPersonName(params.get("pick_person_name").toString());
        safeSiJobSpaceApproval.setIsApproval(0);
        safeSiJobSpaceApproval.setIsRequired(1);
        safeSiJobSpaceApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        return save(safeSiJobSpaceApproval);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobSpaceApprovalReturn(Map<String, Object> params) {
        SafeSiJobSpaceApproval safeSiJobSpaceApproval = getById(params.get("id").toString());
        if (null != safeSiJobSpaceApproval) {
            SafeJobSpace safeJobSpace = safeJobSpaceService.getById(safeSiJobSpaceApproval.getJobId());
            if (null != safeJobSpace) {
                //获取已审批内容
                QueryWrapper<SafeSiJobSpaceApproval> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SafeSiJobSpaceApproval::getJobId, safeSiJobSpaceApproval.getJobId())
                        .eq(SafeSiJobSpaceApproval::getIsApproval, 1)
                        .orderByAsc(SafeSiJobSpaceApproval::getSort);
                List<SafeSiJobSpaceApproval> list = list(queryWrapper);
                //重置审批状态
                UpdateWrapper<SafeSiJobSpaceApproval> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().
                        set(SafeSiJobSpaceApproval::getContent, null).
                        set(SafeSiJobSpaceApproval::getConfirmTime, null).
                        set(SafeSiJobSpaceApproval::getApprovalTime, null).
                        set(SafeSiJobSpaceApproval::getIsApproval, 0).
                        set(SafeSiJobSpaceApproval::getSignature, null);
                updateWrapper.eq("job_id", safeSiJobSpaceApproval.getJobId());
                update(null,updateWrapper);
                safeJobSpace.setStatus(0);
                safeJobSpace.setFinishTime(null);
                //生成回退日志
                safeJobSpaceService.updateById(safeJobSpace);

                SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
                safeJobApprovalRecord.setId(IdUtil.randomUUID());
                safeJobApprovalRecord.setJobId(safeSiJobSpaceApproval.getJobId());
                safeJobApprovalRecord.setJobType(4);
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
        SafeSiJobSpaceApproval approval = getById(params.get("id").toString());
        SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
        safeJobApprovalRecord.setId(IdUtil.randomUUID());
        safeJobApprovalRecord.setJobId(approval.getJobId());
        safeJobApprovalRecord.setJobType(4);
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
