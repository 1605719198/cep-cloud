import request from '@/utils/request'

// 查询资产变动单主档列表
export function listOrgValueSubtract(query) {
  return request({
    url: '/finance/ft/orgValueSubtract/list',
    method: 'get',
    params: query
  })
}

// 查询资产变动单主档详细
export function getOrgValueSubtract(uuid) {
  return request({
    url: '/finance/ft/orgValueSubtract/' + uuid,
    method: 'get'
  })
}

// 新增资产变动单主档
export function addOrgValueSubtract(data) {
  return request({
    url: '/finance/ft/orgValueSubtract',
    method: 'post',
    data: data
  })
}

// 修改资产变动单主档
export function updateOrgValueSubtract(data) {
  return request({
    url: '/finance/ft/orgValueSubtract',
    method: 'put',
    data: data
  })
}

// 删除资产变动单主档
export function delOrgValueSubtract(uuid) {
  return request({
    url: '/finance/ft/orgValueSubtract/' + uuid,
    method: 'delete'
  })
}

