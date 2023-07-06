import request from '@/utils/request'

// 查询还款单资料档列表
export function listRepayment(query) {
  return request({
    url: '/finance/repayment/list',
    method: 'get',
    params: query
  })
}

// 查询还款单资料档详细
export function getRepayment(id) {
  return request({
    url: '/finance/repayment/' + id,
    method: 'get'
  })
}

// 新增还款单资料档
export function addRepayment(data) {
  return request({
    url: '/finance/repayment',
    method: 'post',
    data: data
  })
}

// 修改还款单资料档
export function updateRepayment(data) {
  return request({
    url: '/finance/repayment',
    method: 'put',
    data: data
  })
}

// 删除还款单资料档
export function delRepayment(id) {
  return request({
    url: '/finance/repayment/' + id,
    method: 'delete'
  })
}


// 查询借支申请单
export function selectLoanApply(companyId) {
  return request({
    url: '/finance/repayment/loanApply/' + companyId,
    method: 'get'
  })
}

// 批量新增还款单资料档
export function addRepaymentBatch(data) {
  return request({
    url: '/finance/repayment/batch',
    method: 'post',
    data: data
  })
}
