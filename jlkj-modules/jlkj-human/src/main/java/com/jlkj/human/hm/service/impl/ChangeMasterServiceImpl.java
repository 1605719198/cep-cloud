package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.hm.domain.ChangeDetail;
import com.jlkj.human.hm.domain.ChangeMaster;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.ChangeMasterDTO;
import com.jlkj.human.hm.mapper.ChangeMasterMapper;
import com.jlkj.human.hm.service.IChangeMasterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hm_change_master(人员异动信息主表)】的数据库操作Service实现
* @createDate 2023-03-17 08:39:33
*/
@Service
public class ChangeMasterServiceImpl extends ServiceImpl<ChangeMasterMapper, ChangeMaster>
    implements IChangeMasterService {

    @Resource
    private ChangeDetailServiceImpl changeDetailService;
    @Resource
    private PersonnelServiceImpl personnelService;

    /**
     * 添加员工入职作业
     */
    @Override
    public Object addEmployeeInduction(ChangeMasterDTO changeMasterDTO) {
        ChangeMaster changeMaster = new ChangeMaster();
        BeanUtils.copyProperties(changeMasterDTO, changeMaster);
        List<ChangeDetail> employeeInductionList = changeMasterDTO.getEmployeeInductionList();
        Date effectDate = changeMaster.getEffectDate();
        changeMaster.setChangeTypeId("08");
        changeMaster.setVersionNo(1);
        changeMaster.setUuid(IdUtils.fastSimpleUUID());
        for (ChangeDetail item : employeeInductionList) {
            item.setParentId(changeMaster.getUuid());
            if ("01".equals(item.getPostTypeId())) {
                changeMasterDTO.setPostId(item.getNewPostId());
                changeMasterDTO.setPostName(item.getNewPostName());
            }
        }
        List<ChangeMaster> list = lambdaQuery().eq(ChangeMaster::getEffectDate, effectDate)
                .eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo()).list();
        List<ChangeMaster> list2 = lambdaQuery().eq(ChangeMaster::getEmpNo, changeMaster.getEmpNo()).list();
        List<ChangeDetail> list1 = changeDetailService.lambdaQuery().eq(ChangeDetail::getParentId, changeMaster.getUuid())
                .eq(ChangeDetail::getPostTypeId, "01").list();
        if (!list2.isEmpty()) {
            return AjaxResult.error("入职资料已存在");
        }
        if (list1.size() > 1) {
            return AjaxResult.error("只能存在一笔主岗位资料！！！");
        }
        if (!list.isEmpty()) {
            return AjaxResult.error("员工同一生效日期已存在异动信息，新增失败！");
        }
        boolean result = save(changeMaster);
        changeDetailService.saveOrUpdateBatch(employeeInductionList);
        if (result) {
            personnelService.lambdaUpdate()
                    .set(Personnel::getPostName, changeMasterDTO.getPostName())
                    .set(Personnel::getPostId, changeMasterDTO.getPostId())
                    .set(Personnel::getPostLevelName, changeMasterDTO.getPostLevel())
                    .set(Personnel::getDepartmentName, changeMasterDTO.getDepartmentName())
                    .set(Personnel::getDepartmentId, changeMasterDTO.getDepartmentId())
                    .eq(Personnel::getEmpNo, changeMaster.getEmpNo()).update();
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error("保存失败");
        }
    }
}




