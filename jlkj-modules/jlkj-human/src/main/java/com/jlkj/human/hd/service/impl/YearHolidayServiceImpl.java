package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.human.hd.domain.YearHoliday;
import com.jlkj.human.hd.mapper.YearHolidayMapper;
import com.jlkj.human.hd.service.IYearHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

/**
 * 年休假天数设定Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-04-12
 */
@Service
public class YearHolidayServiceImpl implements IYearHolidayService
{
    @Autowired
    private YearHolidayMapper yearHolidayMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询年休假天数设定
     *
     * @param id 年休假天数设定主键
     * @return 年休假天数设定
     */
    @Override
    public YearHoliday selectYearHolidayById(String id)
    {
        return yearHolidayMapper.selectYearHolidayById(id);
    }

    /**
     * 查询年休假天数设定
     *
     * @param yearHoliday 年休假天数设定主键
     * @return 年休假天数设定
     */
    @Override
    public List<YearHoliday> selectYearHolidayByempNo(YearHoliday yearHoliday)
    {
        return yearHolidayMapper.selectYearHolidayByempNo(yearHoliday);
    }

    /**
     * 查询年休假天数设定列表
     *
     * @param yearHoliday 年休假天数设定
     * @return 年休假天数设定
     */
    @Override
    public List<YearHoliday> selectYearHolidayList(YearHoliday yearHoliday)
    {
        return yearHolidayMapper.selectYearHolidayList(yearHoliday);
    }

    /**
     * 新增年休假天数设定
     *
     * @param yearHoliday 年休假天数设定
     * @return 结果
     */
    @Override
    public int insertYearHoliday(YearHoliday yearHoliday)
    {
        return yearHolidayMapper.insertYearHoliday(yearHoliday);
    }

    /**
     * 修改年休假天数设定
     *
     * @param yearHoliday 年休假天数设定
     * @return 结果
     */
    @Override
    public int updateYearHoliday(YearHoliday yearHoliday)
    {
        return yearHolidayMapper.updateYearHoliday(yearHoliday);
    }

    /**
     * @Description 修改某员工某年年休假数据
     * @param yearHoliday 年休假天数设定
     * @return 结果
     * @author 266861
     * @date 2023/6/29 13:37
     **/
    @Override
    public int updateYearHolidayByEmp(YearHoliday yearHoliday){
        return yearHolidayMapper.updateYearHolidayByEmp(yearHoliday);
    }

    /**
     * 批量删除年休假天数设定
     *
     * @param ids 需要删除的年休假天数设定主键
     * @return 结果
     */
    @Override
    public int deleteYearHolidayByIds(String[] ids)
    {
        return yearHolidayMapper.deleteYearHolidayByIds(ids);
    }

    /**
     * 删除年休假天数设定信息
     *
     * @param id 年休假天数设定主键
     * @return 结果
     */
    @Override
    public int deleteYearHolidayById(String id)
    {
        return yearHolidayMapper.deleteYearHolidayById(id);
    }


    /**
     * 导入年休数据
     *
     * @param yearHolidayList 用户年休假列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<YearHoliday> yearHolidayList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(yearHolidayList) || yearHolidayList.size() == 0)
        {
            throw new ServiceException("导入年休假数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (YearHoliday yearHoliday : yearHolidayList)
        {
            try
            {
                // 查询该员工是否有剩余年休假，如果有则进行清空，重新赋值可休天数，已休天数，剩余可休天数
                //
                YearHoliday u = yearHolidayMapper.selectYearHolidayByEmpNo(yearHoliday);
                if (!"0".equals(u.getPreYearDays().toString())) {
                    yearHoliday.setTotalDays(BigDecimal.valueOf(0));
                    yearHoliday.setRestDays(BigDecimal.valueOf(0));
                    yearHoliday.setPreYearDays(BigDecimal.valueOf(0));
                }
                BeanValidators.validateWithException(validator, yearHoliday);
                yearHoliday.setCreator(operName);
                yearHolidayMapper.updateYearHolidayByEmpNo(yearHoliday);
                successNum++;
                successMsg.append("<br/>" + successNum + "、工号 " + yearHoliday.getEmpNo() + " 导入成功");
                if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, yearHoliday);
                    yearHoliday.setCreator(operName);
                    //
                    yearHolidayMapper.updateYearHoliday(yearHoliday);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + yearHoliday.getEmpNo() + " 更新成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工号 " + yearHoliday.getEmpNo() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
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
