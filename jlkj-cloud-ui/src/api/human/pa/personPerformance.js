import request from '@/utils/request'

// 查询人员绩效主档列表
export function listPersonPerformance(query) {
  return request({
    url: '/human/personPerformance/list',
    method: 'get',
    params: query
  })
}

// 查询KPI指标明细档列表
export function listPersonPerformanceDetail(query) {
  return request({
    url: '/human/personPerformance/listPersonPerformanceDetail',
    method: 'get',
    params: query
  })
}

// 新增人员绩效主档
export function addPersonPerformance(data) {
  return request({
    url: '/human/personPerformance',
    method: 'post',
    data: data
  })
}

// 删除人员绩效主档和明细档
export function delPersonPerformance(data) {
  debugger
  return request({
    url: '/human/personPerformance/delPersonPerformance',
    method: 'post',
    data: data
  })
}
