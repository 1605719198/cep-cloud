package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.domain.MaterialsCodeSmall;
import com.jlkj.material.mr.dto.materialscodesmall.PageMaterialsCodeSmallDTO;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2023-03-22 09:27:37.932375
 */
public interface MaterialsCodeSmallService extends IService<MaterialsCodeSmall> {
    /**
     * 查询
     * @param pageMaterialsCodeSmallDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageMaterialsCodeSmallDTO pageMaterialsCodeSmallDTO);

    /**
     * 查询
     * @return 分页列表
     */
    List<Map<String, Object>> getMaterialsCodeSmallDic();
}
