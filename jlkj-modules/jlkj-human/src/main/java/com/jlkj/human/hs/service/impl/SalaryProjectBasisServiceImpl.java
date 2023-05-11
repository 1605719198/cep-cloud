package com.jlkj.human.hs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.hs.domain.SalaryProjectBasis;
import com.jlkj.human.hs.mapper.SalaryProjectBasisMapper;
import com.jlkj.human.hs.service.ISalaryProjectBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 集团级薪资项目输入维护Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-05-08
 */
@Service
public class SalaryProjectBasisServiceImpl extends ServiceImpl<SalaryProjectBasisMapper, SalaryProjectBasis>
    implements ISalaryProjectBasisService
            {
    @Autowired
    private SalaryProjectBasisMapper salaryProjectBasisMapper;

    /**
     * 查询集团级薪资项目输入维护
     *
     * @param id 集团级薪资项目输入维护主键
     * @return 集团级薪资项目输入维护
     */
    @Override
    public SalaryProjectBasis selectSalaryProjectBasisById(Long id)
    {
        return salaryProjectBasisMapper.selectSalaryProjectBasisById(id);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param salaryProjectBasiss 列表
     * @return 树结构列表
     */
    @Override
    public List<SalaryProjectBasis> buildSalaryProjectTree(List<SalaryProjectBasis> salaryProjectBasiss)
    {
        List<SalaryProjectBasis> returnList = new ArrayList<SalaryProjectBasis>();
        List<Long> tempList = salaryProjectBasiss.stream().map(SalaryProjectBasis::getId).collect(Collectors.toList());
        for (SalaryProjectBasis salaryProjectBasis : salaryProjectBasiss)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(salaryProjectBasis.getParentid()))
            {
                recursionFn(salaryProjectBasiss, salaryProjectBasis);
                returnList.add(salaryProjectBasis);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = salaryProjectBasiss;
        }
        return returnList;
    }
    /**
     * 构建前端所需要下拉树结构
     *
     * @param salaryProjectBasiss 列表
     * @return 下拉树结构列表
     */
    @Override

    public List<TreeSelect> buildSalaryProjectTreeSelect(List<SalaryProjectBasis> salaryProjectBasiss)
    {
        List<SalaryProjectBasis> salaryProjectTrees = buildSalaryProjectTree(salaryProjectBasiss);
        return salaryProjectTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<SalaryProjectBasis> list, SalaryProjectBasis t)
    {
        // 得到子节点列表
        List<SalaryProjectBasis> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SalaryProjectBasis tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<SalaryProjectBasis> getChildList(List<SalaryProjectBasis> list, SalaryProjectBasis t)
    {
        List<SalaryProjectBasis> tlist = new ArrayList<SalaryProjectBasis>();
        Iterator<SalaryProjectBasis> it = list.iterator();
        while (it.hasNext())
        {
            SalaryProjectBasis n = (SalaryProjectBasis) it.next();
            if (StringUtils.isNotNull(n.getParentid()) && n.getParentid().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SalaryProjectBasis> list, SalaryProjectBasis t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 查询集团级薪资项目输入维护列表
     *
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 集团级薪资项目输入维护
     */
    @Override
    public List<SalaryProjectBasis> selectSalaryProjectBasisList(SalaryProjectBasis salaryProjectBasis)
    {
        return salaryProjectBasisMapper.selectSalaryProjectBasisList(salaryProjectBasis);
    }

    /**
     * 新增集团级薪资项目输入维护
     *
     * @param salaryProjectBasisList 集团级薪资项目输入维护
     * @return 结果
     */
    @Override
    public Object insertSalaryProjectBasis(List<SalaryProjectBasis> salaryProjectBasisList)
    {
       return saveOrUpdateBatch(salaryProjectBasisList);
    }

    /**
     * 修改集团级薪资项目输入维护
     *
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 结果
     */
    @Override
    public int updateSalaryProjectBasis(SalaryProjectBasis salaryProjectBasis)
    {
        return salaryProjectBasisMapper.updateSalaryProjectBasis(salaryProjectBasis);
    }

    /**
     * 批量删除集团级薪资项目输入维护
     *
     * @param ids 需要删除的集团级薪资项目输入维护主键
     * @return 结果
     */
    @Override
    public int deleteSalaryProjectBasisByIds(Long[] ids)
    {
        return salaryProjectBasisMapper.deleteSalaryProjectBasisByIds(ids);
    }

    /**
     * 删除集团级薪资项目输入维护信息
     *
     * @param id 集团级薪资项目输入维护主键
     * @return 结果
     */
    @Override
    public int deleteSalaryProjectBasisById(Long id)
    {
        return salaryProjectBasisMapper.deleteSalaryProjectBasisById(id);
    }
}
