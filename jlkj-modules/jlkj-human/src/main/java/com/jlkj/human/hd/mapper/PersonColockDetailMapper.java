package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.PersonColockDetail;
import com.jlkj.human.hd.dto.ClockworkPersonDTO;

import java.util.List;

/**
 * 人员卡钟明细Mapper接口
 *
 * @author 266861
 * @date 2023-03-29
 */
public interface PersonColockDetailMapper
{
    /**
     * 查询人员卡钟明细
     *
     * @param id 人员卡钟明细主键
     * @return 人员卡钟明细
     */
    public PersonColockDetail selectPersonColockDetailById(String id);

    /**
     * 通过卡钟编号查询人员
     *
     * @param macId 人员卡钟编号
     * @return 人员卡钟明细
     */
    public List<ClockworkPersonDTO> queryPersonByColock(String macId);

    /**
     * 查询人员卡钟明细列表
     *
     * @param personColockDetail 人员卡钟明细
     * @return 人员卡钟明细集合
     */
    public List<PersonColockDetail> selectPersonColockDetailList(PersonColockDetail personColockDetail);

    /**
     * 新增人员卡钟明细
     *
     * @param personColockDetail 人员卡钟明细
     * @return 结果
     */
    public int insertPersonColockDetail(PersonColockDetail personColockDetail);

    /**
     * 修改人员卡钟明细
     *
     * @param personColockDetail 人员卡钟明细
     * @return 结果
     */
    public int updatePersonColockDetail(PersonColockDetail personColockDetail);

    /**
     * 删除人员卡钟明细
     *
     * @param id 人员卡钟明细主键
     * @return 结果
     */
    public int deletePersonColockDetailById(String id);

    /**
     * 删除人员卡钟
     *
     * @param personColockId 人员卡钟主键
     * @return 结果
     */
    public int deletePersonColockDetailByPersonColockId(String personColockId);

    /**
     * 批量删除人员卡钟明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonColockDetailByIds(String[] ids);
}
