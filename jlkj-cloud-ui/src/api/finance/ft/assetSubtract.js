import request from '@/utils/request'

// 查询资产减少单列表
export function listAssetSubtract(query) {
  return request({
    url: '/finance/assetSubtract/list',
    method: 'get',
    params: query
  })
}

// 查询资产减少单详细
export function getAssetSubtract(uuid) {
  return request({
    url: '/finance/assetSubtract/' + uuid,
    method: 'get'
  })
}

// 新增资产减少单
export function addAssetSubtract(data) {
  return request({
    url: '/finance/assetSubtract',
    method: 'post',
    data: data
  })
}

// 修改资产减少单
export function updateAssetSubtract(data) {
  return request({
    url: '/finance/assetSubtract',
    method: 'put',
    data: data
  })
}

// 删除资产减少单
export function delAssetSubtract(uuid) {
  return request({
    url: '/finance/assetSubtract/' + uuid,
    method: 'delete'
  })
}
