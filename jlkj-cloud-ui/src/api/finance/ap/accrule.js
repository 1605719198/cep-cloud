import request from '@/utils/request'

// 查询付款抛账规则维护列表
export function listAccrule(query) {
  return request({
    url: '/finance/accrule/list',
    method: 'get',
    params: query
  })
}

// 查询付款抛账规则维护详细
export function getAccrule(id) {
  return request({
    url: '/finance/accrule/' + id,
    method: 'get'
  })
}

// 新增付款抛账规则维护
export function addAccrule(data) {
  return request({
    url: '/finance/accrule',
    method: 'post',
    data: data
  })
}

// 修改付款抛账规则维护
export function updateAccrule(data) {
  return request({
    url: '/finance/accrule',
    method: 'put',
    data: data
  })
}

// 删除付款抛账规则维护
export function delAccrule(id) {
  return request({
    url: '/finance/accrule/' + id,
    method: 'delete'
  })
}
