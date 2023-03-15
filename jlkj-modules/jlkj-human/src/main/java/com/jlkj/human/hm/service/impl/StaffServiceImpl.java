package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hm.domain.Staff;
import com.jlkj.human.hm.mapper.StaffMapper;
import com.jlkj.human.hm.service.IStaffService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员数据 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-07-08
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

}
