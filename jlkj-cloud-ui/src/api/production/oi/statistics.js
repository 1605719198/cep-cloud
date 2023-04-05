import request from "@/utils/request";
//生产管理-统计分析-指标跟踪 分页
export const getPageProductionRealOutputMonth = (page, query) => {
  return request({
    url: '/product/real/getPageProductionRealOutputMonth',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};

//查询-分页-生产管理-统计分析(日)
export const getPageProductionRealOutputDate = (page, query) => {
  return request({
    url: '/product/real/getPageProductionRealOutputDate',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};

//生产管理-指标跟踪-图表-指标项(月实际)
export const getProductionRealOutputMonthTargetItemChart = (params) => {
  return request({
    url: '/product/real/getProductionRealOutputMonthTargetItemChart',
    method: 'get',
    params
  })
};

//生产管理-指标跟踪-图表-指标项(日实际)
export const getProductionRealOutputDateTargetItemChart = (params) => {
  return request({
    url: '/product/real/getProductionRealOutputDateTargetItemChart',
    method: 'get',
    params
  })
};

//生产管理-指标跟踪-图表-指标项(月计划)
export const getProductionPlanOutputMonthTargetItemChart = (params) => {
  return request({
    url: '/product/plan/getProductionPlanOutputMonthTargetItemChart',
    method: 'get',
    params
  })
};

//生产管理-指标跟踪-图表-指标项(日计划)
export const getProductionPlanOutputDateTargetItemChart = (params) => {
  return request({
    url: '/product/plan/getProductionPlanOutputDateTargetItemChart',
    method: 'get',
    params
  })
};

//生产管理-指标跟踪-图表-日期(同环比)
export const getProductionRealOutputMonthRangeChart = (params) => {
  return request({
    url: '/product/real/getProductionRealOutputMonthRangeChart',
    method: 'get',
    params
  })
};
export const getList = (page, query) => {
  return request({
    url: "/product/plan/getProductionTargetItem",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
