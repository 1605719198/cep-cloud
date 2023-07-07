package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 凭证维护-明细Mapper接口
 * 
 * @author 265799
 * @date 2023-04-24
 */
@Repository
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
     * @param financeAaLedgerAcctDTO 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public String[]  seleAcctCode(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
    /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaLedgerAcctDTO 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public List<FinanceAaLedgerAcctDTO>  seleClName(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaLedgerAcctDTO 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public List<FinanceAaLedgerAcctDTO>  selectcalNameList(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public FinanceAaVoucherDetail selectFinanceAaLedgerAcctList(FinanceAaVoucherDetail financeAaVoucherDetail);
    /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaLedgerAcctDTO 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public FinanceAaLedgerAcctDTO selectFinanceAaLedgerAcctDTOList(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 根据条件求和
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public FinanceAaVoucherDetail selectFinanceAdd(FinanceAaVoucherDetail financeAaVoucherDetail);
    /**
     * 根据核算项目条件求和
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public FinanceAaVoucherDetail selectFinancecalCodeAdd(FinanceAaVoucherDetail financeAaVoucherDetail);
    /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public List<FinanceAaVoucherDetail> selectFinanceAaVoucherBatchExecutionList(FinanceAaVoucherDetail financeAaVoucherDetail);
    /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public List<FinanceAaVoucherDetail> selectFinanceAaVoucherExecuteList(FinanceAaVoucherDetail financeAaVoucherDetail);
    /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细集合
     */
    public List<FinanceAaVoucherDetail> selectFinanceAaVoucherExecuteAList(FinanceAaVoucherDetail financeAaVoucherDetail);
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


    /**
    * @description 按系统别查询
    * @param companyId
    * @param acctPeriod 会计周期
    * @param code 会计科目
    * @param pgrmId  系统别
    * @return java.util.List<com.jlkj.finance.aa.domain.FinanceAaVoucherDetail>
    * @Author 114288
    * @Date 2023/7/7 10:38
    **/
    List<FinanceAaVoucherDetail> selectDetailByPgrmId(String companyId, String acctPeriod, String code, String pgrmId);
}
