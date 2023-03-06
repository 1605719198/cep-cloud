import request from "@/utils/request";

//获取年内12个月的计划和实际的产量
export const getProductionYieldAnalysisMonth = (params) => {
  return request({
    url:'/api/neplatform/production/yield/getProductionYieldAnalysisMonth',
    method:'get',
    params
  });
}

//获取月内所有天的计划和实际的产量
export const getProductionYieldAnalysisDate = (params) => {
  return request({
    url:'/api/neplatform/production/yield/getProductionYieldAnalysisDate',
    method:'get',
    params
  });
}

//获取物料名称和ID
export const listMaterialsBox = () => {
  return request({
    url:'/api/neplatform/material/parameter/listMaterialsBox',
    method:'get'
  });
}
