import request from '@/utils/request'

// 查询出差日标准维护列表
export function listTripDayRule(query) {
  return request({
    url: '/human/tripDayRule/list',
    method: 'get',
    params: query
  })
}

// 查询出差日标准维护详细
export function getTripDayRule(id) {
  return request({
    url: '/human/tripDayRule/' + id,
    method: 'get'
  })
}

// 新增出差日标准维护
export function addTripDayRule(data) {
  return request({
    url: '/human/tripDayRule',
    method: 'post',
    data: data
  })
}

// 修改出差日标准维护
export function updateTripDayRule(data) {
  return request({
    url: '/human/tripDayRule',
    method: 'put',
    data: data
  })
}

// 删除出差日标准维护
export function delTripDayRule(id) {
  return request({
    url: '/human/tripDayRule/' + id,
    method: 'delete'
  })
}
