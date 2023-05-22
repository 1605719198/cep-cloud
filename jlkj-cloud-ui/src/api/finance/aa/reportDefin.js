import request from '@/utils/request'

// 查询集团管理-报定义列表
export function listReportDefin(query) {
  return request({
    url: '/finance/reportDefin/list',
    method: 'get',
    params: query
  })
}

// 查询集团管理-报定义详细
export function getReportDefin(reportId) {
  return request({
    url: '/finance/reportDefin/' + reportId,
    method: 'get'
  })
}

// 新增集团管理-报定义
export function addReportDefin(data) {
  return request({
    url: '/finance/reportDefin',
    method: 'post',
    data: data
  })
}

// 修改集团管理-报定义
export function updateReportDefin(data) {
  return request({
    url: '/finance/reportDefin',
    method: 'put',
    data: data
  })
}

// 删除集团管理-报定义
export function delReportDefin(reportId) {
  return request({
    url: '/finance/reportDefin/' + reportId,
    method: 'delete'
  })
}

// 查询报表代号(下拉选单用)
export function selectReportNoList(compId) {
  return request({
    url: '/finance/reportDefin/reportNoList/' + compId,
    method: 'get',
  })
}
