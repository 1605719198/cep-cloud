package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hp.domain.HumanHpJobTitle;
import com.jlkj.human.hp.mapper.HumanHpJobTitleMapper;
import com.jlkj.human.hp.service.IHumanHpJobTitleService;
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
public class HumanHpJobTitleServiceImpl implements IHumanHpJobTitleService
{
    @Autowired
    private HumanHpJobTitleMapper humanHpJobTitleMapper;

    /**
     * 查询职位名称数据维护
     *
     * @param id 职位名称数据维护主键
     * @return 职位名称数据维护
     */
    @Override
    public HumanHpJobTitle selectHumanHpJobTitleById(String id)
    {
        return humanHpJobTitleMapper.selectHumanHpJobTitleById(id);
    }

    /**
     * 查询职位名称数据维护
     *
     * @param compId 职位名称数据维护主键
     * @return 职位名称数据维护
     */
    @Override
    public List<HumanHpJobTitle> selectHumanHpJobTitleidname(String compId)
    {
        return humanHpJobTitleMapper.selectHumanHpJobTitleidname(compId);
    }

    /**
     * 查询职位名称数据维护列表
     *
     * @param humanHpJobTitle 职位名称数据维护
     * @return 职位名称数据维护
     */
    @Override
    public List<HumanHpJobTitle> selectHumanHpJobTitleList(HumanHpJobTitle humanHpJobTitle)
    {
        return humanHpJobTitleMapper.selectHumanHpJobTitleList(humanHpJobTitle);
    }

    /**
     * 新增职位名称数据维护
     *
     * @param humanHpJobTitle 职位名称数据维护
     * @return 结果
     */
    @Override
    public int insertHumanHpJobTitle(HumanHpJobTitle humanHpJobTitle) throws Exception
    {
        humanHpJobTitle.setId(UUID.randomUUID().toString().substring(0, 32));
        List oldhumanHpJobTitle = humanHpJobTitleMapper.selectHumanHpJobTitleByNoName(humanHpJobTitle);
        if(!oldhumanHpJobTitle.isEmpty()){
            throw new Exception("职称代号或职称中文说明已存在，请重新输入");
        }
        return humanHpJobTitleMapper.insertHumanHpJobTitle(humanHpJobTitle);
    }

    /**
     * 修改职位名称数据维护
     *
     * @param humanHpJobTitle 职位名称数据维护
     * @return 结果
     */
    @Override
    public int updateHumanHpJobTitle(HumanHpJobTitle humanHpJobTitle) throws Exception
    {
        return humanHpJobTitleMapper.updateHumanHpJobTitle(humanHpJobTitle);
    }

    /**
     * 批量删除职位名称数据维护
     *
     * @param ids 需要删除的职位名称数据维护主键
     * @return 结果
     */
    @Override
    public int deleteHumanHpJobTitleByIds(String[] ids)
    {
        return humanHpJobTitleMapper.deleteHumanHpJobTitleByIds(ids);
    }

    /**
     * 删除职位名称数据维护信息
     *
     * @param id 职位名称数据维护主键
     * @return 结果
     */
    @Override
    public int deleteHumanHpJobTitleById(String id)
    {
        return humanHpJobTitleMapper.deleteHumanHpJobTitleById(id);
    }
}
