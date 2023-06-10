import request from '@/utils/request'

// 查询往来银行列表
export function listAccount(query) {
  return request({
    url: '/finance/account/list',
    method: 'get',
    params: query
  })
}

// 查询往来银行详细
export function getAccount(id) {
  return request({
    url: '/finance/account/' + id,
    method: 'get'
  })
}

// 新增往来银行
export function addAccount(data) {
  return request({
    url: '/finance/account',
    method: 'post',
    data: data
  })
}

// 修改往来银行
export function updateAccount(data) {
  return request({
    url: '/finance/account',
    method: 'put',
    data: data
  })
}

// 删除往来银行
export function delAccount(id) {
  return request({
    url: '/finance/account/' + id,
    method: 'delete'
  })
}

// 银行状态修改
export function changeBankAccount(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/finance/account/changeStatus',
    method: 'put',
    data: data
  })
}
