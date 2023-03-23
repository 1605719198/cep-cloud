import request from '@/utils/request'
//安全看板-进行中作业票统计
export const getAnalysisBoardTicketTotal = (params) => {
  return request({
    url: '/safety/safe/safeAnalysis/getAnalysisBoardTicketTotal',
    method: 'get',
    params
  })
};

//近七日指数变化
export const getIndexChangeCharts = (params) => {
  return request({
    url:"/sys/monitorData/getIndexChangeCharts",
    method:'get',
    params
  });
};

//安全看板- 报警项
export const getAlertRecordExceedance = (params) => {
  return request({
    url: '/safety/alertRecord/getAlertRecordExceedance',
    method: 'get',
    params
  })
};
//安全看板- 报警项
export const getAlertRecord = (params) => {
  return request({
    url: '/safety/alertRecord/getAlertRecord',
    method: 'get',
    params
  })
};

//安全看板- 监测点检测
export const getEnvironmentalCollectionPoints = (params) => {
  return request({
    url: '/safety/environmentalCollectionPoints/getEnvironmentalCollectionPoints',
    method: 'get',
    params
  })
};

//监测点检测
export const getEnvironmentalCollectionPointsCharts = (params) => {
  return request({
    url:"/sys/monitorData/getEnvironmentalCollectionPointsCharts",
    method:'get',
    params
  });
};





