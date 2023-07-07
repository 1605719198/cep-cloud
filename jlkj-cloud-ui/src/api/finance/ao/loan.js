import request from '@/utils/request'

// 查询报支管理-费用报销冲借支单档列表
export function listLoan(query) {
  return request({
    url: '/finance/loan/list',
    method: 'get',
    params: query
  })
}
// 查询报支管理-费用报销冲借支单档列表
export function getFormLoan(query) {
  return request({
    url: '/finance/loan/listLoan',
    method: 'get',
    params: query
  })
}

// 查询报支管理-费用报销冲借支单档详细
export function getLoan(id) {
  return request({
    url: '/finance/loan/' + id,
    method: 'get'
  })
}

// 新增报支管理-费用报销冲借支单档
export function addLoan(data) {
  return request({
    url: '/finance/loan',
    method: 'post',
    data: data
  })
}

// 修改报支管理-费用报销冲借支单档
export function updateLoan(data) {
  return request({
    url: '/finance/loan',
    method: 'put',
    data: data
  })
}

// 删除报支管理-费用报销冲借支单档
export function delLoan(id) {
  return request({
    url: '/finance/loan/' + id,
    method: 'delete'
  })
}
