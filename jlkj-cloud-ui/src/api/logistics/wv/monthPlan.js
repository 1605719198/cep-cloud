import request from '@/utils/request'

// 查询月度运输计划
export function queryPlanMonthData(params) {
  return request({
    url: '/logistics/logistics/plan/month/queryPlanMonthData',
    method: 'get',
    params: params
  })
}
