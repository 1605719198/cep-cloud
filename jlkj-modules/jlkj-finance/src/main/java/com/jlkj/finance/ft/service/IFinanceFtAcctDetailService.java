package com.jlkj.finance.ft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.ft.domain.FinanceFtAcctDetail;

import java.util.List;

/**
 * 账务代码明细设定Service接口
 * 
 * @author SunXuTong
 * @date 2023-06-30
 */
public interface IFinanceFtAcctDetailService extends IService<FinanceFtAcctDetail>
{
    /**
     * 查询账务代码明细设定
     * 
     * @param uuid 账务代码明细设定主键
     * @return 账务代码明细设定
     */
    public FinanceFtAcctDetail selectFinanceFtAcctDetailByUuid(String uuid);

    /**
     * 查询账务代码明细设定列表
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 账务代码明细设定集合
     */
    public List<FinanceFtAcctDetail> selectFinanceFtAcctDetailList(FinanceFtAcctDetail financeFtAcctDetail);

    /**
     * 新增账务代码明细设定
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 结果
     */
    public int insertFinanceFtAcctDetail(FinanceFtAcctDetail financeFtAcctDetail);

    /**
     * 修改账务代码明细设定
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 结果
     */
    public int updateFinanceFtAcctDetail(FinanceFtAcctDetail financeFtAcctDetail);

    /**
     * 批量删除账务代码明细设定
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param uuids 需要删除的账务代码明细设定主键集合
     * @return 结果
     */
    public int deleteFinanceFtAcctDetailByUuids(String[] uuids);

    /**
     * 删除账务代码明细设定信息
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param uuid 账务代码明细设定主键
     * @return 结果
     */
    public int deleteFinanceFtAcctDetailByUuid(String uuid);
}
