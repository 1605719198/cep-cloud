package com.jlkj.human.hs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hs.domain.PayBank;
import com.jlkj.human.hs.service.IPayBankService;
import com.jlkj.human.hs.mapper.PayBankMapper;
import org.springframework.stereotype.Service;

/**
* @author 89373
* @description 针对表【human_hs_pay_bank(各公司薪资支付银行维护)】的数据库操作Service实现
* @createDate 2023-05-23 11:37:49
*/
@Service
public class PayBankServiceImpl extends ServiceImpl<PayBankMapper, PayBank>
    implements IPayBankService {

}




