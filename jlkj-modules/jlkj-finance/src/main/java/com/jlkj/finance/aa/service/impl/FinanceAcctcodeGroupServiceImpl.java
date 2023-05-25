package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAcctcodeGroup;
import com.jlkj.finance.aa.domain.TreeSelectAcctCode;
import com.jlkj.finance.aa.mapper.FinanceAcctcodeGroupMapper;
import com.jlkj.finance.aa.service.IFinanceAcctcodeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会计科目-集团级Service业务层处理
 *
 * @author 116514
 * @date 2023-04-17
 */
@Service
public class FinanceAcctcodeGroupServiceImpl implements IFinanceAcctcodeGroupService
{
    @Autowired
    private FinanceAcctcodeGroupMapper financeAcctcodeGroupMapper;

    /**
     * 查询会计科目-集团级
     *
     * @param groupAcctId 会计科目-集团级主键
     * @return 会计科目-集团级
     */
    @Override
    public FinanceAcctcodeGroup selectFinanceAcctcodeGroupByGroupAcctId(String groupAcctId)
    {
        return financeAcctcodeGroupMapper.selectFinanceAcctcodeGroupByGroupAcctId(groupAcctId);
    }

    /**
     * 查询会计科目-集团级列表
     *
     * @param financeAcctcodeGroup 会计科目-集团级
     * @return 会计科目-集团级
     */
    @Override
    public List<FinanceAcctcodeGroup> selectFinanceAcctcodeGroupList(FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        return financeAcctcodeGroupMapper.selectFinanceAcctcodeGroupList(financeAcctcodeGroup);
    }

    /**
     * 新增会计科目-集团级
     *
     * @param financeAcctcodeGroup 会计科目-集团级
     * @return 结果
     */
    @Override
    public int insertFinanceAcctcodeGroup(FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        FinanceAcctcodeGroup info = financeAcctcodeGroupMapper.selectFinanceAcctcodeGroupByGroupAcctCode(financeAcctcodeGroup.getParentAcctCode());
        // 如果父节点不为正常状态,则不允许新增子节点
        String parentId = "0" ;
        String parentAcctCode = "0";
        String ancestors = "0";
        String level = "0";
        if (StringUtils.isNotNull(info)){
            if("Y".equals(info.getIsVoucher())){
                throw new ServiceException("父节点为传票性会计科目，不允许新增");
            }
            parentId = info.getGroupAcctId();
            parentAcctCode =  financeAcctcodeGroup.getParentAcctCode();
            ancestors = info.getAncestors()  + "," + financeAcctcodeGroup.getParentAcctCode();
            level = String.valueOf(Integer.parseInt(info.getLevel())+1);
        }
        financeAcctcodeGroup.setGroupAcctId(IdUtils.simpleUUID());
        financeAcctcodeGroup.setCreateBy(SecurityUtils.getUsername());
        financeAcctcodeGroup.setCreateName(SecurityUtils.getNickName());
        financeAcctcodeGroup.setCreateTime(DateUtils.getNowDate());
        financeAcctcodeGroup.setParentId(parentId);
        financeAcctcodeGroup.setParentAcctCode(parentAcctCode);
        financeAcctcodeGroup.setAncestors(ancestors);
        financeAcctcodeGroup.setLevel(level);

        return financeAcctcodeGroupMapper.insertFinanceAcctcodeGroup(financeAcctcodeGroup);
    }

    /**
     * 修改会计科目-集团级
     *
     * @param financeAcctcodeGroup 会计科目-集团级
     * @return 结果
     */
    @Override
    public int updateFinanceAcctcodeGroup(FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        financeAcctcodeGroup.setDisabledDate(null);
        // 禁用要捡核是否有子公司使用（通用方法，删除也有这个判断），有则不允许禁用
        if("N".equals(financeAcctcodeGroup.getDisabledCode())){
            financeAcctcodeGroup.setDisabledDate(DateUtils.getNowDate());
        }
        financeAcctcodeGroup.setUpdateBy(SecurityUtils.getUsername());
        financeAcctcodeGroup.setUpdateName(SecurityUtils.getNickName());
        financeAcctcodeGroup.setUpdateTime(DateUtils.getNowDate());
        return financeAcctcodeGroupMapper.updateFinanceAcctcodeGroup(financeAcctcodeGroup);
    }

