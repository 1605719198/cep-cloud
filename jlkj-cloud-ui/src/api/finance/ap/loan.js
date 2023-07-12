import request from '@/utils/request'

// 查询预付申请主档列表
export function listLoan(query) {
  return request({
    url: '/finance/ap/loan/list',
    method: 'get',
    params: query
  })
}

// 查询预付申请主档详细
export function getLoan(id) {
  return request({
    url: '/finance/ap/loan/' + id,
    method: 'get'
  })
}

// 新增预付申请主档
export function addLoan(data) {
  return request({
    url: '/finance/ap/loan',
    method: 'post',
    data: data
  })
}

// 修改预付申请主档
export function updateLoan(data) {
  return request({
    url: '/finance/ap/loan',
    method: 'put',
    data: data
  })
}

// 删除预付申请主档
export function delLoan(id) {
  return request({
    url: '/finance/ap/loan/' + id,
    method: 'delete'
  })
}
