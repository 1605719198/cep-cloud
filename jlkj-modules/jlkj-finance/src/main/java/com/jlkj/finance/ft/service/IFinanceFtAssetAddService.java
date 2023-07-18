package com.jlkj.finance.ft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.dto.FinanceFtAssetAddDTO;

import java.util.List;

/**
 * 资产增加单Service接口
 * 
 * @author SunXuTong
 * @date 2023-07-11
 */
public interface IFinanceFtAssetAddService extends IService<FinanceFtChange>
{
    /**
     * 查询资产增加单
     * 
     * @param uuid 资产增加单主键
     * @return 资产增加单
     */
    public FinanceFtChange selectFinanceFtChangeByUuid(String uuid);

    /**
     * 查询资产增加单列表
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtAssetAdd 资产增加单
     * @return 资产增加单集合
     */
    public List<FinanceFtChange> selectFinanceFtChangeList(FinanceFtChange financeFtChange);

    /**
     * 新增资产增加单
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtAssetAddDTO 资产增加单
     * @return 结果
     */
    Object insertFinanceFtChange(FinanceFtAssetAddDTO financeFtAssetAddDTO);

    /**
     * 修改资产增加单
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtAssetAdd 资产增加单
     * @return 结果
     */
    public int updateFinanceFtChange(FinanceFtChange financeFtChange);

    /**
     * 批量删除资产增加单
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param uuids 需要删除的资产增加单主键集合
     * @return 结果
     */
    public int deleteFinanceFtChangeByUuids(String[] uuids);

    /**
     * 删除资产增加单信息
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param uuid 资产增加单主键
     * @return 结果
     */
    public int deleteFinanceFtChangeByUuid(String uuid);
}
