package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.ArrangeClass;
import com.jlkj.human.hd.mapper.ArrangeClassMapper;
import com.jlkj.human.hd.service.IArrangeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 排班明细Service业务层处理
 *
 * @author 266861
 * @date 2023-04-10
 */
@Service
public class ArrangeClassServiceImpl implements IArrangeClassService
{
    @Autowired
    private ArrangeClassMapper arrangeClassMapper;

    /**
     * 查询排班明细
     *
     * @param id 排班明细主键
     * @return 排班明细
     */
    @Override
    public ArrangeClass selectArrangeClassById(String id)
    {
        return arrangeClassMapper.selectArrangeClassById(id);
    }

    /**
     * 查询排班明细
     *
     * @param arrangeClass 排班明细
     * @return 排班明细
     */
    @Override
    public ArrangeClass selectArrangeClassByClass(ArrangeClass arrangeClass)
    {
        return arrangeClassMapper.selectArrangeClassByClass(arrangeClass);
    }

    /**
     * 查询排班明细列表（页面展示）
     *
     * @param arrangeClass 排班明细
     * @return 排班明细
     */
    @Override
    public List<ArrangeClass> selectArrangeClassList(ArrangeClass arrangeClass)
    {
        return arrangeClassMapper.selectArrangeClassList(arrangeClass);
    }

    /**
     * 查询排班明细列表（新增比较）
     *
     * @param arrangeClass 排班明细
     * @return 排班明细
     */
    @Override
    public List<ArrangeClass> quertArrangeClass(ArrangeClass arrangeClass)
    {
        return arrangeClassMapper.selectArrangeClassList(arrangeClass);
    }

    /**
     * 新增排班明细
     *
     * @param arrangeClass 排班明细
     * @return 结果
     */
    @Override
    public int insertArrangeClass(ArrangeClass arrangeClass)
    {
        List<ArrangeClass> list = arrangeClassMapper.quertArrangeClass(arrangeClass);
        if(list.size()==0){
            arrangeClass.setId(UUID.randomUUID().toString().substring(0, 32));
            return arrangeClassMapper.insertArrangeClass(arrangeClass);
        }else{
            arrangeClass.setId(list.get(0).getId());
            return arrangeClassMapper.updateArrangeClass(arrangeClass);
        }
    }

    /**
     * 修改排班明细
     *
     * @param arrangeClass 排班明细
     * @return 结果
     */
    @Override
    public int updateArrangeClass(ArrangeClass arrangeClass)
    {
        return arrangeClassMapper.updateArrangeClass(arrangeClass);
    }

    /**
     * 批量删除排班明细
     *
     * @param ids 需要删除的排班明细主键
     * @return 结果
     */
    @Override
    public int deleteArrangeClassByIds(String[] ids)
    {
        return arrangeClassMapper.deleteArrangeClassByIds(ids);
    }

    /**
     * 删除排班明细信息
     *
     * @param id 排班明细主键
     * @return 结果
     */
    @Override
    public int deleteArrangeClassById(String id)
    {
        return arrangeClassMapper.deleteArrangeClassById(id);
    }
}
