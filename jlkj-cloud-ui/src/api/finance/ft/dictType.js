import request from '@/utils/request'

// 查询选单设定列表
export function listDictType(query) {
  return request({
    url: '/finance/dictType/list',
    method: 'get',
    params: query
  })
}

// 查询选单设定详细
export function getDictType(dictId) {
  return request({
    url: '/finance/dictType/' + dictId,
    method: 'get'
  })
}

// 新增选单设定
export function addDictType(data) {
  return request({
    url: '/finance/dictType',
    method: 'post',
    data: data
  })
}

// 修改选单设定
export function updateDictType(data) {
  return request({
    url: '/finance/dictType',
    method: 'put',
    data: data
  })
}

// 删除选单设定
export function delDictType(dictId) {
  return request({
    url: '/finance/dictType/' + dictId,
    method: 'delete'
  })
}
