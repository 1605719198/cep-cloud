/**
 * 出炉实绩Api
 */

import request from "@/utils/request";

/*
  获取出炉实绩的数据列表
 */
export const getOutputPerformanceList = (params) => {
  return request({
    url: "/api/neplatform/production/performance/listOutputPerformance",
    method: "get",
    params
  });
}


//生产管理-炼焦实绩-出炉实绩-手动出炉
export const insertProductionOutputPerformanceManualBake = (params) => {
  return request({
    url: "/api/neplatform/production/productionoutputperformancemanualbake/insertProductionOutputPerformanceManualBake",
    method: "post",
    data: { ...params },
  });
};

//生产管理-炼焦实绩-出炉实绩-手动出炉-查询-分页
export const getProductionOutputPerformanceManualBakePage = (params) => {
  return request({
    url: "/api/neplatform/production/productionoutputperformancemanualbake/getProductionOutputPerformanceManualBakePage",
    method: "get",
    params
  });
}

//生产管理-炼焦实绩-出炉实绩-手动出炉-删除
export const deleteProductionOutputPerformanceManualBake = (params) => {
  return request({
    url: "/api/neplatform/production/productionoutputperformancemanualbake/deleteProductionOutputPerformanceManualBake",
    method: "delete",
    data: { ...params },
  });
};
