package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hd.domain.CancellationPerson;
import com.jlkj.human.hd.service.ICancellationPersonService;
import com.jlkj.human.hd.mapper.CancellationPersonMapper;
import org.springframework.stereotype.Service;

/**
* @author HuangBing
* @description 针对表【human_hd_cancellation_person(人事注销记录表)】的数据库操作Service实现
* @createDate 2023-04-18 14:17:59
*/
@Service
public class CancellationPersonServiceImpl extends ServiceImpl<CancellationPersonMapper, CancellationPerson>
    implements ICancellationPersonService {

}




