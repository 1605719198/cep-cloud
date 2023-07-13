package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.utils.PinYinApi;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PersonnelDTO;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.*;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.mapper.PersonnelMapper;
import com.jlkj.human.hm.service.*;
import com.jlkj.human.hp.domain.HumanDept;
import com.jlkj.human.hp.service.IHumanDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private IContractService contractService;
    @Resource
    private ILeaveService leaveService;
    @Resource
    private IHumanUserService userService;
    @Resource
    private IHumanDeptService deptService;
    @Resource
    private IHumanConfigService configService;

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
    public HumanresourcePersonnelInfoDTO selectPersonnelInfo(String empNo) {
        List<Personnel> personnelList = lambdaQuery().eq(Personnel::getEmpNo, empNo).list();
        List<Contract> contractList = contractService.lambdaQuery().eq(Contract::getEmpNo, empNo).list();
        List<Leave> leaveList = leaveService.lambdaQuery().eq(Leave::getEmpNo, empNo).list();
        HumanresourcePersonnelInfoDTO dto = new HumanresourcePersonnelInfoDTO();
        dto.setPersonnelList(personnelList);
        dto.setContractList(contractList);
        dto.setLeaveList(leaveList);
        return dto;
    }

    /**
     * 新增人员基本信息
     *
     * @param personnel 人员信息
     * @return 结果
     */
    @Override
    public Object addPersonnelBasicInfo(Personnel personnel) {
        List<Personnel> list = lambdaQuery().eq(Personnel::getCertificateNumber, personnel.getCertificateNumber()).list();
        if (list.isEmpty()) {
            List<Personnel> list1 = lambdaQuery().eq(Personnel::getEmpNo, personnel.getEmpNo()).list();
            if (list1.isEmpty()) {
                // 根据姓名 取得大写首字母
                personnel.setFullNamePinyin(PinYinApi.getPinYinHeadChar(personnel.getFullName()));
                personnel.setStatus(Constants.STR_ONE);
                boolean result = save(personnel);
                if (result) {
                    HumanUser humanUser = new HumanUser();
                    humanUser.setUserName(personnel.getEmpNo());
                    humanUser.setNickName(personnel.getFullName());
                    humanUser.setCreateBy(SecurityUtils.getUsername());
                    humanUser.setPassword(SecurityUtils.encryptPassword(configService.selectConfigByKey("sys.user.initPassword")));
                    userService.save(humanUser);
                    return AjaxResult.success("保存成功");
                } else {
                    return AjaxResult.error();
                }
            } else {
                return AjaxResult.error("新增失败，工号" + personnel.getEmpNo() + "已被使用");
            }
        } else if (list.size() == Constants.INT_ONE){
            lambdaUpdate().eq(Personnel::getId, personnel.getId()).update(personnel);
            return AjaxResult.success("修改成功");
        } else {
            if (list.get(0).getEmpNo().equals(personnel.getEmpNo())) {
                return AjaxResult.error("新增失败，工号" + personnel.getEmpNo() + "已经存在");
            } else {
                HumanDept dept = deptService.queryCompById(list.get(0).getCompId());
                return AjaxResult.error("新增失败，身份证号：" + personnel.getCertificateNumber() + "已存在，被" + dept.getDeptName() + "公司工号" + list.get(0).getEmpNo() + "使用");
            }
        }
    }

    /**
     * 获取人员基本信息查询列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personnel 查询参数集
     * @return list 返回数据
     */
    @Override
    public List<Personnel> getPersonnelBasicInfoList(Personnel personnel){
        List<Personnel> list = lambdaQuery()
                .eq(StringUtils.isNotBlank(personnel.getCompId()), Personnel::getCompId, personnel.getCompId())
                .eq(Personnel::getEmpNo, personnel.getEmpNo()).list();
        return list;
    }
}
