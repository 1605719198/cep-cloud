package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.YearAmt;
import com.jlkj.human.hs.mapper.YearAmtMapper;
import com.jlkj.human.hs.service.IYearAmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

/**
 * 年收入维护Service业务层处理
 *
 * @author 266861
 * @date 2023-05-17
 */
@Service
public class YearAmtServiceImpl implements IYearAmtService {
    @Autowired
    private YearAmtMapper yearAmtMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询年收入维护
     *
     * @param id 年收入维护主键
     * @return 年收入维护
     */
    @Override
    public YearAmt selectYearAmtById(String id) {
        return yearAmtMapper.selectYearAmtById(id);
    }

    /**
     * 查询年收入维护列表
     *
     * @param yearAmt 年收入维护
     * @return 年收入维护
     */
    @Override
    public List<YearAmt> selectYearAmtList(YearAmt yearAmt) {
        return yearAmtMapper.selectYearAmtList(yearAmt);
    }

    /**
     * 新增年收入维护
     *
     * @param yearAmtList 年收入维护
     * @return 结果
     */
    @Override
    public int insertYearAmt(List<YearAmt> yearAmtList) {
        for (YearAmt yearAmt : yearAmtList) {
            yearAmt.setCreatorId(SecurityUtils.getUserId().toString());
            yearAmt.setCreatorNo(SecurityUtils.getUsername());
            yearAmt.setCreator(SecurityUtils.getNickName());
            yearAmt.setCreateDate(new Date());
            if (yearAmt.getId() != null) {
                yearAmtMapper.updateYearAmt(yearAmt);
            } else {
                yearAmt.setId(UUID.randomUUID().toString().substring(0, 32));
                yearAmtMapper.insertYearAmt(yearAmt);
            }
        }
        return 1;
    }

    /**
     * 修改年收入维护
     *
     * @param yearAmt 年收入维护
     * @return 结果
     */
    @Override
    public int updateYearAmt(YearAmt yearAmt) {
        return yearAmtMapper.updateYearAmt(yearAmt);
    }

    /**
     * 批量删除年收入维护
     *
     * @param ids 需要删除的年收入维护主键
     * @return 结果
     */
    @Override
    public int deleteYearAmtByIds(String[] ids) {
        return yearAmtMapper.deleteYearAmtByIds(ids);
    }

    /**
     * 删除年收入维护信息
     *
     * @param id 年收入维护主键
     * @return 结果
     */
    @Override
    public int deleteYearAmtById(String id) {
        return yearAmtMapper.deleteYearAmtById(id);
    }

    /**
     * 导入年收入资料数据
     *
     * @param yearAmtList     年收入数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<YearAmt> yearAmtList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(yearAmtList) || yearAmtList.size() == 0) {
            throw new ServiceException("导入年收入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (YearAmt yearAmt : yearAmtList) {
            try {
                BeanValidators.validateWithException(validator, yearAmt);
                yearAmt.setId(UUID.randomUUID().toString().substring(0, 32));
                yearAmt.setCreateBy(operName);
                yearAmtMapper.insertYearAmt(yearAmt);
                successNum++;
                successMsg.append("<br/>" + successNum + "、员工名 " + yearAmt.getEmpName() + " 导入成功");
                if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, yearAmt);
                    yearAmtMapper.updateYearAmt(yearAmt);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、员工名 " + yearAmt.getEmpName() + " 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、员工名 " + yearAmt.getEmpName() + " 导入失败：";
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
