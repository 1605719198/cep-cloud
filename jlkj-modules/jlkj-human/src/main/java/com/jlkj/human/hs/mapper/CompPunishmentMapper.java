package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.CompPunishment;
import com.jlkj.human.hs.dto.CompPunishmentDTO;

import java.util.List;

/**
 * 各公司专业奖罚明细项目维护Mapper接口
 * 
 * @author 266861
 * @date 2023-05-25
 */
public interface CompPunishmentMapper 
{
    /**
     * 查询各公司专业奖罚明细项目维护
     * 
     * @param uuid 各公司专业奖罚明细项目维护主键
     * @return 各公司专业奖罚明细项目维护
     */
    public CompPunishment selectCompPunishmentByUuid(String uuid);

    /**
     * 查询各公司专业奖罚明细项目维护列表
     * 
     * @param compPunishment 各公司专业奖罚明细项目维护
     * @return 各公司专业奖罚明细项目维护集合
     */
    public List<CompPunishmentDTO> selectCompPunishmentList(CompPunishment compPunishment);

    /**
     * 新增各公司专业奖罚明细项目维护
     * 
     * @param compPunishment 各公司专业奖罚明细项目维护
     * @return 结果
     */
    public int insertCompPunishment(CompPunishment compPunishment);

    /**
     * 修改各公司专业奖罚明细项目维护
     * 
     * @param compPunishment 各公司专业奖罚明细项目维护
     * @return 结果
     */
    public int updateCompPunishment(CompPunishment compPunishment);

    /**
     * 删除各公司专业奖罚明细项目维护
     * 
     * @param uuid 各公司专业奖罚明细项目维护主键
     * @return 结果
     */
    public int deleteCompPunishmentByUuid(String uuid);

    /**
     * 删除各公司专业奖罚明细项目维护
     *
     * @param compId 各公司专业奖罚明细项目公司别
     * @return 结果
     */
    public int deleteCompPunishmentByCompId(String compId);

    /**
     * 批量删除各公司专业奖罚明细项目维护
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompPunishmentByUuids(String[] uuids);
}
