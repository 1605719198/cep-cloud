package com.jlkj.human.hm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.human.hm.PersonnelDTO;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;

import java.util.List;

/**
 * <p>
 * 人员数据 服务类
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
public interface IPersonnelService extends IService<Personnel> {

    /**
     * 新增人员资料
     * @param personnelDTO
     */
    void saveData(PersonnelDTO personnelDTO);
    /**
     * 修改人员资料
     * @param personnelDTO
     */
    void updateData(PersonnelDTO personnelDTO);
    /**
     * 删除人员资料
     * @param personnelDTO
     */
    void removeData(PersonnelDTO personnelDTO);

    /**
     * 查询机构下所有人员
     *
     * @param deptId 部门Id
     * @return 人员信息集合
     */
    List<HumanresourcePersonnelInfoDTO> selectOrgPerson(String deptId);

    /**
     * 获取人员基本信息
     *
     * @param empNo 工号
     * @return 人员信息集合
     */
    Object selectPersonnelInfo(String empNo);
}
