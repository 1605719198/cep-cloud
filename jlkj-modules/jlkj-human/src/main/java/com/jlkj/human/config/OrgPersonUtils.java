package com.jlkj.human.config;

import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.service.IPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author 265675
 * @version 1.0
 * @className OrgPersonUtils
 * @description TODO
 * @date 2023年4月20日
 */
@Component
public class OrgPersonUtils {

    @Autowired
    IPersonnelService personnelService;
    public static OrgPersonUtils orgPersonUtils;
    @PostConstruct
    public void init() {
        orgPersonUtils = this;
        orgPersonUtils.personnelService = this.personnelService;
    }

    /**
     * 获取组织机构下所有人员
     */
    public List<HumanresourcePersonnelInfoDTO> selectOrgPerson(String deptId) {
       return orgPersonUtils.personnelService.selectOrgPerson(deptId);
    }

}
