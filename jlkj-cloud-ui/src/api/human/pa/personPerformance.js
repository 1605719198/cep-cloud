import request from '@/utils/request'

// 查询人员绩效主档列表
export function listPersonPerformance(query) {
  return request({
    url: '/human/personPerformance/list',
    method: 'get',
    params: query
  })
}

// 查询固定项目启动列表
export function listPersonPerformanceFix(query) {
  return request({
    url: '/human/personPerformance/listPersonPerformanceFix',
    method: 'get',
    params: query
  })
}

// 查询考评清单列表
export function listEvaluation(query) {
  return request({
    url: '/human/personPerformance/evaluationList',
    method: 'get',
    params: query
  })
}

// 查询人员绩效明细档列表
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

// 添加人员绩效明细档自评和实际完成情况
export function updateSelfApprDetail(data) {
  return request({
    url: '/human/personPerformance/updateSelfApprDetail',
    method: 'post',
    data: data
  })
}

// 添加人员绩效主档自评总结
export function updateSelfAppr(data) {
  return request({
    url: '/human/personPerformance/updateSelfAppr',
    method: 'post',
    data: data
  })
}

// 添加人员绩效主档主管考评和明细档主管考评分数及备注
export function updateAdmAppr(data) {
  return request({
    url: '/human/personPerformance/updateAdmAppr',
    method: 'post',
    data: data
  })
}

// 添加考评意见/复核意见、更新明细表主管考评
export function updateAdmEvaluation(data) {
  return request({
    url: '/human/personPerformance/updateAdmEvaluation',
    method: 'post',
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

// 呈送计划
export function sendPersonPerformanceFix(data) {
  return request({
    url: '/human/personPerformance/sendPersonPerformanceFix',
    method: 'post',
    data: data
  })
}

// 呈送自评
export function sendSelfAppr(data) {
  return request({
    url: '/human/personPerformance/sendSelfAppr',
    method: 'post',
    data: data
  })
}

// 确认按钮
export function confirmPersonalPerformance(data) {
  return request({
    url: '/human/personPerformance/confirmPersonalPerformance',
    method: 'post',
    data: data
  })
}

// 固定考评确认按钮
export function confirmPersonalPerformanceFix(data) {
  return request({
    url: '/human/personPerformance/confirmPersonalPerformanceFix',
    method: 'post',
    data: data
  })
}

// 启动固定项目考评
export function startPersonPerformanceFix(data) {
  return request({
    url: '/human/personPerformance/startPersonPerformanceFix',
    method: 'post',
    data: data
  })
}
