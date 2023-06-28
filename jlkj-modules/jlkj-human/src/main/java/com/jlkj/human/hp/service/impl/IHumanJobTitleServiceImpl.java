package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hp.domain.HumanJobTitle;
import com.jlkj.human.hp.mapper.HumanJobTitleMapper;
import com.jlkj.human.hp.service.IHumanJobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 职位名称数据维护Service业务层处理
 *
 * @author 266861
 * @date 2023-03-13
 */
@Service
public class IHumanJobTitleServiceImpl implements IHumanJobTitleService
{
    @Autowired
    private HumanJobTitleMapper humanJobTitleMapper;

    /**
     * 查询职位名称数据维护
     *
     * @param id 职位名称数据维护主键
     * @return 职位名称数据维护
     */
    @Override
    public HumanJobTitle selectHumanJobTitleById(String id)
    {
        return humanJobTitleMapper.selectHumanJobTitleById(id);
    }

    /**
     * 查询职位名称数据维护
     *
     * @param compId 职位名称数据维护主键
     * @return 职位名称数据维护
     */
    @Override
    public List<HumanJobTitle> selectHumanJobTitleidname(String compId)
    {
        return humanJobTitleMapper.selectHumanJobTitleidname(compId);
    }

    /**
     * 查询职位名称数据维护列表
     *
     * @param humanJobTitle 职位名称数据维护
     * @return 职位名称数据维护
     */
    @Override
    public List<HumanJobTitle> selectHumanJobTitleList(HumanJobTitle humanJobTitle)
    {
        return humanJobTitleMapper.selectHumanJobTitleList(humanJobTitle);
    }

    /**
     * 新增职位名称数据维护
     *
     * @param humanJobTitle 职位名称数据维护
     * @return 结果
     */
    @Override
    public int insertHumanJobTitle(HumanJobTitle humanJobTitle) throws Exception
    {
        humanJobTitle.setId(IdUtils.simpleUUID());
        List oldHumanJobTitle = humanJobTitleMapper.selectHumanJobTitleByNoName(humanJobTitle);
        if(!oldHumanJobTitle.isEmpty()){
            throw new Exception("职称代号或职称中文说明已存在，请重新输入");
        }
        return humanJobTitleMapper.insertHumanJobTitle(humanJobTitle);
    }

    /**
     * 修改职位名称数据维护
     *
     * @param humanJobTitle 职位名称数据维护
     * @return 结果
     */
    @Override
    public int updateHumanJobTitle(HumanJobTitle humanJobTitle) throws Exception
    {
        return humanJobTitleMapper.updateHumanJobTitle(humanJobTitle);
    }

    /**
     * 批量删除职位名称数据维护
     *
     * @param ids 需要删除的职位名称数据维护主键
     * @return 结果
     */
    @Override
    public int deleteHumanJobTitleByIds(String[] ids)
    {
        return humanJobTitleMapper.deleteHumanJobTitleByIds(ids);
    }

    /**
     * 删除职位名称数据维护信息
     *
     * @param id 职位名称数据维护主键
     * @return 结果
     */
    @Override
    public int deleteHumanJobTitleById(String id)
    {
        return humanJobTitleMapper.deleteHumanJobTitleById(id);
    }
}
