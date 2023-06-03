import request from '@/utils/request'

// 查询报支类别集团细项列表
export function listDetail(query) {
  return request({
    url: '/finance/apDetail/list',
    method: 'get',
    params: query
  })
}

// 查询报支类别集团细项详细
export function getDetail(id) {
  return request({
    url: '/finance/apDetail/' + id,
    method: 'get'
  })
}

// 新增报支类别集团细项
export function addDetail(data) {
  return request({
    url: '/finance/apDetail',
    method: 'post',
    data: data
  })
}

// 修改报支类别集团细项
export function updateDetail(data) {
  return request({
    url: '/finance/apDetail',
    method: 'put',
    data: data
  })
}

// 删除报支类别集团细项
export function delDetail(id) {
  return request({
    url: '/finance/apDetail/' + id,
    method: 'delete'
  })
}
