import request from '@/utils/request'

// 查询资产变动单主档列表
export function listOrgValueAdd(query) {
  return request({
    url: '/finance/ft/orgValueAdd/list',
    method: 'get',
    params: query
  })
}

// 查询资产变动单主档详细
export function getOrgValueAdd(uuid) {
  return request({
    url: '/finance/ft/orgValueAdd/' + uuid,
    method: 'get'
  })
}

// 新增资产变动单主档
export function addOrgValueAdd(data) {
  return request({
    url: '/finance/ft/orgValueAdd',
    method: 'post',
    data: data
  })
}

// 修改资产变动单主档
export function updateOrgValueAdd(data) {
  return request({
    url: '/finance/ft/orgValueAdd',
    method: 'put',
    data: data
  })
}

// 删除资产变动单主档
export function delOrgValueAdd(uuid) {
  return request({
    url: '/finance/ft/orgValueAdd/' + uuid,
    method: 'delete'
  })
}
