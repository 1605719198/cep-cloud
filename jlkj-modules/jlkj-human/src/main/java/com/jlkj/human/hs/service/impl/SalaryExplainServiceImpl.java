package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SalaryExplain;
import com.jlkj.human.hs.mapper.SalaryExplainMapper;
import com.jlkj.human.hs.service.ISalaryExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 各公司薪资发放说明Service业务层处理
 *
 * @author 266861
 * @date 2023-05-25
 */
@Service
public class SalaryExplainServiceImpl implements ISalaryExplainService
{
    @Autowired
    private SalaryExplainMapper salaryExplainMapper;

    /**
     * 查询各公司薪资发放说明
     *
     * @param uuid 各公司薪资发放说明主键
     * @return 各公司薪资发放说明
     */
    @Override
    public SalaryExplain selectSalaryExplainByUuid(String uuid)
    {
        return salaryExplainMapper.selectSalaryExplainByUuid(uuid);
    }

    /**
     * 查询各公司薪资发放说明列表
     *
     * @param salaryExplain 各公司薪资发放说明
     * @return 各公司薪资发放说明
     */
    @Override
    public List<SalaryExplain> selectSalaryExplainList(SalaryExplain salaryExplain)
    {
        String[] monthArray = {"12","01","02","03","04","05","06","07","08","09","10","11"};
        String compId = salaryExplain.getCompId();
        Long year = salaryExplain.getYear();
        String maxMonth = "12";
        String yearMonth= null;
        // 创建 HashMap 对象 Sites
        HashMap<String, SalaryExplain> Sites = new HashMap<String, SalaryExplain>();
        for(String month:monthArray){
            Sites.put(month,null);
        }
        List<SalaryExplain> salaryExplainList = salaryExplainMapper.selectSalaryExplainList(salaryExplain);
        for(SalaryExplain salaryExplain1 : salaryExplainList){
            if(!maxMonth.equals(salaryExplain1.getMonth())){
                yearMonth = salaryExplain1.getYear()+salaryExplain1.getMonth();
            }else{
                yearMonth = (salaryExplain1.getYear() - 1) +salaryExplain1.getMonth();
            }
            salaryExplain1.setYearMonth(yearMonth);
            Sites.put(salaryExplain1.getMonth(),salaryExplain1);
        }
        Set<String> set = Sites.keySet();
        for (String month : set) {
            if(Sites.get(month)==null){
                SalaryExplain salaryExplain2 = new SalaryExplain();
                salaryExplain2.setCompId(compId);
                salaryExplain2.setYear(year);
                salaryExplain2.setMonth(month);
                if(!maxMonth.equals(month)){
                    yearMonth = year+month;
                }else{
                    yearMonth = (year-1)+month;
                }
                salaryExplain2.setYearMonth(yearMonth);
                Sites.put(month,salaryExplain2);
            }
        }
        Collection<SalaryExplain> list = Sites.values();
        List<SalaryExplain> finnalList = new ArrayList<>();
        for(String month:monthArray){
            finnalList.add(Sites.get(month));
        }
        return finnalList;
    }

    /**
     * 新增各公司薪资发放说明
     *
     * @param salaryExplain 各公司薪资发放说明
     * @return 结果
     */
    @Override
    public int insertSalaryExplain(SalaryExplain salaryExplain)
    {
        return salaryExplainMapper.insertSalaryExplain(salaryExplain);
    }

    /**
     * 保存各公司薪资发放说明
     *
     * @param salaryExplainList 各公司薪资发放说明列表
     * @return 结果
     */
    @Override
    public int saveSalaryExplain(List<SalaryExplain> salaryExplainList){
        for(SalaryExplain salaryExplain :salaryExplainList){
            salaryExplain.setCreatorId(SecurityUtils.getUserId().toString());
            salaryExplain.setCreatorNo(SecurityUtils.getUsername());
            salaryExplain.setCreator(SecurityUtils.getNickName());
            salaryExplain.setCreateDate(new Date());
            if(salaryExplain.getUuid()!=null){
                salaryExplainMapper.updateSalaryExplain(salaryExplain);

            }else{
                salaryExplain.setUuid(UUID.randomUUID().toString().substring(0, 32));
                salaryExplainMapper.insertSalaryExplain(salaryExplain);
            }
        }
        return 1;
    }

    /**
     * 修改各公司薪资发放说明
     *
     * @param salaryExplain 各公司薪资发放说明
     * @return 结果
     */
    @Override
    public int updateSalaryExplain(SalaryExplain salaryExplain)
    {
        return salaryExplainMapper.updateSalaryExplain(salaryExplain);
    }

    /**
     * 批量删除各公司薪资发放说明
     *
     * @param uuids 需要删除的各公司薪资发放说明主键
     * @return 结果
     */
    @Override
    public int deleteSalaryExplainByUuids(String[] uuids)
    {
        return salaryExplainMapper.deleteSalaryExplainByUuids(uuids);
    }

    /**
     * 删除各公司薪资发放说明信息
     *
     * @param uuid 各公司薪资发放说明主键
     * @return 结果
     */
    @Override
    public int deleteSalaryExplainByUuid(String uuid)
    {
        return salaryExplainMapper.deleteSalaryExplainByUuid(uuid);
    }
}
