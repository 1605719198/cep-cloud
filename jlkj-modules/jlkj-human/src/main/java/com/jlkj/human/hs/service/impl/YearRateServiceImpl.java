package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.YearRate;
import com.jlkj.human.hs.mapper.YearRateMapper;
import com.jlkj.human.hs.service.IYearRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 年度奖金比例设定Service业务层处理
 *
 * @author 267383
 * @date 2023-06-04
 */
@Service
public class YearRateServiceImpl implements IYearRateService
{
    @Autowired
    private YearRateMapper yearRateMapper;

    /**
     * 查询年度奖金比例设定
     *
     * @param id 年度奖金比例设定主键
     * @return 年度奖金比例设定
     */
    @Override
    public YearRate selectYearRateById(String id)
    {
        return yearRateMapper.selectYearRateById(id);
    }

    /**
     * 查询年度奖金比例设定列表
     *
     * @param yearRate 年度奖金比例设定
     * @return 年度奖金比例设定
     */
    @Override
    public List<YearRate> selectYearRateList(YearRate yearRate)
    {
        return yearRateMapper.selectYearRateList(yearRate);
    }

    /**
     * 保存年度奖金比例设定
     *
     * @param yearRate 年度奖金比例设定
     * @return 结果
     */
    @Override
    public int insertYearRate(YearRate yearRate)
    {
        String time = DateUtils.dateTime();
        String year = time.substring(0, 4);
        List<YearRate> yearRates = yearRateMapper.selectYearRateList(yearRate);
        if(!yearRates.isEmpty()) {
            for (YearRate yearRated : yearRates) {
                if (year.equals(yearRated.getYear())) {
                    yearRate.setId(yearRated.getId());
                    yearRate.setCreatorId(SecurityUtils.getUserId().toString());
                    yearRate.setCreator(SecurityUtils.getNickName());
                    yearRate.setCreateDate(new Date());
                    yearRateMapper.updateYearRate(yearRate);
                } else {
                        throw new ServiceException("不可修改历史年份");
                }
            }
        }else{
            yearRate.setId(UUID.randomUUID().toString().substring(0, 32));
            yearRate.setCreatorId(SecurityUtils.getUserId().toString());
            yearRate.setCreator(SecurityUtils.getNickName());
            yearRate.setCreateDate(new Date());
            yearRateMapper.insertYearRate(yearRate);
        }
            return 1;
    }

    /**
     * 修改年度奖金比例设定
     *
     * @param yearRate 年度奖金比例设定
     * @return 结果
     */
    @Override
    public int updateYearRate(YearRate yearRate)
    {
        return yearRateMapper.updateYearRate(yearRate);
    }

    /**
     * 批量删除年度奖金比例设定
     *
     * @param ids 需要删除的年度奖金比例设定主键
     * @return 结果
     */
    @Override
    public int deleteYearRateByIds(String[] ids)
    {
        return yearRateMapper.deleteYearRateByIds(ids);
    }

    /**
     * 删除年度奖金比例设定信息
     *
     * @param id 年度奖金比例设定主键
     * @return 结果
     */
    @Override
    public int deleteYearRateById(String id)
    {
        return yearRateMapper.deleteYearRateById(id);
    }
}
