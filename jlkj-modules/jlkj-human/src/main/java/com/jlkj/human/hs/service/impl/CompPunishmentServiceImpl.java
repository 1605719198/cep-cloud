package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.CompPunishment;
import com.jlkj.human.hs.dto.CompPunishmentDTO;
import com.jlkj.human.hs.mapper.CompPunishmentMapper;
import com.jlkj.human.hs.service.ICompPunishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 各公司专业奖罚明细项目维护Service业务层处理
 *
 * @author 266861
 * @date 2023-05-25
 */
@Service
public class CompPunishmentServiceImpl implements ICompPunishmentService
{
    @Autowired
    private CompPunishmentMapper compPunishmentMapper;

    /**
     * 查询各公司专业奖罚明细项目维护
     *
     * @param uuid 各公司专业奖罚明细项目维护主键
     * @return 各公司专业奖罚明细项目维护
     */
    @Override
    public CompPunishment selectCompPunishmentByUuid(String uuid)
    {
        return compPunishmentMapper.selectCompPunishmentByUuid(uuid);
    }

    /**
     * 查询各公司专业奖罚明细项目维护列表
     *
     * @param compPunishment 各公司专业奖罚明细项目维护
     * @return 各公司专业奖罚明细项目维护
     */
    @Override
    public List<CompPunishmentDTO> selectCompPunishmentList(CompPunishment compPunishment)
    {
        return compPunishmentMapper.selectCompPunishmentList(compPunishment);
    }

    /**
     * 新增各公司专业奖罚明细项目维护
     *
     * @param compPunishment 各公司专业奖罚明细项目维护
     * @return 结果
     */
    @Override
    public int insertCompPunishment(CompPunishment compPunishment)
    {
        return compPunishmentMapper.insertCompPunishment(compPunishment);
    }

    /**
     * 保存各公司专业奖罚明细项目维护
     *
     * @param compPunishmentList 各公司专业奖罚明细项目维护
     * @return 结果
     */
    @Override
    public int saveCompPunishment(List<CompPunishment> compPunishmentList){
        String compId = compPunishmentList.get(0).getCompId();
        compPunishmentMapper.deleteCompPunishmentByCompId(compId);
        int number=0;
        for(CompPunishment compPunishment :compPunishmentList){
            compPunishment.setCreatorId(SecurityUtils.getUserId().toString());
            compPunishment.setCreatorNo(SecurityUtils.getUsername());
            compPunishment.setCreator(SecurityUtils.getNickName());
            compPunishment.setCreateDate(new Date());
            compPunishment.setUuid(IdUtils.simpleUUID());
            compPunishmentMapper.insertCompPunishment(compPunishment);
            number++;
        }
        return number;
    }

    /**
     * 修改各公司专业奖罚明细项目维护
     *
     * @param compPunishment 各公司专业奖罚明细项目维护
     * @return 结果
     */
    @Override
    public int updateCompPunishment(CompPunishment compPunishment)
    {
        return compPunishmentMapper.updateCompPunishment(compPunishment);
    }

    /**
     * 批量删除各公司专业奖罚明细项目维护
     *
     * @param uuids 需要删除的各公司专业奖罚明细项目维护主键
     * @return 结果
     */
    @Override
    public int deleteCompPunishmentByUuids(String[] uuids)
    {
        return compPunishmentMapper.deleteCompPunishmentByUuids(uuids);
    }

    /**
     * 删除各公司专业奖罚明细项目维护信息
     *
     * @param uuid 各公司专业奖罚明细项目维护主键
     * @return 结果
     */
    @Override
    public int deleteCompPunishmentByUuid(String uuid)
    {
        return compPunishmentMapper.deleteCompPunishmentByUuid(uuid);
    }
}
