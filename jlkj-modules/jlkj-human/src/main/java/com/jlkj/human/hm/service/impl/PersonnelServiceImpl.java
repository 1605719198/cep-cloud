package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PersonnelDTO;
import com.jlkj.human.hm.domain.*;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.mapper.PersonnelMapper;
import com.jlkj.human.hm.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 人员数据 服务实现类
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@Service
public class PersonnelServiceImpl extends ServiceImpl<PersonnelMapper, Personnel> implements IPersonnelService {

    private static final String USER = "user";
    private static final String USER_ICON = "el-icon-self-nvxing";
    @Autowired
    private IPerorgTreeService humanresourcePerorgTreeService;
    @Autowired
    private IStaffService humanresourceStaffService;
    @Autowired
    private PersonnelMapper personnelMapper;
    @Resource
    private IContractService contractService;
    @Resource
    private ILeaveService leaveService;

    /**
     * 新增人员资料
     * @param personnelDTO
     */
    @Override
    public void saveData(PersonnelDTO personnelDTO) {
        Personnel personnel = new Personnel();
        Staff staff = new Staff();
        BeanUtils.copyProperties(personnelDTO, personnel);
        BeanUtils.copyProperties(personnelDTO, staff);
        baseMapper.insert(personnel);
        humanresourceStaffService.save(staff);
        //同步至组织机构树资料
        PerorgTree perorgTree = new PerorgTree();
        perorgTree.setId(personnelDTO.getId());
        perorgTree.setParentId(personnelDTO.getAlternateField14());
        perorgTree.setParentName(personnelDTO.getDepartmentName());
        perorgTree.setJobNumber(personnelDTO.getJobNumber());
        perorgTree.setLabel(personnelDTO.getFullName());
        perorgTree.setPostName(personnelDTO.getPostName());
        perorgTree.setPostId(personnelDTO.getPostId());
        perorgTree.setFlag(USER);
        perorgTree.setIcon(USER_ICON);
        humanresourcePerorgTreeService.save(perorgTree);
    }
    /**
     * 修改人员资料
     * @param personnelDTO
     */
    @Override
    public void updateData(PersonnelDTO personnelDTO) {
        Personnel humanresourcePersonnel = new Personnel();
        Staff staff = new Staff();
        BeanUtils.copyProperties(personnelDTO,humanresourcePersonnel);
        BeanUtils.copyProperties(personnelDTO, staff);
        PerorgTree perorgTree = new PerorgTree();
        perorgTree.setId(personnelDTO.getId());
        perorgTree.setParentId(personnelDTO.getAlternateField14());
        perorgTree.setParentName(personnelDTO.getDepartmentName());
        perorgTree.setJobNumber(personnelDTO.getJobNumber());
        perorgTree.setLabel(personnelDTO.getFullName());
        perorgTree.setPostName(personnelDTO.getPostName());
        perorgTree.setPostId(personnelDTO.getPostId());
        perorgTree.setFlag(USER);
        perorgTree.setIcon(USER_ICON);
        Personnel personnel = baseMapper.selectById(humanresourcePersonnel.getId());
        if (StringUtils.isNotNull(personnel)) {
//            baseMapper.updateById()
            baseMapper.updateById(humanresourcePersonnel);
            humanresourcePerorgTreeService.updateById(perorgTree);
            humanresourceStaffService.updateById(staff);
        } else {
            baseMapper.insert(humanresourcePersonnel);
            humanresourcePerorgTreeService.save(perorgTree);
            humanresourceStaffService.save(staff);
        }
    }
    /**
     * 删除人员资料
     * @param personnelDTO
     */
    @Override
    public void removeData(PersonnelDTO personnelDTO) {
        Personnel personnel = new Personnel();
        Staff staff = new Staff();
        BeanUtils.copyProperties(personnelDTO, personnel);
        BeanUtils.copyProperties(personnelDTO, staff);
        baseMapper.deleteById(personnel);
        humanresourcePerorgTreeService.removeById(personnel.getId());
        humanresourceStaffService.removeById(staff.getId());
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param deptId 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<HumanresourcePersonnelInfoDTO> selectOrgPerson(String deptId) {
        return personnelMapper.selectOrgPerson(deptId);
    }

    /**
     * 获取人员基本信息
     * @param empNo 工号
     * @return 人员信息集合
     */
    @Override
    public Object selectPersonnelInfo(String empNo) {
        List<Personnel> personnelList = lambdaQuery().eq(Personnel::getEmpNo, empNo).list();
        List<Contract> contractList = contractService.lambdaQuery().eq(Contract::getEmpNo, empNo).list();
        List<Leave> leaveList = leaveService.lambdaQuery().eq(Leave::getEmpNo, empNo).list();
        Map<String, Object> resultMap = new HashMap<>(32);
        resultMap.put("personnelList", personnelList);
        resultMap.put("contractList", contractList);
        resultMap.put("leaveList", leaveList);
        return AjaxResult.success(resultMap);
    }
}
