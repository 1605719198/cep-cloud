package com.jlkj.finance.aa.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.TreeSelectAcctCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaAcctcodeCorpMapper;
import com.jlkj.finance.aa.domain.FinanceAaAcctcodeCorp;
import com.jlkj.finance.aa.service.IFinanceAaAcctcodeCorpService;

/**
 * 会计科目-公司级Service业务层处理
 *
 * @author 116514
 * @date 2023-04-19
 */
@Service
public class FinanceAaAcctcodeCorpServiceImpl implements IFinanceAaAcctcodeCorpService
{
    @Autowired
    private FinanceAaAcctcodeCorpMapper financeAaAcctcodeCorpMapper;

    /**
     * 查询会计科目-公司级
     *
     * @param groupAcctId 会计科目-公司级主键
     * @return 会计科目-公司级
     */
    @Override
    public FinanceAaAcctcodeCorp selectFinanceAaAcctcodeCorpByGroupAcctId(String groupAcctId)
    {
        return financeAaAcctcodeCorpMapper.selectFinanceAaAcctcodeCorpByGroupAcctId(groupAcctId);
    }

    /**
     * 查询会计科目-公司级列表
     *
     * @param financeAaAcctcodeCorp 会计科目-公司级
     * @return 会计科目-公司级
     */
    @Override
    public List<FinanceAaAcctcodeCorp> selectFinanceAaAcctcodeCorpList(FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        return financeAaAcctcodeCorpMapper.selectFinanceAaAcctcodeCorpList(financeAaAcctcodeCorp);
    }

    /**
     * 新增会计科目-公司级
     *
     * @param financeAaAcctcodeCorp 会计科目-公司级
     * @return 结果
     */
    @Override
    public int insertFinanceAaAcctcodeCorp(FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        financeAaAcctcodeCorp.setCreateTime(DateUtils.getNowDate());
        return financeAaAcctcodeCorpMapper.insertFinanceAaAcctcodeCorp(financeAaAcctcodeCorp);
    }

    /**
     * 修改会计科目-公司级
     *
     * @param financeAaAcctcodeCorp 会计科目-公司级
     * @return 结果
     */
    @Override
    public int updateFinanceAaAcctcodeCorp(FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        financeAaAcctcodeCorp.setUpdateBy(SecurityUtils.getUsername());
        financeAaAcctcodeCorp.setUpdateName(SecurityUtils.getNickName());
        financeAaAcctcodeCorp.setUpdateTime(DateUtils.getNowDate());
        return financeAaAcctcodeCorpMapper.updateFinanceAaAcctcodeCorp(financeAaAcctcodeCorp);
    }

