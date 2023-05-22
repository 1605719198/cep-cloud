import request from '@/utils/request'
// 删除凭证维护-主
export function delVoucher(data) {
  return request({
    url: '/finance/voucherBatchExecution',
    method: 'delete',
    data: data
  })
}
// 弹窗查询凭证维护-主列表
export function listVoucherBatchExecution(query) {
  return request({
    url: '/finance/voucherBatchExecution/list',
    method: 'get',
    params: query
  })
}
// 首笔查询凭证维护-主列表
export function listHeadVoucher(query) {
  return request({
    url: '/finance/voucherBatchExecution/listHeadVoucher',
    method: 'get',
    params: query
  })
}
// 确认修改凭证维护-主
export function updateVoucherStatus(data) {
  return request({
    url: '/finance/voucherBatchExecution/status',
    method: 'put',
    data: data
  })
}
// 确认修改凭证维护-主
export function updateVoucherCross(data) {
  return request({
    url: '/finance/voucherBatchExecution/cross',
    method: 'put',
    data: data
  })
}
