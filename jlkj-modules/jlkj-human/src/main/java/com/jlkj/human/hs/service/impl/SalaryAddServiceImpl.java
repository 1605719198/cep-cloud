package com.jlkj.human.hs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.service.impl.PersonnelServiceImpl;
import com.jlkj.human.hs.domain.*;
import com.jlkj.human.hs.mapper.SalaryAddMapper;
import com.jlkj.human.hs.service.ISalaryAddService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author HuangBing
* @description 针对表【human_hs_salary_add(薪资项目金额追补扣)】的数据库操作Service实现
* @createDate 2023-05-30 18:19:49
*/
@Service
public class SalaryAddServiceImpl extends ServiceImpl<SalaryAddMapper, SalaryAdd>
    implements ISalaryAddService {

    @Resource
    protected Validator validator;

    @Resource
    private SalaryNoteServiceImpl salaryNoteService;

    @Resource
    private SalaryStatusServiceImpl salaryStatusService;

    @Resource
    private PersonnelServiceImpl personnelService;

    @Resource
    private SalaryProjectBasisServiceImpl salaryProjectBasisService;

    /**
     * 导入薪资项目追补数据
     *
     * @param salaryAddList 薪资项目追补列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SalaryAdd> salaryAddList, Boolean isUpdateSupport, String operName, String compId, String date)
    {
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<SalaryStatus> list = salaryStatusService.lambdaQuery()
                .eq(SalaryStatus::getPayTypeNo, "02")
                .eq(SalaryStatus::getYear, date.substring(0, 3))
                .eq(SalaryStatus::getMonth, date.substring(5, 6))
                .eq(SalaryStatus::getCompId, compId).list();
        if (list.isEmpty()){
            if (StringUtils.isNull(salaryAddList) || salaryAddList.size() == 0)
            {
                throw new ServiceException("导入薪资项目追补数据不能为空！");
            }
            SalaryNote salaryNote = new SalaryNote();
            salaryNote.setCompId(compId);
            salaryNote.setStatus("1");
            salaryNote.setPayType("4");
            salaryNote.setMonth(date);
            salaryNote.setCreator(SecurityUtils.getNickName());
            salaryNote.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
            salaryNoteService.save(salaryNote);
            List<SalaryAdd> salaryAddList1 = salaryAddList.stream().filter(item -> null != item.getEmpId()).filter(item -> !item.getEmpId().isEmpty()).collect(Collectors.toList());
            for (SalaryAdd salaryAdd : salaryAddList1)
            {
                try
                {
                    // 验证是否存在这笔数据
                    SalaryAdd u = lambdaQuery().eq(SalaryAdd::getEmpId, salaryAdd.getEmpId()).one();
                    // 验证工号属于导入的公司
                    Personnel personnel = personnelService.lambdaQuery().eq(Personnel::getCompId, salaryAdd.getCompId()).eq(Personnel::getEmpNo, salaryAdd.getEmpId()).one();
                    // 验证薪资项目编码是否正确
                    SalaryProjectBasis salaryProjectBasis = salaryProjectBasisService.lambdaQuery().eq(SalaryProjectBasis::getPayProCode, salaryAdd.getPayTabCode()).one();
                    if (StringUtils.isNull(u) && StringUtils.isNotNull(personnel) && StringUtils.isNotNull(salaryProjectBasis) && "Y".equals(salaryProjectBasis.getIfEnd()))
                    {
                        BeanValidators.validateWithException(validator, salaryAdd);
                        salaryAdd.setCreator(operName);
                        save(salaryAdd);
                        salaryNote.setStatus("1");
                        salaryNoteService.save(salaryNote);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、工号 " + salaryAdd.getEmpId() + " 导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        BeanValidators.validateWithException(validator, salaryAdd);
                        salaryAdd.setCreator(operName);
                        lambdaUpdate().eq(SalaryAdd::getEmpId, salaryAdd.getEmpId()).update();
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、工号 " + salaryAdd.getEmpId() + " 更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、工号 " + salaryAdd.getEmpId() + " 已存在");
                    }
                }
                catch (Exception e)
                {
                    salaryNote.setStatus("2");
                    salaryNoteService.save(salaryNote);
                    SalaryError salaryError = new SalaryError();
                    salaryError.setPayType("4");
                    salaryError.setCompId(salaryAdd.getCompId());
                    salaryError.setEmpNo(salaryAdd.getEmpId());
                    salaryError.setMark(e.getMessage());
                    salaryError.setCreator(SecurityUtils.getNickName());
                    salaryError.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、工号 " + salaryAdd.getEmpId() + " 导入失败：";
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
        } else {
            return failureMsg.append("<br/>已有资料，无法导入").toString();
        }
    }

    /**
     * 导入专项附加追补数据
     *
     * @param salaryAddList 专项附加追补列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser1(List<SalaryAdd> salaryAddList, Boolean isUpdateSupport, String operName, String compId, String date)
    {
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<SalaryStatus> list = salaryStatusService.lambdaQuery()
                .eq(SalaryStatus::getPayTypeNo, "02")
                .eq(SalaryStatus::getYear, date.substring(0, 3))
                .eq(SalaryStatus::getMonth, date.substring(5, 6))
                .eq(SalaryStatus::getCompId, compId).list();
        if (list.isEmpty()){
            if (StringUtils.isNull(salaryAddList) || salaryAddList.size() == 0)
            {
                throw new ServiceException("导入专项附加追补数据不能为空！");
            }
            SalaryNote salaryNote = new SalaryNote();
            salaryNote.setCompId(compId);
            salaryNote.setStatus("1");
            salaryNote.setPayType("4");
            salaryNote.setMonth(date);
            salaryNote.setCreator(SecurityUtils.getNickName());
            salaryNote.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
            salaryNoteService.save(salaryNote);
            List<SalaryAdd> salaryAddList1 = salaryAddList.stream().filter(item -> null != item.getEmpId()).filter(item -> !item.getEmpId().isEmpty()).collect(Collectors.toList());
            for (SalaryAdd salaryAdd : salaryAddList1)
            {
                try
                {
                    // 验证是否存在这笔数据
                    SalaryAdd u = lambdaQuery().eq(SalaryAdd::getEmpId, salaryAdd.getEmpId()).one();
                    // 验证工号属于导入的公司
                    Personnel personnel = personnelService.lambdaQuery().eq(Personnel::getCompId, salaryAdd.getCompId()).eq(Personnel::getEmpNo, salaryAdd.getEmpId()).one();
                    // 验证薪资项目编码是否正确
                    SalaryProjectBasis salaryProjectBasis = salaryProjectBasisService.lambdaQuery().eq(SalaryProjectBasis::getPayProCode, salaryAdd.getPayTabCode()).one();
                    if (StringUtils.isNull(u) && StringUtils.isNotNull(personnel) && StringUtils.isNotNull(salaryProjectBasis))
                    {
                        BeanValidators.validateWithException(validator, salaryAdd);
                        salaryAdd.setCreator(operName);
                        save(salaryAdd);
                        salaryNote.setStatus("1");
                        salaryNoteService.save(salaryNote);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、工号 " + salaryAdd.getEmpId() + " 导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        BeanValidators.validateWithException(validator, salaryAdd);
                        salaryAdd.setCreator(operName);
                        lambdaUpdate().eq(SalaryAdd::getEmpId, salaryAdd.getEmpId()).update();
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、工号 " + salaryAdd.getEmpId() + " 更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、工号 " + salaryAdd.getEmpId() + " 已存在");
                    }
                }
                catch (Exception e)
                {
                    salaryNote.setStatus("2");
                    salaryNoteService.save(salaryNote);
                    SalaryError salaryError = new SalaryError();
                    salaryError.setPayType("4");
                    salaryError.setCompId(salaryAdd.getCompId());
                    salaryError.setEmpNo(salaryAdd.getEmpId());
                    salaryError.setMark(e.getMessage());
                    salaryError.setCreator(SecurityUtils.getNickName());
                    salaryError.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、工号 " + salaryAdd.getEmpId() + " 导入失败：";
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
        } else {
            return failureMsg.append("<br/>已有资料，无法导入").toString();
        }
    }

}




