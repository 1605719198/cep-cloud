import request from '@/utils/request'

// 查询日运输计划
export function queryPlanDayData(params) {
  return request({
    url: '/logistics/logistics/plan/day/queryPlanDayData',
    method: 'get',
    params: params
  })
}
