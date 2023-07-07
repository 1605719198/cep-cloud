import request from '@/utils/request'

// 查询会计确认主档列表
export function listAccountHost(query) {
  return request({
    url: '/finance/ao/accountHost/list',
    method: 'get',
    params: query
  })
}
// 查询会计确明细列表
export function detailList(query) {
  return request({
    url: '/finance/ao/accountHost/detailList',
    method: 'get',
    params: query
  })
}


// 查询会计确认主档详细
export function getAccountHost(id) {
  return request({
    url: '/finance/ao/accountHost/' + id,
    method: 'get'
  })
}

// 新增会计确认主档
export function addAccountHost(data) {
  return request({
    url: '/finance/ao/accountHost',
    method: 'post',
    data: data
  })
}

// 修改会计确认主档
export function updateAccountHost(data) {
  return request({
    url: '/finance/ao/accountHost',
    method: 'put',
    data: data
  })
}
// 还款修改会计确认主档
export function updateAccountHostRepayment(data) {
  return request({
    url: '/finance/ao/accountHost/editRepayment',
    method: 'put',
    data: data
  })
}
// 删除会计确认主档
export function delAccountHost(id) {
  return request({
    url: '/finance/ao/accountHost/' + id,
    method: 'delete'
  })
}
