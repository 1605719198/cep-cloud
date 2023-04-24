import request from '@/utils/request'

// 查询核算项目-类别维护列表
export function listCalType(query) {
  return request({
    url: '/finance/calType/list',
    method: 'get',
    params: query
  })
}

// 查询核算项目-类别维护详细
export function getCalType(id) {
  return request({
    url: '/finance/calType/' + id,
    method: 'get'
  })
}

// 新增核算项目-类别维护
export function addCalType(data) {
  return request({
    url: '/finance/calType',
    method: 'post',
    data: data
  })
}

// 修改核算项目-类别维护
export function updateCalType(data) {
  return request({
    url: '/finance/calType',
    method: 'put',
    data: data
  })
}

// 删除核算项目-类别维护
export function delCalType(id) {
  return request({
    url: '/finance/calType/' + id,
    method: 'delete'
  })
}

// 查询核算项目类别(下拉选单用)
export function selectCalTypeList() {
  return request({
    url: '/finance/calType/calTypeList',
    method: 'get',
  })
}
