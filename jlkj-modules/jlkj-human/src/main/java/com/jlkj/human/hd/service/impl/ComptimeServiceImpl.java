package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.human.hd.domain.Comptime;
import com.jlkj.human.hd.mapper.ComptimeMapper;
import com.jlkj.human.hd.service.IComptimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_comptime(补休记录表)】的数据库操作Service实现
* @createDate 2023-04-13 15:34:08
*/
@Service
public class ComptimeServiceImpl extends ServiceImpl<ComptimeMapper, Comptime>
    implements IComptimeService {

    @Autowired
    protected Validator validator;

    /**
     * 导入补休数据
     *
     * @param comptimeList 用户补休列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<Comptime> comptimeList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(comptimeList) || comptimeList.size() == 0)
        {
            throw new ServiceException("导入补休数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Comptime comptime : comptimeList)
        {
            try
            {
                // 验证是否存在这笔数据
                Comptime u = lambdaQuery().eq(Comptime::getEmpNo, comptime.getEmpNo()).one();
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, comptime);
                    comptime.setCreator(operName);
                    comptime.setStatus("未送出");
                    comptime.setCompId("J00");
                    save(comptime);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + comptime.getEmpNo() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, comptime);
                    comptime.setCreator(operName);
                    lambdaUpdate().eq(Comptime::getEmpNo, comptime.getEmpNo()).update();
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + comptime.getEmpNo() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、工号 " + comptime.getEmpNo() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工号 " + comptime.getEmpNo() + " 导入失败：";
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




