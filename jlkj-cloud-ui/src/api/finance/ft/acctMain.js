import request from '@/utils/request'

// 查询账务代码列表
export function listAcctMain(query) {
  return request({
    url: '/finance/acctMain/list',
    method: 'get',
    params: query
  })
}

// 查询账务代码详细
export function getAcctMain(uuid) {
  return request({
    url: '/finance/acctMain/' + uuid,
    method: 'get'
  })
}

// 新增账务代码
export function addAcctMain(data) {
  return request({
    url: '/finance/acctMain',
    method: 'post',
    data: data
  })
}

// 修改账务代码
export function updateAcctMain(data) {
  return request({
    url: '/finance/acctMain',
    method: 'put',
    data: data
  })
}

// 删除账务代码
export function delAcctMain(uuid) {
  return request({
    url: '/finance/acctMain/' + uuid,
    method: 'delete'
  })
}
