package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobApprovalRecord;
import com.jlkj.safety.si.mapper.SafeJobApprovalRecordMapper;
import com.jlkj.safety.si.service.SafeJobApprovalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yzl
 * @create 2023-02-01 13:41:04.068747
 */
@Service
@Slf4j
public class SafeJobApprovalRecordServiceImpl extends ServiceImpl<SafeJobApprovalRecordMapper, SafeJobApprovalRecord>
        implements SafeJobApprovalRecordService {
}

