import request from '@/utils/request'

// 查询凭证类别维护列表
export function listVoucherType(query) {
  return request({
    url: '/finance/voucherType/list',
    method: 'get',
    params: query
  })
}

// 查询凭证类别维护详细
export function getVoucherType(id) {
  return request({
    url: '/finance/voucherType/' + id,
    method: 'get'
  })
}

// 新增凭证类别维护
export function addVoucherType(data) {
  return request({
    url: '/finance/voucherType',
    method: 'post',
    data: data
  })
}

// 修改凭证类别维护
export function updateVoucherType(data) {
  return request({
    url: '/finance/voucherType',
    method: 'put',
    data: data
  })
}

// 删除凭证类别维护
export function delVoucherType(id) {
  return request({
    url: '/finance/voucherType/' + id,
    method: 'delete'
  })
}
