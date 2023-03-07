/**
 * 上煤实绩api
 */

import request from "@/utils/request";

/**
 * 获取上煤实绩列表
 */
export const getMaterialLoadingPerformanceList = (params) => {
  return request({
    url: "/api/neplatform/production/performance/listMaterialLoadingPerformance",
    method: "get",
    params
  });
}

//生产实绩-上煤实绩-查询-分页
export const getProductionConveyingCoalActualPage = (page, query) => {
  return request({
    url: "/api/neplatform/production/productionconveyingcoalactual/getProductionConveyingCoalActualPage",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
}

//上传上煤实绩ERP
export const send = (params) => {
  return request({
    url: '/api/neplatform/material/stock/materialsCoalStock/send',
    method: 'get',
    params
  });
}
