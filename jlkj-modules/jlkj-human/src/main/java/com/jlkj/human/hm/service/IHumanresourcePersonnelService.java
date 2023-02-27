package com.jlkj.human.hm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.human.hm.HumanresourcePersonnelDTO;
import com.jlkj.human.hm.domain.HumanresourcePersonnel;

/**
 * <p>
 * 人员数据 服务类
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
public interface IHumanresourcePersonnelService extends IService<HumanresourcePersonnel> {

    /**
     * 新增人员资料
     * @param humanresourcePersonnelDTO
     */
    void saveData(HumanresourcePersonnelDTO humanresourcePersonnelDTO);
    /**
     * 修改人员资料
     * @param humanresourcePersonnelDTO
     */
    void updateData(HumanresourcePersonnelDTO humanresourcePersonnelDTO);
    /**
     * 删除人员资料
     * @param humanresourcePersonnelDTO
     */
    void removeData(HumanresourcePersonnelDTO humanresourcePersonnelDTO);
}
