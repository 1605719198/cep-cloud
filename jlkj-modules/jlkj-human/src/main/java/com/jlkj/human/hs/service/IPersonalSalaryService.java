package com.jlkj.human.hs.service;

import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hs.domain.PersonalSalary;
import com.jlkj.human.hs.domain.ProjectPay;

import java.util.List;

/**
 * 薪资核定Service接口
 * 
 * @author 266861
 * @date 2023-06-01
 */
public interface IPersonalSalaryService 
{
    /**
     * 查询薪资核定
     * 
     * @param id 薪资核定主键
     * @return 薪资核定
     */
    public PersonalSalary selectPersonalSalaryById(String id);

    /**
     * 查询薪资核定列表
     * 
     * @param personalSalary 薪资核定
     * @return 薪资核定集合
     */
    public List<PersonalSalary> selectPersonalSalaryList(PersonalSalary personalSalary);

    /**
     * 新增薪资核定
     * 
     * @param personalSalary 薪资核定
     * @return 结果
     */
    public int insertPersonalSalary(PersonalSalary personalSalary);

    /**
     * 修改薪资核定
     * 
     * @param personalSalary 薪资核定
     * @return 结果
     */
    public int updatePersonalSalary(PersonalSalary personalSalary);

    /**
     * 批量删除薪资核定
     * 
     * @param ids 需要删除的薪资核定主键集合
     * @return 结果
     */
    public int deletePersonalSalaryByIds(String[] ids);

    /**
     * 删除薪资核定信息
     * 
     * @param id 薪资核定主键
     * @return 结果
     */
    public int deletePersonalSalaryById(String id);

    /**
     * 查询公司薪资支付银行编码，名称ID
     *
     * @param compId 公司别
     * @return 结果
     */
    public List<BasisOptionsDTO> getSalaryBank(String compId);


    /**
     * 查询公司薪资薪酬项目
     *
     * @param compId 公司别
     * @return 结果
     */
    public List<ProjectPay> getPayFormation(String compId);
}
