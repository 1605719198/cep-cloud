import request from '@/utils/request'

// 查询厂商增户申请列表
export function listAdd(query) {
  return request({
    url: '/finance/finance/add/list',
    method: 'get',
    params: query
  })
}

// 查询厂商增户申请详细
export function getAdd(applyId) {
  return request({
    url: '/finance/finance/add/' + applyId,
    method: 'get'
  })
}

// 新增厂商增户申请
export function addAdd(data) {
  return request({
    url: '/finance/finance/add',
    method: 'post',
    data: data
  })
}

// 修改厂商增户申请
export function updateAdd(data) {
  return request({
    url: '/finance/finance/add',
    method: 'put',
    data: data
  })
}

// 删除厂商增户申请
export function delAdd(applyId) {
  return request({
    url: '/finance/finance/add/' + applyId,
    method: 'delete'
  })
}
