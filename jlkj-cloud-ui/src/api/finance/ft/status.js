import request from '@/utils/request'

// 查询资产使用状态列表
export function listStatus(query) {
  return request({
    url: '/finance/status/list',
    method: 'get',
    params: query
  })
}

// 查询资产使用状态详细
export function getStatus(uuid) {
  return request({
    url: '/finance/status/' + uuid,
    method: 'get'
  })
}

// 新增资产使用状态
export function addStatus(data) {
  return request({
    url: '/finance/status',
    method: 'post',
    data: data
  })
}

// 修改资产使用状态
export function updateStatus(data) {
  return request({
    url: '/finance/status',
    method: 'put',
    data: data
  })
}

// 删除资产使用状态
export function delStatus(uuid) {
  return request({
    url: '/finance/status/' + uuid,
    method: 'delete'
  })
}
