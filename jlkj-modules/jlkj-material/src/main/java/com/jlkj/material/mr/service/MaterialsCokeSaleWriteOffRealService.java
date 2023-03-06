package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.DeleteMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.InsertMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.ListHomeCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.PageMaterialsCokeSaleWriteOffRealDto;
import com.jlkj.material.mr.domain.MaterialsCokeSaleWriteOffReal;

import java.util.List;
import java.util.Map;

/**
 * 服务接口-焦炭外售销账实际
 * @author sudeyou
 * @since 2022-08-19 13:58:42
 */
public interface MaterialsCokeSaleWriteOffRealService extends IService<MaterialsCokeSaleWriteOffReal> {

    /**
     * 焦炭外售销账实际-新增
     * @param insertMaterialsCokeSaleWriteOffRealDTO 新增dto
     * @return 执行结果
     */
    Object insertMaterialsCokeSaleWriteOffRealData(InsertMaterialsCokeSaleWriteOffRealDTO insertMaterialsCokeSaleWriteOffRealDTO);

    /**
     * 焦炭外售销账实际表-删除
     * @param deleteMaterialsCokeSaleWriteOffRealDTO 删除dto
     * @return 执行结果
     */
    Object deleteMaterialsCokeSaleWriteOffRealData(DeleteMaterialsCokeSaleWriteOffRealDTO deleteMaterialsCokeSaleWriteOffRealDTO);

    /**
     * 焦炭外售销账实际表-列表
     * @param dto 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageMaterialsCokeSaleWriteOffRealDto dto);
    /**
     * 焦炭外发-首页-图表
     * @param dto 查询条件dto
     * @return 分页列表
     */
    List<Map<String, String>> getHomeCokeOut(ListHomeCokeSaleWriteOffRealDTO dto);
}

