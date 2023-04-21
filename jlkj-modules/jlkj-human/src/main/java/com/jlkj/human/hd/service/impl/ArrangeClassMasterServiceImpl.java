package com.jlkj.human.hd.service.impl;

import java.util.List;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.ArrangeClass;
import com.jlkj.human.hd.service.IArrangeClassService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hd.mapper.ArrangeClassMasterMapper;
import com.jlkj.human.hd.domain.ArrangeClassMaster;
import com.jlkj.human.hd.service.IArrangeClassMasterService;

/**
 * 排班记录Service业务层处理
 *
 * @author 266861
 * @date 2023-04-10
 */
@Service
public class ArrangeClassMasterServiceImpl implements IArrangeClassMasterService
{
    @Autowired
    private ArrangeClassMasterMapper arrangeClassMasterMapper;
    @Autowired
    private IArrangeClassService arrangeClassService;

    /**
     * 查询排班记录
     *
     * @param id 排班记录主键
     * @return 排班记录
     */
    @Override
    public ArrangeClassMaster selectArrangeClassMasterById(String id)
    {
        return arrangeClassMasterMapper.selectArrangeClassMasterById(id);
    }

    /**
     * 查询排班记录列表
     *
     * @param arrangeClassMaster 排班记录
     * @return 排班记录
     */
    @Override
    public List<ArrangeClassMaster> selectArrangeClassMasterList(ArrangeClassMaster arrangeClassMaster)
    {
        return arrangeClassMasterMapper.selectArrangeClassMasterList(arrangeClassMaster);
    }

    /**
     * 新增排班记录
     *
     * @param arrangeClassMaster 排班记录
     * @return 结果
     */
    @Override
    public int insertArrangeClassMaster(ArrangeClassMaster arrangeClassMaster)
    {   arrangeClassMaster.setId(UUID.randomUUID().toString().substring(0, 32));
        for(ArrangeClass arrangeClass: arrangeClassMaster.getDetailList()){
            BeanUtils.copyProperties(arrangeClassMaster,arrangeClass);
            arrangeClass.setArrangeClassMasterId(arrangeClassMaster.getId());
            arrangeClass.setId(null);
            arrangeClassService.insertArrangeClass(arrangeClass);
        }
        return arrangeClassMasterMapper.insertArrangeClassMaster(arrangeClassMaster);
    }

    /**
     * 修改排班记录
     *
     * @param arrangeClassMaster 排班记录
     * @return 结果
     */
    @Override
    public int updateArrangeClassMaster(ArrangeClassMaster arrangeClassMaster)
    {
        return arrangeClassMasterMapper.updateArrangeClassMaster(arrangeClassMaster);
    }

    /**
     * 批量删除排班记录
     *
     * @param ids 需要删除的排班记录主键
     * @return 结果
     */
    @Override
    public int deleteArrangeClassMasterByIds(String[] ids)
    {
        return arrangeClassMasterMapper.deleteArrangeClassMasterByIds(ids);
    }

    /**
     * 删除排班记录信息
     *
     * @param id 排班记录主键
     * @return 结果
     */
    @Override
    public int deleteArrangeClassMasterById(String id)
    {
        return arrangeClassMasterMapper.deleteArrangeClassMasterById(id);
    }
}
