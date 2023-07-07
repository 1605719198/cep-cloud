import request from '@/utils/request'

// 查询费用报销主档列表
export function listReimbursement(query) {
  return request({
    url: '/finance/reimbursement/list',
    method: 'get',
    params: query
  })
}
// 查询费用报销主档详细
export function getReimbursementFrom(id) {
  return request({
    url: '/finance/reimbursement/' + id,
    method: 'get'
  })
}
// 新增费用报销主档
export function addReimbursement(data) {
  return request({
    url: '/finance/reimbursement',
    method: 'post',
    data: data
  })
}

// 修改费用报销主档
export function updateReimbursement(data) {
  return request({
    url: '/finance/reimbursement',
    method: 'put',
    data: data
  })
}

// 删除费用报销主档
export function delReimbursement(id) {
  return request({
    url: '/finance/reimbursement/' + id,
    method: 'delete'
  })
}
