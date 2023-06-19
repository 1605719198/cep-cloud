import request from '@/utils/request'

// 查询预算设置列表
export function listBudget(query) {
  return request({
    url: '/finance/budget/list',
    method: 'get',
    params: query
  })
}

// 查询预算设置详细
export function getBudget(id) {
  return request({
    url: '/finance/budget/' + id,
    method: 'get'
  })
}

// 新增预算设置
export function addBudget(data) {
  return request({
    url: '/finance/budget',
    method: 'post',
    data: data
  })
}

// 修改预算设置
export function updateBudget(data) {
  return request({
    url: '/finance/budget',
    method: 'put',
    data: data
  })
}

// 删除预算设置
export function delBudget(id) {
  return request({
    url: '/finance/budget/' + id,
    method: 'delete'
  })
}
