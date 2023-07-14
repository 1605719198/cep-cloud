package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.SysDictData;
import com.jlkj.product.oi.dto.sysdictdata.ListSysDictDataDTO;
import com.jlkj.product.oi.vo.sysdictdata.ListSysDictDataVO;

import java.util.List;

/**
*@description: 服务接口-字典数据表
*@Author: 265823
*@date: 2023/7/11 15:51
*/
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 获取字典数据列表
     * @param listSysDictDataDTO 查询条件dto
     * @return 列表数据
     */
    List<ListSysDictDataVO> getSysDictDataListData(ListSysDictDataDTO listSysDictDataDTO);


}

