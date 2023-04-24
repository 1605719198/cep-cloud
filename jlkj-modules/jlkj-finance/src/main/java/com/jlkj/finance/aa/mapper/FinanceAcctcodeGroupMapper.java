package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAcctcodeGroup;
import org.apache.ibatis.annotations.Param;

/**
 * 会计科目-集团级Mapper接口
 *
 * @author 116514
 * @date 2023-04-17
 */
public interface FinanceAcctcodeGroupMapper
{
    /**
     * 查询会计科目-集团级
     *
     * @param groupAcctId 会计科目-集团级主键
     * @return 会计科目-集团级
     */
    public FinanceAcctcodeGroup selectFinanceAcctcodeGroupByGroupAcctId(String groupAcctId);

    /**
     * 查询会计科目-集团级列表
     *
     * @param financeAcctcodeGroup 会计科目-集团级
     * @return 会计科目-集团级集合
     */
    public List<FinanceAcctcodeGroup> selectFinanceAcctcodeGroupList(FinanceAcctcodeGroup financeAcctcodeGroup);

    /**
     * 新增会计科目-集团级
     *
     * @param financeAcctcodeGroup 会计科目-集团级
     * @return 结果
     */
    public int insertFinanceAcctcodeGroup(FinanceAcctcodeGroup financeAcctcodeGroup);

    /**
     * 修改会计科目-集团级
     *
     * @param financeAcctcodeGroup 会计科目-集团级
     * @return 结果
     */
    public int updateFinanceAcctcodeGroup(FinanceAcctcodeGroup financeAcctcodeGroup);

    /**
     * 删除会计科目-集团级
     *
     * @param groupAcctId 会计科目-集团级主键
     * @return 结果
     */
    public int deleteFinanceAcctcodeGroupByGroupAcctId(String groupAcctId);

    /**
     * 批量删除会计科目-集团级
     *
     * @param groupAcctIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAcctcodeGroupByGroupAcctIds(String[] groupAcctIds);

    /**
     * 查询会计科目-集团级
     * @param GroupAcctCode
     * @return
     */
    public FinanceAcctcodeGroup selectFinanceAcctcodeGroupByGroupAcctCode(String GroupAcctCode);

    /**
     * 查询会计科目子节点
     * @param GroupAcctCode
     * @return
     */
    public List<FinanceAcctcodeGroup> selectFinancAcctcodeChildrenList(String GroupAcctCode);

    /**
     * 通过GroupAcctCode删除会计科目
     * @param GroupAcctCode
     * @return
     */
    public int deleteFinanceAcctcodeGroupByGroupAcctCode(String GroupAcctCode);


    /**
     * 通过上层会计科目和公司别,查询下一层集团级会计科目
     * @param acctCode
     * @param companyId
     * @return
     */
    public List<FinanceAcctcodeGroup> selectCodeGroupList(@Param("acctCode") String acctCode, @Param("companyId") String companyId);
}
