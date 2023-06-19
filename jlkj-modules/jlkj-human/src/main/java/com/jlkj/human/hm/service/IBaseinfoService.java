package com.jlkj.human.hm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hm.domain.Baseinfo;
import com.jlkj.human.hm.dto.BaseInfoDTO;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hm_baseinfo(选单配置表)】的数据库操作Service
* @createDate 2023-03-07 18:33:11
*/
public interface IBaseinfoService extends IService<Baseinfo> {

    /**
     * 获取选单配置树
     * @return
     */
    List<Baseinfo> getBaseInfoTreeList();

    /**
     * 获取专业下拉选单列表
     */
    Object getDegreeMajor(BaseInfoDTO humanresourceBaseInfoDTO);

}
