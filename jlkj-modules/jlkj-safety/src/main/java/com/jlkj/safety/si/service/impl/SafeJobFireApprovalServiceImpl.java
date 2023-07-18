package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.entity.SafeSiJobFire;
import com.jlkj.safety.si.entity.SafeSiJobFireApproval;
import com.jlkj.safety.si.mapper.SafeJobFireApprovalMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import com.jlkj.safety.si.service.SafeJobFireApprovalService;
import com.jlkj.safety.si.service.SafeJobFireService;
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
 * 动火安全作业证-审批记录 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class SafeJobFireApprovalServiceImpl extends ServiceImpl<SafeJobFireApprovalMapper, SafeSiJobFireApproval> implements SafeJobFireApprovalService {

    @Resource
    SafeJobFireService safeJobFireService;
    @Resource
    SafeJobApprovalRecordService safeJobApprovalRecordService;

    /**
     * 审批记录修改
     * @author 265800
     * @date 2023/7/14 15:25
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobFireApproval(Map<String, Object> params) {
        SafeSiJobFireApproval safeSiJobFireApproval = getById(params.get("id").toString());
        if (null != safeSiJobFireApproval) {
            safeSiJobFireApproval.setApprovalPersonId(params.get("approval_person_id").toString());
            safeSiJobFireApproval.setApprovalPersonName(params.get("approval_person_name").toString());
            safeSiJobFireApproval.setPickPersonId(params.get("pick_person_id").toString());
            safeSiJobFireApproval.setPickPersonName(params.get("pick_person_name").toString());
            safeSiJobFireApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiJobFireApproval)) {
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
     * 审批记录确认
     * @author 265800
     * @date 2023/7/14 15:25
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobFireApproval(Map<String, Object> params) {
        SafeSiJobFireApproval safeSiJobFireApproval = getById(params.get("id").toString());
        if (null != safeSiJobFireApproval) {
            if (safeSiJobFireApproval.getConfirmTime() == null) {
                return AjaxResult.error("该审批记录未确认，请先确认", params);
            }
            Map<String, Object> lastApproval = null;
            Map<String, Object> lastApproval2 = null;
            Map<String, Object> firstApproval = null;
            QueryWrapper<SafeSiJobFireApproval> queryWrapperNoApproval = new QueryWrapper<>();
            queryWrapperNoApproval
                    .eq("job_id", safeSiJobFireApproval.getJobId())
                    .eq("is_approval", 0)
                    .lt("sort", safeSiJobFireApproval.getSort())
                    .orderByDesc("sort");
            if (listMaps(queryWrapperNoApproval).size() > 0) {
                return AjaxResult.error("上步审批未完成", params);
            }
            QueryWrapper<SafeSiJobFireApproval> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("job_id", safeSiJobFireApproval.getJobId())
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
            SafeSiJobFire safeSiJobFire = safeJobFireService.getById(safeSiJobFireApproval.getJobId());
            if (safeSiJobFireApproval.getId().equals(lastApproval.get(ID).toString()) && safeSiJobFire.getStatus() < STATUS_FINISH) {
                safeSiJobFire.setStatus(3);
            }
            else if (safeSiJobFireApproval.getId().equals(lastApproval2.get(ID).toString()) && safeSiJobFire.getStatus() < STATUS_WORK) {
                safeSiJobFire.setStatus(2);
            }
            else if (safeSiJobFireApproval.getId().equals(firstApproval.get(ID).toString()) && safeSiJobFire.getStatus() < 1) {
                safeSiJobFire.setStatus(1);
            }
            safeJobFireService.updateById(safeSiJobFire);
            safeSiJobFireApproval.setContent(params.get("content").toString());
            safeSiJobFireApproval.setIsApproval(1);
            safeSiJobFireApproval.setApprovalTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiJobFireApproval.setSignature(params.get("signature").toString());
            if (updateById(safeSiJobFireApproval)) {
                //更新动火审批
                this.confirmReturnAddOne(params);
                if (safeSiJobFire.getStatus() == STATUS_FINISH) {
                    params.put("id", safeSiJobFire.getId());
                    safeJobFireService.sendSafeJobFireMessageQueue(params);
                }
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
     * 审批确认时间
     * @author 265800
     * @date 2023/7/14 15:25
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobFireApprovalTime(Map<String, Object> params) {
        SafeSiJobFireApproval safeSiJobFireApproval = getById(params.get("id").toString());
        if (null != safeSiJobFireApproval) {
            safeSiJobFireApproval.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (updateById(safeSiJobFireApproval)) {
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
     * 审批新增
     * @author 265800
     * @date 2023/7/14 15:25
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobFireApprovals(Map<String, Object> params) {
        SafeSiJobFireApproval safeSiJobFireApproval = new SafeSiJobFireApproval();
        safeSiJobFireApproval.setId(IdUtil.randomUUID());
        safeSiJobFireApproval.setJobId(params.get("job_id").toString());
        safeSiJobFireApproval.setSort(Integer.parseInt(params.get("sort").toString()));
        safeSiJobFireApproval.setApprovalName(params.get("approval_name").toString());
        safeSiJobFireApproval.setApprovalPersonId(params.get("approval_person_id").toString());
        safeSiJobFireApproval.setApprovalPersonName(params.get("approval_person_name").toString());
        safeSiJobFireApproval.setPickPersonId(params.get("pick_person_id").toString());
        safeSiJobFireApproval.setPickPersonName(params.get("pick_person_name").toString());
        safeSiJobFireApproval.setIsApproval(0);
        safeSiJobFireApproval.setIsRequired(1);
        safeSiJobFireApproval.setPickTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        return save(safeSiJobFireApproval);
    }

    /**
     * 审批退回
     * @author 265800
     * @date 2023/7/14 15:25
     * @param params
     * @return java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobFireApprovalReturn(Map<String, Object> params) {
        SafeSiJobFireApproval safeSiJobFireApproval = getById(params.get("id").toString());
        if (null != safeSiJobFireApproval) {
            SafeSiJobFire safeSiJobFire = safeJobFireService.getById(safeSiJobFireApproval.getJobId());
            if (null != safeSiJobFire) {
                //获取已审批内容
                QueryWrapper<SafeSiJobFireApproval> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SafeSiJobFireApproval::getJobId, safeSiJobFireApproval.getJobId())
                        .eq(SafeSiJobFireApproval::getIsApproval, 1)
                        .orderByAsc(SafeSiJobFireApproval::getSort);
                List<SafeSiJobFireApproval> list = list(queryWrapper);
                //重置审批状态
                UpdateWrapper<SafeSiJobFireApproval> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().
                        set(SafeSiJobFireApproval::getContent, null).
                        set(SafeSiJobFireApproval::getConfirmTime, null).
                        set(SafeSiJobFireApproval::getApprovalTime, null).
                        set(SafeSiJobFireApproval::getIsApproval, 0).
                        set(SafeSiJobFireApproval::getSignature, null);
                updateWrapper.eq("job_id", safeSiJobFireApproval.getJobId());
                update(null,updateWrapper);
                safeSiJobFire.setStatus(0);
                safeSiJobFire.setFinishTime(null);
                safeJobFireService.updateById(safeSiJobFire);
                //生成回退日志

                SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
                safeJobApprovalRecord.setId(IdUtil.randomUUID());
                safeJobApprovalRecord.setJobId(safeSiJobFireApproval.getJobId());
                safeJobApprovalRecord.setJobType(2);
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
     * @param params
     */
    private void confirmReturnAddOne(Map<String, Object> params) {
        SafeSiJobFireApproval approval = getById(params.get("id").toString());
        SafeJobApprovalRecord safeJobApprovalRecord = new SafeJobApprovalRecord();
        safeJobApprovalRecord.setId(IdUtil.randomUUID());
        safeJobApprovalRecord.setJobId(approval.getJobId());
        safeJobApprovalRecord.setJobType(2);
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
