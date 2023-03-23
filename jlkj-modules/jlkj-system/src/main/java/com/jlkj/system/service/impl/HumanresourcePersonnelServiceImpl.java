package com.jlkj.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.system.dto.humanresourcepersonnel.InfoHumanresourcePersonnelSignatureDTO;
import com.jlkj.system.dto.humanresourcepersonnel.UpdateHumanresourcePersonneSignaturelDTO;
import com.jlkj.system.dto.user.GetPersonnelSchedulingDTO;
import com.jlkj.system.dto.user.GetUserListByDepartmentDTO;
import com.jlkj.system.dto.user.GetUserListByNameOrJobNumberDTO;
import com.jlkj.system.dto.user.GetUsersByIdsDTO;
import com.jlkj.system.entity.HumanresourceOrganization;
import com.jlkj.system.entity.HumanresourcePersonnel;
import com.jlkj.system.mapper.HumanresourcePersonnelMapper;
import com.jlkj.system.service.HumanresourcePersonnelService;
import com.jlkj.system.vo.humanresourcepersonnel.InfoHumanresourcePersonnelSignatureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zyf
 * @description 针对表【human_hm_personnel(人员数据)】的数据库操作Service实现
 * @createDate 2022-06-09 14:45:48
 */
@Service
public class HumanresourcePersonnelServiceImpl extends ServiceImpl<HumanresourcePersonnelMapper, HumanresourcePersonnel>
        implements HumanresourcePersonnelService {

    @Autowired
    HumanresourceOrganizationServiceImpl organizationService;

    public Object getUserListByDepartment(GetUserListByDepartmentDTO dto) {
        List<HumanresourcePersonnel> list = list(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(!dto.getDepartment_id().isEmpty(), HumanresourcePersonnel::getAlternateField14, dto.getDepartment_id()));
        return AjaxResult.success(list);
    }

    public Object getUserListByDepartmentOrNameOrJobnumber(GetUserListByNameOrJobNumberDTO dto) {
        ArrayList<String> departIds = new ArrayList<>();
        List<HumanresourceOrganization> orgList = organizationService.list(new LambdaQueryWrapper<HumanresourceOrganization>()
                .orderByAsc(HumanresourceOrganization::getSerialNo));
        if (!dto.getDepartment_id().isEmpty()) {
            departIds.add(dto.getDepartment_id());
            recursiveDepartmentTree(orgList, dto.getDepartment_id(), departIds);
        }
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = pageMaps(page, new QueryWrapper<HumanresourcePersonnel>()
                .orderByAsc("convert(full_name using gbk)")
                .lambda()
                .in(!dto.getDepartment_id().isEmpty(), HumanresourcePersonnel::getAlternateField14, departIds)
                .and(!dto.getKeywords().isEmpty(), humanresourcePersonnelLambdaQueryWrapper -> humanresourcePersonnelLambdaQueryWrapper
                        .like(!dto.getKeywords().isEmpty(), HumanresourcePersonnel::getFullName, dto.getKeywords())
                        .or()
                        .like(!dto.getKeywords().isEmpty(), HumanresourcePersonnel::getJobNumber, dto.getKeywords())
                        .or(true)
                        .like(!dto.getKeywords().isEmpty(), HumanresourcePersonnel::getFullNamePinyin, dto.getKeywords())
                ));
        return AjaxResult.success(list);
    }

    private void recursiveDepartmentTree(List<HumanresourceOrganization> orgList, String childrenId, ArrayList<String> departIds) {
        List<HumanresourceOrganization> childList = orgList.stream().filter(organization -> childrenId.equals(organization.getParentId())).collect(Collectors.toList());
        if (childList.size() > 0) {
            childList.forEach(organization -> {
                departIds.add(organization.getId());
                recursiveDepartmentTree(orgList, organization.getId(), departIds);
            });
        }
    }

    public Object getUsersByids(GetUsersByIdsDTO dto) {
        List<Map<String, Object>> list = listMaps(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .in(HumanresourcePersonnel::getId, dto.getUser_ids()));
        return AjaxResult.success(list);
    }

    @Override
    @Transactional(readOnly = true)
    public InfoHumanresourcePersonnelSignatureVO getHumanresourcePersonnelSignatureData(InfoHumanresourcePersonnelSignatureDTO infoHumanresourcePersonnelSignatureDTO) {
        HumanresourcePersonnel humanresourcePersonnel = getById(infoHumanresourcePersonnelSignatureDTO.getId());
        if (null != humanresourcePersonnel) {
            InfoHumanresourcePersonnelSignatureVO infoHumanresourcePersonnelSignatureVO = new InfoHumanresourcePersonnelSignatureVO();
            infoHumanresourcePersonnelSignatureVO.setSignature(humanresourcePersonnel.getSignature());
            return infoHumanresourcePersonnelSignatureVO;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateHumanresourcePersonnelSignatureData(UpdateHumanresourcePersonneSignaturelDTO updateHumanresourcePersonneSignaturelDTO) {
        HumanresourcePersonnel humanresourcePersonnel = getById(updateHumanresourcePersonneSignaturelDTO.getId());
        if (null != humanresourcePersonnel) {
            humanresourcePersonnel.setSignature(updateHumanresourcePersonneSignaturelDTO.getSignature());
            updateById(humanresourcePersonnel);
            return AjaxResult.success("人员修改成功");
        }
        else {
            return AjaxResult.error(HttpStatus.ERROR, "人员不存在");
        }
    }


    public  List<Map<String, Object>> getTeamByJobNumber(String jobNumber) {
        return getBaseMapper().getTeamByJobNumber(jobNumber);
    }



    public IPage<Map<String, String>> getPersonnelScheduling(GetPersonnelSchedulingDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getPersonnelScheduling(page, dto);
    }


}




