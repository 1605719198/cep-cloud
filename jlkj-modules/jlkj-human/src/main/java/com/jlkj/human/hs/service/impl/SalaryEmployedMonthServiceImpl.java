package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SalaryEmployedMonth;
import com.jlkj.human.hs.mapper.SalaryEmployedMonthMapper;
import com.jlkj.human.hs.service.ISalaryEmployedMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

/**
 * 员工受雇月维护Service业务层处理
 *
 * @author 267383
 * @date 2023-05-30
 */
@Service
public class SalaryEmployedMonthServiceImpl implements ISalaryEmployedMonthService
{
    @Autowired
    private SalaryEmployedMonthMapper salaryEmployedMonthMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询员工受雇月维护
     *
     * @param id 员工受雇月维护主键
     * @return 员工受雇月维护
     */
    @Override
    public SalaryEmployedMonth selectSalaryEmployedMonthById(String id)
    {
        return salaryEmployedMonthMapper.selectSalaryEmployedMonthById(id);
    }

    /**
     * 查询员工受雇月维护列表
     *
     * @param salaryEmployedMonth 员工受雇月维护
     * @return 员工受雇月维护
     */
    @Override
    public List<SalaryEmployedMonth> selectSalaryEmployedMonthList(SalaryEmployedMonth salaryEmployedMonth)
    {
        return salaryEmployedMonthMapper.selectSalaryEmployedMonthList(salaryEmployedMonth);
    }

    /**
     * 新增员工受雇月维护
     *
     * @param salaryEmployedMonthList 员工受雇月维护
     * @return 结果
     */
    @Override
    public int insertSalaryEmployedMonth(List<SalaryEmployedMonth> salaryEmployedMonthList) {
        for (SalaryEmployedMonth salaryEmployedMonth : salaryEmployedMonthList) {
            SalaryEmployedMonth param = new SalaryEmployedMonth();
            param.setEmpNo(salaryEmployedMonth.getEmpNo());
            List<SalaryEmployedMonth> salaryEmployedMonths = salaryEmployedMonthMapper.selectSalaryEmployedMonthList(param);
            for (SalaryEmployedMonth item : salaryEmployedMonths) {
                if (!item.getEmpNo().isEmpty()) {
                    throw new ServiceException("员工不允许重复");
                }
            }
            if (salaryEmployedMonth.getId() != null) {
                salaryEmployedMonth.setCreatorId(SecurityUtils.getUserId().toString());
                salaryEmployedMonth.setCreator(SecurityUtils.getNickName());
                salaryEmployedMonth.setCreateDate(new Date());
                salaryEmployedMonthMapper.updateSalaryEmployedMonth(salaryEmployedMonth);
            } else {
                salaryEmployedMonth.setId(UUID.randomUUID().toString().substring(0, 32));
                salaryEmployedMonthMapper.insertSalaryEmployedMonth(salaryEmployedMonth);
            }
        }
        return 1;
    }

    /**
     * 修改员工受雇月维护
     *
     * @param salaryEmployedMonth 员工受雇月维护
     * @return 结果
     */
    @Override
    public int updateSalaryEmployedMonth(SalaryEmployedMonth salaryEmployedMonth)
    {
        return salaryEmployedMonthMapper.updateSalaryEmployedMonth(salaryEmployedMonth);
    }

    /**
     * 批量删除员工受雇月维护
     *
     * @param ids 需要删除的员工受雇月维护主键
     * @return 结果
     */
    @Override
    public int deleteSalaryEmployedMonthByIds(String[] ids)
    {
        return salaryEmployedMonthMapper.deleteSalaryEmployedMonthByIds(ids);
    }

    /**
     * 删除员工受雇月维护信息
     *
     * @param id 员工受雇月维护主键
     * @return 结果
     */
    @Override
    public int deleteSalaryEmployedMonthById(String id)
    {
        return salaryEmployedMonthMapper.deleteSalaryEmployedMonthById(id);
    }

    /**
     * 导入员工受雇月维护数据
     *
     * @param salaryEmployedMonthList 员工受雇月维护列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SalaryEmployedMonth> salaryEmployedMonthList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(salaryEmployedMonthList) || salaryEmployedMonthList.size() == 0)
        {
            throw new ServiceException("导入员工受雇月维护数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SalaryEmployedMonth salaryEmployedMonth : salaryEmployedMonthList)
        {
            try
            {
                    BeanValidators.validateWithException(validator, salaryEmployedMonth);
                    salaryEmployedMonth.setId(UUID.randomUUID().toString().substring(0, 32));
                    salaryEmployedMonth.setCreator(operName);
                    salaryEmployedMonthMapper.insertSalaryEmployedMonth(salaryEmployedMonth);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、工号 ").append(salaryEmployedMonth.getEmpNo()).append(" 导入成功");
                if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, salaryEmployedMonth);
                    salaryEmployedMonthMapper.updateSalaryEmployedMonth(salaryEmployedMonth);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、工号 ").append(salaryEmployedMonth.getEmpNo()).append(" 更新成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工号 " + salaryEmployedMonth.getEmpNo() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
