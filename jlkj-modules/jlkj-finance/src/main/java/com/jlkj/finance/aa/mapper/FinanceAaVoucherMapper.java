package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaVoucher;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;

/**
 * 凭证维护-主Mapper接口
 * 
 * @author 265799
 * @date 2023-04-24
 */
public interface FinanceAaVoucherMapper 
{
    /**
     * 查询凭证维护-主
     * 
     * @param id 凭证维护-主主键
     * @return 凭证维护-主
     */
    public FinanceAaVoucher selectFinanceAaVoucherById(String id);
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public List<FinanceAaVoucher> selectFinanceAaVoucherList(FinanceAaVoucher financeAaVoucher);
    /**
     * 查询凭证维护-主列表
     * 
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public List<FinanceAaVoucher> selectFinanceAaVoucherDetailList(FinanceAaVoucher financeAaVoucher);


    /**
     * 查询凭证维护-主列表(不去重)
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public List<FinanceAaVoucher> selectFinanceAaVoucherLinkList(FinanceAaVoucher financeAaVoucher);
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public FinanceAaVoucher selectFinanceAaVoucher(FinanceAaVoucher financeAaVoucher);
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public  List<FinanceAaVoucher> selectFinanceAaVoucherFront(FinanceAaVoucher financeAaVoucher);
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public  List<FinanceAaVoucher> selectFinanceAaVoucherOrder(FinanceAaVoucher financeAaVoucher);
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public  List<FinanceAaVoucher> selectFinanceAaVoucherLast(FinanceAaVoucher financeAaVoucher);
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public List<FinanceAaVoucher> selectFinanceAaVoucherListHeadVoucher(FinanceAaVoucher financeAaVoucher);

    /**
     * 新增凭证维护-主
     * 
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public int insertFinanceAaVoucher(FinanceAaVoucher financeAaVoucher);

    /**
     * 批量新增凭证维护-明细
     *
     * @param financeAaVoucherDetailList 凭证维护-明细列表
     * @return 结果
     */
    public int batchFinanceAaVoucherDetail(List<FinanceAaVoucherDetail> financeAaVoucherDetailList);
    /**
     * 修改凭证维护-主
     * 
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public int updateFinanceAaVoucher(FinanceAaVoucher financeAaVoucher);

    /**
     * 通过凭证维护-主主键删除凭证维护-明细信息
     *
     * @param voucherNo 凭证维护-
     * @return 结果
     */
    public int deleteFinanceAaVoucherDetailByVoucherNo(String voucherNo);
    /**
     * 批量删除凭证维护-明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherDetailByVoucherNos(String[] ids);

    /**
     * 删除凭证维护-主
     * 
     * @param id 凭证维护-主主键
     * @return 结果
     */
    public int deleteFinanceAaVoucherById(String id);

    /**
     * 批量删除凭证维护-主
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherByIds(String[] ids);
}
