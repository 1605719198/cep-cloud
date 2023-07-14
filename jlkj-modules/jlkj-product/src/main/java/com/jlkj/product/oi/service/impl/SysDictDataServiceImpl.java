package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.SysDictData;
import com.jlkj.product.oi.dto.sysdictdata.ListSysDictDataDTO;
import com.jlkj.product.oi.mapper.SysDictDataMapper;
import com.jlkj.product.oi.service.SysDictDataService;
import com.jlkj.product.oi.vo.sysdictdata.ListSysDictDataVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*@description: 服务实现-字典数据表
*@Author: 265823
*@date: 2023/7/11 15:51
*/
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData>
    implements SysDictDataService {

    /**
     * 获取字典数据列表
     * @param listSysDictDataDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListSysDictDataVO> getSysDictDataListData(ListSysDictDataDTO listSysDictDataDTO) {
        return getBaseMapper().getSysDictDataListData(listSysDictDataDTO);
    }
}

