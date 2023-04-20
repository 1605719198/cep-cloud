package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.dto.human.hm.PersonnelDTO;
import com.jlkj.human.hm.domain.PerorgTree;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.domain.Staff;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.mapper.PersonnelMapper;
import com.jlkj.human.hm.service.IPerorgTreeService;
import com.jlkj.human.hm.service.IPersonnelService;
import com.jlkj.human.hm.service.IStaffService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
