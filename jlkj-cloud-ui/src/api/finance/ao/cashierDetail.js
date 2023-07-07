import request from '@/utils/request'

// 查询出纳确认明细档列表
export function listCashierDetail(query) {
  return request({
    url: '/finance/cashierDetail/list',
    method: 'get',
    params: query
  })
}

// 查询出纳确认明细档详细
export function getCashierDetail(id) {
  return request({
    url: '/finance/cashierDetail/' + id,
    method: 'get'
  })
}
// 查询出纳确认明细档
export function getLoanADetailCashier(query) {
  return request({
    url: '/finance/cashierDetail/listLoanADetailCashier',
    method: 'get',
    params: query
  })
}
// 新增出纳确认明细档
export function addDetailCashier(data) {
  return request({
    url: '/finance/cashierDetail',
    method: 'post',
    data: data
  })
}

// 修改出纳确认明细档
export function updateDetailCashier(data) {
  return request({
    url: '/finance/cashierDetail',
    method: 'put',
    data: data
  })
}

// 删除出纳确认明细档
export function delCashierDetail(id) {
  return request({
    url: '/finance/cashierDetail/' + id,
    method: 'delete'
  })
}
