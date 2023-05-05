import request from '@/utils/request'

// 查询利率汇率管理列表
export function listRate(query) {
  return request({
    url: '/finance/rate/list',
    method: 'get',
    params: query
  })
}

// 查询利率汇率管理详细
export function getRate(id) {
  return request({
    url: '/finance/rate/' + id,
    method: 'get'
  })
}

// 新增利率汇率管理
export function addRate(data) {
  return request({
    url: '/finance/rate',
    method: 'post',
    data: data
  })
}

// 修改利率汇率管理
export function updateRate(data) {
  return request({
    url: '/finance/rate',
    method: 'put',
    data: data
  })
}

// 删除利率汇率管理
export function delRate(id) {
  return request({
    url: '/finance/rate/' + id,
    method: 'delete'
  })
}
