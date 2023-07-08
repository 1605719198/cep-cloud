package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaLedgerlCal;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 账薄-核算项目余额Mapper接口
 * 
 * @author jlkj
 * @date 2023-04-29
 */
@Repository
public interface FinanceAaLedgerlCalMapper 
{
    /**
     * 查询账薄-核算项目余额
     * 
     * @param id 账薄-核算项目余额主键
     * @return 账薄-核算项目余额
     */
    public FinanceAaLedgerlCal selectFinanceAaLedgerlCalById(String id);

    /**
     * 查询账薄-核算项目余额列表
     * 
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerlCal> selectFinanceAaLedgerlCalList(FinanceAaLedgerlCal financeAaLedgerlCal);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectCalName(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public FinanceAaLedgerAcctDTO selectBgnAmtAndBgnQty(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public FinanceAaLedgerAcctDTO  selectBnlj(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectDetailSteelCalName(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public FinanceAaLedgerAcctDTO selecMaxMonth(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public FinanceAaLedgerAcctDTO selectDetailMaxMonth(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectAmountNotDisplayedA(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public FinanceAaLedgerAcctDTO  selectFinanceAaLedgerlCalMaxMonth(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectFinanceAaGeneralLedgerCalCode1(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectAmountNotDisplayedB(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectAmountNotDisplayedC(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectAmountNotDisplayedD(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);

    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerlCal> selectAcctPeriodList(FinanceAaLedgerlCal financeAaLedgerlCal);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public FinanceAaLedgerlCal selectFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public FinanceAaLedgerlCal selectFinanceAaLedgerlCalName(FinanceAaLedgerlCal financeAaLedgerlCal);

    /**
     * 新增账薄-核算项目余额
     * 
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    public int insertFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);
    /**
     * 批量新增账薄-核算项目余额
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    public int batchFinanceAaLedgerlCal(List<FinanceAaLedgerlCal> financeAaLedgerlCal);

    /**
     * 修改账薄-核算项目余额
     * 
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    public int updateFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);

    /**
     * 删除账薄-核算项目余额
     * 
     * @param id 账薄-核算项目余额主键
     * @return 结果
     */
    public int deleteFinanceAaLedgerlCalById(String id);

    /**
     * 批量删除账薄-核算项目余额
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaLedgerlCalByIds(String[] ids);

    /**
     * 科目余额查询
     * @param financeAaLedgerAcctDTO
     * @return
     */
    List<FinanceAaLedgerAcctDTO> selectAmountNotDisplayedCal(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);


    /**
    *  户号借余、贷余
    * @param  compId 公司
    * @param  acctPeriod 会计周期
    * @param  acctCode 会计科目
    * @param  drCrType 借贷方类别
    * @return java.util.Map[]
    * @Author 114288
    * @Date 2023/7/7 8:21
    **/
    Map[] getKindaByIdCode(String compId, String acctPeriod, String acctCode, String drCrType);

    /**
     * 管理报表取值
    * @description
    * @param companyId 公司别
    * @param acctPeriod 会计周期
    * @param sumstr 数量栏位
    * @param sumstr2 金额栏位
    * @param sumstr3 查询条件
    * @param equalslike 会计科目条件
    * @param acctCode 会计科目
    * @return
    * @Author 114288
    * @Date 2023/7/4 14:37
    **/
    Map[] selectByManageReport(@Param("companyId")String companyId, @Param("acctPeriod")String acctPeriod,
                               @Param("sumstr")String sumstr,  @Param("sumstr2")String sumstr2,
                               @Param("sumstr3")String sumstr3,  @Param("equalslike")String equalslike,  @Param("acctCode")String acctCode);
}
