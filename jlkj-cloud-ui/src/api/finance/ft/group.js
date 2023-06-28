import request from '@/utils/request'

// 查询资产大类基本资料列表
export function listGroup(query) {
  return request({
    url: '/finance/group/list',
    method: 'get',
    params: query
  })
}

// 查询资产大类基本资料详细
export function getGroup(id) {
  return request({
    url: '/finance/group/' + id,
    method: 'get'
  })
}

// 新增资产大类基本资料
export function addGroup(data) {
  return request({
    url: '/finance/group',
    method: 'post',
    data: data
  })
}

// 修改资产大类基本资料
export function updateGroup(data) {
  return request({
    url: '/finance/group',
    method: 'put',
    data: data
  })
}

// 删除资产大类基本资料
export function delGroup(id) {
  return request({
    url: '/finance/group/' + id,
    method: 'delete'
  })
}
