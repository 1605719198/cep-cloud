package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.PersonColock;

import java.util.List;

/**
 * 人员卡钟Mapper接口
 *
 * @author 266861
 * @date 2023-03-29
 */
public interface PersonColockMapper
{
    /**
     * 查询人员卡钟
     *
     * @param id 人员卡钟主键
     * @return 人员卡钟
     */
    public PersonColock selectPersonColockById(String id);

    /**
     * 查询最新生效卡钟
     *
     * @param personColock 人员卡钟
     * @return 人员卡钟
     */
    public PersonColock queryLastEffectData(PersonColock personColock);

    /**
     * 查询人员卡钟列表
     *
     * @param personColock 人员卡钟
     * @return 人员卡钟集合
     */
    public List<PersonColock> selectPersonColockList(PersonColock personColock);

    /**
     * 新增人员卡钟
     *
     * @param personColock 人员卡钟
     * @return 结果
     */
    public int insertPersonColock(PersonColock personColock);

    /**
     * 修改人员卡钟
     *
     * @param personColock 人员卡钟
     * @return 结果
     */
    public int updatePersonColock(PersonColock personColock);

    /**
     * 删除人员卡钟
     *
     * @param id 人员卡钟主键
     * @return 结果
     */
    public int deletePersonColockById(String id);

    /**
     * 批量删除人员卡钟
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonColockByIds(String[] ids);
}
