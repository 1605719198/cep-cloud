package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.sysdictdata.ListSysDictDataDTO;
import com.jlkj.material.mr.domain.SysDictData;
import com.jlkj.material.mr.vo.sysdictdata.ListDictDataVO;

import java.util.List;

/**
 * 服务接口-字典数据表
 *
 * @author sudeyou
 * @since 2022-11-02 09:46:29
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 获取字典数据列表
     * @param listSysDictDataDTO 查询条件dto
     * @return 列表数据
     */
    List<ListDictDataVO> getSysDictDataListData(ListSysDictDataDTO listSysDictDataDTO);


}

