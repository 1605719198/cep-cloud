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

// 查询个人绩效进度档列表
export function listPersonPerformanceSchedule(query) {
  return request({
    url: '/human/personPerformance/listPersonPerformanceSchedule',
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

// 新增人员绩效明细档
export function addPersonPerformanceDetail(data) {
  return request({
    url: '/human/personPerformance/addDetail',
    method: 'post',
    data: data
  })
}

// 新增人员绩效明细档
export function addPersonPerformanceKpiDetail(data) {
  return request({
    url: '/human/personPerformance/addKpiDetail',
    method: 'post',
    data: data
  })
}

// 修改人员绩效明细档
export function updatePersonPerformanceDetail(data) {
  return request({
    url: '/human/personPerformance',
    method: 'put',
    data: data
  })
}

// 删除人员绩效主档和明细档
export function delPersonPerformance(data) {
  return request({
    url: '/human/personPerformance/delPersonPerformance',
    method: 'post',
    data: data
  })
}

// 删除人员绩效明细档
export function delPersonPerformanceDetail(data) {
  return request({
    url: '/human/personPerformance/delPersonPerformanceDetail',
    method: 'post',
    data: data
  })
}

// 呈送计划
export function sendPersonPerformance(data) {
  return request({
    url: '/human/personPerformance/sendPersonPerformance',
    method: 'post',
    data: data
  })
}
