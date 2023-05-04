package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;

/**
 * 凭证维护-明细Mapper接口
 * 
 * @author 265799
 * @date 2023-04-24
 */
public interface FinanceAaVoucherDetailMapper 
{
    /**
     * 查询凭证维护-明细
     * 
     * @param id 凭证维护-明细主键
     * @return 凭证维护-明细
     */
    public FinanceAaVoucherDetail selectFinanceAaVoucherDetailById(String id);

    /**
     * 查询凭证维护-明细列表
     * 
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public List<FinanceAaVoucherDetail> selectFinanceAaVoucherDetailList(FinanceAaVoucherDetail financeAaVoucherDetail);

    /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public List<FinanceAaVoucherDetail> selectFinanceAaVoucherList(FinanceAaVoucherDetail financeAaVoucherDetail);

    /**
     * 新增凭证维护-明细
     * 
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 结果
     */
    public int insertFinanceAaVoucherDetail(FinanceAaVoucherDetail financeAaVoucherDetail);

    /**
     * 修改凭证维护-明细
     * 
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 结果
     */
    public int updateFinanceAaVoucherDetail(FinanceAaVoucherDetail financeAaVoucherDetail);

    /**
     * 删除凭证维护-明细
     * 
     * @param id 凭证维护-明细主键
     * @return 结果
     */
    public int deleteFinanceAaVoucherDetailById(String id);

    /**
     * 批量删除凭证维护-明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherDetailByIds(String[] ids);
}
