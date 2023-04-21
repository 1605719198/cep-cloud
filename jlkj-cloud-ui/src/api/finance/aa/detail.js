import request from '@/utils/request'

// 查询系统选单-明细设定列表
export function listDetail(query) {
  return request({
    url: '/finance/detail/list',
    method: 'get',
    params: query
  })
}

// 查询系统选单-明细设定详细
export function getDetail(id) {
  return request({
    url: '/finance/detail/' + id,
    method: 'get'
  })
}

// 新增系统选单-明细设定
export function addDetail(data) {
  return request({
    url: '/finance/detail',
    method: 'post',
    data: data
  })
}

// 修改系统选单-明细设定
export function updateDetail(data) {
  return request({
    url: '/finance/detail',
    method: 'put',
    data: data
  })
}

// 删除系统选单-明细设定
export function delDetail(id) {
  return request({
    url: '/finance/detail/' + id,
    method: 'delete'
  })
}
