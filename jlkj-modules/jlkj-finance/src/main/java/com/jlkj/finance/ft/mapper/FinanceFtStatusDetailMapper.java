package com.jlkj.finance.ft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.finance.ft.domain.FinanceFtStatusDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资产使用状态明细设定Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-07-06
 */
@Repository
public interface FinanceFtStatusDetailMapper  extends BaseMapper<FinanceFtStatusDetail>
{
    /**
     * 查询资产使用状态明细设定
     * 
     * @param uuid 资产使用状态明细设定主键
     * @return 资产使用状态明细设定
     */
    public FinanceFtStatusDetail selectFinanceFtStatusDetailByUuid(String uuid);

    /**
     * 查询资产使用状态明细设定列表
     * 
     * @param financeFtStatusDetail 资产使用状态明细设定
     * @return 资产使用状态明细设定集合
     */
    public List<FinanceFtStatusDetail> selectFinanceFtStatusDetailList(FinanceFtStatusDetail financeFtStatusDetail);

    /**
     * 新增资产使用状态明细设定
     * 
     * @param financeFtStatusDetail 资产使用状态明细设定
     * @return 结果
     */
    public int insertFinanceFtStatusDetail(FinanceFtStatusDetail financeFtStatusDetail);

    /**
     * 修改资产使用状态明细设定
     * 
     * @param financeFtStatusDetail 资产使用状态明细设定
     * @return 结果
     */
    public int updateFinanceFtStatusDetail(FinanceFtStatusDetail financeFtStatusDetail);

    /**
     * 删除资产使用状态明细设定
     * 
     * @param uuid 资产使用状态明细设定主键
     * @return 结果
     */
    public int deleteFinanceFtStatusDetailByUuid(String uuid);

    /**
     * 批量删除资产使用状态明细设定
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtStatusDetailByUuids(String[] uuids);
}
