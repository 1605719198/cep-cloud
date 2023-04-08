package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.domain.SysDeptVersion;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.hp.dto.CopySysDeptDTO;
import com.jlkj.human.hp.dto.DeptUnionPostDTO;
import com.jlkj.human.hp.dto.FirstDeptDTO;
import com.jlkj.human.hp.mapper.SysDeptMapper;
import com.jlkj.human.hp.mapper.SysDeptVersionMapper;
import com.jlkj.human.hp.service.ISysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 部门资料维护Service业务层处理
 *
 * @author jlkj
 * @date 2023-03-08
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDeptVersionMapper sysDeptVersionMapper;

    /**
     * 查询部门资料维护
     *
     * @param deptId 部门资料维护主键
     * @return 部门资料维护
     */
    @Override
    public SysDept selectSysDeptByDeptId(Long deptId)
    {
        return sysDeptMapper.selectSysDeptByDeptId(deptId);
    }
    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        for (SysDept dept : depts)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
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

    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
    /**
     * 查询部门资料维护列表
     *
     * @param sysDept 部门资料维护
     * @return 部门资料维护
     */
    @Override
    public List<SysDept> selectSysDeptList(SysDept sysDept)
    {
        return sysDeptMapper.selectSysDeptList(sysDept);
    }


    /**
     * 查询公司资料列表
     *
     * @return 公司资料列表
     */
    @Override
    public List<SysDept> selectCompanyList()
    {
        return sysDeptMapper.selectCompanyList();
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return sysDeptMapper.selectDeptById(deptId);
    }


    /**
     * 新增部门资料维护
     *
     * @param sysDept 部门资料维护
     * @return 结果
     */
    @Override
    public int insertSysDept(SysDept sysDept) throws Exception
    {
        SysDept oldsysdept = sysDeptMapper.selectSysDeptByDeptCode(sysDept.getDeptCode());
        if(oldsysdept!=null){
            throw new Exception("机构编码已存在，请重复输入");
        }
        SysDeptVersion sysDeptVersion = new SysDeptVersion();
        sysDept.setCreateTime(DateUtils.getNowDate());
        sysDept.setUpdateTime(DateUtils.getNowDate());
        SysDept info = sysDeptMapper.selectDeptById(sysDept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
//        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
//        {
//            throw new ServiceException("部门停用，不允许新增");
//        }
        sysDept.setAncestors(info.getAncestors() + "," + sysDept.getParentId());
        int insertOk=sysDeptMapper.insertSysDept(sysDept);
        if(insertOk>=1){
            BeanUtils.copyProperties(sysDept,sysDeptVersion);
            sysDeptVersionMapper.insertSysDeptVersion(sysDeptVersion);
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
    public int copySysDept (CopySysDeptDTO copySysDeptDTO) throws Exception{
        int olddept = sysDeptMapper.querycopybyOldCompId(copySysDeptDTO.getOldCompId());
        int newdept = sysDeptMapper.querycopybyNewCompId(copySysDeptDTO.getNewCompId());
        if(olddept==0){
            throw new Exception("来源公司下无组织机构数据");
        }else if(newdept>0){
            throw new Exception("目标公司下已有组织机构数据");
        }
        String onlydept = "0";
        SysDept oldsysDept = new SysDept();
        oldsysDept.setIfCompany(onlydept);
        oldsysDept.setCompId(copySysDeptDTO.getOldCompId());
        //一级部门逗号数
        int firstLevel = 2;
        //部门组级逗号数
        int ancestorsLevel =firstLevel;
        SysDept newCompany = new SysDept();
        SysDept oldCompany = new SysDept();
        newCompany.setCompId(copySysDeptDTO.getNewCompId());
        newCompany = sysDeptMapper.selectSysDeptList(newCompany).get(0);
        oldCompany.setCompId(copySysDeptDTO.getOldCompId());
        oldCompany.setIfCompany("1");
        oldCompany = sysDeptMapper.selectSysDeptList(oldCompany).get(0);
        HashMap<String, String> idchange = new HashMap<String, String>(16);
        idchange.put(oldCompany.getDeptId().toString(),newCompany.getDeptId().toString());
        while (true){
            oldsysDept.setAncestorsLevel(ancestorsLevel);
            List<SysDept> newsysDept= sysDeptMapper.selectSysDeptList(oldsysDept);
            if(null == newsysDept || newsysDept.size() ==0){
                break;
            }else{
                for(int i=0;i<newsysDept.size();i++){
                    String oldid = newsysDept.get(i).getDeptId().toString();
                    SysDept newDeptchild = newsysDept.get(i);
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
                            newDeptchild.setParentId(Long.valueOf(newDeptchild.getParentId().toString().replace(h,idchange.get(h))));
                            newDeptchild.setParentName(newDeptchild.getDeptName().replace(h,idchange.get(h)));
                            newDeptchild.setAncestors(newDeptchild.getAncestors().replace(h,idchange.get(h)));
                        }
                    sysDeptMapper.insertSysDept(newDeptchild);
                    System.out.println(oldid+':'+newDeptchild.getDeptId().toString());
                    idchange.put(oldid,newDeptchild.getDeptId().toString());
                }
                ancestorsLevel++;
            }
        }


        return 1;
    }


    /**
     * 修改部门资料维护
     *
     * @param sysDept 部门资料维护
     * @return 结果
     */
    @Override
    public int updateSysDept(SysDept sysDept) throws Exception
    {
        SysDeptVersion sysDeptVersion = new SysDeptVersion();
        SysDept oldDept = sysDeptMapper.selectSysDeptByDeptId(sysDept.getDeptId());
        SysDept newParentDept = sysDeptMapper.selectSysDeptByDeptId(sysDept.getParentId());
        String newAncestors = newParentDept.getAncestors()+","+newParentDept.getDeptId();
        sysDept.setAncestors(newAncestors);
        int updateOk=sysDeptMapper.updateSysDept(sysDept);
        if(updateOk==1){
            BeanUtils.copyProperties(sysDept,sysDeptVersion);
            sysDeptVersionMapper.updateisNew(sysDeptVersion.getDeptId());
            sysDeptVersionMapper.insertSysDeptVersion(sysDeptVersion);
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
    public int deleteSysDeptByDeptIds(Long[] deptIds)
    {
        return sysDeptMapper.deleteSysDeptByDeptIds(deptIds);
    }

    /**
     * 删除部门资料维护信息
     *
     * @param deptId 部门资料维护主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptByDeptId(Long deptId)
    {
        return sysDeptMapper.deleteSysDeptByDeptId(deptId);
    }


    /**
     * 查询部门岗位资料维护列表
     *
     * @param deptpost 部门岗位资料维护
     * @return 部门岗位资料集合
     */
    @Override
    public List<DeptUnionPostDTO> selectDeptPostList(DeptUnionPostDTO deptpost){
        return sysDeptMapper.selectDeptUnionPost(deptpost);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param deptpostList 部门岗位列表
     * @return 树结构列表
     */
    @Override
    public List<DeptUnionPostDTO> buildDeptPostTree(List<DeptUnionPostDTO> deptpostList)
    {
        List<DeptUnionPostDTO> returnList = new ArrayList<DeptUnionPostDTO>();
        List<Long> tempList = deptpostList.stream().map(DeptUnionPostDTO::getDeptId).collect(Collectors.toList());
        for (DeptUnionPostDTO deptpost : deptpostList)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(deptpost.getParentId()))
            {
                recursionFn(deptpostList, deptpost);
                returnList.add(deptpost);
            }
        }
        if (returnList.isEmpty())
        {
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

    public List<TreeSelect> buildDeptPostTreeSelect(List<DeptUnionPostDTO> deptpostList)
    {
        List<DeptUnionPostDTO> deptpostTrees = buildDeptPostTree(deptpostList);
        return deptpostTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<DeptUnionPostDTO> list, DeptUnionPostDTO t)
    {
        // 得到子节点列表
        List<DeptUnionPostDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (DeptUnionPostDTO tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<DeptUnionPostDTO> getChildList(List<DeptUnionPostDTO> list, DeptUnionPostDTO t)
    {
        List<DeptUnionPostDTO> tlist = new ArrayList<DeptUnionPostDTO>();
        Iterator<DeptUnionPostDTO> it = list.iterator();
        while (it.hasNext())
        {
            DeptUnionPostDTO n = (DeptUnionPostDTO) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<DeptUnionPostDTO> list, DeptUnionPostDTO t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 通过部门id查一级部门
     */
    @Override
    public FirstDeptDTO getFirstDeptByDept(String empId){

        return sysDeptMapper.getFirstDeptByDept(empId);
    }

    /**
     * 通过员工工号查一级部门
     */
    @Override
    public FirstDeptDTO getFirstDeptByPerson(String empId){
        return sysDeptMapper.getFirstDeptByPerson(empId);
    }

    /**
     * 通过部门查下属员工工号
     */
    @Override
    public ArrayList<FirstDeptDTO> getPersonByDept(String deptId){
        return sysDeptMapper.getPersonByDept(deptId);
    }


}
