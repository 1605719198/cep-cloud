package com.jlkj.finance.ap.mapper;

import java.util.List;
import com.jlkj.finance.ap.domain.FinanceApConfirmDetail;
import org.springframework.stereotype.Repository;

/**
 * 会计确认明细Mapper接口
 * 
 * @author jlkj
 * @date 2023-07-10
 */
@Repository
public interface FinanceApConfirmDetailMapper 
{
    /**
     * 查询会计确认明细
     * 
     * @param billId 会计确认明细主键
     * @return 会计确认明细
     */
    public FinanceApConfirmDetail selectFinanceApConfirmDetailByBillId(String billId);

    /**
     * 查询会计确认明细列表
     * 
     * @param financeApConfirmDetail 会计确认明细
     * @return 会计确认明细集合
     */
    public List<FinanceApConfirmDetail> selectFinanceApConfirmDetailList(FinanceApConfirmDetail financeApConfirmDetail);

    /**
     * 新增会计确认明细
     * 
     * @param financeApConfirmDetail 会计确认明细
     * @return 结果
     */
    public int insertFinanceApConfirmDetail(FinanceApConfirmDetail financeApConfirmDetail);

    /**
     * 修改会计确认明细
     * 
     * @param financeApConfirmDetail 会计确认明细
     * @return 结果
     */
    public int updateFinanceApConfirmDetail(FinanceApConfirmDetail financeApConfirmDetail);

    /**
     * 删除会计确认明细
     * 
     * @param billId 会计确认明细主键
     * @return 结果
     */
    public int deleteFinanceApConfirmDetailByBillId(String billId);

    /**
     * 批量删除会计确认明细
     * 
     * @param billIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApConfirmDetailByBillIds(String[] billIds);
}
