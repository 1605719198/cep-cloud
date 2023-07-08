package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.HomeLeaveHoliday;
import com.jlkj.human.hd.mapper.HomeLeaveHolidayMapper;
import com.jlkj.human.hd.service.IHomeLeaveHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

/**
 * 探亲假天数设定Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-04-08
 */
@Service
public class HomeLeaveHolidayServiceImpl implements IHomeLeaveHolidayService {
    @Autowired
    private HomeLeaveHolidayMapper homeLeaveHolidayMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询探亲假天数设定
     *
     * @param id 探亲假天数设定主键
     * @return 探亲假天数设定
     */
    @Override
    public HomeLeaveHoliday selectHomeLeaveHolidayById(String id) {
        return homeLeaveHolidayMapper.selectHomeLeaveHolidayById(id);
    }

    /**
     * 查询探亲假天数设定
     *
     * @param homeLeaveHoliday 探亲假天数设定主键
     * @return 探亲假天数设定
     */
    @Override
    public List<HomeLeaveHoliday> selectHomeLeaveHolidayByempNo(HomeLeaveHoliday homeLeaveHoliday) {
        return homeLeaveHolidayMapper.selectHomeLeaveHolidayByempNo(homeLeaveHoliday);
    }

    /**
     * 查询探亲假天数设定列表
     *
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 探亲假天数设定
     */
    @Override
    public List<HomeLeaveHoliday> selectHomeLeaveHolidayList(HomeLeaveHoliday homeLeaveHoliday) {
        return homeLeaveHolidayMapper.selectHomeLeaveHolidayList(homeLeaveHoliday);
    }

    /**
     * 新增探亲假天数设定
     *
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 结果
     */
    @Override
    public int insertHomeLeaveHoliday(HomeLeaveHoliday homeLeaveHoliday) {
        homeLeaveHoliday.setId(IdUtils.fastSimpleUUID());
        homeLeaveHoliday.setCreator(SecurityUtils.getUsername());
        return homeLeaveHolidayMapper.insertHomeLeaveHoliday(homeLeaveHoliday);
    }

    /**
     * 修改探亲假天数设定
     *
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 结果
     */
    @Override
    public int updateHomeLeaveHoliday(HomeLeaveHoliday homeLeaveHoliday) {
        return homeLeaveHolidayMapper.updateHomeLeaveHoliday(homeLeaveHoliday);
    }

    /**
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 结果
     * @Description 修改某员工某年探亲假数据
     * @author 266861
     * @date 2023/6/29 13:37
     **/
    @Override
    public int updateHomeLeaveHolidayByEmp(HomeLeaveHoliday homeLeaveHoliday) {
        return homeLeaveHolidayMapper.updateHomeLeaveHolidayByEmp(homeLeaveHoliday);
    }

    /**
     * 批量删除探亲假天数设定
     *
     * @param ids 需要删除的探亲假天数设定主键
     * @return 结果
     */
    @Override
    public int deleteHomeLeaveHolidayByIds(String[] ids) {
        return homeLeaveHolidayMapper.deleteHomeLeaveHolidayByIds(ids);
    }

    /**
     * 删除探亲假天数设定信息
     *
     * @param id 探亲假天数设定主键
     * @return 结果
     */
    @Override
    public int deleteHomeLeaveHolidayById(String id) {
        return homeLeaveHolidayMapper.deleteHomeLeaveHolidayById(id);
    }

    /**
     * 导入探亲假数据
     *
     * @param homeLeaveHolidayList 用户探亲假列表
     * @param isUpdateSupport      是否更新支持，如果已存在，则进行更新数据
     * @param operName             操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<HomeLeaveHoliday> homeLeaveHolidayList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(homeLeaveHolidayList) || homeLeaveHolidayList.size() == 0) {
            throw new ServiceException("导入探亲假数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (HomeLeaveHoliday homeLeaveHoliday : homeLeaveHolidayList) {
            try {
                // 查询该员工是否有剩余年休假，如果有则进行清空，重新赋值可休天数，已休天数，剩余可休天数
                //
                HomeLeaveHoliday u = homeLeaveHolidayMapper.selectHomeLeaveHolidayByEmpNo(homeLeaveHoliday);
                if (!"0".equals(u.getPreHomeDays().toString())) {
                    homeLeaveHoliday.setDueHomeDays(BigDecimal.valueOf(0));
                    homeLeaveHoliday.setRestHomeDays(BigDecimal.valueOf(0));
                    homeLeaveHoliday.setPreHomeDays(BigDecimal.valueOf(0));
                }
                BeanValidators.validateWithException(validator, homeLeaveHoliday);
                homeLeaveHoliday.setCreator(operName);
                homeLeaveHolidayMapper.updateHomeLeaveHolidayByEmpNo(homeLeaveHoliday);
                successNum++;
                successMsg.append("<br/>" + successNum + "、工号 " + homeLeaveHoliday.getEmpNo() + " 导入成功");
                if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, homeLeaveHoliday);
                    homeLeaveHoliday.setCreator(operName);
                    //
                    homeLeaveHolidayMapper.updateHomeLeaveHoliday(homeLeaveHoliday);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + homeLeaveHoliday.getEmpNo() + " 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工号 " + homeLeaveHoliday.getEmpNo() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}

