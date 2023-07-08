import request from '@/utils/request'

// 查询薪资应付作业列表
export function listPayAmt(query) {
  return request({
    url: '/human/payAmt/list',
    method: 'get',
    params: query
  })
}

// 查询薪资应付作业详细
export function getPayAmt(id) {
  return request({
    url: '/human/payAmt/' + id,
    method: 'get'
  })
}

// 新增薪资应付作业
export function addPayAmt(data) {
  return request({
    url: '/human/payAmt',
    method: 'post',
    data: data
  })
}

// 修改薪资应付作业
export function updatePayAmt(data) {
  return request({
    url: '/human/payAmt',
    method: 'put',
    data: data
  })
}

// 删除薪资应付作业
export function delPayAmt(id) {
  return request({
    url: '/human/payAmt/' + id,
    method: 'delete'
  })
}

/**
 * 付款抛帐
 * @param id
 * @returns {}
 */
export function sendAAPayAmt(id) {
  debugger
  return request({
    url: '/human/payAmt/sendAA/' + id,
    method: 'post'
  })
}
// 查询薪资应付作业列表
export function getListPayAmtDetail(data) {
  return request({
    url: '/human/payAmt/detaillist',
    method: 'post',
    data: data
  })
}

