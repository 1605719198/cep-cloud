package com.jlkj.human.hs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hs.domain.SalaryError;
import com.jlkj.human.hs.service.ISalaryErrorService;
import com.jlkj.human.hs.mapper.SalaryErrorMapper;
import org.springframework.stereotype.Service;

/**
* @author 89373
* @description 针对表【human_hs_salary_error(薪资核定导入错误信息表)】的数据库操作Service实现
* @createDate 2023-06-02 17:41:26
*/
@Service
public class SalaryErrorServiceImpl extends ServiceImpl<SalaryErrorMapper, SalaryError>
    implements ISalaryErrorService {

}




