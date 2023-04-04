package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.MaterialsCategory;
import com.jlkj.product.oi.mapper.MaterialsCategoryMapper;
import com.jlkj.product.oi.service.MaterialsCategoryService;
import org.springframework.stereotype.Service;

/**
 * 服务实现-物料管理-物料类别维护表
 *
 * @author sudeyou
 * @since 2022-12-07 15:10:09
 */
@Service
public class MaterialsCategoryServiceImpl extends ServiceImpl<MaterialsCategoryMapper, MaterialsCategory>
    implements MaterialsCategoryService {
}
