package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hm.domain.Contract;
import com.jlkj.human.hm.service.IContractService;
import com.jlkj.human.hm.mapper.ContractMapper;
import org.springframework.stereotype.Service;

/**
* @author 89373
* @description 针对表【human_hm_contract(人员劳动合同表)】的数据库操作Service实现
* @createDate 2023-03-28 14:15:18
*/
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract>
    implements IContractService {

}




