import request from '@/utils/request'

// 查询币别管理列表
export function listCrcy(query) {
  return request({
    url: '/finance/crcy/list',
    method: 'get',
    params: query
  })
}

// 查询会计公司清单(下拉选单用)
export function selectCrcy() {
  return request({
    url: '/finance/crcy/selectCrcy',
    method: 'get',
  })
}

// 查询币别管理详细
export function getCrcy(id) {
  return request({
    url: '/finance/crcy/' + id,
    method: 'get'
  })
}

// 新增币别管理
export function addCrcy(data) {
  return request({
    url: '/finance/crcy',
    method: 'post',
    data: data
  })
}

// 修改币别管理
export function updateCrcy(data) {
  return request({
    url: '/finance/crcy',
    method: 'put',
    data: data
  })
}

// 删除币别管理
export function delCrcy(id) {
  return request({
    url: '/finance/crcy/' + id,
    method: 'delete'
  })
}
