import request from '@/utils/request'

// 查询初始化记录列表
export function listInit(query) {
  return request({
    url: '/finance/init/list',
    method: 'get',
    params: query
  })
}

// 查询初始化记录详细
export function getInit(id) {
  return request({
    url: '/finance/init/' + id,
    method: 'get'
  })
}

// 新增初始化记录
export function addInit(data) {
  return request({
    url: '/finance/init',
    method: 'post',
    data: data
  })
}

// 修改初始化记录
export function updateInit(data) {
  return request({
    url: '/finance/init',
    method: 'put',
    data: data
  })
}

// 删除初始化记录
export function delInit(id) {
  return request({
    url: '/finance/init/' + id,
    method: 'delete'
  })
}
