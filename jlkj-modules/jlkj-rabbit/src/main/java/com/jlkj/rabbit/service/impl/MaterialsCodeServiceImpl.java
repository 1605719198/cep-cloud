package com.jlkj.rabbit.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.rabbit.entity.MaterialsCode;
import com.jlkj.rabbit.mapper.MaterialsCodeMapper;
import com.jlkj.rabbit.service.MaterialsCodeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zyf
* @description 针对表【t_materials_code(物料代码维护表)】的数据库操作Service实现
* @createDate 2022-08-18 15:16:25
*/
@Service
public class MaterialsCodeServiceImpl extends ServiceImpl<MaterialsCodeMapper, MaterialsCode>
    implements MaterialsCodeService {

    public void add(JSONObject jo) {
        MaterialsCode materialsCode = new MaterialsCode();
        materialsCode.setId(IdUtil.randomUUID());
        materialsCode.setMaterialsCode(jo.getString("mtrlNo"));
        materialsCode.setMaterialsName(jo.getString("mtrlName"));
        materialsCode.setCategoryId(jo.getString("sortId"));
        materialsCode.setMeasurementUnit(jo.getString("unit"));
        materialsCode.setMtrltype(jo.getString("mtrlType"));
        materialsCode.setDeleteFlag(0);
        materialsCode.setCreateTime(DateUtil.date());
        save(materialsCode);
    }

    public void del(JSONObject jo) {
        List<MaterialsCode> list = list(new LambdaQueryWrapper<MaterialsCode>()
                .eq(MaterialsCode::getMaterialsCode, jo.getString("mtrlNo")));
        if (list.size() > 0) {
            list.forEach(materialsCode -> {
                materialsCode.setDeleteFlag(1);
                updateById(materialsCode);
            });
        }
    }
}




