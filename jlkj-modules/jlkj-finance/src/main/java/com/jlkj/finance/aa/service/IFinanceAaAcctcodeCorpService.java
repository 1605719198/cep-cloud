package com.jlkj.finance.aa.service;

import java.util.List;
import java.util.Map;

import com.jlkj.finance.aa.domain.FinanceAaAcctcodeCorp;
import com.jlkj.finance.aa.domain.FinanceAcctcodeGroup;
import com.jlkj.finance.aa.domain.TreeSelectAcctCode;

/**
 * 会计科目-公司级Service接口
 * 
 * @author 116514
 * @date 2023-04-19
 */
public interface IFinanceAaAcctcodeCorpService 
{
    /**
     * 查询会计科目-公司级
     * 
     * @param groupAcctId 会计科目-公司级主键
     * @return 会计科目-公司级
     */
    public FinanceAaAcctcodeCorp selectFinanceAaAcctcodeCorpByGroupAcctId(String groupAcctId);

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
     * 批量删除会计科目-公司级
     * 
     * @param groupAcctIds 需要删除的会计科目-公司级主键集合
     * @return 结果
     */
    public int deleteFinanceAaAcctcodeCorpByGroupAcctIds(String[] groupAcctIds);

    /**
     * 删除会计科目-公司级信息
     * 
     * @param groupAcctId 会计科目-公司级主键
     * @return 结果
     */
    public int deleteFinanceAaAcctcodeCorpByGroupAcctId(String groupAcctId);


    /**
     * 查询科目树结构信息
     *
     * @param acctcodeCorp 科目信息
     * @return 部门树信息集合
     */
    public List<TreeSelectAcctCode> selectCodeTreeList(FinanceAaAcctcodeCorp acctcodeCorp);


    /**
     * 构建前端所需要树结构
     *
     * @param acctcodeCorps 科目列表
     * @return 树结构列表
     */
    public List<FinanceAaAcctcodeCorp> buildCodeTree(List<FinanceAaAcctcodeCorp> acctcodeCorps);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param acctcodeCorps 科目列表
     * @return 下拉树结构列表
     */
    public List<TreeSelectAcctCode> buildCodeTreeSelect(List<FinanceAaAcctcodeCorp> acctcodeCorps);


    /**
     * 批量新增集团会计科目到公司级会计科目表中
     * @param request
     * @return
     */
    public int insertBatchFinanceAcctcodeCorp(Map request);
}