    /**
     * 批量删除会计科目-集团级
     *
     * @param groupAcctIds 需要删除的会计科目-集团级主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAcctcodeGroupByGroupAcctIds(String[] groupAcctIds)
    {
        return financeAcctcodeGroupMapper.deleteFinanceAcctcodeGroupByGroupAcctIds(groupAcctIds);
    }

    /**
     * 删除会计科目-集团级信息
     *
     * @param groupAcctId 会计科目-集团级主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAcctcodeGroupByGroupAcctId(String groupAcctId)
    {
        financeAcctcodeGroupMapper.selectFinancAcctcodeChildrenList(groupAcctId);
        return financeAcctcodeGroupMapper.deleteFinanceAcctcodeGroupByGroupAcctId(groupAcctId);
    }

    /**
     *  通过GroupAcctCode删除会计科目
     * @param groupAcctCode
     * @return
     */
    @Override
    public int deleteFinanceAcctcodeGroupByGroupAcctCode(String groupAcctCode){
        List<FinanceAcctcodeGroup> financeAcctcodes = financeAcctcodeGroupMapper.selectFinancAcctcodeChildrenList(groupAcctCode);
        if(financeAcctcodes.size()>0){
            throw new ServiceException("此会计科目存在子节点，不允许删除!");
        }
        return financeAcctcodeGroupMapper.deleteFinanceAcctcodeGroupByGroupAcctCode(groupAcctCode);
    }


    @Override
    public List<TreeSelectAcctCode> selectCodeTreeList(FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        List<FinanceAcctcodeGroup> financeAcctcodeGroups = financeAcctcodeGroupMapper.selectFinanceAcctcodeGroupList(financeAcctcodeGroup);
        return buildCodeTreeSelect(financeAcctcodeGroups);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelectAcctCode> buildCodeTreeSelect(List<FinanceAcctcodeGroup> depts)
    {
        List<FinanceAcctcodeGroup> deptTrees = buildCodeTree(depts);
        return deptTrees.stream().map(TreeSelectAcctCode::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<FinanceAcctcodeGroup> buildCodeTree(List<FinanceAcctcodeGroup> depts)
    {
        List<FinanceAcctcodeGroup> returnList = new ArrayList<FinanceAcctcodeGroup>();
        List<String> tempList = depts.stream().map(FinanceAcctcodeGroup::getGroupAcctCode).collect(Collectors.toList());
        for (FinanceAcctcodeGroup dept : depts)
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
    private void recursionFn(List<FinanceAcctcodeGroup> list, FinanceAcctcodeGroup t)
    {
        // 得到子节点列表
        List<FinanceAcctcodeGroup> childList = getChildList(list, t);
        t.setChildren(childList);
        for (FinanceAcctcodeGroup tChild : childList)
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
    private boolean hasChild(List<FinanceAcctcodeGroup> list, FinanceAcctcodeGroup t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 得到子节点列表
     */
    private List<FinanceAcctcodeGroup> getChildList(List<FinanceAcctcodeGroup> list, FinanceAcctcodeGroup t)
    {
        List<FinanceAcctcodeGroup> tlist = new ArrayList<FinanceAcctcodeGroup>();
        Iterator<FinanceAcctcodeGroup> it = list.iterator();
        while (it.hasNext())
        {
            FinanceAcctcodeGroup n = (FinanceAcctcodeGroup) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentAcctCode().equals(t.getGroupAcctCode()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 通过会计科目编号查询集团级会计科目
     * @param acctCode
     * @return
     */
    @Override
    public List<FinanceAcctcodeGroup> selectCodeGroupList(String acctCode, String companyId) {
        acctCode = StringUtils.isEmpty(acctCode) ? "0" :acctCode;
        return financeAcctcodeGroupMapper.selectCodeGroupList(acctCode,companyId);
    }


    /**
     * 根据会计科目编号查询会计科目中文名称
     * @param groupAcctCode
     * @return
     */
    @Override
    public String selectAcctNameByCode(String groupAcctCode){
        String acctName = financeAcctcodeGroupMapper.selectAcctNameByCode(groupAcctCode);
        acctName = (!"".equals(acctName) && acctName !=null) ? acctName : "" ;
        return acctName;
    }

    /**
     * 集团会计科目弹窗查询方法
     * @param financeAcctcodeGroup
     * @return
     */
    @Override
    public List<FinanceAcctcodeGroup> selectFinanceAcctcodeGroupPopList(FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        return financeAcctcodeGroupMapper.selectFinanceAcctcodeGroupPopList(financeAcctcodeGroup);
    }
}
