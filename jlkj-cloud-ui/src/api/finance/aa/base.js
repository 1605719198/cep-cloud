import request from '@/utils/request'

// 查询系统选单-基本设定列表
export function listBase(query) {
  return request({
    url: '/finance/base/list',
    method: 'get',
    params: query
  })
}
// 查询系统选单-基本设定列表
export function listBaseTab(query) {
  return request({
    url: '/finance/base/listTab',
    method: 'get',
    params: query
  })
}


// 查询系统选单-基本设定详细
export function getBase(id) {
  return request({
    url: '/finance/base/' + id,
    method: 'get'
  })
}

// 新增系统选单-基本设定
export function addBase(data) {
  return request({
    url: '/finance/base',
    method: 'post',
    data: data
  })
}

// 修改系统选单-基本设定
export function updateBase(data) {
  return request({
    url: '/finance/base',
    method: 'put',
    data: data
  })
}

// 删除系统选单-基本设定
export function delBase(leafId) {
  return request({
    url: '/finance/base/' + leafId,
    method: 'delete'
  })
}
