package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.PersonalIncomeTax;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 个人所得税比例维护Mapper接口
 * 
 * @author jiangbingchen
 * @date 2023-05-18
 */
public interface PersonalIncomeTaxMapper 
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
     * @param personalIncomeTax 个人所得税比例维护
     * @return 结果
     */
    public int insertPersonalIncomeTax(PersonalIncomeTax personalIncomeTax);

    /**
     * 修改个人所得税比例维护
     * 
     * @param personalIncomeTax 个人所得税比例维护
     * @return 结果
     */
    public int updatePersonalIncomeTax(PersonalIncomeTax personalIncomeTax);

    /**
     * 删除个人所得税比例维护
     * 
     * @param id 个人所得税比例维护主键
     * @return 结果
     */
    public int deletePersonalIncomeTaxById(String id);

    /**
     * 批量删除个人所得税比例维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonalIncomeTaxByIds(String[] ids);

    /**
     * 版本号列表
     *
     * @param type 类别
     * @return 结果
     */
    List<PersonalIncomeTax> getVersionList(String type);

    /**
     * 获取最大版本号
     *
     * @param type 类别
     * @return 结果
     */
    Map<String,Object> selectMaxVersionNo(String type);

    /**
     * 删除个人所得税数据
     * @Description 删除个人所得税数据
     * @param type 类别
     * @param versionNo 版本号
     * @return 个人所得税删除结果
     * @author 267383
     * @Date 2023/6/26 15:35
     **/
    public int deletePersonalIncomeTaxByVersionNo(@Param("type") String type, @Param("versionNo") Long versionNo);
}
