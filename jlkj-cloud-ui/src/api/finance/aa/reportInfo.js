import request from '@/utils/request'

// 查询印项目计算记录列表
export function listReportInfo(query) {
  return request({
    url: '/finance/reportInfo/list',
    method: 'get',
    params: query
  })
}

// 查询印项目计算记录详细
export function getReportInfo(id) {
  return request({
    url: '/finance/reportInfo/' + id,
    method: 'get'
  })
}

// 新增印项目计算记录
export function addReportInfo(data) {
  return request({
    url: '/finance/reportInfo',
    method: 'post',
    data: data
  })
}

// 修改印项目计算记录
export function updateReportInfo(data) {
  return request({
    url: '/finance/reportInfo',
    method: 'put',
    data: data
  })
}

// 删除印项目计算记录
export function delReportInfo(id) {
  return request({
    url: '/finance/reportInfo/' + id,
    method: 'delete'
  })
}
