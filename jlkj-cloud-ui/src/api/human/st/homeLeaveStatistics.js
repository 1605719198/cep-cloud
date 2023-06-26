import request from '@/utils/request'

// 查询探亲假统计列表
export function listHomeLeaveStatistics(query) {
  return request({
    url: '/human/homeLeaveStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询探亲假统计详细
export function getHomeLeaveStatistics(id) {
  return request({
    url: '/human/homeLeaveStatistics/' + id,
    method: 'get'
  })
}

// 新增探亲假统计
export function addHomeLeaveStatistics(data) {
  return request({
    url: '/human/homeLeaveStatistics',
    method: 'post',
    data: data
  })
}

// 修改探亲假统计
export function updateHomeLeaveStatistics(data) {
  return request({
    url: '/human/homeLeaveStatistics',
    method: 'put',
    data: data
  })
}

// 删除探亲假统计
export function delHomeLeaveStatistics(id) {
  return request({
    url: '/human/homeLeaveStatistics/' + id,
    method: 'delete'
  })
}
