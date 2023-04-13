package com.jlkj.rabbit.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.rabbit.entity.MaterialsCategory;
import com.jlkj.rabbit.mapper.MaterialsCategoryMapper;
import com.jlkj.rabbit.service.MaterialsCategoryService;
import org.springframework.stereotype.Service;

/**
* @author zyf
* @description 针对表【materials_mr_category(物料类别维护表)】的数据库操作Service实现
* @createDate 2022-09-06 13:38:03
*/
@Service
public class MaterialsCategoryServiceImpl extends ServiceImpl<MaterialsCategoryMapper, MaterialsCategory>
    implements MaterialsCategoryService {

    public void add(JSONObject jo) {
        MaterialsCategory entity = new MaterialsCategory();
        entity.setId(IdUtil.randomUUID());
        entity.setCategoryCode(jo.getString("sortId"));
        entity.setCategoryName(jo.getString("sortName"));
        entity.setDeleteFlag(0);
        entity.setCreateTime(DateUtil.date());
        save(entity);
    }

    public void del(JSONObject jo) {
        MaterialsCategory entity = getOne(new LambdaQueryWrapper<MaterialsCategory>()
                .eq(MaterialsCategory::getCategoryCode, jo.getString("sortId")));
        if (entity != null) {
            entity.setDeleteFlag(1);
            updateById(entity);
        }
    }

    public void addOne(JSONObject jo) {
        MaterialsCategory entity = getOne(new LambdaQueryWrapper<MaterialsCategory>()
                .eq(MaterialsCategory::getCategoryCode, jo.getString("sortId")));
        if (entity == null) {
            entity = new MaterialsCategory();
            entity.setId(IdUtil.randomUUID());
            entity.setCategoryCode(jo.getString("sortId"));
            entity.setCategoryName(jo.getString("sortName"));
            entity.setDeleteFlag(0);
            entity.setCreateTime(DateUtil.date());
            save(entity);
        }
    }
}




