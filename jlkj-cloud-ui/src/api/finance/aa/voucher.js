import request from '@/utils/request'

// 查询凭证维护-主列表
export function listVoucher(query) {
  return request({
    url: '/finance/voucher/listVoucher',
    method: 'get',
    params: query
  })
}
// 更多条件查询凭证维护-主列表
export function listVoucherDetailSelect(query) {
  return request({
    url: '/finance/voucher/listVoucherDetailSelect',
    method: 'get',
    params: query
  })
}
// 更多条件查询凭证维护-主列表
export function listVoucherLinkSelect(query) {
  return request({
    url: '/finance/voucher/listVoucherLinkSelect',
    method: 'get',
    params: query
  })
}
// 查询前笔凭证维护-主列表
export function listFrontVoucher(query) {
  return request({
    url: '/finance/voucher/listFrontVoucher',
    method: 'get',
    params: query
  })
}
// 查询首笔笔凭证维护-主列表
export function listOrderVoucher(query) {
  return request({
    url: '/finance/voucher/listOrderVoucher',
    method: 'get',
    params: query
  })
}
// 查询末笔笔凭证维护-主列表
export function listLastVoucher(query) {
  return request({
    url: '/finance/voucher/listLastVoucher',
    method: 'get',
    params: query
  })
}
// 弹窗查询凭证维护-主列表
export function listVoucherSelect(query) {
  return request({
    url: '/finance/voucher/list',
    method: 'get',
    params: query
  })
}
// 首笔查询凭证维护-主列表
export function listHeadVoucher(query) {
  return request({
    url: '/finance/voucher/listHeadVoucher',
    method: 'get',
    params: query
  })
}
// 查询凭证维护-主详细
export function getVoucher(id) {
  return request({
    url: '/finance/voucher/' + id,
    method: 'get'
  })
}

// 新增凭证维护-主
export function addVoucher(data) {
  return request({
    url: '/finance/voucher/addVoucher',
    method: 'post',
    data: data
  })
}
//红冲凭证维护-主
export function addHongChongVoucher(data) {
  return request({
    url: '/finance/voucher/addHongChongVoucher',
    method: 'post',
    data: data
  })
}

// 修改凭证维护-主
export function updateVoucher(data) {
  return request({
    url: '/finance/voucher',
    method: 'put',
    data: data
  })
}

// 确认修改凭证维护-主
export function updateVoucherStatus(data) {
  return request({
    url: '/finance/voucher/status',
    method: 'put',
    data: data
  })
}
// 确认修改凭证维护-主
export function updateVoucherCross(data) {
  return request({
    url: '/finance/voucher/cross',
    method: 'put',
    data: data
  })
}
// 删除凭证维护-主
export function delVoucher(data) {
  return request({
    url: '/finance/voucher',
    method: 'delete',
    data: data
  })
}

