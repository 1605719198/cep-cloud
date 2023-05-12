import request from '@/utils/request'

// 查询资产大类维护列表
export function listAssetCategory(query) {
  return request({
    url: '/finance/assetCategory/list',
    method: 'get',
    params: query
  })
}

// 查询资产大类维护详细
export function getAssetCategory(id) {
  return request({
    url: '/finance/assetCategory/' + id,
    method: 'get'
  })
}

// 新增资产大类维护
export function addAssetCategory(data) {
  return request({
    url: '/finance/assetCategory',
    method: 'post',
    data: data
  })
}

// 修改资产大类维护
export function updateAssetCategory(data) {
  return request({
    url: '/finance/assetCategory',
    method: 'put',
    data: data
  })
}

// 删除资产大类维护
export function delAssetCategory(id) {
  return request({
    url: '/finance/assetCategory/' + id,
    method: 'delete'
  })
}
