package com.jlkj.human.hs.service.impl;

import com.jlkj.human.hs.domain.SalaryResult;
import com.jlkj.human.hs.dto.SalaryResultDTO;
import com.jlkj.human.hs.dto.SalaryResultDetailDTO;
import com.jlkj.human.hs.mapper.SalaryResultMapper;
import com.jlkj.human.hs.service.ISalaryResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 薪资计算结果Service业务层处理
 *
 * @author 266861
 * @date 2023-06-20
 */
@Service
public class SalaryResultServiceImpl implements ISalaryResultService
{
    @Autowired
    private SalaryResultMapper salaryResultMapper;

    /**
     * 查询薪资计算结果
     *
     * @param id 薪资计算结果主键
     * @return 薪资计算结果
     */
    @Override
    public SalaryResultDTO selectSalaryResultById(String id)
    {
        SalaryResultDTO dto = salaryResultMapper.selectSalaryResultById(id);
        List<SalaryResultDetailDTO> result = salaryResultMapper.selectDetailList(id);
        List<SalaryResultDetailDTO> list00 = new ArrayList<>();
        List<SalaryResultDetailDTO> list01 = new ArrayList<>();
        List<SalaryResultDetailDTO> list02 = new ArrayList<>();
        List<SalaryResultDetailDTO> list03 = new ArrayList<>();
        List<SalaryResultDetailDTO> list04 = new ArrayList<>();
        for(SalaryResultDetailDTO detailDTO: result){
            //追捕扣，个人加，个人减，企业支付，计算参数
            switch (detailDTO.getType()){
                case "00" :
                    list00.add(detailDTO);
                    break;
                case "01" :
                    list01.add(detailDTO);
                    break;
                case "02" :
                    list02.add(detailDTO);
                    break;
                case "03" :
                    list03.add(detailDTO);
                    break;
                case "04" :
                    list04.add(detailDTO);
                    break;
                default:
                    break;
            }
        }
        int length01 = list01.size();
        int length02 = list02.size();
        int length03 = list03.size();
        int length04 = list04.size();
        int [] array = {length01,length02,length03,length04};
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if(array[i] > max) {
                max = array[i];
            }
        }
        SalaryResultDetailDTO nullDto = new SalaryResultDetailDTO();
        //统一各list长度
        for(int i = length01;i<max;i++){
            list01.add(nullDto);
        }
        for(int i = length02;i<max;i++){
            list02.add(nullDto);
        }
        for(int i = length03;i<max;i++){
            list03.add(nullDto);
        }
        for(int i = length04;i<max;i++){
            list04.add(nullDto);
        }
        dto.setList00(list00);
        dto.setList01(list01);
        dto.setList02(list02);
        dto.setList03(list03);
        dto.setList04(list04);
        return dto;
    }

    /**
     * 查询薪资计算结果列表
     *
     * @param salaryResultDTO 薪资计算结果
     * @return 薪资计算结果
     */
    @Override
    public List<SalaryResultDTO> selectSalaryResultList(SalaryResultDTO salaryResultDTO)
    {
        return salaryResultMapper.selectSalaryResultList(salaryResultDTO);
    }

    /**
     * 新增薪资计算结果
     *
     * @param salaryResult 薪资计算结果
     * @return 结果
     */
    @Override
    public int insertSalaryResult(SalaryResult salaryResult)
    {
        return salaryResultMapper.insertSalaryResult(salaryResult);
    }

    /**
     * 修改薪资计算结果
     *
     * @param salaryResult 薪资计算结果
     * @return 结果
     */
    @Override
    public int updateSalaryResult(SalaryResult salaryResult)
    {
        return salaryResultMapper.updateSalaryResult(salaryResult);
    }

    /**
     * 批量删除薪资计算结果
     *
     * @param ids 需要删除的薪资计算结果主键
     * @return 结果
     */
    @Override
    public int deleteSalaryResultByIds(String[] ids)
    {
        return salaryResultMapper.deleteSalaryResultByIds(ids);
    }

    /**
     * 删除薪资计算结果信息
     *
     * @param id 薪资计算结果主键
     * @return 结果
     */
    @Override
    public int deleteSalaryResultById(String id)
    {
        return salaryResultMapper.deleteSalaryResultById(id);
    }
}
