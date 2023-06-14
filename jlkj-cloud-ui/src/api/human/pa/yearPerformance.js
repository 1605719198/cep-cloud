import request from '@/utils/request'

// 查询年度绩效主档列表
export function listYearPerformance(query) {
  return request({
    url: '/human/yearPerformance/list',
    method: 'get',
    params: query
  })
}

// 查询年度绩效主档详细
export function getYearPerformance(id) {
  return request({
    url: '/human/yearPerformance/' + id,
    method: 'get'
  })
}

// 新增年度绩效主档
export function addYearPerformance(data) {
  return request({
    url: '/human/yearPerformance',
    method: 'post',
    data: data
  })
}

// 修改年度绩效主档
export function updateYearPerformance(data) {
  return request({
    url: '/human/yearPerformance',
    method: 'put',
    data: data
  })
}

// 删除年度绩效主档
export function delYearPerformance(id) {
  return request({
    url: '/human/yearPerformance/' + id,
    method: 'delete'
  })
}
