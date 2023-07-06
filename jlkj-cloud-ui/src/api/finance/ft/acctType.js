import request from '@/utils/request'

// 查询账务代码设定列表
export function listAcctType(query) {
  return request({
    url: '/finance/acctType/list',
    method: 'get',
    params: query
  })
}

// 查询账务代码设定详细
export function getAcctType(uuid) {
  return request({
    url: '/finance/acctType/' + uuid,
    method: 'get'
  })
}

// 新增账务代码设定
export function addAcctType(data) {
  return request({
    url: '/finance/acctType',
    method: 'post',
    data: data
  })
}

// 修改账务代码设定
export function updateAcctType(data) {
  return request({
    url: '/finance/acctType',
    method: 'put',
    data: data
  })
}

// 删除账务代码设定
export function delAcctType(uuid) {
  return request({
    url: '/finance/acctType/' + uuid,
    method: 'delete'
  })
}
