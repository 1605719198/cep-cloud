package com.jlkj.rabbit.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.rabbit.entity.StorageMaintain;
import com.jlkj.rabbit.mapper.StorageMaintainMapper;
import com.jlkj.rabbit.service.StorageMaintainService;
import org.springframework.stereotype.Service;

/**
* @author zyf
* @description 针对表【t_storage_maintain(料仓储位维护表)】的数据库操作Service实现
* @createDate 2022-08-18 14:54:36
*/
@Service
public class StorageMaintainServiceImpl extends ServiceImpl<StorageMaintainMapper, StorageMaintain>
    implements StorageMaintainService {

    public void add(JSONObject jo) {
        StorageMaintain storageMaintain = new StorageMaintain();
        storageMaintain.setId(IdUtil.randomUUID());
        storageMaintain.setStorageSpacesName(jo.getString("stgDesc"));
        storageMaintain.setCreateTime(DateUtil.date());
        save(storageMaintain);
    }

    public void del(JSONObject jo) {
        StorageMaintain storageMaintain = getOne(new LambdaQueryWrapper<StorageMaintain>()
                .eq(StorageMaintain::getStorageSpacesName, jo.getString("stgDesc")));
        if (storageMaintain != null) {
            removeById(storageMaintain);
        }
    }
}




