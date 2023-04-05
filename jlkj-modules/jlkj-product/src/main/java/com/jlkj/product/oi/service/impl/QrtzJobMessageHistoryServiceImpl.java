package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.QrtzJobMessageHistory;
import com.jlkj.product.oi.mapper.QrtzJobMessageHistoryMapper;
import com.jlkj.product.oi.service.QrtzJobMessageHistoryService;
import org.springframework.stereotype.Service;

/**
 * 服务实现-报文抛送历史表
 *
 * @author sudeyou
 * @since 2023-01-06 11:14:57
 */
@Service
public class QrtzJobMessageHistoryServiceImpl extends ServiceImpl<QrtzJobMessageHistoryMapper, QrtzJobMessageHistory>
    implements QrtzJobMessageHistoryService {
}
