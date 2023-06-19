package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hd.domain.OverTime;
import com.jlkj.human.hd.service.IOverTimeService;
import com.jlkj.human.hd.mapper.OverTimeMapper;
import org.springframework.stereotype.Service;

/**
* @author 89373
* @description 针对表【human_hd_over_time(加班汇总表（薪酬计算用）)】的数据库操作Service实现
* @createDate 2023-06-05 11:29:08
*/
@Service
public class OverTimeServiceImpl extends ServiceImpl<OverTimeMapper, OverTime>
    implements IOverTimeService {

}




