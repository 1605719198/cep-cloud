import request from "@/utils/request";
//焦碳计划实绩图表
export const getProductionHomeYieldChart = (params) => {
  return request({
    url: '/api/neplatform/production/yield/getProductionHomeYieldChart',
    method: 'get',
    params
  })
};
//焦碳计划实绩统计
export const getProductionHomeYieldStatistics = (params) => {
  return request({
    url: '/api/neplatform/production/yield/getProductionHomeYieldStatistics',
    method: 'get',
    params
  })
};
//发电计划实绩统计
export const getProductionHomePowerStatistics = (params) => {
  return request({
    url: '/api/neplatform/production/yield/getProductionHomePowerStatistics',
    method: 'get',
    params
  })
};
//发电仪表
export const getProductionHomePowerMeterChart = (params) => {
  return request({
    url: '/api/neplatform/production/yield/getProductionHomePowerMeterChart',
    method: 'get',
    params
  })
};
// 生产管理-首页
export const getProductionHomeStatistics = (params) => {
  return request({
    url: '/api/neplatform/production/yield/getProductionHomeStatistics',
    method: 'get',
    params
  })
};


// 生产管理-生产看板-今日产量
export const getTodayYield = (params) => {
  return request({
    url: '/api/neplatform/production//plan/dataBoard/getTodayYield',
    method: 'get',
    params:params
  })
};

// 生产管理-生产看板-总耗用量
export const getTodayConsumption = (params) => {
  return request({
    url: '/api/neplatform/production//plan/dataBoard/getTodayConsumption',
    method: 'get',
    params:params
  })
};

// 生产管理-生产看板-焦棚库存量
export const getTodayCokeStock = (params) => {
  return request({
    url: '/api/neplatform/production//plan/dataBoard/getTodayCokeStock',
    method: 'get',
    params:params
  })
};

// 生产管理-生产看板-焦炭发运量
export const getTodayCokeShipment = (params) => {
  return request({
    url: '/api/neplatform/production//plan/dataBoard/getTodayCokeShipment',
    method: 'get',
    params:params
  })
};

// 生产管理-生产看板-蒸汽产量
export const getTodayCokeSteam = (params) => {
  return request({
    url: '/api/neplatform/production//plan/dataBoard/getTodayCokeSteam',
    method: 'get',
    params:params
  })
};





























