package com.jlkj.human.HM.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.HM.domain.HumanresourcePerorgTree;

import java.util.List;

/**
 * <p>
 * 人员机构树型结构存储表 服务类
 * </p>
 *
 * @author xin
 * @since 2022-05-17
 */
public interface IHumanresourcePerorgTreeService extends IService<HumanresourcePerorgTree> {

    /**
     * 获取人员信息树
     * @return
     */
    List<HumanresourcePerorgTree> getOrganizationTreeList();

    /**
     * 获取部门信息树
     * @return
     */
    List<HumanresourcePerorgTree> getOrganizationDeptTreeList();
    /**
     * 获取机构及人员树结构资料(去除无人员部门树结构)
     * @return
     */
    List<HumanresourcePerorgTree> getOrganizationDelTreeList();
}
