package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.SysDictData;
import com.jlkj.product.oi.dto.sysdictdata.ListSysDictDataDTO;
import com.jlkj.product.oi.vo.sysdictdata.ListSysDictDataVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
 * Mapper接口-字典数据表
 *
 * @author sudeyou
 * @since 2022-11-02 09:46:29
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 获取字典数据列表
     * @param condition 查询条件dto
     * @return 列表数据
     */
    @MapKey("id")
    List<ListSysDictDataVO> getSysDictDataListData(ListSysDictDataDTO condition);
}

