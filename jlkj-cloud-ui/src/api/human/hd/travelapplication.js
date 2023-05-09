import request from '@/utils/request'

// 查询出差申请列表
export function listTravelapplication(query) {
  return request({
    url: '/human/travelapplication/list',
    method: 'get',
    params: query
  })
}

// 查询出差申请详细
export function getTravelapplication(id) {
  return request({
    url: '/human/travelapplication/' + id,
    method: 'get'
  })
}

// 新增出差申请
export function addTravelapplication(data) {
  return request({
    url: '/human/travelapplication',
    method: 'post',
    data: data
  })
}

// 修改出差申请
export function updateTravelapplication(data) {
  return request({
    url: '/human/travelapplication',
    method: 'put',
    data: data
  })
}

// 删除出差申请
export function delTravelapplication(id) {
  return request({
    url: '/human/travelapplication/' + id,
    method: 'delete'
  })
}
