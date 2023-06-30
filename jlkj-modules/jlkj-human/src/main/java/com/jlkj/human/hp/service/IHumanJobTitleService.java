package com.jlkj.human.hp.service;

import com.jlkj.human.hp.domain.HumanJobTitle;

import java.util.List;

/**
 * 职位名称数据维护Service接口
 * 
 * @author 266861
 * @date 2023-03-13
 */
public interface IHumanJobTitleService
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
     * @param compId 公司别
     * @return 职位名称数据维护
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
     * 新增职位名称数据维护
     * 
     * @param humanJobTitle 职位名称数据维护
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int insertHumanJobTitle (HumanJobTitle humanJobTitle)throws Exception;

    /**
     * 修改职位名称数据维护
     * 
     * @param humanJobTitle 职位名称数据维护
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int updateHumanJobTitle(HumanJobTitle humanJobTitle) throws Exception;

    /**
     * 批量删除职位名称数据维护
     * 
     * @param ids 需要删除的职位名称数据维护主键集合
     * @return 结果
     */
    public int deleteHumanJobTitleByIds(String[] ids);

    /**
     * 删除职位名称数据维护信息
     * 
     * @param id 职位名称数据维护主键
     * @return 结果
     */
    public int deleteHumanJobTitleById(String id);
}