    /**
     * 批量删除会计科目-公司级
     *
     * @param groupAcctIds 需要删除的会计科目-公司级主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaAcctcodeCorpByGroupAcctIds(String[] groupAcctIds)
    {
        return financeAaAcctcodeCorpMapper.deleteFinanceAaAcctcodeCorpByGroupAcctIds(groupAcctIds);
    }

    /**
     * 删除会计科目-公司级信息
     *
     * @param groupAcctId 会计科目-公司级主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaAcctcodeCorpByGroupAcctId(String groupAcctId)
    {
        return financeAaAcctcodeCorpMapper.deleteFinanceAaAcctcodeCorpByGroupAcctId(groupAcctId);
    }

    @Override
    public List<TreeSelectAcctCode> selectCodeTreeList(FinanceAaAcctcodeCorp acctcodeCorp)
    {
        List<FinanceAaAcctcodeCorp> financeAcctcodeCorps = financeAaAcctcodeCorpMapper.selectFinanceAaAcctcodeCorpList(acctcodeCorp);
        return buildCodeTreeSelect(financeAcctcodeCorps);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelectAcctCode> buildCodeTreeSelect(List<FinanceAaAcctcodeCorp> depts)
    {
        List<FinanceAaAcctcodeCorp> deptTrees = buildCodeTree(depts);
        return deptTrees.stream().map(TreeSelectAcctCode::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<FinanceAaAcctcodeCorp> buildCodeTree(List<FinanceAaAcctcodeCorp> depts)
    {
        List<FinanceAaAcctcodeCorp> returnList = new ArrayList<FinanceAaAcctcodeCorp>();
        List<String> tempList = depts.stream().map(FinanceAaAcctcodeCorp::getAcctCode).collect(Collectors.toList());
        for (FinanceAaAcctcodeCorp dept : depts)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentAcctCode()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<FinanceAaAcctcodeCorp> list, FinanceAaAcctcodeCorp t)
    {
        // 得到子节点列表
        List<FinanceAaAcctcodeCorp> childList = getChildList(list, t);
        t.setChildren(childList);
        for (FinanceAaAcctcodeCorp tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }


    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<FinanceAaAcctcodeCorp> list, FinanceAaAcctcodeCorp t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 得到子节点列表
     */
    private List<FinanceAaAcctcodeCorp> getChildList(List<FinanceAaAcctcodeCorp> list, FinanceAaAcctcodeCorp t)
    {
        List<FinanceAaAcctcodeCorp> tlist = new ArrayList<FinanceAaAcctcodeCorp>();
        Iterator<FinanceAaAcctcodeCorp> it = list.iterator();
        while (it.hasNext())
        {
            FinanceAaAcctcodeCorp n = (FinanceAaAcctcodeCorp) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentAcctCode().equals(t.getAcctCode()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 批量新增集团会计科目到公司级会计科目表中
     * @param request
     * @return
     */
    @Override
    public int insertBatchFinanceAcctcodeCorp(Map request){
        String companyId = request.get("companyId").toString();
        List<Map<Object, Object>> acctcodeGroups = (List<Map<Object, Object>>) request.get("checkedList");
        List<FinanceAaAcctcodeCorp> acctcodeCorps = new ArrayList<>();
        for (Map acctcodeGroup : acctcodeGroups) {
            FinanceAaAcctcodeCorp acctcodeCorp = new FinanceAaAcctcodeCorp();
            acctcodeCorp.setAcctId(IdUtils.simpleUUID());
            acctcodeCorp.setCompanyId(companyId);
            acctcodeCorp.setGroupAcctId((String) acctcodeGroup.get("groupAcctId"));
            acctcodeCorp.setAcctCode((String) acctcodeGroup.get("groupAcctCode"));
            acctcodeCorp.setAcctName((String) acctcodeGroup.get("groupAcctName"));
            acctcodeCorp.setParentId((String) acctcodeGroup.get("parentId"));
            acctcodeCorp.setParentAcctCode((String) acctcodeGroup.get("parentAcctCode"));
            acctcodeCorp.setAncestors((String) acctcodeGroup.get("ancestors"));
            acctcodeCorp.setIsVoucher((String) acctcodeGroup.get("isVoucher"));
            acctcodeCorp.setDrOrCr((String) acctcodeGroup.get("drOrCr"));
            acctcodeCorp.setIsCash((String) acctcodeGroup.get("isCash"));
            acctcodeCorp.setLevel((String) acctcodeGroup.get("level"));
            acctcodeCorp.setDisabledCode("Y");
            acctcodeCorp.setCreateBy(SecurityUtils.getUsername());
            acctcodeCorp.setCreateName(SecurityUtils.getNickName());
            acctcodeCorp.setCreateTime(DateUtils.getNowDate());
            acctcodeCorps.add(acctcodeCorp);
        }
        return financeAaAcctcodeCorpMapper.insertBatchFinanceAcctcodeCorp(acctcodeCorps);
    }

    /**
     * 查询会计科目-公司级列表(POP)
     *
     * @param financeAaAcctcodeCorp 会计科目-公司级
     * @return 会计科目-公司级
     */
    @Override
    public List<FinanceAaAcctcodeCorp> selectFinanceAaAcctcodeCorpListPop(FinanceAaAcctcodeCorp financeAaAcctcodeCorp)
    {
        return financeAaAcctcodeCorpMapper.selectFinanceAaAcctcodeCorpListPop(financeAaAcctcodeCorp);
    }
}
