package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.hd.domain.Attendencebasis;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hd.mapper.AttendencebasisMapper;
import com.jlkj.human.hd.service.IAttendencebasisService;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工出勤基本资料维护Service业务层处理
 *
 * @author 266861
 * @date 2023-03-20
 */
@Service
public class AttendencebasisServiceImpl implements IAttendencebasisService
{
    @Autowired
    private AttendencebasisMapper attendencebasisMapper;

    /**
     * 查询员工出勤基本资料维护
     *
     * @param id 员工出勤基本资料维护主键
     * @return 员工出勤基本资料维护
     */
    @Override
    public Attendencebasis selectAttendencebasisById(Long id)
    {
        return attendencebasisMapper.selectAttendencebasisById(id);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param attendencebasiss 部门列表
     * @return 树结构列表
     */
    @Override
    public List<Attendencebasis> buildAttendenceTree(List<Attendencebasis> attendencebasiss)
    {
        List<Attendencebasis> returnList = new ArrayList<Attendencebasis>();
        List<Long> tempList = attendencebasiss.stream().map(Attendencebasis::getId).collect(Collectors.toList());
        for (Attendencebasis attendencebasis : attendencebasiss)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(attendencebasis.getParentid()))
            {
                recursionFn(attendencebasiss, attendencebasis);
                returnList.add(attendencebasis);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = attendencebasiss;
        }
        return returnList;
    }
    /**
     * 构建前端所需要下拉树结构
     *
     * @param attendencebasiss 部门列表
     * @return 下拉树结构列表
     */
    @Override

    public List<TreeSelect> buildAttendenceTreeSelect(List<Attendencebasis> attendencebasiss)
    {
        List<Attendencebasis> attendenceTrees = buildAttendenceTree(attendencebasiss);
        return attendenceTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<Attendencebasis> list, Attendencebasis t)
    {
        // 得到子节点列表
        List<Attendencebasis> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Attendencebasis tChild : childList)
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
    private List<Attendencebasis> getChildList(List<Attendencebasis> list, Attendencebasis t)
    {
        List<Attendencebasis> tlist = new ArrayList<Attendencebasis>();
        Iterator<Attendencebasis> it = list.iterator();
        while (it.hasNext())
        {
            Attendencebasis n = (Attendencebasis) it.next();
            if (StringUtils.isNotNull(n.getParentid()) && n.getParentid().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Attendencebasis> list, Attendencebasis t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
    
    

    /**
     * 查询员工出勤基本资料维护列表
     *
     * @param attendencebasis 员工出勤基本资料维护
     * @return 员工出勤基本资料维护
     */
    @Override
    public List<Attendencebasis> selectAttendencebasisList(Attendencebasis attendencebasis)
    {
        return attendencebasisMapper.selectAttendencebasisList(attendencebasis);
    }

    /**
     * 新增员工出勤基本资料维护
     *
     * @param attendencebasis 员工出勤基本资料维护
     * @return 结果
     */
    @Override
    public int insertAttendencebasis(Attendencebasis attendencebasis) throws Exception
    {
        if(attendencebasis.getParentid()==1){
            int i = attendencebasisMapper.queryRepetitivedata(attendencebasis);
            if(i>0){
                throw new Exception("资料编码已存在，请重新输入");
            }
        }
        Attendencebasis info = attendencebasisMapper.selectAttendencebasisById(attendencebasis.getParentid());
        attendencebasis.setParents(info.getParents() + "," + attendencebasis.getParentid());
        return attendencebasisMapper.insertAttendencebasis(attendencebasis);
    }

    /**
     * 修改员工出勤基本资料维护
     *
     * @param attendencebasis 员工出勤基本资料维护
     * @return 结果
     */
    @Override
    public int updateAttendencebasis(Attendencebasis attendencebasis) throws Exception
    {
        if(attendencebasis.getParentid()==1) {
            int i = attendencebasisMapper.queryRepetitivedata(attendencebasis);
            if (i > 0) {
                throw new Exception("资料编码已存在，请重新输入");
            }
        }
        return attendencebasisMapper.updateAttendencebasis(attendencebasis);
    }

    /**
     * 批量删除员工出勤基本资料维护
     *
     * @param ids 需要删除的员工出勤基本资料维护主键
     * @return 结果
     */
    @Override
    public int deleteAttendencebasisByIds(Long[] ids)
    {
        return attendencebasisMapper.deleteAttendencebasisByIds(ids);
    }

    /**
     * 删除员工出勤基本资料维护信息
     *
     * @param id 员工出勤基本资料维护主键
     * @return 结果
     */
    @Override
    public int deleteAttendencebasisById(Long id) throws Exception
    {
        List oldAttendencebasis = attendencebasisMapper.selectAttendencebasisByParentid(id);
        if(!oldAttendencebasis.isEmpty()){
            throw new Exception("该资料下存在子节点，不可删除");
        }
        return attendencebasisMapper.deleteAttendencebasisById(id);
    }

    /**
     * 查询员工出勤基本资料维护选单
     *
     * @param code 员工出勤基本资料维护编码
     * @return 结果
     */
    @Override
    public List<BasisOptionsDTO> selectBasisOptions(String code) {
        Long parentid = attendencebasisMapper.selectAttendencebasisParentByCode(code).getId();
        List<BasisOptionsDTO> list = attendencebasisMapper.selectAttendencebasisByParentid(parentid);
        return list;
    }
}
