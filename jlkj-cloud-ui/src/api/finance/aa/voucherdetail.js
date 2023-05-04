import request from '@/utils/request'

// 查询凭证维护-明细列表
export function listDetail(query) {
  return request({
    url: '/finance/aa/detail/list',
    method: 'get',
    params: query
  })
}
// 查询凭证维护-明细列表
export function listVoucherSelect(query) {
  return request({
    url: '/finance/aa/detail/listVoucherSelect',
    method: 'get',
    params: query
  })
}
// 查询前笔凭证维护-明细列表
export function listFrontDetail(query) {
  return request({
    url: '/finance/aa/detail/listFrontDetail',
    method: 'get',
    params: query
  })
}


// 查询凭证维护-明细详细
export function getDetail(id) {
  return request({
    url: '/finance/aa/detail/' + id,
    method: 'get'
  })
}

// 新增凭证维护-明细
export function addDetail(data) {
  return request({
    url: '/finance/aa/detail',
    method: 'post',
    data: data
  })
}

// 修改凭证维护-明细
export function updateDetail(data) {
  return request({
    url: '/finance/aa/detail',
    method: 'put',
    data: data
  })
}

// 删除凭证维护-明细
export function delDetail(id) {
  return request({
    url: '/finance/aa/detail/' + id,
    method: 'delete'
  })
}
