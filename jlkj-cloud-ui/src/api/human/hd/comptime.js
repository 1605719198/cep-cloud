import request from '@/utils/request'

// 查询补休记录列表
export function listComptime(query) {
  return request({
    url: '/human/comptime/list',
    method: 'get',
    params: query
  })
}

// 查询补休记录详细
export function getComptime(id) {
  return request({
    url: '/human/comptime/' + id,
    method: 'get'
  })
}

// 新增补休记录
export function addComptime(data) {
  return request({
    url: '/human/comptime',
    method: 'post',
    data: data
  })
}

// 修改补休记录
export function updateComptime(data) {
  return request({
    url: '/human/comptime',
    method: 'put',
    data: data
  })
}

// 删除补休记录
export function delComptime(id) {
  return request({
    url: '/human/comptime/' + id,
    method: 'delete'
  })
}
