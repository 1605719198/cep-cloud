package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.entity.SafeSiJobHoisting;
import com.jlkj.safety.si.entity.SafeSiJobHoistingApproval;
import com.jlkj.safety.si.mapper.SafeJobHoistingApprovalMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import com.jlkj.safety.si.service.SafeJobHoistingApprovalService;
import com.jlkj.safety.si.service.SafeJobHoistingService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.*;

/**
 * <p>
 * 吊装安全作业证-审批记录 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobHoistingApprovalServiceImpl extends ServiceImpl<SafeJobHoistingApprovalMapper, SafeSiJobHoistingApproval> implements SafeJobHoistingApprovalService {
    @Resource
    @Lazy
    SafeJobHoistingService safeJobHoistingService;
    @Resource
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    /**
     * 吊装安全作业证-审批记录修改
     * @author 265800
     * @date 2023/7/13 13:47
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobHoistingApproval(Map<String, Object> params) {
        SafeSiJobHoistingApproval safeSiJobHoistingApproval = getById(params.get("id").toString());
        if (null != safeSiJobHoistingApproval) {
            safeSiJobHoistingApproval.setApprovalPersonId(params.get("approval_person_id").toString());
            safeSiJobHoistingApproval.setApprovalPersonName(params.get("approval_person_name").toString());
            safeSiJobHoistingApproval.setPickPersonId(params.get("pick_person_id").toString());
            safeSiJobHoistingApproval.setPickPersonName(params.get("pick_person_name").toString());
            safeSiJobHoistingApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiJobHoistingApproval)) {
                return AjaxResult.success("设置审批人成功");
            } else {
                return AjaxResult.error("设置审批人失败", params);
            }
        }
        else {
            return AjaxResult.error("审批记录不存在", params);
        }
    }

    /**
     * 吊装安全作业证-审批记录确认
     * @author 265800
     * @date 2023/7/13 14:39
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobHoistingApproval(Map<String, Object> params) {
        SafeSiJobHoistingApproval safeSiJobHoistingApproval = getById(params.get("id").toString());
        if (null != safeSiJobHoistingApproval) {
            if (safeSiJobHoistingApproval.getConfirmTime() == null) {
                return AjaxResult.error("该审批记录未确认，请先确认", params);
            }
            Map<String, Object> lastApproval = null;
            Map<String, Object> lastApproval2 = null;
            Map<String, Object> firstApproval = null;
            QueryWrapper<SafeSiJobHoistingApproval> queryWrapperNoApproval = new QueryWrapper<>();
            queryWrapperNoApproval
                    .eq("job_id", safeSiJobHoistingApproval.getJobId())
                    .eq("is_approval", 0)
                    .lt("sort", safeSiJobHoistingApproval.getSort())
                    .orderByDesc("sort");
            if (listMaps(queryWrapperNoApproval).size() > 0) {
                return AjaxResult.error("上步审批未完成", params);
            }
            QueryWrapper<SafeSiJobHoistingApproval> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("job_id", safeSiJobHoistingApproval.getJobId())
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
                return AjaxResult.error("审批列表数据不完整", params);
            }
            SafeSiJobHoisting safeSiJobHoisting = safeJobHoistingService.getById(safeSiJobHoistingApproval.getJobId());
            if (safeSiJobHoistingApproval.getId().equals(lastApproval.get(ID).toString()) && safeSiJobHoisting.getStatus() < STATUS_FINISH) {
                safeSiJobHoisting.setStatus(3);
            }
            else if (safeSiJobHoistingApproval.getId().equals(lastApproval2.get(ID).toString()) && safeSiJobHoisting.getStatus() < STATUS_WORK) {
                safeSiJobHoisting.setStatus(2);
            }
            else if (safeSiJobHoistingApproval.getId().equals(firstApproval.get(ID).toString()) && safeSiJobHoisting.getStatus() < 1) {
                safeSiJobHoisting.setStatus(1);
            }
            safeJobHoistingService.updateById(safeSiJobHoisting);
            safeSiJobHoistingApproval.setContent(params.get("content").toString());
            safeSiJobHoistingApproval.setIsApproval(1);
            safeSiJobHoistingApproval.setApprovalTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiJobHoistingApproval.setSignature(params.get("signature").toString());
            if (updateById(safeSiJobHoistingApproval)) {
                if (safeSiJobHoisting.getStatus() == STATUS_FINISH) {
                    params.put("id", safeSiJobHoisting.getId());
                    safeJobHoistingService.sendSafeJobHoistingMessageQueue(params);
                }
                //更新吊装作业审批记录
                this.confirmReturnAddOne(params);
                return AjaxResult.success("审批成功");
            } else {
                return AjaxResult.error("审批失败", params);
            }
        }
        else {
            return AjaxResult.error("审批记录不存在", params);
        }
    }

    /**
     * 吊装安全作业证-审批记录确认时间
     * @author 265800
     * @date 2023/7/13 14:40
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobHoistingApprovalTime(Map<String, Object> params) {
        SafeSiJobHoistingApproval safeSiJobHoistingApproval = getById(params.get("id").toString());
        if (null != safeSiJobHoistingApproval) {
            safeSiJobHoistingApproval.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiJobHoistingApproval)) {
                return AjaxResult.success("确认成功");
            } else {
                return AjaxResult.error("确认失败", params);
            }
        }
        else {
            return AjaxResult.error("审批记录不存在", params);
        }
    }

    /**
     * 吊装安全作业证-审批记录新增
     * @author 265800
     * @date 2023/7/13 14:40
     * @param params
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHoistingApprovals(Map<String, Object> params) {
        SafeSiJobHoistingApproval safeSiJobHoistingApproval = new SafeSiJobHoistingApproval();
        safeSiJobHoistingApproval.setId(IdUtil.randomUUID());
        safeSiJobHoistingApproval.setJobId(params.get("job_id").toString());
        safeSiJobHoistingApproval.setSort(Integer.parseInt(params.get("sort").toString()));
        safeSiJobHoistingApproval.setApprovalName(params.get("approval_name").toString());
        safeSiJobHoistingApproval.setApprovalPersonId(params.get("approval_person_id").toString());
        safeSiJobHoistingApproval.setApprovalPersonName(params.get("approval_person_name").toString());
        safeSiJobHoistingApproval.setPickPersonId(params.get("pick_person_id").toString());
        safeSiJobHoistingApproval.setPickPersonName(params.get("pick_person_name").toString());
        safeSiJobHoistingApproval.setIsApproval(0);
        safeSiJobHoistingApproval.setIsRequired(1);
        safeSiJobHoistingApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        return save(safeSiJobHoistingApproval);
    }

    /**
     * 吊装安全作业证-审批退回
     * @author 265800
     * @date 2023/7/13 14:41
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobHoistingApprovalReturn(Map<String, Object> params) {
        SafeSiJobHoistingApproval safeSiJobHoistingApproval = getById(params.get("id").toString());
        if (null != safeSiJobHoistingApproval) {
            SafeSiJobHoisting safeSiJobHoisting = safeJobHoistingService.getById(safeSiJobHoistingApproval.getJobId());
            if (null != safeSiJobHoisting) {
                //获取已审批内容
                QueryWrapper<SafeSiJobHoistingApproval> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SafeSiJobHoistingApproval::getJobId, safeSiJobHoistingApproval.getJobId())
                        .eq(SafeSiJobHoistingApproval::getIsApproval, 1)
                        .orderByAsc(SafeSiJobHoistingApproval::getSort);
                List<SafeSiJobHoistingApproval> list = list(queryWrapper);
                //重置审批状态
                UpdateWrapper<SafeSiJobHoistingApproval> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().
                        set(SafeSiJobHoistingApproval::getContent, null).
                        set(SafeSiJobHoistingApproval::getConfirmTime, null).
                        set(SafeSiJobHoistingApproval::getApprovalTime, null).
                        set(SafeSiJobHoistingApproval::getIsApproval, 0).
                        set(SafeSiJobHoistingApproval::getSignature, null);
                updateWrapper.eq("job_id", safeSiJobHoistingApproval.getJobId());
                update(null,updateWrapper);
                safeSiJobHoisting.setStatus(0);
                safeSiJobHoisting.setFinishTime(null);
                safeJobHoistingService.updateById(safeSiJobHoisting);
                //生成回退日志审核时生成所以去掉

                SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
                safeJobApprovalRecord.setId(IdUtil.randomUUID());
                safeJobApprovalRecord.setJobId(safeSiJobHoistingApproval.getJobId());
                safeJobApprovalRecord.setJobType(1);
                safeJobApprovalRecord.setSort((Integer) params.get("sort"));
                safeJobApprovalRecord.setApprovalName(params.get("approval_name").toString());
                safeJobApprovalRecord.setApprovalPersonId(params.get("approval_person_id").toString());
                safeJobApprovalRecord.setApprovalPersonName(params.get("approval_person_name").toString());
                safeJobApprovalRecord.setApprovalTime(LocalDateTime.now());
                safeJobApprovalRecord.setIsPass(0);
                safeJobApprovalRecord.setContent(params.get("content").toString());
                safeJobApprovalRecordService.save(safeJobApprovalRecord);
                return AjaxResult.success("审批退回成功");
            } else {
                return AjaxResult.error("作业票不存在", params);
            }
        } else {
            return AjaxResult.error("审批记录不存在", params);
        }
    }

    /**
     * 审批历史记录添加
     * @author 265800
     * @date 2023/7/13 14:41
     * @param params
     * @return void
     */
    private void confirmReturnAddOne(Map<String, Object> params) {
        SafeSiJobHoistingApproval approval = getById(params.get("id").toString());
        SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
        safeJobApprovalRecord.setId(IdUtil.randomUUID());
        safeJobApprovalRecord.setJobId(approval.getJobId());
        safeJobApprovalRecord.setJobType(1);
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
