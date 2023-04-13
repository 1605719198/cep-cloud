import request from '@/utils/request'

// 查询厂商异动申请列表
export function listChange(query) {
  return request({
    url: '/finance/finance/change/list',
    method: 'get',
    params: query
  })
}

// 查询厂商异动申请详细
export function getChange(applyId) {
  return request({
    url: '/finance/finance/change/' + applyId,
    method: 'get'
  })
}

// 新增厂商异动申请
export function addChange(data) {
  return request({
    url: '/finance/finance/change',
    method: 'post',
    data: data
  })
}

// 修改厂商异动申请
export function updateChange(data) {
  return request({
    url: '/finance/finance/change',
    method: 'put',
    data: data
  })
}

// 删除厂商异动申请
export function delChange(applyId) {
  return request({
    url: '/finance/finance/change/' + applyId,
    method: 'delete'
  })
}
