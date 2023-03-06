/**
 * 蒸汽产量实绩api
 */

import request from "@/utils/request";

/**
 * 获取蒸汽产量实绩列表
 */

export const getProductionSteamPerformanceList = (params) => {
  return request({
    url:"/api/neplatform/production/steamYieldPerformance/getProductionSteamPerformanceList",
    method:"get",
    params
  });
}
