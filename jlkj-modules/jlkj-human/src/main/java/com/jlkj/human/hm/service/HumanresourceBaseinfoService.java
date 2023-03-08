package com.jlkj.human.hm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hm.domain.HumanresourceBaseinfo;

import java.util.List;

/**
* @author 89373
* @description 针对表【human_hm_baseinfo(选单配置表)】的数据库操作Service
* @createDate 2023-03-07 18:33:11
*/
public interface HumanresourceBaseinfoService extends IService<HumanresourceBaseinfo> {

    /**
     * 获取选单配置树
     * @return
     */
    List<HumanresourceBaseinfo> getBaseInfoTreeList();

}
