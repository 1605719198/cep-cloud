package com.jlkj.human.hs.service;

import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hs.domain.PersonalSalary;
import com.jlkj.human.hs.dto.PersonalSalaryBankDTO;
import com.jlkj.human.hs.dto.PersonalSalaryDTO;
import com.jlkj.human.hs.dto.PersonalSalaryDetailDTO;

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
     * @param setEndPayDate 是否修改止薪日期
     * @return 结果
     */
    public int updatePersonalSalary(PersonalSalary personalSalary,Boolean setEndPayDate);

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
    public List<PersonalSalaryDetailDTO> getPayFormation(String compId);

    /**
     * 导入薪资核定维护数据
     *
     * @param dtos 薪资核定资料列表
     * @param isUpdateSupport 是否新增
     * @param compId 公司别
     * @param operName 操作用户
     * @return 结果
     */
    public String importSalary(List<PersonalSalaryDTO> dtos, Boolean isUpdateSupport, String operName, String compId);

    /**
     * 导入银行账号数据
     *
     * @param dtos 银行账号资料列表
     * @param isUpdateSupport 是否只修改最新一笔
     * @param compId 公司别
     * @param operName 操作用户
     * @return 结果
     */
    public String importBankNo(List<PersonalSalaryBankDTO> dtos, Boolean isUpdateSupport, String operName, String compId);

    /**
     * 修改薪资核定银行账号数据
     *
     * @param id 要改变的数据的id
     * @param bankDTO 银行账号数据
     * @param compId 公司别
     * @return 结果
     */
    public int updatePersonalSalaryBank(String compId,String id,PersonalSalaryBankDTO bankDTO);
}
