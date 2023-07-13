package com.jlkj.human.ex.service.impl;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.ex.domain.ExamType;
import com.jlkj.human.ex.domain.TypeTreeSelect;
import com.jlkj.human.ex.mapper.ExamTypeMapper;
import com.jlkj.human.ex.service.IExamTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 考试分类Service业务层处理
 * 
 * @author qnsdt
 * @Date:  2022-12-14
 */
@Service
public class ExamTypeServiceImpl implements IExamTypeService
{
    @Autowired
    private ExamTypeMapper examTypeMapper;

    /**
     * 查询考试分类
     * 
     * @param typeId 考试分类ID
     * @return 考试分类
     */
    @Override
    public ExamType selectExamTypeById(Long typeId)
    {
        return examTypeMapper.selectExamTypeById(typeId);
    }

    /**
     * 查询考试分类列表
     * 
     * @param examType 考试分类
     * @return 考试分类
     */
    @Override
    public List<ExamType> selectExamTypeList(ExamType examType)
    {
        return examTypeMapper.selectExamTypeList(examType);
    }

    /**
     * 新增考试分类
     * 
     * @param examType 考试分类
     * @return 结果
     */
    @Override
    public int insertExamType(ExamType examType)
    {
        return examTypeMapper.insertExamType(examType);
    }

    /**
     * 修改考试分类
     * 
     * @param examType 考试分类
     * @return 结果
     */
    @Override
    public int updateExamType(ExamType examType)
    {
        return examTypeMapper.updateExamType(examType);
    }

    /**
     * 批量删除考试分类
     * 
     * @param typeIds 需要删除的考试分类ID
     * @return 结果
     */
    @Override
    public int deleteExamTypeByIds(Long[] typeIds)
    {
        return examTypeMapper.deleteExamTypeByIds(typeIds);
    }

    /**
     * 删除考试分类信息
     * 
     * @param typeId 考试分类ID
     * @return 结果
     */
    @Override
    public int deleteExamTypeById(Long typeId)
    {
        return examTypeMapper.deleteExamTypeById(typeId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ExamType> list, ExamType t)
    {
        // 得到子节点列表
        List<ExamType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ExamType tChild : childList)
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
    private List<ExamType> getChildList(List<ExamType> list, ExamType t)
    {
        List<ExamType> tlist = new ArrayList<ExamType>();
        Iterator<ExamType> it = list.iterator();
        while (it.hasNext())
        {
        	ExamType n = (ExamType) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getTypeId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ExamType> list, ExamType t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
    
    
	@Override
	public List<ExamType> buildTypeTree(List<ExamType> examTypeList) {
        List<ExamType> returnList = new ArrayList<ExamType>();
        List<Long> tempList = new ArrayList<Long>();
        for (ExamType examtype : examTypeList)
        {
            tempList.add(examtype.getTypeId());
        }
        for (Iterator<ExamType> iterator = examTypeList.iterator(); iterator.hasNext();)
        {
        	ExamType examtype = (ExamType) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(examtype.getParentId()))
            {
                recursionFn(examTypeList, examtype);
                returnList.add(examtype);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = examTypeList;
        }
        return returnList;
	}

	@Override
	public List<TypeTreeSelect> buildTypeTreeSelect(List<ExamType> examTypeList) {
        List<ExamType> typeTrees = buildTypeTree(examTypeList);
        return typeTrees.stream().map(TypeTreeSelect::new).collect(Collectors.toList());
	}
	
	
}
