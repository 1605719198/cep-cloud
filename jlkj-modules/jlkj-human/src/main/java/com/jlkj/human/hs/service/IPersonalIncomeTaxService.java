package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.PersonalIncomeTax;

import java.util.List;
import java.util.Map;

/**
 * 个人所得税比例维护Service接口
 * 
 * @author jiangbingchen
 * @date 2023-05-18
 */
public interface IPersonalIncomeTaxService 
{
    /**
     * 查询个人所得税比例维护
     * 
     * @param id 个人所得税比例维护主键
     * @return 个人所得税比例维护
     */
    public PersonalIncomeTax selectPersonalIncomeTaxById(String id);

    /**
     * 查询个人所得税比例维护列表
     * 
     * @param personalIncomeTax 个人所得税比例维护
     * @return 个人所得税比例维护集合
     */
    public List<PersonalIncomeTax> selectPersonalIncomeTaxList(PersonalIncomeTax personalIncomeTax);

    /**
     * 新增个人所得税比例维护
     * 
     * @param personalIncomeTaxList 个人所得税比例维护
     * @return 结果
     */
    public int insertPersonalIncomeTax(List<PersonalIncomeTax> personalIncomeTaxList);

    /**
     * 修改个人所得税比例维护
     * 
     * @param personalIncomeTax 个人所得税比例维护
     * @return 结果
     */
    public int updatePersonalIncomeTax(PersonalIncomeTax personalIncomeTax);

    /**
     * 批量删除个人所得税比例维护
     * 
     * @param ids 需要删除的个人所得税比例维护主键集合
     * @return 结果
     */
    public int deletePersonalIncomeTaxByIds(String[] ids);

    /**
     * 删除个人所得税比例维护信息
     * 
     * @param id 个人所得税比例维护主键
     * @return 结果
     */
    public int deletePersonalIncomeTaxById(String id);

    /**
     * 版本号列表
     * @return
     */
    List<Map<String,Long>> getVersionList(String type);
}
