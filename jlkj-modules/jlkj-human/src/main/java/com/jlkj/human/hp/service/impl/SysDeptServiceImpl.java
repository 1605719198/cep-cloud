package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.domain.SysDeptVersion;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.hp.mapper.SysDeptMapper;
import com.jlkj.human.hp.mapper.SysDeptVersionMapper;
import com.jlkj.human.hp.service.ISysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 部门资料维护Service业务层处理
 *
 * @author jlkj
 * @date 2023-03-08
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDeptVersionMapper SysDeptVersionMapper;

    /**
     * 查询部门资料维护
     *
     * @param deptId 部门资料维护主键
     * @return 部门资料维护
     */
    @Override
    public SysDept selectSysDeptByDeptId(Long deptId)
    {
        return sysDeptMapper.selectSysDeptByDeptId(deptId);
    }
    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        for (SysDept dept : depts)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
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
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override

    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    /**
     * 查询部门资料维护列表
     *
     * @param sysDept 部门资料维护
     * @return 部门资料维护
     */
    @Override
    public List<SysDept> selectSysDeptList(SysDept sysDept)
    {
        return sysDeptMapper.selectSysDeptList(sysDept);
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return sysDeptMapper.selectDeptById(deptId);
    }


    /**
     * 新增部门资料维护
     *
     * @param sysDept 部门资料维护
     * @return 结果
     */
    @Override
    public int insertSysDept(SysDept sysDept)
    {
        SysDeptVersion sysDeptVersion = new SysDeptVersion();
        sysDept.setCreateTime(DateUtils.getNowDate());
        sysDept.setUpdateTime(DateUtils.getNowDate());
        SysDept info = sysDeptMapper.selectDeptById(sysDept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
//        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
//        {
//            throw new ServiceException("部门停用，不允许新增");
//        }
        sysDept.setAncestors(info.getAncestors() + "," + sysDept.getParentId());
        int insertOk=sysDeptMapper.insertSysDept(sysDept);
        if(insertOk>=1){
            BeanUtils.copyProperties(sysDept,sysDeptVersion);
            SysDeptVersionMapper.insertSysDeptVersion(sysDeptVersion);
        }
        return insertOk;
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 修改部门资料维护
     *
     * @param sysDept 部门资料维护
     * @return 结果
     */
    @Override
    public int updateSysDept(SysDept sysDept)
    {
        SysDeptVersion sysDeptVersion = new SysDeptVersion();
        sysDept.setUpdateTime(DateUtils.getNowDate());
        int updateOk=sysDeptMapper.updateSysDept(sysDept);
        if(updateOk==1){
            BeanUtils.copyProperties(sysDept,sysDeptVersion);
            SysDeptVersionMapper.updateisNew(sysDeptVersion.getDeptId());
            SysDeptVersionMapper.insertSysDeptVersion(sysDeptVersion);
        }
        return updateOk;
    }

    /**
     * 批量删除部门资料维护
     *
     * @param deptIds 需要删除的部门资料维护主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptByDeptIds(Long[] deptIds)
    {
        return sysDeptMapper.deleteSysDeptByDeptIds(deptIds);
    }

    /**
     * 删除部门资料维护信息
     *
     * @param deptId 部门资料维护主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptByDeptId(Long deptId)
    {
        return sysDeptMapper.deleteSysDeptByDeptId(deptId);
    }
    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
