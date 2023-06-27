package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.human.hp.domain.HumanDept;
import com.jlkj.human.hp.domain.HumanDeptVersion;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.hp.dto.CopySysDeptDTO;
import com.jlkj.human.hp.dto.DeptUnionPostDTO;
import com.jlkj.human.hp.dto.FirstDeptDTO;
import com.jlkj.human.hp.mapper.HumanDeptMapper;
import com.jlkj.human.hp.mapper.HumanDeptVersionMapper;
import com.jlkj.human.hp.service.IHumanDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门资料维护Service业务层处理
 *
 * @author 266861
 * @date 2023-03-08
 */
@Service
public class HumanDeptServiceImpl implements IHumanDeptService {
    @Autowired
    private HumanDeptMapper humanDeptMapper;
    @Autowired
    private HumanDeptVersionMapper humanDeptVersionMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询部门资料维护
     *
     * @param deptId 部门资料维护主键
     * @return 部门资料维护
     */
    @Override
    public HumanDept selectSysDeptByDeptId(Long deptId) {
        return humanDeptMapper.selectSysDeptByDeptId(deptId);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<HumanDept> buildDeptTree(List<HumanDept> depts) {
        List<HumanDept> returnList = new ArrayList<HumanDept>();
        List<Long> tempList = depts.stream().map(HumanDept::getDeptId).collect(Collectors.toList());
        for (HumanDept dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override

    public List<TreeSelect> buildDeptTreeSelect(List<HumanDept> depts) {
        List<HumanDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<HumanDept> list, HumanDept t) {
        // 得到子节点列表
        List<HumanDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (HumanDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<HumanDept> getChildList(List<HumanDept> list, HumanDept t) {
        List<HumanDept> tlist = new ArrayList<HumanDept>();
        Iterator<HumanDept> it = list.iterator();
        while (it.hasNext()) {
            HumanDept n = (HumanDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<HumanDept> list, HumanDept t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 查询部门资料维护列表
     *
     * @param humanDept 部门资料维护
     * @return 部门资料维护
     */
    @Override
    public List<HumanDept> selectSysDeptList(HumanDept humanDept) {
        return humanDeptMapper.selectSysDeptList(humanDept);
    }


    /**
     * 查询公司资料列表
     *
     * @return 公司资料列表
     */
    @Override
    public List<HumanDept> selectCompanyList() {
        return humanDeptMapper.selectCompanyList();
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public HumanDept selectDeptById(Long deptId) {
        return humanDeptMapper.selectDeptById(deptId);
    }


    /**
     * 新增部门资料维护
     *
     * @param humanDept 部门资料维护
     * @return 结果
     */
    @Override
    public int insertSysDept(HumanDept humanDept) throws Exception {
        //重复编码个数
        Integer ifInsert = humanDeptMapper.queryRepetitivedata(humanDept);
        if (ifInsert != 0) {
            throw new Exception("机构编码已存在，请重复输入");
        }
        HumanDeptVersion humanDeptVersion = new HumanDeptVersion();
        humanDept.setCreateTime(DateUtils.getNowDate());
        humanDept.setUpdateTime(DateUtils.getNowDate());
        HumanDept info = humanDeptMapper.selectDeptById(humanDept.getParentId());
        humanDept.setAncestors(info.getAncestors() + "," + humanDept.getParentId());
        int insertOk = humanDeptMapper.insertSysDept(humanDept);
        if (insertOk >= 1) {
            BeanUtils.copyProperties(humanDept, humanDeptVersion);
            humanDeptVersion.setCreateTime(humanDept.getUpdateTime());
            humanDeptVersion.setCreateBy(humanDept.getUpdateBy());
            humanDeptVersionMapper.insertSysDeptVersion(humanDeptVersion);
        }
        return insertOk;
    }

    /**
     * 复制组织机构设定
     *
     * @param copySysDeptDTO 假别参数复制设定
     * @return 结果
     */
    @Override
    public int copySysDept(CopySysDeptDTO copySysDeptDTO) throws Exception {
        int result = 0;
        int olddept = humanDeptMapper.querycopybyOldCompId(copySysDeptDTO.getOldCompId());
        int newdept = humanDeptMapper.querycopybyNewCompId(copySysDeptDTO.getNewCompId());
        if (olddept == 0) {
            throw new Exception("来源公司下无组织机构数据");
        } else if (newdept > 0) {
            throw new Exception("目标公司下已有组织机构数据");
        }
        String onlydept = "0";
        HumanDept oldsysDept = new HumanDept();
        oldsysDept.setIfCompany(onlydept);
        oldsysDept.setCompId(copySysDeptDTO.getOldCompId());
        //一级部门逗号数
        int firstLevel = 2;
        //部门组级逗号数
        int ancestorsLevel = firstLevel;
        HumanDept newCompany = new HumanDept();
        HumanDept oldCompany = new HumanDept();
        newCompany.setCompId(copySysDeptDTO.getNewCompId());
        newCompany = humanDeptMapper.selectSysDeptList(newCompany).get(0);
        oldCompany.setCompId(copySysDeptDTO.getOldCompId());
        oldCompany.setIfCompany("1");
        oldCompany = humanDeptMapper.selectSysDeptList(oldCompany).get(0);
        HashMap<String, String> idchange = new HashMap<String, String>(16);
        idchange.put(oldCompany.getDeptId().toString(), newCompany.getDeptId().toString());
        while (true) {
            oldsysDept.setAncestorsLevel(ancestorsLevel);
            List<HumanDept> newsysDept = humanDeptMapper.selectSysDeptList(oldsysDept);
            if (null == newsysDept || newsysDept.size() == 0) {
                break;
            } else {
                for (int i = 0; i < newsysDept.size(); i++) {
                    String oldid = newsysDept.get(i).getDeptId().toString();
                    HumanDept newDeptchild = newsysDept.get(i);
                    newDeptchild.setDeptId(null);
                    newDeptchild.setLeader(null);
                    newDeptchild.setPhone(null);
                    newDeptchild.setFax(null);
                    newDeptchild.setEmail(null);
                    newDeptchild.setCreateBy(copySysDeptDTO.getCreateBy());
                    newDeptchild.setUpdateBy(copySysDeptDTO.getUpdateBy());
                    newDeptchild.setCreateTime(copySysDeptDTO.getCreateTime());
                    newDeptchild.setUpdateTime(copySysDeptDTO.getUpdateTime());
                    newDeptchild.setEffectDate(copySysDeptDTO.getEffectDate());
                    newDeptchild.setCompId(copySysDeptDTO.getNewCompId());
                    newDeptchild.setVersionNo("1");
                    for (String h : idchange.keySet()) {
                        result++;
                        newDeptchild.setParentId(Long.valueOf(newDeptchild.getParentId().toString().replace(h, idchange.get(h))));
                        newDeptchild.setParentName(newDeptchild.getDeptName().replace(h, idchange.get(h)));
                        newDeptchild.setAncestors(newDeptchild.getAncestors().replace(h, idchange.get(h)));
                    }
                    humanDeptMapper.insertSysDept(newDeptchild);
                    System.out.println(oldid + ':' + newDeptchild.getDeptId().toString());
                    idchange.put(oldid, newDeptchild.getDeptId().toString());
                }
                ancestorsLevel++;
            }
        }

        return result;
    }


    /**
     * 修改部门资料维护
     *
     * @param humanDept 部门资料维护
     * @return 结果
     */
    @Override
    public int updateSysDept(HumanDept humanDept) throws Exception {
        Integer ifInsert = humanDeptMapper.queryRepetitivedata(humanDept);
        if (ifInsert != 0) {
            throw new Exception("机构编码已存在，请重复输入");
        }
        HumanDeptVersion humanDeptVersion = new HumanDeptVersion();
        HumanDept oldDept = humanDeptMapper.selectSysDeptByDeptId(humanDept.getDeptId());
        HumanDept newParentDept = humanDeptMapper.selectSysDeptByDeptId(humanDept.getParentId());
        String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
        humanDept.setAncestors(newAncestors);
        int updateOk = humanDeptMapper.updateSysDept(humanDept);
        if (updateOk == 1) {
            BeanUtils.copyProperties(humanDept, humanDeptVersion);
            humanDeptVersion.setCreateTime(humanDept.getUpdateTime());
            humanDeptVersion.setCreateBy(humanDept.getUpdateBy());
            humanDeptVersionMapper.updateisNew(humanDeptVersion.getDeptId());
            humanDeptVersionMapper.insertSysDeptVersion(humanDeptVersion);
        }
        return updateOk;
    }

    /**
     * 批量删除部门资料维护
     *
     * @param deptIds 需要删除的部门资料维护主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptByDeptIds(Long[] deptIds) throws Exception {
        if (humanDeptMapper.queryChildNumber(deptIds[0]) == 0) {
            return humanDeptMapper.deleteSysDeptByDeptId(deptIds[0]);
        } else {
            throw new Exception("该机构下已分配其他部门，不能删除");
        }
    }

    /**
     * 查询子部门个数
     *
     * @param deptId 部门id
     * @return 子部门个数
     */
    @Override
    public Integer queryChildNumber(Long deptId) {
        return humanDeptMapper.queryChildNumber(deptId);
    }

    /**
     * 删除部门资料维护信息
     *
     * @param deptId 部门资料维护主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptByDeptId(Long deptId) {
        return humanDeptMapper.deleteSysDeptByDeptId(deptId);
    }


    /**
     * 查询部门岗位资料维护列表
     *
     * @param deptpost 部门岗位资料维护
     * @return 部门岗位资料集合
     */
    @Override
    public List<DeptUnionPostDTO> selectDeptPostList(DeptUnionPostDTO deptpost) {
        return humanDeptMapper.selectDeptUnionPost(deptpost);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param deptpostList 部门岗位列表
     * @return 树结构列表
     */
    @Override
    public List<DeptUnionPostDTO> buildDeptPostTree(List<DeptUnionPostDTO> deptpostList) {
        List<DeptUnionPostDTO> returnList = new ArrayList<DeptUnionPostDTO>();
        List<Long> tempList = deptpostList.stream().map(DeptUnionPostDTO::getDeptId).collect(Collectors.toList());
        for (DeptUnionPostDTO deptpost : deptpostList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(deptpost.getParentId())) {
                recursionFn(deptpostList, deptpost);
                returnList.add(deptpost);
            }
        }
        if (returnList.isEmpty()) {
            returnList = deptpostList;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param deptpostList 部门岗位列表
     * @return 下拉树结构列表
     */
    @Override

    public List<TreeSelect> buildDeptPostTreeSelect(List<DeptUnionPostDTO> deptpostList) {
        List<DeptUnionPostDTO> deptpostTrees = buildDeptPostTree(deptpostList);
        return deptpostTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<DeptUnionPostDTO> list, DeptUnionPostDTO t) {
        // 得到子节点列表
        List<DeptUnionPostDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (DeptUnionPostDTO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<DeptUnionPostDTO> getChildList(List<DeptUnionPostDTO> list, DeptUnionPostDTO t) {
        List<DeptUnionPostDTO> tlist = new ArrayList<DeptUnionPostDTO>();
        Iterator<DeptUnionPostDTO> it = list.iterator();
        while (it.hasNext()) {
            DeptUnionPostDTO n = (DeptUnionPostDTO) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<DeptUnionPostDTO> list, DeptUnionPostDTO t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 通过部门id查一级部门
     */
    @Override
    public FirstDeptDTO getFirstDeptByDept(String empId) {

        return humanDeptMapper.getFirstDeptByDept(empId);
    }

    /**
     * 通过员工工号查一级部门
     */
    @Override
    public FirstDeptDTO getFirstDeptByPerson(String empId) {
        return humanDeptMapper.getFirstDeptByPerson(empId);
    }

    /**
     * 通过部门查下属员工工号
     */
    @Override
    public ArrayList<FirstDeptDTO> getPersonByDept(String deptId) {
        return humanDeptMapper.getPersonByDept(deptId);
    }

    /**
     * 根据部门编码查询信息
     *
     * @param deptCode 部门编码
     * @return 部门信息
     */
    @Override
    public HumanDept selectSysDeptByDeptCode(String deptCode) {
        return humanDeptMapper.selectSysDeptByDeptCode(deptCode);
    }

    /**
     * 查询父id为此部门编码的数据
     */
    @Override
    public List<HumanDept> selectParentIdByDeptCode(Long deptId) {
        return humanDeptMapper.selectParentIdByDeptCode(deptId);
    }

    /**
     * 根据公司别查询信息
     *
     * @param compId 公司别
     * @return 公司信息
     */
    @Override
    public HumanDept queryCompById(String compId){
        return humanDeptMapper.selectSysDeptByCompId(compId);
    }

    /**
     * 导入部门资料数据
     *
     * @param humanDeptList     部门数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<HumanDept> humanDeptList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(humanDeptList) || humanDeptList.size() == 0) {
            throw new ServiceException("导入部门数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (HumanDept humanDept : humanDeptList) {
            try {
                Integer ifInsert = humanDeptMapper.queryRepetitivedata(humanDept);
                if (ifInsert == 0) {
                    BeanValidators.validateWithException(validator, humanDept);
                    humanDept.setCreateBy(operName);
                    humanDeptMapper.insertSysDept(humanDept);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、部门编码 " + humanDept.getDeptCode() + " 导入成功");
                    if (isUpdateSupport) {
                        BeanValidators.validateWithException(validator, humanDept);
                        humanDept.setCreateBy(operName);
                        humanDept.setUpdateBy(operName);
                        //
                        humanDeptMapper.updateSysDept(humanDept);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、部门编码 " + humanDept.getDeptCode() + " 更新成功");
                    }
                }else{
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、部门编码 " + humanDept.getDeptCode() + " 导入失败：";
                    failureMsg.append(msg + "部门编码重复");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、部门编码 " + humanDept.getDeptCode() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 查询部门名称（id/编码 转名称）
     * @param compId 公司别
     * @return 结果
     */
    @Override
    public List<Map<String,Object>> selectDeptName(String compId){
        return humanDeptMapper.selectDeptName(compId);
    }

}
