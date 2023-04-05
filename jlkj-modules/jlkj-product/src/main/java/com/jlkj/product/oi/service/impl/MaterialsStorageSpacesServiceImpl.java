package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.MaterialsStorageSpaces;
import com.jlkj.product.oi.mapper.MaterialsStorageSpacesMapper;
import com.jlkj.product.oi.service.MaterialsStorageSpacesService;
import org.springframework.stereotype.Service;

/**
 * 服务实现-物料管理-储位维护表
 *
 * @author sudeyou
 * @since 2022-11-09 19:41:36
 */
@Service
public class MaterialsStorageSpacesServiceImpl extends ServiceImpl<MaterialsStorageSpacesMapper, MaterialsStorageSpaces>
    implements MaterialsStorageSpacesService {
}
