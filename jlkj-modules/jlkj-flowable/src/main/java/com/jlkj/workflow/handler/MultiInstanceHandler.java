package com.jlkj.workflow.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.domain.R;
import com.jlkj.flowable.common.constant.ProcessConstants;
import com.jlkj.system.api.RemoteUserService;
import lombok.AllArgsConstructor;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多实例处理类
 *
 * @author xin
 */
@AllArgsConstructor
@Component("multiInstanceHandler")
public class MultiInstanceHandler {

    @Autowired
    private RemoteUserService userService;

    public HashSet<String> getUserIds(DelegateExecution execution) {
        HashSet<String> candidateUserIds = new LinkedHashSet<>();
        FlowElement flowElement = execution.getCurrentFlowElement();
        if (ObjectUtil.isNotEmpty(flowElement) && flowElement instanceof UserTask) {
            UserTask userTask = (UserTask) flowElement;
            String dataType = userTask.getAttributeValue(ProcessConstants.NAMASPASE, ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
            if ("USERS".equals(dataType) && CollUtil.isNotEmpty(userTask.getCandidateUsers())) {
                candidateUserIds.addAll(userTask.getCandidateUsers());
            } else if (CollUtil.isNotEmpty(userTask.getCandidateGroups())) {
                List<String> groups = userTask.getCandidateGroups()
                    .stream().map(item -> item.substring(4)).collect(Collectors.toList());
                if ("ROLES".equals(dataType)) {
                    groups.forEach(item -> {
                        R<List<Long>> listR = userService.selectUserIdsByRoleId(Long.parseLong(item), SecurityConstants.INNER);
                        List<String> userIds = listR.getData()
                            .stream().map(String::valueOf).collect(Collectors.toList());
                        candidateUserIds.addAll(userIds);
                    });
                } else if ("DEPTS".equals(dataType)) {
                    R<List<Long>> listR = userService.selectList(groups, SecurityConstants.INNER);
                    List<String> userIds = listR.getData()
                        .stream().map(String::valueOf).collect(Collectors.toList());
                    candidateUserIds.addAll(userIds);
                }
            }
        }
        return candidateUserIds;
    }
}
