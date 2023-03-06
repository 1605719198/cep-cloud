package com.jlkj.material.mr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.material.mr.dto.sysdictdata.ListSysDictDataDTO;
import com.jlkj.material.mr.domain.SysDictData;
import com.jlkj.material.mr.mapper.SysDictDataMapper;
import com.jlkj.material.mr.service.SysDictDataService;
import com.jlkj.material.mr.vo.sysdictdata.ListDictDataVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现-字典数据表
 *
 * @author sudeyou
 * @since 2022-11-02 09:46:29
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> 
    implements SysDictDataService {

    @Override
    @Transactional(readOnly = true)
    public List<ListDictDataVO> getSysDictDataListData(ListSysDictDataDTO listSysDictDataDTO) {
        return getBaseMapper().getSysDictDataListData(listSysDictDataDTO);
    }
}

