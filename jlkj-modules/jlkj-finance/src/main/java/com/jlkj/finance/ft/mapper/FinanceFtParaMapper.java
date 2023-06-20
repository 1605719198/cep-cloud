package com.jlkj.finance.ft.mapper;

import com.jlkj.finance.ft.domain.FinanceFtPara;

import java.util.List;

/**
 * 固定资产参数维护Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-06-13
 */
public interface FinanceFtParaMapper 
{
    /**
     * 查询固定资产参数维护
     * 
     * @param id 固定资产参数维护主键
     * @return 固定资产参数维护
     */
    public FinanceFtPara selectFinanceFtParaById(String id);

    /**
     * 查询固定资产参数维护列表
     * 
     * @param financeFtPara 固定资产参数维护
     * @return 固定资产参数维护集合
     */
    public List<FinanceFtPara> selectFinanceFtParaList(FinanceFtPara financeFtPara);

    /**
     * 新增固定资产参数维护
     * 
     * @param financeFtPara 固定资产参数维护
     * @return 结果
     */
    public int insertFinanceFtPara(FinanceFtPara financeFtPara);

    /**
     * 修改固定资产参数维护
     * 
     * @param financeFtPara 固定资产参数维护
     * @return 结果
     */
    public int updateFinanceFtPara(FinanceFtPara financeFtPara);

    /**
     * 删除固定资产参数维护
     * 
     * @param id 固定资产参数维护主键
     * @return 结果
     */
    public int deleteFinanceFtParaById(String id);

    /**
     * 批量删除固定资产参数维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtParaByIds(String[] ids);
}
