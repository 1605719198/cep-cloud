package com.jlkj.human.hs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.service.impl.PersonnelServiceImpl;
import com.jlkj.human.hs.domain.*;
import com.jlkj.human.hs.mapper.SalaryPunishmentMapper;
import com.jlkj.human.hs.service.ISalaryPunishmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author HuangBing
* @description 针对表【human_hs_salary_punishment(专业奖惩金额追补扣)】的数据库操作Service实现
* @createDate 2023-06-02 22:53:13
*/
@Service
public class SalaryPunishmentServiceImpl extends ServiceImpl<SalaryPunishmentMapper, SalaryPunishment>
    implements ISalaryPunishmentService {


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
     * 导入专业奖罚数据
     *
     * @param salaryPunishmentList 专业奖罚列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SalaryPunishment> salaryPunishmentList, Boolean isUpdateSupport, String operName, String compId, String date)
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
            if (StringUtils.isNull(salaryPunishmentList) || salaryPunishmentList.size() == 0)
            {
                throw new ServiceException("导入专业奖罚数据不能为空！");
            }
            SalaryNote salaryNote = new SalaryNote();
            salaryNote.setCompId(compId);
            salaryNote.setStatus("1");
            salaryNote.setPayType("5");
            salaryNote.setMonth(date);
            salaryNote.setCreator(SecurityUtils.getNickName());
            salaryNote.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
            salaryNoteService.save(salaryNote);
            List<SalaryPunishment> salaryPunishmentList1 = salaryPunishmentList.stream().filter(item -> null != item.getEmpId()).filter(item -> !item.getEmpId().isEmpty()).collect(Collectors.toList());
            for (SalaryPunishment salaryPunishment : salaryPunishmentList1)
            {
                try
                {
                    // 验证是否存在这笔数据
                    SalaryPunishment u = lambdaQuery().eq(SalaryPunishment::getEmpId, salaryPunishment.getEmpId()).one();
                    // 验证工号属于导入的公司
                    Personnel personnel = personnelService.lambdaQuery().eq(Personnel::getCompId, salaryPunishment.getCompId()).eq(Personnel::getEmpNo, salaryPunishment.getEmpId()).one();
                    // 验证薪资项目编码是否正确
                    SalaryProjectBasis salaryProjectBasis = salaryProjectBasisService.lambdaQuery().eq(SalaryProjectBasis::getPayProCode, salaryPunishment.getPayTabCode()).one();
                    // 差导入公司不允许导入父项信息验证  salaryProjectBasis.getIsEnd == "Y"
                    if (StringUtils.isNull(u) && StringUtils.isNotNull(personnel) && StringUtils.isNotNull(salaryProjectBasis))
                    {
                        BeanValidators.validateWithException(validator, salaryPunishment);
                        salaryPunishment.setCreator(operName);
                        save(salaryPunishment);
                        salaryNote.setStatus("1");
                        salaryNoteService.save(salaryNote);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、工号 " + salaryPunishment.getEmpId() + " 导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        BeanValidators.validateWithException(validator, salaryPunishment);
                        salaryPunishment.setCreator(operName);
                        lambdaUpdate().eq(SalaryPunishment::getEmpId, salaryPunishment.getEmpId()).update();
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、工号 " + salaryPunishment.getEmpId() + " 更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、工号 " + salaryPunishment.getEmpId() + " 已存在");
                    }
                }
                catch (Exception e)
                {
                    salaryNote.setStatus("2");
                    salaryNoteService.save(salaryNote);
                    SalaryError salaryError = new SalaryError();
                    salaryError.setPayType("5");
                    salaryError.setCompId(salaryPunishment.getCompId());
                    salaryError.setEmpNo(salaryPunishment.getEmpId());
                    salaryError.setMark(e.getMessage());
                    salaryError.setCreator(SecurityUtils.getNickName());
                    salaryError.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、工号 " + salaryPunishment.getEmpId() + " 导入失败：";
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




