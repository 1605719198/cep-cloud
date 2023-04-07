package com.jlkj.material.mr.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.material.mr.domain.MaterialsCodeSmall;
import com.jlkj.material.mr.dto.materialscodesmall.PageMaterialsCodeSmallDTO;
import com.jlkj.material.mr.mapper.MaterialsCodeSmallMapper;
import com.jlkj.material.mr.service.MaterialsCodeSmallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2023-03-22 09:27:37.933375
 */
@Service
@Slf4j
public class MaterialsCodeSmallServiceImpl extends ServiceImpl<MaterialsCodeSmallMapper, MaterialsCodeSmall>
        implements MaterialsCodeSmallService {
    @Override
    public IPage<Map<String, String>> getListPage(PageMaterialsCodeSmallDTO pageMaterialsCodeSmallDTO) {
        Page<Map<String, String>> page = new Page<>(pageMaterialsCodeSmallDTO.getCurrent(), pageMaterialsCodeSmallDTO.getSize());
        return getBaseMapper().getListPage(page, pageMaterialsCodeSmallDTO);
    }



    @Override
    public List<Map<String, Object>> getMaterialsCodeSmallDic() {
        return getBaseMapper().getMaterialsCodeSmallDic();
    }



}

