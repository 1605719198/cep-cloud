package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.human.hd.domain.OvertimeRecord;
import com.jlkj.human.hd.mapper.OvertimeRecordMapper;
import com.jlkj.human.hd.service.IOvertimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_overtime_record(加班记录表)】的数据库操作Service实现
* @createDate 2023-04-10 14:15:56
*/
@Service
public class OvertimeRecordServiceImpl extends ServiceImpl<OvertimeRecordMapper, OvertimeRecord>
    implements IOvertimeRecordService {

    @Autowired
    protected Validator validator;

    /**
     * 导入加班数据
     *
     * @param overtimeRecordList 用户加班列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<OvertimeRecord> overtimeRecordList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(overtimeRecordList) || overtimeRecordList.size() == 0)
        {
            throw new ServiceException("导入加班数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (OvertimeRecord overtimeRecord : overtimeRecordList)
        {
            try
            {
                // 验证是否存在这笔数据
                OvertimeRecord u = lambdaQuery().eq(OvertimeRecord::getEmpNo, overtimeRecord.getEmpNo()).one();
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, overtimeRecord);
                    overtimeRecord.setCreator(operName);
                    overtimeRecord.setStatus("未送出");
                    overtimeRecord.setCompId("J00");
                    save(overtimeRecord);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + overtimeRecord.getEmpNo() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, overtimeRecord);
                    overtimeRecord.setCreator(operName);
                    lambdaUpdate().eq(OvertimeRecord::getEmpNo, overtimeRecord.getEmpNo()).update();
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + overtimeRecord.getEmpNo() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、工号 " + overtimeRecord.getEmpNo() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工号 " + overtimeRecord.getEmpNo() + " 导入失败：";
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




