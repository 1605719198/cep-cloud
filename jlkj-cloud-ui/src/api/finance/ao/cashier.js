import request from '@/utils/request'

// 查询出纳确认主档列表
export function listCashier(query) {
  return request({
    url: '/finance/cashier/list',
    method: 'get',
    params: query
  })
}

// 查询出纳确认主档详细
export function getCashier(id) {
  return request({
    url: '/finance/cashier/' + id,
    method: 'get'
  })
}

// 新增出纳确认主档
export function addCashier(data) {
  return request({
    url: '/finance/cashier',
    method: 'post',
    data: data
  })
}

// 修改出纳确认主档
export function updateCashier(data) {
  return request({
    url: '/finance/cashier',
    method: 'put',
    data: data
  })
}
// 还款修改会计确认主档
export function updateCashierRepayment(data) {
  return request({
    url: '/finance/cashier/editCashierRepayment',
    method: 'put',
    data: data
  })
}

// 删除出纳确认主档
export function delCashier(id) {
  return request({
    url: '/finance/cashier/' + id,
    method: 'delete'
  })
}
