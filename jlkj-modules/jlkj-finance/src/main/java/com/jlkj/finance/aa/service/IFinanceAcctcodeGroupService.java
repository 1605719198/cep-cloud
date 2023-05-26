package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.FinanceAcctcodeGroup;
import com.jlkj.finance.aa.domain.TreeSelectAcctCode;

import java.util.List;

/**
 * 会计科目-集团级Service接口
 *
 * @author 116514
 * @date 2023-04-17
 */
public interface IFinanceAcctcodeGroupService
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
     * 批量删除会计科目-集团级
     *
     * @param groupAcctIds 需要删除的会计科目-集团级主键集合
     * @return 结果
     */
    public int deleteFinanceAcctcodeGroupByGroupAcctIds(String[] groupAcctIds);

    /**
     * 删除会计科目-集团级信息
     *
     * @param groupAcctId 会计科目-集团级主键
     * @return 结果
     */
    public int deleteFinanceAcctcodeGroupByGroupAcctId(String groupAcctId);



    /**
     * 查询科目树结构信息
     *
     * @param acctcodeGroup 科目信息
     * @return 部门树信息集合
     */
    public List<TreeSelectAcctCode> selectCodeTreeList(FinanceAcctcodeGroup acctcodeGroup);


    /**
     * 构建前端所需要树结构
     *
     * @param acctcodeGroups 科目列表
     * @return 树结构列表
     */
    public List<FinanceAcctcodeGroup> buildCodeTree(List<FinanceAcctcodeGroup> acctcodeGroups);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param acctcodeGroups 科目列表
     * @return 下拉树结构列表
     */
    public List<TreeSelectAcctCode> buildCodeTreeSelect(List<FinanceAcctcodeGroup> acctcodeGroups);

    /**
     * 通过GroupAcctCode删除会计科目
     * @param groupAcctCode
     * @return
     */
    public int deleteFinanceAcctcodeGroupByGroupAcctCode(String groupAcctCode);

    /**
     * 通过会计科目编号查询集团级会计科目
     * @param acctCode
     * @return
     */
    public List<FinanceAcctcodeGroup> selectCodeGroupList(String acctCode, String companyId);

    /**
     * 根据会计科目编号查询会计科目中文名称
     * @param groupAcctCode
     * @return
     */
    public String selectAcctNameByCode(String groupAcctCode);

    /**
     * 集团会计科目弹窗查询方法
     * @param financeAcctcodeGroup
     * @return
     */
    public List<FinanceAcctcodeGroup> selectFinanceAcctcodeGroupPopList(FinanceAcctcodeGroup financeAcctcodeGroup);
}
