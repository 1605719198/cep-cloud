package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hm.domain.Leave;
import com.jlkj.human.hm.service.ILeaveService;
import com.jlkj.human.hm.mapper.LeaveMapper;
import org.springframework.stereotype.Service;

/**
* @author 89373
* @description 针对表【human_hm_leave(人员离职信息表)】的数据库操作Service实现
* @createDate 2023-03-23 14:25:12
*/
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave>
    implements ILeaveService {

}




