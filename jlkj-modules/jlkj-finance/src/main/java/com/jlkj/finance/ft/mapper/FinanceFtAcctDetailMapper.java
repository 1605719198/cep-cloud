package com.jlkj.finance.ft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.finance.ft.domain.FinanceFtAcctDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账务代码明细设定Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-06-30
 */
@Repository
public interface FinanceFtAcctDetailMapper extends BaseMapper<FinanceFtAcctDetail>
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
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 账务代码明细设定集合
     */
    public List<FinanceFtAcctDetail> selectFinanceFtAcctDetailList(FinanceFtAcctDetail financeFtAcctDetail);

    /**
     * 新增账务代码明细设定
     * 
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 结果
     */
    public int insertFinanceFtAcctDetail(FinanceFtAcctDetail financeFtAcctDetail);

    /**
     * 修改账务代码明细设定
     * 
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 结果
     */
    public int updateFinanceFtAcctDetail(FinanceFtAcctDetail financeFtAcctDetail);

    /**
     * 删除账务代码明细设定
     * 
     * @param uuid 账务代码明细设定主键
     * @return 结果
     */
    public int deleteFinanceFtAcctDetailByUuid(String uuid);

    /**
     * 批量删除账务代码明细设定
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtAcctDetailByUuids(String[] uuids);
}
