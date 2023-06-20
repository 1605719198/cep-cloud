package com.jlkj.human.hm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hm.domain.ChangeMaster;
import com.jlkj.human.hm.dto.ChangeMasterDTO;

/**
* @author HuangBing
* @description 针对表【human_hm_change_master(人员异动信息主表)】的数据库操作Service
* @createDate 2023-03-17 08:39:33
*/
public interface IChangeMasterService extends IService<ChangeMaster> {

    /**
     * 添加员工入职作业
     */
    Object addEmployeeInduction(ChangeMasterDTO changeMasterDTO);

    /**
     * 添加员工入职作业
     */
    Object updateEmployeeInduction(ChangeMasterDTO changeMasterDTO);
}
