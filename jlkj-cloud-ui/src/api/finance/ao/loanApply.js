import request from '@/utils/request'

// 查询借支申请主档列表
export function listLoanApply(query) {
  return request({
    url: '/finance/loanApply/list',
    method: 'get',
    params: query
  })
}
// 查询会计处理借支申请
export function loanList (query) {
  return request({
    url: '/finance/loanApply/loanList',
    method: 'get',
    params: query
  })
}
// 查询借支申请主档详细
export function getLoanApply(id) {
  return request({
    url: '/finance/loanApply/' + id,
    method: 'get'
  })
}
// 查询借支申请主档详细
export function getLoanApplyFrom(id) {
  return request({
    url: `/finance/loanApply/loanApplyFrom/` + id,
    method: 'get'
  })
}
// 新增借支申请主档
export function addLoanApply(data) {
  return request({
    url: '/finance/loanApply',
    method: 'post',
    data: data
  })
}

// 修改借支申请主档
export function updateLoanApply(data) {
  return request({
    url: '/finance/loanApply',
    method: 'put',
    data: data
  })
}

// 删除借支申请主档
export function delLoanApply(id) {
  return request({
    url: '/finance/loanApply/' + id,
    method: 'delete'
  })
}
