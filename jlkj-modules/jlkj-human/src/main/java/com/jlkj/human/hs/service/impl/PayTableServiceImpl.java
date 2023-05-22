package com.jlkj.human.hs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hs.domain.PayTable;
import com.jlkj.human.hs.service.IPayTableService;
import com.jlkj.human.hs.mapper.PayTableMapper;
import org.springframework.stereotype.Service;

/**
* @author 89373
* @description 针对表【human_hs_pay_table(公司级薪资表项目维护)】的数据库操作Service实现
* @createDate 2023-05-15 15:06:42
*/
@Service
public class PayTableServiceImpl extends ServiceImpl<PayTableMapper, PayTable>
    implements IPayTableService {

}




