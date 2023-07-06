package com.jlkj.finance.ft.mapper;

import com.jlkj.finance.ft.domain.FinanceFtAcctMain;
import com.jlkj.finance.ft.dto.FinanceFtAcctMainDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账务代码Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-06-27
 */
@Repository
public interface FinanceFtAcctMainMapper 
{
    /**
     * 查询账务代码
     * 
     * @param uuid 账务代码主键
     * @return 账务代码
     */
    public FinanceFtAcctMain selectFinanceFtAcctMainByUuid(String uuid);

    /**
     * 查询账务代码列表
     * 
     * @param financeFtAcctMain 账务代码
     * @return 账务代码集合
     */
    public List<FinanceFtAcctMain> selectFinanceFtAcctMainList(FinanceFtAcctMain financeFtAcctMain);

    /**
     * 新增账务代码
     * 
     * @param financeFtAcctMainDTO 账务代码
     * @return 结果
     */
    public int insertFinanceFtAcctMain(FinanceFtAcctMainDTO financeFtAcctMainDTO);

    /**
     * 修改账务代码
     * 
     * @param financeFtAcctMain 账务代码
     * @return 结果
     */
    public int updateFinanceFtAcctMain(FinanceFtAcctMain financeFtAcctMain);

    /**
     * 删除账务代码
     * 
     * @param uuid 账务代码主键
     * @return 结果
     */
    public int deleteFinanceFtAcctMainByUuid(String uuid);

    /**
     * 批量删除账务代码
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtAcctMainByUuids(String[] uuids);
}
