package com.jlkj.material.mr.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.changelog.InsertChangeLogDTO;
import com.jlkj.material.mr.domain.ChangeLog;
import com.jlkj.material.mr.mapper.ChangeLogMapper;
import com.jlkj.material.mr.service.ChangeLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 服务实现-变更日志
 * @author sudeyou
 * @since 2022-09-21 10:57:59
 */
@Service
public class ChangeLogServiceImpl extends ServiceImpl<ChangeLogMapper, ChangeLog> 
    implements ChangeLogService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertChangeLogData(InsertChangeLogDTO insertChangeLogDTO) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setId(IdUtil.randomUUID());
        changeLog.setModuleName(insertChangeLogDTO.getModuleName());
        changeLog.setFunctionName(insertChangeLogDTO.getFunctionName());
        changeLog.setContent(insertChangeLogDTO.getContent());
        changeLog.setCreateUserId(insertChangeLogDTO.getCreateUserId());
        changeLog.setCreateUserName(insertChangeLogDTO.getCreateUserName());
        changeLog.setCreateTime(new Date());
        save(changeLog);
        return AjaxResult.success("变更日志增加成功");
    }
}

