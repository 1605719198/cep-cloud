package com.jlkj.rabbit.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.rabbit.entity.SysQueueDlx;
import com.jlkj.rabbit.mapper.SysQueueDlxMapper;
import com.jlkj.rabbit.service.SysQueueDlxService;
import org.springframework.stereotype.Service;

/**
* @author zyf
* @description 针对表【t_sys_queue_dlx】的数据库操作Service实现
* @createDate 2022-07-07 15:51:15
*/
@Service
public class SysQueueDlxServiceImpl extends ServiceImpl<SysQueueDlxMapper, SysQueueDlx>
    implements SysQueueDlxService {

    public void saveQueueException(String queueName, String message, String exception) {
        SysQueueDlx sysQueueDlx = new SysQueueDlx();
        sysQueueDlx.setId(IdUtil.randomUUID());
        sysQueueDlx.setCreateTime(DateUtil.date());
        sysQueueDlx.setQueueName(queueName);
        sysQueueDlx.setMessageContent(message);
        sysQueueDlx.setExceptionComtent(exception);
        sysQueueDlx.setIsChecked(0);
        save(sysQueueDlx);
    }
}




