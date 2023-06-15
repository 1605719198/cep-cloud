import request from '@/utils/request'

// 查询KPI指标主档列表
export function listDeptKpi(query) {
  return request({
    url: '/human/deptKpi/list',
    method: 'get',
    params: query
  })
}

// 查询KPI指标明细档列表
export function listDeptKpiDetail(query) {
  return request({
    url: '/human/deptKpi/listDeptKpiDetail',
    method: 'get',
    params: query
  })
}

// 新增KPI指标主档
export function addDeptKpi(data) {
  return request({
    url: '/human/deptKpi',
    method: 'post',
    data: data
  })
}

// 修改KPI指标主档
export function updateDeptKpi(data) {
  return request({
    url: '/human/deptKpi',
    method: 'put',
    data: data
  })
}

// 删除KPI指标主档和明细档
export function delDeptKpi(id) {
  return request({
    url: '/human/deptKpi/' + id,
    method: 'delete'
  })
}

// 删除KPI指标明细档
export function delDeptKpiDetail(detailId) {
  return request({
    url: '/human/deptKpi/removeDetail/' + detailId,
    method: 'delete'
  })
}

// 新增KPI指标明细档
export function addDeptKpiDetail(data) {
  return request({
    url: '/human/deptKpi/addDeptKpiDetail',
    method: 'post',
    data: data
  })
}

// 更新KPI指标主档状态
export function updateDeptKpiStatus(data) {
  return request({
    url: '/human/deptKpi/updateDeptKpiStatus',
    method: 'post',
    data: data
  })
}
