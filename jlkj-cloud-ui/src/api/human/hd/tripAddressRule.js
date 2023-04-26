import request from '@/utils/request'

// 查询出差地点记录列表
export function listTripAddressRule(query) {
  return request({
    url: '/human/tripAddressRule/list',
    method: 'get',
    params: query
  })
}

// 查询出差地点列表
export function listTripAddress(query) {
  return request({
    url: '/human/tripAddressRule/listTripAddress',
    method: 'get',
    params: query
  })
}

// 查询出差地点记录详细
export function getTripAddressRule(id) {
  return request({
    url: '/human/tripAddressRule/' + id,
    method: 'get'
  })
}

// 新增出差地点记录
export function addTripAddressRule(data) {
  return request({
    url: '/human/tripAddressRule',
    method: 'post',
    data: data
  })
}

// 修改出差地点记录
export function updateTripAddressRule(data) {
  return request({
    url: '/human/tripAddressRule',
    method: 'put',
    data: data
  })
}

// 删除出差地点记录
export function delTripAddressRule(id) {
  return request({
    url: '/human/tripAddressRule/' + id,
    method: 'delete'
  })
}
