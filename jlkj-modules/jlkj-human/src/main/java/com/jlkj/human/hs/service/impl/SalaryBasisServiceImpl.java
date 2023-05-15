package com.jlkj.human.hs.service.impl;

import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hd.dto.OptinonTypeDTO;
import com.jlkj.human.hs.domain.SalaryBasis;
import com.jlkj.human.hs.mapper.SalaryBasisMapper;
import com.jlkj.human.hs.service.ISalaryBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 薪资资料Service业务层处理
 *
 * @author 266861
 * @date 2023-05-09
 */
@Service
public class SalaryBasisServiceImpl implements ISalaryBasisService
{
    @Autowired
    private SalaryBasisMapper salaryBasisMapper;

    /**
     * 查询薪资资料
     *
     * @param id 薪资资料主键
     * @return 薪资资料
     */
    @Override
    public SalaryBasis selectSalaryBasisById(Long id)
    {
        return salaryBasisMapper.selectSalaryBasisById(id);
    }

    /**
     * 查询薪资资料列表
     *
     * @param salaryBasis 薪资资料
     * @return 薪资资料
     */
    @Override
    public List<SalaryBasis> selectSalaryBasisList(SalaryBasis salaryBasis)
    {
        return salaryBasisMapper.selectSalaryBasisList(salaryBasis);
    }

    /**
     * 查询是否有重复编码数据
     *
     * @param salaryBasis 员工薪资基本资料
     * @return 员工出勤基本资料维护
     */
    @Override
    public int queryRepetitivedata(SalaryBasis salaryBasis){
        return salaryBasisMapper.queryRepetitivedata(salaryBasis);
    }

    /**
     * 新增薪资资料
     *
     * @param salaryBasis 薪资资料
     * @return 结果
     */
    @Override
    public int insertSalaryBasis(SalaryBasis salaryBasis) throws Exception
    {
        if(salaryBasis.getParentid()==1){
            int i =salaryBasisMapper.queryRepetitivedata(salaryBasis);
            if(i>0){
                throw new Exception("资料编码已存在，请重新输入");
            }
        }
        SalaryBasis info = salaryBasisMapper.selectSalaryBasisById(salaryBasis.getParentid());
        salaryBasis.setParents(info.getParents() + "," + salaryBasis.getParentid());
        return salaryBasisMapper.insertSalaryBasis(salaryBasis);
    }

    /**
     * 修改薪资资料
     *
     * @param salaryBasis 薪资资料
     * @return 结果
     */
    @Override
    public int updateSalaryBasis(SalaryBasis salaryBasis)throws Exception
    {
        if(salaryBasis.getParentid()==1) {
            int i = salaryBasisMapper.queryRepetitivedata(salaryBasis);
            if (i > 0) {
                throw new Exception("资料编码已存在，请重新输入");
            }
        }
        return salaryBasisMapper.updateSalaryBasis(salaryBasis);
    }


    /**
     * 批量删除薪资资料
     *
     * @param ids 需要删除的薪资资料主键
     * @return 结果
     */
    @Override
    public int deleteSalaryBasisByIds(Long[] ids)
    {
        return salaryBasisMapper.deleteSalaryBasisByIds(ids);
    }

    /**
     * 删除薪资资料信息
     *
     * @param id 薪资资料主键
     * @return 结果
     */
    @Override
    public int deleteSalaryBasisById(Long id)throws Exception
    {
        List oldData = salaryBasisMapper.selectSalaryBasisByParentid(id);
        if(!oldData.isEmpty()){
            throw new Exception("该资料下存在子节点，不可删除");
        }
        return salaryBasisMapper.deleteSalaryBasisById(id);
    }

    /**
     * 通过父节点id查询员工薪资基本资料维护
     *
     * @param parentid 员工薪资基本资料维护编码
     * @return 员工薪资基本资料维护
     */
    @Override
    public List<SalaryBasis> selectSalaryBasisByParentid(Long  parentid){
        return salaryBasisMapper.selectSalaryBasisByParentid(parentid);
    }

    /**
     * 查询员工薪资基本资料维护选单
     *
     * @param salaryBasis 员工薪资基本资料维护
     * @return 结果
     */
    @Override
    public List<BasisOptionsDTO> selectBasisOptions(SalaryBasis salaryBasis) {
        Long parentid = salaryBasisMapper.selectSalaryParentByCode(salaryBasis).getId();
        List<BasisOptionsDTO> list = salaryBasisMapper.selectSalaryByParentid(parentid);
        return list;
    }

    /**
     * 获取薪资作业下拉选单主项
     *
     * @param optinonType 选单查询条件
     * @return 结果
     */
    @Override
    public HashMap<String, List<BasisOptionsDTO>> getBasisOptions(OptinonTypeDTO optinonType) {
        List<String> optionsType = optinonType.getOptionsType();
        HashMap<String, List<BasisOptionsDTO>> map = new HashMap<>(16);
        for (String item : optionsType) {
            try{
                SalaryBasis items = new SalaryBasis();
                items.setInfoCode(item);
                items.setCompId(optinonType.getCompId());
                List<BasisOptionsDTO> list = selectBasisOptions(items);
                map.put(item, list);
            } catch (Exception e) {

            }
        }
        return map;
    }

    /**
     * 获取薪资作业下拉选单细项
     *
     * @param optinonType 选单查询条件
     * @return 结果
     */
    @Override
    public List<BasisOptionsDTO> getDeepOptions(OptinonTypeDTO optinonType){
        List<BasisOptionsDTO> list = null;
        try{
            list = salaryBasisMapper.selectSalaryByParentid(optinonType.getId());
        } catch (Exception e) {

        }
        return list;
    }
}
