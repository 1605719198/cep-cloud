import request from '@/utils/request'

// 查询资产增加单列表
export function listAssetAdd(query) {
  return request({
    url: '/finance/assetAdd/list',
    method: 'get',
    params: query
  })
}

// 查询资产增加单详细
export function getAssetAdd(uuid) {
  return request({
    url: '/finance/assetAdd/' + uuid,
    method: 'get'
  })
}

// 新增资产增加单
export function addAssetAdd(data) {
  return request({
    url: '/finance/assetAdd',
    method: 'post',
    data: data
  })
}

// 修改资产增加单
export function updateAssetAdd(data) {
  return request({
    url: '/finance/assetAdd',
    method: 'put',
    data: data
  })
}

// 删除资产增加单
export function delAssetAdd(uuid) {
  return request({
    url: '/finance/assetAdd/' + uuid,
    method: 'delete'
  })
}
