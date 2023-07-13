package com.jlkj.human.pa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.context.SecurityContextHolder;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.service.IPersonnelService;
import com.jlkj.human.pa.domain.YearPerformance;
import com.jlkj.human.pa.dto.YearPerformanceDTO;
import com.jlkj.human.pa.mapper.YearPerformanceMapper;
import com.jlkj.human.pa.service.IYearPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;

/**
 * 年度绩效主档Service业务层处理
 *
 * @author 267383
 * @date 2023-06-09
 */
@Service
public class YearPerformanceServiceImpl extends ServiceImpl<YearPerformanceMapper, YearPerformance>
implements IYearPerformanceService {
    @Autowired
    private YearPerformanceMapper yearPerformanceMapper;
    @Autowired
    protected Validator validator;
    @Resource
    private IPersonnelService personnelService;

    /**
     * 查询年度绩效主档
     *
     * @param id 年度绩效主档主键
     * @return 年度绩效主档
     */
    @Override
    public YearPerformance selectYearPerformanceById(String id)
    {
        return yearPerformanceMapper.selectYearPerformanceById(id);
    }

    /**
     * 查询年度绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param yearPerformance 查询参数集
     * @return list 分页数据
     */
    @Override
    public List<YearPerformance> listYearPerformance(YearPerformance yearPerformance){
        List<YearPerformance> list = lambdaQuery()
                .eq(YearPerformance::getCompId, yearPerformance.getCompId())
                .eq(YearPerformance::getYear, yearPerformance.getYear())
                .eq(StringUtils.isNotBlank(yearPerformance.getEmpId()), YearPerformance::getEmpId, yearPerformance.getEmpId())
                .eq(StringUtils.isNotBlank(yearPerformance.getDept()), YearPerformance::getDept, yearPerformance.getDept()).list();
        return list;
    }

    /**
     * 查询年度绩效主档列表
     *
     * @param yearPerformance 年度绩效主档
     * @return 年度绩效主档
     */
    @Override
    public List<YearPerformance> selectYearPerformanceList(YearPerformance yearPerformance)
    {
        return yearPerformanceMapper.selectYearPerformanceList(yearPerformance);
    }

    /**
     * 新增年度绩效主档
     * @author HuangBing
     * @date 2023-06-20
     * @param yearPerformance 年度绩效主档
     * @return 新增结果
     */
    @Override
    public Object insertYearPerformance(YearPerformance yearPerformance)
    {
        yearPerformance.setStatus(Constants.STR_ONE);
        yearPerformance.setScore(yearPerformance.getYearScore());
        save(yearPerformance);
        return AjaxResult.success();
    }

    /**
     * 修改年度绩效主档
     *
     * @param yearPerformance 年度绩效主档
     * @return 结果
     */
    @Override
    public int updateYearPerformance(YearPerformance yearPerformance)
    {
        return yearPerformanceMapper.updateYearPerformance(yearPerformance);
    }

    /**
     * 批量删除年度绩效主档
     *
     * @param ids 需要删除的年度绩效主档主键
     * @return 结果
     */
    @Override
    public int deleteYearPerformanceByIds(String[] ids)
    {
        return yearPerformanceMapper.deleteYearPerformanceByIds(ids);
    }

    /**
     * 删除年度绩效主档信息
     *
     * @param id 年度绩效主档主键
     * @return 结果
     */
    @Override
    public int deleteYearPerformanceById(String id)
    {
        return yearPerformanceMapper.deleteYearPerformanceById(id);
    }

    /**
     * 呈送计划
     * @author HuangBing
     * @date 2023-6-20
     * @param yearPerformanceDTO 表格数据
     * @return 呈送结果
     */
    @Override
    public Object sendYearPerformance(YearPerformanceDTO yearPerformanceDTO){
        for (YearPerformance item : yearPerformanceDTO.getYearPerformanceList()){
            item.setStatus(Constants.STR_TWO);
        }
        updateBatchById(yearPerformanceDTO.getYearPerformanceList());
        return AjaxResult.success("呈送成功");
    }

    /**
     * 最终复核
     * @author HuangBing
     * @date 2023-6-20
     * @param yearPerformanceDTO 表格数据
     * @return 最终复核结果
     */
    @Override
    public Object finalCheckYearPerformance(YearPerformanceDTO yearPerformanceDTO){
        for (YearPerformance item : yearPerformanceDTO.getYearPerformanceList()){
            item.setAdmEmpId(SecurityContextHolder.getUserName());
            item.setAdmScore(item.getYearScore());
            item.setStatus(Constants.STR_THREE);
        }
        updateBatchById(yearPerformanceDTO.getYearPerformanceList());
        return AjaxResult.success("最终复核成功");
    }

    /**
     * 导入年度绩效数据
     * @author HuangBing
     * @date 2023-6-20
     * @param yearPerformanceList 年度绩效数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importYearPerformance(List<YearPerformance> yearPerformanceList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(yearPerformanceList) || yearPerformanceList.size() == 0)
        {
            throw new ServiceException("导入年度绩效数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        Personnel personnel = new Personnel();
        for (YearPerformance yearPerformance : yearPerformanceList)
        {
            try
            {
                // 验证是否存在这笔数据
                YearPerformance u = lambdaQuery().eq(YearPerformance::getEmpId, yearPerformance.getEmpId()).one();
                personnel.setEmpNo(yearPerformance.getEmpId());
                List<Personnel> list = personnelService.getPersonnelBasicInfoList(personnel);
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, yearPerformance);
                    yearPerformance.setDept(list.get(0).getDepartmentName());
                    yearPerformance.setCreator(operName);
                    save(yearPerformance);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + yearPerformance.getEmpId() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, yearPerformance);
                    yearPerformance.setDept(list.get(0).getDepartmentName());
                    yearPerformance.setCreator(operName);
                    lambdaUpdate().eq(YearPerformance::getEmpId, yearPerformance.getEmpId()).update();
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + yearPerformance.getEmpId() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、工号 " + yearPerformance.getEmpId() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工号 " + yearPerformance.getEmpId() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
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
