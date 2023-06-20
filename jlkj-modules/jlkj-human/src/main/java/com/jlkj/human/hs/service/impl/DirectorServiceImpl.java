package com.jlkj.human.hs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hs.domain.Director;
import com.jlkj.human.hs.service.IDirectorService;
import com.jlkj.human.hs.mapper.DirectorMapper;
import org.springframework.stereotype.Service;

/**
* @author HuangBing
* @description 针对表【human_hs_director(各公司人事业务负责人)】的数据库操作Service实现
* @createDate 2023-05-24 13:46:42
*/
@Service
public class DirectorServiceImpl extends ServiceImpl<DirectorMapper, Director>
    implements IDirectorService {

}




