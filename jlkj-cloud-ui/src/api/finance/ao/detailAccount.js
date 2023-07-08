import request from '@/utils/request'

// 查询会计处理明细列表
export function listDetailAccount(query) {
  return request({
    url: '/finance/detailAccount/list',
    method: 'get',
    params: query
  })
}

// 查询会计处理明细详细
export function getDetailAccount(id) {
  return request({
    url: '/finance/detailAccount/' + id,
    method: 'get'
  })
}
// 查询会计处理明细列表
export function getLoanADetailAccount(query) {
  return request({
    url: '/finance/detailAccount/listLoanADetailAccount',
    method: 'get',
    params: query
  })
}
// 新增会计处理明细
export function addDetailAccount(data) {
  return request({
    url: '/finance/detailAccount',
    method: 'post',
    data: data
  })
}

// 修改会计处理明细
export function updateDetailAccount(data) {
  return request({
    url: '/finance/detailAccount',
    method: 'put',
    data: data
  })
}

// 删除会计处理明细
export function delDetailAccount(id) {
  return request({
    url: '/finance/detailAccount/' + id,
    method: 'delete'
  })
}
