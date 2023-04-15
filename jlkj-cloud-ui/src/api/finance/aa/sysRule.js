import request from '@/utils/request'

// 查询核算项目-系统设定列表
export function listSysRule(query) {
  return request({
    url: '/finance/sysRule/list',
    method: 'get',
    params: query
  })
}

// 查询核算项目-系统设定详细
export function getSysRule(id) {
  return request({
    url: '/finance/sysRule/' + id,
    method: 'get'
  })
}

// 新增核算项目-系统设定
export function addSysRule(data) {
  return request({
    url: '/finance/sysRule',
    method: 'post',
    data: data
  })
}

// 修改核算项目-系统设定
export function updateSysRule(data) {
  return request({
    url: '/finance/sysRule',
    method: 'put',
    data: data
  })
}

// 删除核算项目-系统设定
export function delSysRule(id) {
  return request({
    url: '/finance/sysRule/' + id,
    method: 'delete'
  })
}
