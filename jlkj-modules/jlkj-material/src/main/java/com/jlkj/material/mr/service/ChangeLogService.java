package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.changelog.InsertChangeLogDTO;
import com.jlkj.material.mr.domain.ChangeLog;

/**
 * 服务接口-变更日志
 * @author sudeyou
 * @since 2022-09-21 10:57:59
 */
public interface ChangeLogService extends IService<ChangeLog> {

    /**
     * 变更日志-新增
     * @param insertChangeLogDTO 新增dto
     * @return 执行结果
     */
    Object insertChangeLogData(InsertChangeLogDTO insertChangeLogDTO);
}

