package com.jlkj.finance.ft.service;

import com.jlkj.finance.ft.domain.FinanceFtPara;

import java.util.List;

/**
 * 固定资产参数维护Service接口
 * 
 * @author SunXuTong
 * @date 2023-06-13
 */
public interface IFinanceFtParaService 
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
     * 批量删除固定资产参数维护
     * 
     * @param ids 需要删除的固定资产参数维护主键集合
     * @return 结果
     */
    public int deleteFinanceFtParaByIds(String[] ids);

    /**
     * 删除固定资产参数维护信息
     * 
     * @param id 固定资产参数维护主键
     * @return 结果
     */
    public int deleteFinanceFtParaById(String id);


    /**
    * @Description: 根据固定资产参数键名获取value值
    * @param:[paramKey]固定资产参数键名
    * @return:java.lang.String 参数键值
    * @Author: 116524_wxj
    * @Date: 2023/7/13 9:22
    */
    public String getParamValue(String paramKey);
}
