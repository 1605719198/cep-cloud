package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaAcctcodeCorp;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会计科目-公司级Mapper接口
 * 
 * @author 116514
 * @date 2023-04-19
 */
@Repository
public interface FinanceAaAcctcodeCorpMapper 
{
    /**
     * 查询会计科目-公司级
     * 
     * @param groupAcctId 会计科目-公司级主键
     * @return 会计科目-公司级
     */
    public FinanceAaAcctcodeCorp selectFinanceAaAcctcodeCorpByGroupAcctId(String groupAcctId);
    /**
     * 查询会计科目-公司级
     *
     * @param groupAcctId 会计科目-公司级主键
     * @param companyId 会计科目-公司
     * @return 会计科目-公司级
     */
    public FinanceAaAcctcodeCorp selectFinanceAaAcctcodeCorpByGroupAcct(@Param("groupAcctId")String groupAcctId,@Param("companyId") String companyId);
    /**
     * 查询会计科目-公司级
     *
     * @param acctCode 会计科目-公司级编码
     * @param companyId 会计科目-公司
     * @return 会计科目-公司级
     */
    public FinanceAaAcctcodeCorp selectAcctId(@Param("acctCode")String acctCode,@Param("companyId") String companyId);

    /**
     * 查询会计科目-公司级列表
     * 
     * @param financeAaAcctcodeCorp 会计科目-公司级
     * @return 会计科目-公司级集合
     */
    public List<FinanceAaAcctcodeCorp> selectFinanceAaAcctcodeCorpList(FinanceAaAcctcodeCorp financeAaAcctcodeCorp);

    /**
     * 新增会计科目-公司级
     * 
     * @param financeAaAcctcodeCorp 会计科目-公司级
     * @return 结果
     */
    public int insertFinanceAaAcctcodeCorp(FinanceAaAcctcodeCorp financeAaAcctcodeCorp);

    /**
     * 修改会计科目-公司级
     * 
     * @param financeAaAcctcodeCorp 会计科目-公司级
     * @return 结果
     */
    public int updateFinanceAaAcctcodeCorp(FinanceAaAcctcodeCorp financeAaAcctcodeCorp);

    /**
     * 删除会计科目-公司级
     * 
     * @param groupAcctId 会计科目-公司级主键
     * @return 结果
     */
    public int deleteFinanceAaAcctcodeCorpByGroupAcctId(String groupAcctId);

    /**
     * 批量删除会计科目-公司级
     * 
     * @param groupAcctIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaAcctcodeCorpByGroupAcctIds(String[] groupAcctIds);
    /**
     * 批量查询会计科目-公司级
     *
     * @param acctCode 需要删除的数据主键集合
     * @param  companyId 公司别
     * @return 结果
     */
    public List<FinanceAaLedgerAcctDTO> selectForeach ( @Param("acctCode") String[] acctCode, @Param("companyId")String companyId);
    /**
     * 批量新增集团会计科目到公司级会计科目表中
     * @param acctcodeCorps
     * @return
     */
    public int insertBatchFinanceAcctcodeCorp(List<FinanceAaAcctcodeCorp> acctcodeCorps);

    /**
     * 查询会计科目-公司级列表(弹窗用)
     *
     * @param financeAaAcctcodeCorp 会计科目-公司级
     * @return 会计科目-公司级集合
     */
    public List<FinanceAaAcctcodeCorp> selectFinanceAaAcctcodeCorpListPop(FinanceAaAcctcodeCorp financeAaAcctcodeCorp);
}
