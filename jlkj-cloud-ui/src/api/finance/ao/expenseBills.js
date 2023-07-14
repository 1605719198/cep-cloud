import request from '@/utils/request'

// 查询我的发票列表
export function listExpenseBills(query) {
  return request({
    url: '/finance/expenseBills/list',
    method: 'get',
    params: query
  })
}

// 查询我的发票详细
export function getExpenseBills(id) {
  return request({
    url: '/finance/expenseBills/' + id,
    method: 'get'
  })
}

// 新增我的发票
export function addExpenseBills(data) {
  return request({
    url: '/finance/expenseBills',
    method: 'post',
    data: data
  })
}

// 修改我的发票
export function updateExpenseBills(data) {
  return request({
    url: '/finance/expenseBills',
    method: 'put',
    data: data
  })
}

// 删除我的发票
export function delExpenseBills(id) {
  return request({
    url: '/finance/expenseBills/' + id,
    method: 'delete'
  })
}
