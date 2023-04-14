package com.jlkj.human.hd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hd.domain.Comptime;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_comptime(补休记录表)】的数据库操作Service
* @createDate 2023-04-13 15:34:08
*/
public interface IComptimeService extends IService<Comptime> {

    /**
     * 导入补休资料数据
     *
     * @param comptimeList 补休数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<Comptime> comptimeList, Boolean isUpdateSupport, String operName);

}
