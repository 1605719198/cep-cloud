package com.jlkj.human.hp.service;

import com.jlkj.human.hp.domain.HumanHpJobTitle;

import java.util.List;

/**
 * 职位名称数据维护Service接口
 * 
 * @author 266861
 * @date 2023-03-13
 */
public interface IHumanHpJobTitleService 
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
     * @param compId 公司别
     * @return 职位名称数据维护
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
     * 新增职位名称数据维护
     * 
     * @param humanHpJobTitle 职位名称数据维护
     * @return 结果
     */
    public int insertHumanHpJobTitle (HumanHpJobTitle humanHpJobTitle)throws Exception;

    /**
     * 修改职位名称数据维护
     * 
     * @param humanHpJobTitle 职位名称数据维护
     * @return 结果
     */
    public int updateHumanHpJobTitle(HumanHpJobTitle humanHpJobTitle) throws Exception;

    /**
     * 批量删除职位名称数据维护
     * 
     * @param ids 需要删除的职位名称数据维护主键集合
     * @return 结果
     */
    public int deleteHumanHpJobTitleByIds(String[] ids);

    /**
     * 删除职位名称数据维护信息
     * 
     * @param id 职位名称数据维护主键
     * @return 结果
     */
    public int deleteHumanHpJobTitleById(String id);
}
