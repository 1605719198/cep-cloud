package com.jlkj.human.hm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.human.hm.PersonnelDTO;
import com.jlkj.human.hm.domain.Personnel;

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
}
