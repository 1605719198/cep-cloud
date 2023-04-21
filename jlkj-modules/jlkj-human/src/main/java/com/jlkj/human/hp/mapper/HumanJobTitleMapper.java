package com.jlkj.human.hp.mapper;

import com.jlkj.human.hp.domain.HumanJobTitle;

import java.util.List;

/**
 * 职位名称数据维护Mapper接口
 * 
 * @author 266861
 * @date 2023-03-13
 */
public interface HumanJobTitleMapper
{
    /**
     * 查询职位名称数据维护
     * 
     * @param id 职位名称数据维护主键
     * @return 职位名称数据维护
     */
    public HumanJobTitle selectHumanJobTitleById(String id);
    /**
     * 查询职位名称数据维护
     *
     * @param compId 公司Id
     * @return 职位名称数据维护列表
     */
    public List<HumanJobTitle> selectHumanJobTitleidname(String compId);


    /**
     * 查询职位名称数据维护列表
     * 
     * @param humanJobTitle 职位名称数据维护
     * @return 职位名称数据维护集合
     */
    public List<HumanJobTitle> selectHumanJobTitleList(HumanJobTitle humanJobTitle);

    /**
     * 通过职位中文名或编码查数据
     *
     * @param humanJobTitle 职位名称数据维护
     * @return 职位名称数据维护集合
     */
    public List<HumanJobTitle> selectHumanJobTitleByNoName(HumanJobTitle humanJobTitle);

    /**
     * 新增职位名称数据维护
     * 
     * @param humanJobTitle 职位名称数据维护
     * @return 结果
     */
    public int insertHumanJobTitle(HumanJobTitle humanJobTitle);

    /**
     * 修改职位名称数据维护
     * 
     * @param humanJobTitle 职位名称数据维护
     * @return 结果
     */
    public int updateHumanJobTitle(HumanJobTitle humanJobTitle);

    /**
     * 删除职位名称数据维护
     * 
     * @param id 职位名称数据维护主键
     * @return 结果
     */
    public int deleteHumanJobTitleById(String id);

    /**
     * 批量删除职位名称数据维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHumanJobTitleByIds(String[] ids);
}
