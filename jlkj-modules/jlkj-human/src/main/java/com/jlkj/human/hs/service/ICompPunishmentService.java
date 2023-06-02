package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.CompPunishment;
import com.jlkj.human.hs.dto.CompPunishmentDTO;

import java.util.List;

/**
 * 各公司专业奖罚明细项目维护Service接口
 * 
 * @author 266861
 * @date 2023-05-25
 */
public interface ICompPunishmentService 
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
     * 保存各公司专业奖罚明细项目维护
     *
     * @param compPunishmentList 各公司专业奖罚明细项目维护
     * @return 结果
     */
    public int saveCompPunishment(List<CompPunishment> compPunishmentList);

    /**
     * 修改各公司专业奖罚明细项目维护
     * 
     * @param compPunishment 各公司专业奖罚明细项目维护
     * @return 结果
     */
    public int updateCompPunishment(CompPunishment compPunishment);

    /**
     * 批量删除各公司专业奖罚明细项目维护
     * 
     * @param uuids 需要删除的各公司专业奖罚明细项目维护主键集合
     * @return 结果
     */
    public int deleteCompPunishmentByUuids(String[] uuids);

    /**
     * 删除各公司专业奖罚明细项目维护信息
     * 
     * @param uuid 各公司专业奖罚明细项目维护主键
     * @return 结果
     */
    public int deleteCompPunishmentByUuid(String uuid);
}
