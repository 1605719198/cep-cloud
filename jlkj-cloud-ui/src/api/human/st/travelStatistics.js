import request from '@/utils/request'

// 查询出差统计列表
export function listTravelStatistics(query) {
  return request({
    url: '/human/travelStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询出差统计详细
export function getTravelStatistics(id) {
  return request({
    url: '/human/travelStatistics/' + id,
    method: 'get'
  })
}

// 新增出差统计
export function addTravelStatistics(data) {
  return request({
    url: '/human/travelStatistics',
    method: 'post',
    data: data
  })
}

// 修改出差统计
export function updateTravelStatistics(data) {
  return request({
    url: '/human/travelStatistics',
    method: 'put',
    data: data
  })
}

// 删除出差统计
export function delTravelStatistics(id) {
  return request({
    url: '/human/travelStatistics/' + id,
    method: 'delete'
  })
}
