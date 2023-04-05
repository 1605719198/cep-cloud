/**
 * 堆取料机Api
 */

import request from "@/utils/request";

/**
 * 获取堆取料机实绩列表
 */
export const getStackerReclaimerPerformanceList = (params) => {
  return request({
    url:"/product/performance/listStackerReclaimerPerformance",
    method:"get",
    params
  });
}

export const insertProductionStackerReclaimerPerformance = (params) => {
  return request({
    url:'/product/productionstackerreclaimerperformance/insertProductionStackerReclaimerPerformance',
    method:'post',
    data:{...params}
  });
}

export const updateProductionStackerReclaimerPerformance = (params) => {
  return request({
    url:'/product/productionstackerreclaimerperformance/updateProductionStackerReclaimerPerformance',
    method:'put',
    data:{...params}
  });
}

export const deleteProductionStackerReclaimerPerformance = (delId) => {
  return request({
    url:'/product/productionstackerreclaimerperformance/deleteProductionStackerReclaimerPerformance',
    method:'delete',
    data:{id:delId}
  });
}

//获取排班信息
export const getUserSchedulingInfo = (params) => {
  return request({
    url:'/product/productionstackerreclaimerperformance/getUserSchedulingInfo',
    method:'get',
    params
  });
}
