package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.dto.safeindex.PageSafeIndexDTO;
import com.jlkj.safety.si.dto.safeindex.SafeAnalysisListDTO;
import com.jlkj.safety.si.entity.SafeIndex;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author yzl
 * @Description 环保指标项
 * @create 2022年7月19日 08:51:54
 */
public interface SafeIndexMapper extends BaseMapper<SafeIndex> {

    /**
     * 环保指标项-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page,@Param("condition") PageSafeIndexDTO condition);

    /**
     * 安全作业分析-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @MapKey("id")
    IPage<Map<String, String>> getAnalysisListPage(Page<Map<String, String>> page,@Param("condition") SafeAnalysisListDTO condition);

    /**
     * 安全作业分析-查询饼图
     * @param condition 查询条件
     * @return 结果列表
     */
    @MapKey("id")
    List<Map<String, String>> getAnalysisPie(SafeAnalysisListDTO condition);

    /**
     * 安全作业分析-查询折线图
     * @param condition 查询条件
     * @return 结果列表
     */
    @MapKey("id")
    List<Map<String, String>> getAnalysisLine(SafeAnalysisListDTO condition);

    /**
     * 安全看板-进行中作业票统计
     * @return 结果列表
     */
    @MapKey("id")
    List<Map<String, String>> getAnalysisBoardTicketTotal();

}


