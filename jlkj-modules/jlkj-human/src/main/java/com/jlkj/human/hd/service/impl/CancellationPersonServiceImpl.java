package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.human.hd.domain.AttendanceAbnormal;
import com.jlkj.human.hd.domain.CancellationPerson;
import com.jlkj.human.hd.mapper.CancellationPersonMapper;
import com.jlkj.human.hd.service.IAttendanceAbnormalService;
import com.jlkj.human.hd.service.ICancellationPersonService;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.service.IPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_cancellation_person(人事注销记录表)】的数据库操作Service实现
* @createDate 2023-04-18 14:17:59
*/
@Service
public class CancellationPersonServiceImpl extends ServiceImpl<CancellationPersonMapper, CancellationPerson>
    implements ICancellationPersonService {

    @Autowired
    protected Validator validator;

    @Autowired
    IPersonnelService personnelService;

    @Autowired
    IAttendanceAbnormalService iAttendanceAbnormalService;


    /**
     * 导入夜班数据
     *
     * @param cancellationPersonList 基于表格注销列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<CancellationPerson> cancellationPersonList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(cancellationPersonList) || cancellationPersonList.size() == 0)
        {
            throw new ServiceException("基于表格注销数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (CancellationPerson cancellationPerson : cancellationPersonList)
        {
            try
            {
                // 验证是否存在这笔数据
                BeanValidators.validateWithException(validator, cancellationPerson);
                cancellationPerson.setCreator(operName);
                List<Personnel> personnelList = personnelService.lambdaQuery().eq(Personnel::getEmpNo, cancellationPerson.getEmpNo()).list();
                if (personnelList.isEmpty()) {
                    return "工号:" + cancellationPerson.getEmpNo() + "不存在，不能注销";
                } else {
                    List<AttendanceAbnormal> attendanceAbnormalList = iAttendanceAbnormalService.lambdaQuery()
                            .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
                            .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                            .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPerson.getCheckStartDate())
                            .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPerson.getCheckEndDate())
                            .list();
                    if (attendanceAbnormalList.isEmpty()) {
                        return "工号:" + cancellationPerson.getEmpNo() + "已处理不能注销";
                    } else {
                        boolean update = iAttendanceAbnormalService.lambdaUpdate()
                                .set(AttendanceAbnormal::getStatus, "05")
                                .set(AttendanceAbnormal::getDisposeId, "03")
                                .set(AttendanceAbnormal::getExcReaId, "03")
                                .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
                                .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                                .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPerson.getCheckStartDate())
                                .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPerson.getCheckEndDate())
                                .update();
                        if (update) {
                            save(cancellationPerson);
                        }
                    }
                }
                successNum++;
                successMsg.append("<br/>" + successNum + "、工号 " + cancellationPerson.getEmpNo() + " 导入成功");
                if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, cancellationPerson);
                    cancellationPerson.setCreator(operName);
                    lambdaUpdate().eq(CancellationPerson::getEmpNo, cancellationPerson.getEmpNo()).update();
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + cancellationPerson.getEmpNo() + " 更新成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工号 " + cancellationPerson.getEmpNo() + " 导入失败：";
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




