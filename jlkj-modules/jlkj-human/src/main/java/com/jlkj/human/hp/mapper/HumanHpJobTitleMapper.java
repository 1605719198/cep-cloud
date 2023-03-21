package com.jlkj.human.hp.mapper;

import com.jlkj.human.hp.domain.HumanHpJobTitle;

import java.util.List;

/**
 * 职位名称数据维护Mapper接口
 * 
 * @author 266861
 * @date 2023-03-13
 */
public interface HumanHpJobTitleMapper 
{
    /**
     * 查询职位名称数据维护
     * 
     * @param id 职位名称数据维护主键
     * @return 职位名称数据维护
     */
    public HumanHpJobTitle selectHumanHpJobTitleById(String id);
    /**
     * 查询职位名称数据维护
     *
     * @param compId 公司Id
     * @return 职位名称数据维护列表
     */
    public List<HumanHpJobTitle> selectHumanHpJobTitleidname(String compId);


    /**
     * 查询职位名称数据维护列表
     * 
     * @param humanHpJobTitle 职位名称数据维护
     * @return 职位名称数据维护集合
     */
    public List<HumanHpJobTitle> selectHumanHpJobTitleList(HumanHpJobTitle humanHpJobTitle);

    /**
     * 通过职位中文名或编码查数据
     *
     * @param humanHpJobTitle 职位名称数据维护
     * @return 职位名称数据维护集合
     */
    public List<HumanHpJobTitle> selectHumanHpJobTitleByNoName(HumanHpJobTitle humanHpJobTitle);

    /**
     * 新增职位名称数据维护
     * 
     * @param humanHpJobTitle 职位名称数据维护
     * @return 结果
     */
    public int insertHumanHpJobTitle(HumanHpJobTitle humanHpJobTitle);

    /**
     * 修改职位名称数据维护
     * 
     * @param humanHpJobTitle 职位名称数据维护
     * @return 结果
     */
    public int updateHumanHpJobTitle(HumanHpJobTitle humanHpJobTitle);

    /**
     * 删除职位名称数据维护
     * 
     * @param id 职位名称数据维护主键
     * @return 结果
     */
    public int deleteHumanHpJobTitleById(String id);

    /**
     * 批量删除职位名称数据维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHumanHpJobTitleByIds(String[] ids);
}
