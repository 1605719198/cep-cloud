package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.dto.human.hm.HumanresourcePersonnelDTO;
import com.jlkj.human.hm.domain.HumanresourcePerorgTree;
import com.jlkj.human.hm.domain.HumanresourcePersonnel;
import com.jlkj.human.hm.domain.HumanresourceStaff;
import com.jlkj.human.hm.mapper.HumanresourcePersonnelMapper;
import com.jlkj.human.hm.service.IHumanresourcePerorgTreeService;
import com.jlkj.human.hm.service.IHumanresourcePersonnelService;
import com.jlkj.human.hm.service.IHumanresourceStaffService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员数据 服务实现类
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@Service
public class HumanresourcePersonnelServiceImpl extends ServiceImpl<HumanresourcePersonnelMapper, HumanresourcePersonnel> implements IHumanresourcePersonnelService {

    private static final String USER = "user";
    private static final String USER_ICON = "el-icon-self-nvxing";
    @Autowired
    private IHumanresourcePerorgTreeService humanresourcePerorgTreeService;
    @Autowired
    private IHumanresourceStaffService humanresourceStaffService;
    /**
     * 新增人员资料
     * @param humanresourcePersonnelDTO
     */
    @Override
    public void saveData(HumanresourcePersonnelDTO humanresourcePersonnelDTO) {
        HumanresourcePersonnel humanresourcePersonnel = new HumanresourcePersonnel();
        HumanresourceStaff humanresourceStaff = new HumanresourceStaff();
        BeanUtils.copyProperties(humanresourcePersonnelDTO,humanresourcePersonnel);
        BeanUtils.copyProperties(humanresourcePersonnelDTO,humanresourceStaff);
        baseMapper.insert(humanresourcePersonnel);
        humanresourceStaffService.save(humanresourceStaff);
        //同步至组织机构树资料
        HumanresourcePerorgTree humanresourcePerorgTree = new HumanresourcePerorgTree();
        humanresourcePerorgTree.setId(humanresourcePersonnelDTO.getId());
        humanresourcePerorgTree.setParentId(humanresourcePersonnelDTO.getAlternateField14());
        humanresourcePerorgTree.setParentName(humanresourcePersonnelDTO.getDepartmentName());
        humanresourcePerorgTree.setJobNumber(humanresourcePersonnelDTO.getJobNumber());
        humanresourcePerorgTree.setLabel(humanresourcePersonnelDTO.getFullName());
        humanresourcePerorgTree.setPostName(humanresourcePersonnelDTO.getPostName());
        humanresourcePerorgTree.setPostId(humanresourcePersonnelDTO.getPostId());
        humanresourcePerorgTree.setFlag(USER);
        humanresourcePerorgTree.setIcon(USER_ICON);
        humanresourcePerorgTreeService.save(humanresourcePerorgTree);
    }
    /**
     * 修改人员资料
     * @param humanresourcePersonnelDTO
     */
    @Override
    public void updateData(HumanresourcePersonnelDTO humanresourcePersonnelDTO) {
        HumanresourcePersonnel humanresourcePersonnel = new HumanresourcePersonnel();
        HumanresourceStaff humanresourceStaff = new HumanresourceStaff();
        BeanUtils.copyProperties(humanresourcePersonnelDTO,humanresourcePersonnel);
        BeanUtils.copyProperties(humanresourcePersonnelDTO,humanresourceStaff);
        HumanresourcePerorgTree humanresourcePerorgTree = new HumanresourcePerorgTree();
        humanresourcePerorgTree.setId(humanresourcePersonnelDTO.getId());
        humanresourcePerorgTree.setParentId(humanresourcePersonnelDTO.getAlternateField14());
        humanresourcePerorgTree.setParentName(humanresourcePersonnelDTO.getDepartmentName());
        humanresourcePerorgTree.setJobNumber(humanresourcePersonnelDTO.getJobNumber());
        humanresourcePerorgTree.setLabel(humanresourcePersonnelDTO.getFullName());
        humanresourcePerorgTree.setPostName(humanresourcePersonnelDTO.getPostName());
        humanresourcePerorgTree.setPostId(humanresourcePersonnelDTO.getPostId());
        humanresourcePerorgTree.setFlag(USER);
        humanresourcePerorgTree.setIcon(USER_ICON);
        HumanresourcePersonnel personnel = baseMapper.selectById(humanresourcePersonnel.getId());
        if (StringUtils.isNotNull(personnel)) {
//            baseMapper.updateById()
            baseMapper.updateById(humanresourcePersonnel);
            humanresourcePerorgTreeService.updateById(humanresourcePerorgTree);
            humanresourceStaffService.updateById(humanresourceStaff);
        } else {
            baseMapper.insert(humanresourcePersonnel);
            humanresourcePerorgTreeService.save(humanresourcePerorgTree);
            humanresourceStaffService.save(humanresourceStaff);
        }
    }
    /**
     * 删除人员资料
     * @param humanresourcePersonnelDTO
     */
    @Override
    public void removeData(HumanresourcePersonnelDTO humanresourcePersonnelDTO) {
        HumanresourcePersonnel humanresourcePersonnel = new HumanresourcePersonnel();
        HumanresourceStaff humanresourceStaff = new HumanresourceStaff();
        BeanUtils.copyProperties(humanresourcePersonnelDTO,humanresourcePersonnel);
        BeanUtils.copyProperties(humanresourcePersonnelDTO,humanresourceStaff);
        baseMapper.deleteById(humanresourcePersonnel);
        humanresourcePerorgTreeService.removeById(humanresourcePersonnel.getId());
        humanresourceStaffService.removeById(humanresourceStaff.getId());
    }
}
