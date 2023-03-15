package com.jlkj.human.hm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.human.hm.OrganizationDTO;
import com.jlkj.human.hm.domain.Organization;
import com.jlkj.human.hm.dto.OrganizationTreeDTO;

import java.util.List;

/**
 * <p>
 * 机构数据 服务类
 * </p>
 *
 * @author xin
 * @since 2022-05-05
 */
public interface IOrganizationService extends IService<Organization> {

    /**
     * 获取机构树结构资料
     * @return
     */
    List<OrganizationTreeDTO> getOrganizationTreeList();

    /**
     * 新增机构资料
     * @param organizationDTO
     */
    void saveData(OrganizationDTO organizationDTO);
    /**
     * 修改机构资料
     * @param organizationDTO
     */
    void updateData(OrganizationDTO organizationDTO);
    /**
     * 删除机构资料
     * @param organizationDTO
     * @return
     */
    void removeData(OrganizationDTO organizationDTO);
}
