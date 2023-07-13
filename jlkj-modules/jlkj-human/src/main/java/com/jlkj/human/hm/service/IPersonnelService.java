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
    HumanresourcePersonnelInfoDTO selectPersonnelInfo(String empNo);

    /**
     * 新增人员基本信息
     *
     * @param personnel 人员信息
     * @return 结果
     */
    Object addPersonnelBasicInfo(Personnel personnel);

    /**
     * 获取人员基本信息查询列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personnel 查询参数集
     * @return list 返回数据
     */
    List<Personnel> getPersonnelBasicInfoList(Personnel personnel);
}
