package com.jlkj.finance.ft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.dto.FinanceFtAssetAddDTO;

import java.util.List;

/**
 * 资产减少单Service接口
 * 
 * @author SunXuTong
 * @date 2023-07-15
 */
public interface IFinanceFtAssetSubtractService extends IService<FinanceFtChange>
{
    /**
     * 查询资产减少单
     * 
     * @param uuid 资产减少单主键
     * @return 资产减少单
     */
    public FinanceFtChange selectFinanceFtChangeByUuid(String uuid);

    /**
     * 查询资产减少单列表
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param financeFtAssetSubtract 资产减少单
     * @return 资产减少单集合
     */
    public List<FinanceFtChange> selectFinanceFtChangeList(FinanceFtChange financeFtChange);

    /**
     * 新增资产减少单
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param financeFtAssetSubtract 资产减少单
     * @return 结果
     */
    public int insertFinanceFtChange(FinanceFtChange financeFtChange);

    Object insertFinanceFtChange(FinanceFtAssetAddDTO financeFtAssetAddDTO);

    /**
     * 修改资产减少单
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param financeFtAssetSubtract 资产减少单
     * @return 结果
     */
    public int updateFinanceFtChange(FinanceFtChange financeFtChange);

    /**
     * 批量删除资产减少单
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param uuids 需要删除的资产减少单主键集合
     * @return 结果
     */
    public int deleteFinanceFtChangeByUuids(String[] uuids);

    /**
     * 删除资产减少单信息
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param uuid 资产减少单主键
     * @return 结果
     */
    public int deleteFinanceFtChangeByUuid(String uuid);
}
