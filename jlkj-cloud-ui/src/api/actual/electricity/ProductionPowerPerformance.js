/**
 * 发电量实际Api
 */

import request from "@/utils/request";

/*
  获取焦炉系数数据列表
 */
export const getProductionPowerPerformance = (params) => {
    return request({
        url: "/api/neplatform/production/powerPerformance/getProductionPowerPerformance",
        method: "get",
        params
    });
}

/**
 * 获取发电量实绩列表
 */
export const getProductionPowerYieldListPerformanceList = (params) => {
  return request({
    url:"/api/neplatform/production/powerYieldPerformance/getProductionPowerYieldListPerformanceList",
    method:"get",
    params
  });
}
