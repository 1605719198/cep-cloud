/**
 * 配煤实绩Api
 */

import request from "@/utils/request";

/**
 * 获取配煤实绩Api
 */
export const getConfigureCoalSpeciesList = (params) => {
  return request({
    url:"/api/neplatform/production/performance/listConfigureCoalSpeciesPerformance",
    method:"get",
    params
  });
}

/**
 * 配煤实绩详情
 */
export const getConfigureCoalSpeciesDetail = (params) => {
  return request({
    url:"/api/neplatform/production/performance/listConfigureCoalSpeciesPerformanceDetail",
    method:"get",
    params
  });
}


/**
 * 统计分析-配煤比分析-图表
 */
export const getConfigureCoalSpeciesAnalysisStatistics = (params) => {
  return request({
    url:"/api/neplatform/production/performance/getConfigureCoalSpeciesAnalysisStatistics",
    method:"get",
    params
  });
}




