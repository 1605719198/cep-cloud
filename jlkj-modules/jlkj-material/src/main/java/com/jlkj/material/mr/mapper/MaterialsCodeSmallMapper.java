package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.domain.MaterialsCodeSmall;
import com.jlkj.material.mr.dto.materialscodesmall.PageMaterialsCodeSmallDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2023-03-22 09:27:37.934375
 */
public interface MaterialsCodeSmallMapper extends BaseMapper<MaterialsCodeSmall> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, @Param("condition") PageMaterialsCodeSmallDTO condition);

    @MapKey("id")
    List<Map<String, Object>> getMaterialsCodeSmallDic();
}


