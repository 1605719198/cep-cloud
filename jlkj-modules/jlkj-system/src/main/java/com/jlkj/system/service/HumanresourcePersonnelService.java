package com.jlkj.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.system.dto.humanresourcepersonnel.InfoHumanresourcePersonnelSignatureDTO;
import com.jlkj.system.dto.humanresourcepersonnel.UpdateHumanresourcePersonneSignaturelDTO;
import com.jlkj.system.entity.HumanresourcePersonnel;
import com.jlkj.system.vo.humanresourcepersonnel.InfoHumanresourcePersonnelSignatureVO;

/**
* @author zyf
* @description 针对表【human_hm_personnel(人员数据)】的数据库操作Service
* @createDate 2022-06-09 14:45:48
*/
public interface HumanresourcePersonnelService extends IService<HumanresourcePersonnel> {
    /**
     * 获取人员签章
     * @param infoHumanresourcePersonnelSignatureDTO 查询条件dto
     * @return 单行数据
     */
    InfoHumanresourcePersonnelSignatureVO getHumanresourcePersonnelSignatureData(InfoHumanresourcePersonnelSignatureDTO infoHumanresourcePersonnelSignatureDTO);

    /**
     * 保存人员签章
     * @param updateHumanresourcePersonneSignaturelDTO 修改dto
     * @return 执行结果
     */
    Object updateHumanresourcePersonnelSignatureData(UpdateHumanresourcePersonneSignaturelDTO updateHumanresourcePersonneSignaturelDTO);
}
