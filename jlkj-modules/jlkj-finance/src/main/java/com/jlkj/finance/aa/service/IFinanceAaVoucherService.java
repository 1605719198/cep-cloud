package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.FinanceAaVoucher;
import com.jlkj.finance.aa.dto.FinanceAaVoucherDTO;
import com.jlkj.finance.api.bean.FinanceVoucherBean;

import java.util.List;
import java.util.Map;

/**
 * 凭证维护-主Service接口
 * 
 * @author 265799
 * @date 2023-04-24
 */
public interface IFinanceAaVoucherService 
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
     * 前笔查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public FinanceAaVoucher selectFinanceAaVoucherFront(FinanceAaVoucher financeAaVoucher);
    /**
     * 前笔查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public FinanceAaVoucher selectFinanceAaVoucherLast(FinanceAaVoucher financeAaVoucher);
    /**
     * 首笔笔查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public FinanceAaVoucher selectFinanceAaVoucherHead(FinanceAaVoucher financeAaVoucher);
    /**
     * 次笔查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public FinanceAaVoucher selectFinanceAaVoucherOrder(FinanceAaVoucher financeAaVoucher);
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
    public String insertFinanceAaVoucher(FinanceAaVoucher financeAaVoucher);


/** 新增凭证 外部
  * @Description
  * @Param No such property: code for class: Script1
 * @param financeVoucherBean 凭证接口-主对象
  * @Author 265799
  * @Date 2023/6/27 15:00
 * @return 结果
**/
    Map<String,Object> doVoucher(FinanceVoucherBean financeVoucherBean);

    /**
     * 新增凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public List<Map<String,String>> insertFinanceAaVoucherSubmitForm(FinanceAaVoucher financeAaVoucher);
    /**
     * 红冲凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public List<Map<String,String>> insertFinanceAaVoucherHongChong(FinanceAaVoucher financeAaVoucher);

    /**
     * 修改凭证维护-主
     * 
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public List<Map<String,String>> updateFinanceAaVoucher(FinanceAaVoucher financeAaVoucher);

    /**
     * 确认和取消确认凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public int updateFinanceAaVoucherStatus(FinanceAaVoucher financeAaVoucher);
    /**
     * 过账凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public int updateFinanceAaVoucherCross(FinanceAaVoucher financeAaVoucher);


    /**
     * 批量删除凭证维护-主
     * 
     * @param financeAaVoucher 需要删除的凭证维护-主主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherByIds(FinanceAaVoucher financeAaVoucher);

    /**
     * 删除凭证维护-主信息
     * 
     * @param id 凭证维护-主主键
     * @return 结果
     */
    public int deleteFinanceAaVoucherById(String id);


    /**
     * 导入凭证
     *
     * @param financeAaVoucher 凭证数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     *  @param companyId 公司别
     * @return 结果
     */
    public List<FinanceAaVoucherDTO> importFinanceAaVoucher(List<FinanceAaVoucherDTO> financeAaVoucher, Boolean isUpdateSupport, String operName,String companyId);
}

