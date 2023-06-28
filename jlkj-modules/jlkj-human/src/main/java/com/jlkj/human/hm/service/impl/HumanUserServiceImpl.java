package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hm.domain.HumanUser;
import com.jlkj.human.hm.mapper.HumanUserMapper;
import com.jlkj.human.hm.service.IHumanUserService;
import org.springframework.stereotype.Service;

/**
* @author HuangBing
* @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
* @createDate 2023-05-05 09:59:33
*/
@Service
public class HumanUserServiceImpl extends ServiceImpl<HumanUserMapper, HumanUser>
    implements IHumanUserService {

}




