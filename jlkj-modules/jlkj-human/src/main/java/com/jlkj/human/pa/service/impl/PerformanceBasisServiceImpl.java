package com.jlkj.human.pa.service.impl;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.pa.domain.PerformanceBasis;
import com.jlkj.human.pa.mapper.PerformanceBasisMapper;
import com.jlkj.human.pa.service.IPerformanceBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 绩效管理基本资料维护Service业务层处理
 *
 * @author 116519
 * @date 2023-05-17
 */
@Service
public class PerformanceBasisServiceImpl implements IPerformanceBasisService
{
    @Autowired
    private PerformanceBasisMapper performanceBasisMapper;

    /**
     * 查询绩效管理基本资料维护
     *
     * @param id 绩效管理基本资料维护主键
     * @return 绩效管理基本资料维护
     */
    @Override
    public PerformanceBasis selectPerformanceBasisById(Long id)
    {
        return performanceBasisMapper.selectPerformanceBasisById(id);
    }

    /**
     * 查询绩效管理基本资料维护列表
     *
     * @param performanceBasis 绩效管理基本资料维护
     * @return 绩效管理基本资料维护
     */
    @Override
    public List<PerformanceBasis> selectPerformanceBasisList(PerformanceBasis performanceBasis)
    {
        return performanceBasisMapper.selectPerformanceBasisList(performanceBasis);
    }

    /**
     * 新增绩效管理基本资料维护
     *
     * @param performanceBasis 绩效管理基本资料维护
     * @return 结果
     */
    @Override
    public int insertPerformanceBasis(PerformanceBasis performanceBasis)
    {
        return performanceBasisMapper.insertPerformanceBasis(performanceBasis);
    }

    /**
     * 修改绩效管理基本资料维护
     *
     * @param performanceBasis 绩效管理基本资料维护
     * @return 结果
     */
    @Override
    public int updatePerformanceBasis(PerformanceBasis performanceBasis)  throws Exception
    {
        if(performanceBasis.getParentid()==1) {
            int i = performanceBasisMapper.queryRepetitivedata(performanceBasis);
            if (i > 0) {
                throw new Exception("资料编码已存在，请重新输入");
            }
        }

        return performanceBasisMapper.updatePerformanceBasis(performanceBasis);
    }

    /**
     * 批量删除绩效管理基本资料维护
     *
     * @param ids 需要删除的绩效管理基本资料维护主键
     * @return 结果
     */
    @Override
    public int deletePerformanceBasisByIds(Long[] ids)
    {
        return performanceBasisMapper.deletePerformanceBasisByIds(ids);
    }

    /**
     * 删除绩效管理基本资料维护信息
     *
     * @param id 绩效管理基本资料维护主键
     * @return 结果
     */
    @Override
    public int deletePerformanceBasisById(Long id)  throws Exception
    {
        List oldPerformanceBasis = performanceBasisMapper.selectAttendencebasisByParentid(id);
        if(!oldPerformanceBasis.isEmpty()){
            throw new Exception("该资料下存在子节点，不可删除");
        }

        return performanceBasisMapper.deletePerformanceBasisById(id);
    }


    /**
     * 查询绩效基本资料维护选单
     *
     * @param code 员工出勤基本资料维护编码
     * @return 结果
     */
    @Override
    public List<BasisOptionsDTO> selectBasisOptions(String code) {
        Long parentid = performanceBasisMapper.selectAttendencebasisParentByCode(code).getId();
        List<BasisOptionsDTO> list = performanceBasisMapper.selectAttendencebasisByParentid(parentid);
        return list;
    }


    /**
     * 构建前端所需要下拉树结构
     *
     * @param performanceBasiss 部门列表
     * @return 下拉树结构列表
     */
    @Override

    public List<TreeSelect> buildAttendenceTreeSelect(List<PerformanceBasis> performanceBasiss)
    {
        List<PerformanceBasis> attendenceTrees = buildAttendenceTree(performanceBasiss);
        return attendenceTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    /**
     * 构建前端所需要树结构
     *
     * @param performancebasiss 部门列表
     * @return 树结构列表
     */
    @Override
    public List<PerformanceBasis> buildAttendenceTree(List<PerformanceBasis> performancebasiss)
    {
        List<PerformanceBasis> returnList = new ArrayList<PerformanceBasis>();
        List<Long> tempList = performancebasiss.stream().map(PerformanceBasis::getId).collect(Collectors.toList());
        for (PerformanceBasis performanceBasis : performancebasiss)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(performanceBasis.getParentid()))
            {
                recursionFn(performancebasiss, performanceBasis);
                returnList.add(performanceBasis);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = performancebasiss;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<PerformanceBasis> list, PerformanceBasis t)
    {
        // 得到子节点列表
        List<PerformanceBasis> childList = getChildList(list, t);
        t.setChildren(childList);
        for (PerformanceBasis tChild : childList)
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
    private List<PerformanceBasis> getChildList(List<PerformanceBasis> list, PerformanceBasis t)
    {
        List<PerformanceBasis> tlist = new ArrayList<PerformanceBasis>();
        Iterator<PerformanceBasis> it = list.iterator();
        while (it.hasNext())
        {
            PerformanceBasis n = (PerformanceBasis) it.next();
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
    private boolean hasChild(List<PerformanceBasis> list, PerformanceBasis t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
