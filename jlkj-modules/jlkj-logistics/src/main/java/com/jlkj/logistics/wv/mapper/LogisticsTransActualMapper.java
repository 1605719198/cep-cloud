package com.jlkj.logistics.wv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.logistics.wv.domain.LogisticsPlanDay;
import com.jlkj.logistics.wv.domain.LogisticsTransActual;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 运输实绩表 Mapper 接口
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
public interface LogisticsTransActualMapper extends BaseMapper<LogisticsTransActual> {

    /**
     * 查询月倒出焦炭比
     * @param startDate
     * @param endDate
     * @return
     */
    List<LogisticsTransActual> getOutCokeScale(@Param("startDate") String startDate,@Param("endDate") String endDate);
    /**
     * 查询月焦炭倒出量(按收货地点)
     * @param startDate
     * @param endDate
     * @return
     */
    List<LogisticsTransActual> getOutCokeLocation(@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 查询月焦炭倒出量实际
     * @param startDate
     * @param endDate
     * @return
     */
    List<LogisticsTransActual> getOutCokeAmountReal(@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 查询月焦炭倒出量计划
     * @param startDate
     * @param endDate
     * @return
     */
    List<LogisticsPlanDay> getOutCokeAmountPlan(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 查询月焦炭倒入量
     * @param startDate
     * @param endDate
     * @return
     */
    List<LogisticsTransActual> getInCokeAmount(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
