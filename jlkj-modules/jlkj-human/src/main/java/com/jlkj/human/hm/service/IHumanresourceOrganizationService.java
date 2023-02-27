package com.jlkj.human.hm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.human.hm.HumanresourceOrganizationDTO;
import com.jlkj.human.hm.domain.HumanresourceOrganization;
import com.jlkj.human.hm.dto.HumanresourceOrganizationTreeDTO;

import java.util.List;

/**
 * <p>
 * 机构数据 服务类
 * </p>
 *
 * @author xin
 * @since 2022-05-05
 */
public interface IHumanresourceOrganizationService extends IService<HumanresourceOrganization> {

    /**
     * 获取机构树结构资料
     * @return
     */
    List<HumanresourceOrganizationTreeDTO> getOrganizationTreeList();

    /**
     * 新增机构资料
     * @param humanresourceOrganizationDTO
     */
    void saveData(HumanresourceOrganizationDTO humanresourceOrganizationDTO);
    /**
     * 修改机构资料
     * @param humanresourceOrganizationDTO
     */
    void updateData(HumanresourceOrganizationDTO humanresourceOrganizationDTO);
    /**
     * 删除机构资料
     * @param humanresourceOrganizationDTO
     * @return
     */
    void removeData(HumanresourceOrganizationDTO humanresourceOrganizationDTO);
}
